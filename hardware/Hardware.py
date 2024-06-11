from time import sleep
import RPi.GPIO as GPIO
from mfrc522 import SimpleMFRC522
import time
import json
import requests
from requests import Session
# define reader
reader = SimpleMFRC522()
hostname = 'localhost'
port = 8089
hostUrl = "http://" + "172.20.10.4" + ":" + str(port)

#session
session = requests.Session()

# define pins
green_pin=35
red_pin=37
rfid_reader=11

# define the encode method
GPIO.setmode(GPIO.BOARD)
GPIO.setwarnings(False)

# setup
GPIO.setup(green_pin,GPIO.OUT,initial=GPIO.LOW)
GPIO.setup(red_pin,GPIO.OUT,initial=GPIO.LOW)
# set pull_up_down for rfid reader
GPIO.setup(rfid_reader,GPIO.IN, pull_up_down=GPIO.PUD_UP)
# set pull_up_down for light sensor
GPIO.setup(8, GPIO.IN, pull_up_down=GPIO.PUD_UP)

# color of the light
#light_led(green_pin)，light_led(red_pin)
def light_led(pin,sec=3):
    GPIO.output(pin,GPIO.HIGH)
    sleep(sec)
    GPIO.output(pin,GPIO.LOW)

# get the status of light sensor: if the light sensor is on, return True, else return False
def lightsensor():
    return GPIO.input(8)

try:
    # get box id, name, address from config file
    b = open("box_config.json")
    box_config = json.load(b)

    while True:
        role = input("Are you dispatcher(1), deliverer(2) or customer(3)?")
        print("Hold a tag near the reader")
        id, user_obj = reader.read()
        if user_obj == None:
            print("reading failed")
        else:
            print("Card is read successfully.")
        
        try:
            if role == "1" :
                new_text = input("Please input the information:")
                reader.write(new_text)
                id, user_obj = reader.read()
                print("Information was written successfully:"+user_obj)
                print("You can remove your tag now.")
                sleep(3)
            elif role == "2" or role =="3":
                # if authenticator(json.loads(user_obj)["id"]):
                #     light_led(green_pin)
                # else:
                #     light_led(red_pin)
                # read the role from the card
                user_obj = json.loads(user_obj)
                print("Your role is:"+user_obj["role"])
                # authenticate the user
                urlIdAuthen=hostUrl+"/idAuthen"
                data={"boxSerial" : box_config["box_id"],"role":user_obj["role"],"rfid":user_obj["uId"]}
                r = session.post(urlIdAuthen, data)
                # authenticator(user_obj)
                string1 = str(r.content, encoding = 'utf-8')
                flag1 = string1 == "true"
                
                
                if flag1:
                    print("Please open the box.")
                    light_led(green_pin,5)

                    while True:
                        a = 0
                        isopen=False
                        while lightsensor() == False:
#                           print("box is opened")
                            isopen=True
                            if a<10:
                                if a==0:print("Box is opened, please close the box is 10 seconds.")
                                time.sleep(1)
                                a = a + 1
                                print(a)
                            else:
                                light_led(red_pin, 1)
                                time.sleep(1) 
                                print("Box is not locked properly. Please check it.")
                        if isopen:
                            urlStatusUpdate = hostUrl + "/statusUpdate"
                            r = session.post(urlStatusUpdate, data)
                            print(r.content)
                            break


                else:
                    light_led(red_pin)
                    print("Unauthorized.")
            else:
                print("Role unmatched, please input your role number again.")

        except ValueError:
            light_led(red_pin,5)

except KeyboardInterrupt:
    GPIO.cleanup()
    raise

