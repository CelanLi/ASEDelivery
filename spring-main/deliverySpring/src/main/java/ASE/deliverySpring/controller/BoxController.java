package ASE.deliverySpring.controller;

import ASE.deliverySpring.base.BaseController;
import ASE.deliverySpring.base.BaseMessage;
import ASE.deliverySpring.entity.Box;
import ASE.deliverySpring.entity.Order;
import ASE.deliverySpring.entity.UserAccount;
import ASE.deliverySpring.utils.DataUtil;
import ASE.deliverySpring.utils.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/api/box")
public class BoxController extends BaseController {

    /**
     * get all boxes
     * 
     * @return /
     */
    @RequestMapping("/find/all")
    public Result findAll() {
        List<Box> boxs = boxService.findAll();
        return Result.success("successful query", boxs);
    }

    /**
     * save box
     * 
     * @param box
     * @return
     */
    @RequestMapping("/publish")
    public Result publish(@RequestBody Box box) {
        box.setStatus("free");
        if (boxService.findBySerial(box.getSerial()) != null) {
            return Result.error("This serial already exists!");
        } else {
            return boxService.save(box) ? Result.success("successful creation") : Result.error("failed creation");
        }
    }

    @RequestMapping("/update/box")
    public Result update(@RequestBody Box box) {
        boxService.update(box);
        return boxService.update(box) ? Result.success("successful update") : Result.error("failed update");
    }

    /**
     * delete box
     * 
     * @param requestBox serial of box
     * @return
     */
    @RequestMapping("/remove")
    public Result remove(@RequestBody Box requestBox) {
        Box box = boxService.findBySerial(requestBox.getSerial());
        if (box == null) {
            return Result.error("the box does not exists");
        }
        return boxService.remove(requestBox.getSerial()) ? Result.success("delete successfully")
                : Result.error("failed delete");
    }

    /**
     * get all boxes occupied by one user account
     *
     * @param userAccount
     * @return
     */
    @RequestMapping("/findAllByAccount")
    public Result findAllByAccount(@RequestBody UserAccount userAccount) {
        List<String> serials = new ArrayList<>();

        List<Order> orders = orderService.findByUser(userAccount.getSerial(), userAccount.getRole());

        orders.forEach(item -> {
            String serial = item.getBoxSerial();
            serials.add(serial);
        });

        return Result.success("successful query", boxService.findAllByAccount(serials));
    }
}
