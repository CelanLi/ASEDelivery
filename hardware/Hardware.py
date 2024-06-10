from time import sleep
import RPi.GPIO as GPIO
from mfrc522 import SimpleMFRC522
import time
import json
import requests
from requests import Session
#定义读卡器
reader = SimpleMFRC522()
hostname = 'localhost'
port = 8089
hostUrl = "http://" + "172.20.10.4" + ":" + str(port)

#session
session = requests.Session()

#定义灯和读卡器接触的引脚
green_pin=35
red_pin=37
rfid_reader=11

#定义引脚编号的方式
GPIO.setmode(GPIO.BOARD)
GPIO.setwarnings(False)

#setup，初始化引脚
GPIO.setup(green_pin,GPIO.OUT,initial=GPIO.LOW)
GPIO.setup(red_pin,GPIO.OUT,initial=GPIO.LOW)
#为读卡器设置上拉电阻
GPIO.setup(rfid_reader,GPIO.IN, pull_up_down=GPIO.PUD_UP)
#为光敏电阻设置上拉电阻
GPIO.setup(8, GPIO.IN, pull_up_down=GPIO.PUD_UP)

#控制灯的颜色
#light_led(green_pin)为亮绿灯，light_led(red_pin)为亮红灯
def light_led(pin,sec=3):
    GPIO.output(pin,GPIO.HIGH)
    sleep(sec)
    GPIO.output(pin,GPIO.LOW)

#光敏电阻状态检测：开箱时返回1，未开箱返回0
def lightsensor():
    return GPIO.input(8)

# #读卡器认证：认证成功返回1，认证失败返回0
# def authenticator(user_obj):
#     #读取本地json文件
#     f = open("user_ids.json")
#     credentials = json.load(f)
#     print("authenticate function executes")
# 
#     #在json文件里寻找匹配的user_id
#     for item in credentials:
#         if item["id"] == json.loads(user_obj)["id"]:
#             #寻找到匹配的user_id则返回true，未找到返回false
#             print("found match:")
#             print(item["id"])
#             return True
#     return False

try:
    #读取箱子配置文件，获取箱子id、name、address
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
                # 读卡
                user_obj = json.loads(user_obj)
                print("Your role is:"+user_obj["role"])
                # 验证，需要上传后端验证
                urlIdAuthen=hostUrl+"/idAuthen"
                data={"boxSerial" : box_config["box_id"],"role":user_obj["role"],"rfid":user_obj["uId"]}
                r = session.post(urlIdAuthen, data)
                # authenticator(user_obj)
                string1 = str(r.content, encoding = 'utf-8')
                flag1 = string1 == "true"
                
                
                if flag1:
                    print("Please open the box.")
                    light_led(green_pin,5)
                    #一直判断，盒子是不是打开的状态
                    #True:盒子关闭 False：盒子打开
                        #盒子一旦打开，等待10秒，然后开始一直判断，盒子是不是关上的状态
                        # if lightsensor()==True:
                            # time.sleep(10)

                    while True:
                        a = 0
                        isopen=False
                        #time.sleep(10)
                        #if lightsensor() == True:
                            #print("Time is out!")
                            #break
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

                            # light_led(red_pin,1)
                        #关上盒子后，灯永久地灭掉
                        #sleep(10)

                else:
                    light_led(red_pin)
                    print("Unauthorized.")
            else:
                print("Role unmatched, please input your role number again.")

        except ValueError:
            light_led(red_pin,5)
        #读卡

except KeyboardInterrupt:
    GPIO.cleanup()
    raise

