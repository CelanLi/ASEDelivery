package ASE.deliverySpring.controller;

import ASE.deliverySpring.base.BaseController;
import ASE.deliverySpring.base.BaseMessage;
import ASE.deliverySpring.entity.Product;
import ASE.deliverySpring.entity.UserAccount;
import ASE.deliverySpring.utils.DataUtil;
import ASE.deliverySpring.utils.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述:
 * 货物管理接口
 * @author test
 * @version 1.0
 * 版权所有：
 * @className ProductController
 * @projectName deliverySpring
 * @date 2022/12/3
 */

@CrossOrigin
@RestController
@RequestMapping("/v1/api/product")
public class ProductController extends BaseController {

    /**
     * 货物列表查询
     * @return /
     */
    @GetMapping("/find/all")
    public Result findAll(){

        try{

            List<Product> products = productService.findAll();

            return Result.success("查询成功",products);

        }catch (Exception e){
            e.printStackTrace();
        }

        return Result.error(BaseMessage.服务器内部错误请联系管理员.name());
    }

    /**
     * 保存货物
     * @param product 货物对象
     * @return
     */
    @PostMapping("/publish")
    public Result publish(Product product){

        try{


            if (StringUtils.isEmpty(product.getSerial())){

                product.setSerial(DataUtil.getComSerial());

                return productService.save(product) ? Result.success("新增成功") : Result.error("新增失败");


            }else {

                Product dest = productService.findBySerial(product.getSerial());

                if (dest == null){
                    return Result.error("当前用户信息不存在");
                }


                return productService.update(product) ? Result.success("修改成功") : Result.error("修改失败");

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return Result.error(BaseMessage.服务器内部错误请联系管理员.name());
    }

    /**
     * 删除货物
     * @param serial 货物唯一编号
     * @return
     */
    @GetMapping("/remove")
    public Result remove(@RequestParam("serial") String serial){

        try{

            Product product = productService.findBySerial(serial);

            if (product == null){
                return Result.error("当前用户不存在");
            }

            return productService.remove(serial) ? Result.success("删除成功") : Result.error("删除失败");

        }catch (Exception e){
            e.printStackTrace();
        }

        return Result.error(BaseMessage.服务器内部错误请联系管理员.name());

    }


}
