package ASE.deliverySpring.controller;

import ASE.deliverySpring.base.BaseController;
import ASE.deliverySpring.base.BaseMessage;
import ASE.deliverySpring.entity.Order;
import ASE.deliverySpring.entity.UserAccount;
import ASE.deliverySpring.utils.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述:
 * 订单管理接口
 * 
 * @author test
 * @version 1.0
 *          版权所有：
 * @className OrderController
 * @projectName deliverySpring
 * @date 2022/12/11
 */

@CrossOrigin
@RestController
@RequestMapping("/v1/api/order")
public class OrderController extends BaseController {

    /**
     * 下单
     * 
     * @param order 订单对象
     * @return /
     */

    @RequestMapping("/publish")
    public Result publish(@RequestBody Order order) {

        order.setStatus("delivering");
        if (orderService.findBySerial(order.getSerial()) != null) {
            return Result.error("The serial already exists, please choose another one.");
        }

        else if (!legitimacyTest.customerUniquenessTest(order)) {
            return Result.error("The box is allocated,please choose another one.");
        }

        else if (orderService.save(order)) {
            emailService.orderCreationMail(order);
            return Result.success("order placing successfully");

        } else {
            return Result.error("order placing failed");
        }

    }

    /**
     * 删除订单
     * 
     * @param requestOrder 唯一编号
     * @return /
     */
    @RequestMapping("/remove")
    public Result remove(@RequestBody Order requestOrder) {

        Order order = orderService.findBySerial(requestOrder.getSerial());

        if (order == null) {
            return Result.error("this order does not exists");
        }

        return orderService.remove(requestOrder.getSerial()) ? Result.success("successful delete")
                : Result.error("failed delete");

    }

    /**
     * 查询订单列表
     * 
     * @return /
     */
    @RequestMapping("/find/all")
    public Result findAll() {
        List<Order> orders = orderService.findAll();
        return Result.success("successful query", orders);
    }

    /**
     * 指派送货员
     * 
     * @param requestOrder
     * @return /
     */
    @RequestMapping("/update/delivery")
    public Result update(@RequestBody Order requestOrder) {
        Order order = orderService.findBySerial(requestOrder.getSerial());

        if (order == null) {
            return Result.error("This order does not exists");
        }

        return orderService.update(requestOrder) ? Result.success("successful update")
                : Result.error("failed update");
    }

    // /**
    // * 修改订单状态，送货、已收货
    // * @param status 订单状态
    // * @param serial 订单唯一编号
    // * @return /
    // */
    // @RequestMapping("/update/status")
    // public Result delivery(String status,String serial){
    //
    // try{
    //
    // Order order = orderService.findBySerial(serial);
    //
    // if (order == null){
    // return Result.error("This order does not exists");
    // }
    //
    // order.setStatus(status);
    //
    // return orderService.update(order) ? Result.success("successful update") :
    // Result.error("failed update");
    //
    // }catch (Exception e){
    // e.printStackTrace();
    // }
    //
    // return Result.error(BaseMessage.服务器内部错误请联系管理员.name());
    // }
    //
    @CrossOrigin
    @RequestMapping("/update/status_collection")
    public Result updatStatusWhenCollecting(@RequestBody Order requestOrder) {
        Order order = orderService.findBySerial(requestOrder.getSerial());
        order.setStatus("delivering");
        if (orderService.update(order)) {
            emailService.deliveryCollectionMail(order);
            return Result.success("successful status update");
        }
        else {
            return Result.error("failed status update");
        }
    }

    @RequestMapping("/findBySerial")
    public Result findBySerial(@RequestBody Order requestOrder) {
        Order order = orderService.findBySerial(requestOrder.getSerial());
        if (order == null) {
            return Result.error("This order does not exists");
        }
        else {
            return Result.success("successful query", order);
        }
    }

    @RequestMapping("/findAllByAccount")
    public Result findAllByAccount(@RequestBody UserAccount userAccount) {
        UserAccount account = userAccountService.findByAccount(userAccount.getAccount());
        List<Order> orders = orderService.findByUser(account.getSerial(), account.getRole());
        return Result.success("successful query", orders);
    }

}
