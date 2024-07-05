package ASE.deliverySpring.controller;

import ASE.deliverySpring.base.BaseController;
import ASE.deliverySpring.entity.BoxInfo;
import ASE.deliverySpring.service.BoxService_hardware;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import javax.annotation.Resource;

@RestController
public class BoxController_hardware extends BaseController {

    // verify the box
    @CrossOrigin
    @RequestMapping(value = "/idAuthen")
    public boolean idAuthen(@RequestBody Map<String, String> payload) {
        // using the frontend request to simulate the hardware
        BoxInfo boxInfo = new BoxInfo(payload.get("boxSerial"), payload.get("rfid"), payload.get("role"));
        return boxServiceHardware.verify(boxInfo);
    }

    @CrossOrigin
    @RequestMapping(value = "/statusUpdate")
    public String statusUpdate(@RequestBody BoxInfo boxInfo) {
        if (boxServiceHardware.statusChange(boxInfo)) {
            if (boxInfo.getRole().equals("deliverer"))
                return "The package is placed successfully.";
            else if (boxInfo.getRole().equals("customer")) {
                return "The package is picked up successfully.";
            }
        } else {
            return "unauthorized status";
        }
        return "An internal exception of the server, please contact the administrator";
    }
}