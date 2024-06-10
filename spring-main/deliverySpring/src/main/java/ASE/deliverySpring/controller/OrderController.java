package ASE.deliverySpring.controller;

import ASE.deliverySpring.base.BaseController;
import ASE.deliverySpring.base.BaseMessage;
import ASE.deliverySpring.entity.Order;
import ASE.deliverySpring.entity.Product;
import ASE.deliverySpring.entity.UserAccount;
import ASE.deliverySpring.utils.DataUtil;
import ASE.deliverySpring.utils.Result;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 描述:
 * 订单管理接口
 * @author test
 * @version 1.0
 * 版权所有：
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
     * @param order 订单对象
     * @return /
     */

    @RequestMapping("/publish")
    public Result publish(@RequestBody Order order){

        try{
             order.setStatus("delivering");
//              order.setSerial(DataUtil.getComSerial());
              if(orderService.findBySerial(order.getSerial())!=null){
                  return Result.error("The serial already exists, please choose another one.");
              }

              else if(!legitimacyTest.customerUniquenessTest(order)){
                  return Result.error("The box is allocated,please choose another one.");
              }

              else if(orderService.save(order)){
                  emailService.orderCreationMail(order);
                  return Result.success("order placing successfully");

              }
              else{
                  return Result.error("order placing failed");
              }
//            return orderService.save(order) ? Result.success("下单成功") : Result.error("下单失败");


        }catch (Exception e){
            e.printStackTrace();
        }

        return Result.error(BaseMessage.服务器内部错误请联系管理员.name());

    }

    /**
     * 删除订单
     * @param requestOrder  唯一编号
     * @return /
     */
    @RequestMapping("/remove")
    public Result remove(@RequestBody Order requestOrder){

        try{

            Order order = orderService.findBySerial(requestOrder.getSerial());

            if (order == null){
                return Result.error("this order does not exists");
            }

            return orderService.remove(requestOrder.getSerial()) ? Result.success("successful delete") : Result.error("failed delete");

        }catch (Exception e){
            e.printStackTrace();
        }

        return Result.error(BaseMessage.服务器内部错误请联系管理员.name());

    }

    /**
     * 查询订单列表
     * @return /
     */
    @RequestMapping("/find/all")
    public Result findAll(){

        try{

            List<Order> orders = orderService.findAll();

//            orders.forEach(item ->{
//                Product product = productService.findBySerial(item.getProductSerial());
//                item.setProduct(product);
//            });
            return Result.success("successful query",orders);

        }catch (Exception e){
            e.printStackTrace();
        }

        return Result.error(BaseMessage.服务器内部错误请联系管理员.name());
    }

    /**
     * 指派送货员
     * @param requestOrder
     * @return /
     */
    @RequestMapping("/update/delivery")
    public Result update(@RequestBody  Order requestOrder){

        try{

            Order order = orderService.findBySerial(requestOrder.getSerial());

            if (order == null){
                return Result.error("This order does not exists");
            }

            return orderService.update(requestOrder) ? Result.success("successful update") : Result.error("failed update");


        }catch (Exception e){
            e.printStackTrace();
        }

        return Result.error(BaseMessage.服务器内部错误请联系管理员.name());

    }


//    /**
//     * 修改订单状态，送货、已收货
//     * @param status 订单状态
//     * @param serial 订单唯一编号
//     * @return /
//     */
//    @RequestMapping("/update/status")
//    public Result delivery(String status,String serial){
//
//        try{
//
//            Order order = orderService.findBySerial(serial);
//
//            if (order == null){
//                return Result.error("This order does not exists");
//            }
//
//            order.setStatus(status);
//
//            return orderService.update(order) ? Result.success("successful update") : Result.error("failed update");
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return Result.error(BaseMessage.服务器内部错误请联系管理员.name());
//    }
//
//    @RequestMapping("/update/status_collection")
//    public Result updatStatusWhenCollecting(@RequestBody Order order){
//
//        try{
//            order.setStatus("collected");
//
//            if (orderService.update(order)){
//                emailService.deliveryCollectionMail(order);
//                return Result.success("successful status update");
//            }
//
//            else{
//                return Result.error("failed status update");
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return Result.error(BaseMessage.服务器内部错误请联系管理员.name());
//
//    }

    @RequestMapping ("/findBySerial")
    public Result findBySerial(@RequestBody Order requestOrder){
        Order order=orderService.findBySerial(requestOrder.getSerial());
        try{
            if (order==null){
                return Result.error("This order does not exists");

            }

            else{
                return Result.success("successful query",order);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return Result.error(BaseMessage.服务器内部错误请联系管理员.name());
    }
    @RequestMapping("/findAllByAccount")
    public Result findAllByAccount(@RequestBody UserAccount userAccount){

        try{
            UserAccount account=userAccountService.findByAccount(userAccount.getAccount());
            List<Order> orders = orderService.findByUser(account.getSerial(),account.getRole());

//            orders.forEach(item ->{
//                Product product = productService.findBySerial(item.getProductSerial());
//                item.setProduct(product);
//            });
            return Result.success("successful query",orders);

        }catch (Exception e){
            e.printStackTrace();
        }

        return Result.error(BaseMessage.服务器内部错误请联系管理员.name());
    }

}
