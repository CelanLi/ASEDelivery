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
     * get all products list
     * @return /
     */
    @GetMapping("/find/all")
    public Result findAll(){
            List<Product> products = productService.findAll();
            return Result.success("success",products);
    }

    /**
     * save or update product
     * @param product
     * @return
     */
    @PostMapping("/publish")
    public Result publish(Product product){
        if (StringUtils.isEmpty(product.getSerial())){
            product.setSerial(DataUtil.getComSerial());
            return productService.save(product) ? Result.success("Added Successfully!") : Result.error("Failed!");
        }else {
            Product dest = productService.findBySerial(product.getSerial());
            if (dest == null){
                return Result.error("Product doesn't exists!");
            }
            return productService.update(product) ? Result.success("Update successfully!") : Result.error("Update failed!");
        }
    }

    /**
     * delete product
     * @param serial serial of product
     * @return
     */
    @GetMapping("/remove")
    public Result remove(@RequestParam("serial") String serial){
            Product product = productService.findBySerial(serial);
            if (product == null){
                return Result.error("Product doesn't exist!");
            }
            return productService.remove(serial) ? Result.success("Deleted successfully!") : Result.error("Deleted failed!");
    }

}
