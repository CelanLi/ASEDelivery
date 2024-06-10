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
     * box列表查询
     * @return /
     */
    @RequestMapping("/find/all")
    public Result findAll(){

        try{

            List<Box> boxs = boxService.findAll();

            return Result.success("successful query",boxs);

        }catch (Exception e){
            e.printStackTrace();
        }

        return Result.error(BaseMessage.服务器内部错误请联系管理员.name());
    }

    /**
     * 保存box
     * @param box box对象
     * @return
     */
    @RequestMapping("/publish")
    public Result publish(@RequestBody  Box box){

        try{

            box.setStatus("free");
            if (boxService.findBySerial(box.getSerial())!=null){

                return Result.error("This serial already exists!");




            }else {

                return boxService.save(box) ? Result.success("successful creation") : Result.error("failed creation");

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return Result.error(BaseMessage.服务器内部错误请联系管理员.name());
    }

    @RequestMapping("/update/box")
    public Result update(@RequestBody Box box){

        try{

            Order order = orderService.findBySerial(box.getSerial());

            if (order == null){
                return Result.error("This order does not exists.");
            }

            return boxService.update(box) ? Result.success("successful update") : Result.error("failed update");


        }catch (Exception e){
            e.printStackTrace();
        }

        return Result.error(BaseMessage.服务器内部错误请联系管理员.name());

    }

    /**
     * 删除box
     * @param requestBox box唯一编号
     * @return
     */
    @RequestMapping("/remove")
    public Result remove(@RequestBody Box requestBox){

        try{

            Box box = boxService.findBySerial(requestBox.getSerial());

            if (box == null){
                return Result.error("the box does not exists");
            }

            return boxService.remove(requestBox.getSerial()) ? Result.success("delete successfully") : Result.error("failed delete");

        }catch (Exception e){
            e.printStackTrace();
        }

        return Result.error(BaseMessage.服务器内部错误请联系管理员.name());

    }
    @RequestMapping("/findAllByAccount")
    public Result findAllByAccount(@RequestBody UserAccount userAccount){
        List<String> serials=new ArrayList<String>();

        try{
            List<Order> orders=orderService.findByUser(userAccount.getSerial(),userAccount.getRole());



            orders.forEach(item ->{
                String serial = item.getBoxSerial();
                serials.add(serial);
            });

            return Result.success("successful query",boxService.findAllByAccount(serials));

        }catch (Exception e){
            e.printStackTrace();
        }

        return Result.error(BaseMessage.服务器内部错误请联系管理员.name());
    }
}
