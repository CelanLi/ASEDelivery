package ASE.deliverySpring.controller;




import ASE.deliverySpring.base.BaseController;
import ASE.deliverySpring.entity.BoxInfo;
import ASE.deliverySpring.service.BoxService_hardware;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class BoxController_hardware extends BaseController {



    @CrossOrigin
    @RequestMapping(value="/idAuthen")
    public boolean idAuthen(String boxSerial,String role,String rfid){
        try{

            BoxInfo boxInfo=new BoxInfo(boxSerial,rfid,role);

            return boxServiceHardware.verify(boxInfo);

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }
    @CrossOrigin
    @RequestMapping(value="/statusUpdate")
    public String statusUpdate(BoxInfo boxInfo){

        try{

            if(boxServiceHardware.statusChange(boxInfo)){
                if(boxInfo.getRole().equals("deliverer"))
                    return "The package is placed successfully.";
                else if(boxInfo.getRole().equals("customer")){
                    return "The package is picked up successfully.";
                }
            }
            else {
                return "unauthorized status";
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        return "An internal exception of the server, please contact the administrator";
    }

}