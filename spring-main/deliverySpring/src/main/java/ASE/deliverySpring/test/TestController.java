package ASE.deliverySpring.test;

import ASE.deliverySpring.base.BaseController;
import ASE.deliverySpring.base.BaseMessage;
import ASE.deliverySpring.entity.UserAccount;
import ASE.deliverySpring.utils.DataUtil;
import ASE.deliverySpring.utils.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述:
 * 测试测试测试
 * @author test
 * @version 1.0
 * 版权所有：
 * @className TestController
 * @projectName deliverySpring
 * @date 2022/12/2
 */

@RestController
@RequestMapping("/v1/api/test")
public class TestController extends BaseController {

    @GetMapping("/t1")
    public Result hello(){

        try{

            List<UserAccount> userAccounts = userAccountService.findAll();

            return Result.success("查询成功",userAccounts);

        }catch (Exception e){
            e.printStackTrace();
        }

        return Result.error(BaseMessage.服务器内部错误请联系管理员.name());
    }

    @PostMapping("/t2")
    public Result insert(UserAccount userAccount){

        try{


            if (StringUtils.isEmpty(userAccount.getSerial())){

                userAccount.setSerial(DataUtil.getComSerial());


                return userAccountService.save(userAccount) ? Result.success("新增成功") : Result.error("新增失败");


            }else {

                UserAccount dest = userAccountService.findBySerial(userAccount.getSerial());

                if (dest == null){
                    return Result.error("当前用户信息不存在");
                }

                userAccount.setRealName("张三三");

                return userAccountService.update(userAccount) ? Result.success("修改成功") : Result.error("修改失败");

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return Result.error(BaseMessage.服务器内部错误请联系管理员.name());
    }

    @GetMapping("/t3")
    public Result remove(@RequestParam("serial") String serial){

        try{

            UserAccount userAccount = userAccountService.findBySerial(serial);

            if (userAccount == null){
                return Result.error("当前用户不存在");
            }

            return userAccountService.remove(serial) ? Result.success("删除成功") : Result.error("删除失败");

        }catch (Exception e){
            e.printStackTrace();
        }

        return Result.error(BaseMessage.服务器内部错误请联系管理员.name());

    }

    @PostMapping("/testException")
    public Result exceptionTest(@RequestBody UserAccount userAccount){
        throw new RuntimeException("测试全局异常处理程序");
    }
}
