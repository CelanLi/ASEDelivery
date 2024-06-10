package ASE.deliverySpring.controller;

import ASE.deliverySpring.base.BaseController;
import ASE.deliverySpring.base.BaseMessage;
import ASE.deliverySpring.entity.Order;
import ASE.deliverySpring.entity.UserAccount;
import ASE.deliverySpring.utils.DataUtil;
import ASE.deliverySpring.utils.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述:
 * 用户接口管理
 * 
 * @author test
 * @version 1.0
 *          版权所有：
 * @className UserAccountController
 * @projectName deliverySpring
 * @date 2022/12/3
 */

@CrossOrigin
@RestController
@RequestMapping("/v1/api/user")
public class UserAccountController extends BaseController {

    /**
     * 用户列表查询
     * 
     * @return
     */
    @RequestMapping("/find/all")
    public Result findAll() {

        try {

            List<UserAccount> userAccounts = userAccountService.findAll();

            return Result.success("successful query", userAccounts);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.error(BaseMessage.服务器内部错误请联系管理员.name());
    }

    /**
     * 保存用户
     * 
     * @param userAccount 用户对象
     * @return /
     */
    @RequestMapping("/publish")
    public Result publish(@RequestBody UserAccount userAccount) {

        try {
            userAccount.setSerial(DataUtil.getComSerial());
            if (userAccountService.findBySerial(userAccount.getSerial()) != null) {
                return Result.error("The serial already exists, please choose another one.");
            } else if (userAccountService.findByAccount(userAccount.getAccount()) != null) {

                return Result.error("This account already exists!");
            } else if (userAccountService.save(userAccount)) {

                emailService.userCreationMail(userAccount);
                return Result.success("successfully added");
            } else {
                return Result.error("failed add");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.error(BaseMessage.服务器内部错误请联系管理员.name());
    }

    /**
     * 删除用户
     * 
     * @param requestUserAccount 用户唯一编号
     * @return /
     */
    @RequestMapping("/remove")
    public Result remove(@RequestBody UserAccount requestUserAccount) {

        try {

            UserAccount userAccount = userAccountService.findBySerial(requestUserAccount.getSerial());

            if (userAccount == null) {
                return Result.error("this account does not exists");
            }

            return userAccountService.remove(requestUserAccount.getSerial()) ? Result.success("successful delete")
                    : Result.error("failed delete");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.error(BaseMessage.服务器内部错误请联系管理员.name());

    }

    @RequestMapping("/update")
    public Result update(@RequestBody UserAccount requestUserAccount) {

        try {

            UserAccount userAccount = userAccountService.findBySerial(requestUserAccount.getSerial());

            if (userAccount == null) {
                return Result.error("This account does not exists.");
            }

            return userAccountService.update(requestUserAccount) ? Result.success("successful update")
                    : Result.error("failed update");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.error(BaseMessage.服务器内部错误请联系管理员.name());

    }

    /**
     * 用户登录验证
     * 
     * @param requestUserAccount
     * @return /
     */
    @RequestMapping("/login")
    public Result login(@RequestBody UserAccount requestUserAccount) {
        System.out.println("login!");

        try {

            // 密码先加密，再和数据库里加密后的比对
            // String destPwd = DataUtil.getMd5Password(requestUserAccount.getPassword());

            System.out.println("login: " + requestUserAccount.getAccount() + ", " + requestUserAccount.getPassword());

            UserAccount userAccount = userAccountService.findByPassword(requestUserAccount.getAccount(),
                    requestUserAccount.getPassword());

            System.out.println("login: " + userAccount);
            if (userAccount == null) {
                return Result.error("账号密码错误");
            }

            return Result.success("login", userAccount);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.error(BaseMessage.服务器内部错误请联系管理员.name());

    }

    /**
     * 调度员分配rfid
     * 
     * @param email    邮箱地址
     * @param rfid     设备号
     * @param password 密码
     * @param role     用户角色 （送货员\客户)
     * @return
     */
    @RequestMapping("/share/rfid")
    public Result shareRfid(@RequestParam("email") String email, @RequestParam("rfid") String rfid,
            @RequestParam("password") String password, String role, String serial) {

        try {

            UserAccount current = userAccountService.findBySerial(serial);

            if (current == null) {
                return Result.error("当前登录人员不存在，请核实后再操作");
            }

            if (!"调度员".equals(current.getRole())) {
                return Result.error("当前登录人员无权分配，请通知调度人员");
            }

            if (StringUtils.isEmpty(role)) {
                return Result.error("请指定要分配的用户角色");
            }

            UserAccount userAccount = new UserAccount();
            userAccount.setSerial(DataUtil.getComSerial());
            userAccount.setPassword(password);
            userAccount.setEmail(email);
            userAccount.setRfid(rfid);
            userAccount.setRole(role);

            return userAccountService.save(userAccount) ? Result.success("分配成功") : Result.error("分配失败");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.error(BaseMessage.服务器内部错误请联系管理员.name());
    }

    /**
     * 调度员创建新的调度员
     * 
     * @param password 密码
     * @param email    邮箱
     * @return /
     */
    @RequestMapping("/create/scheduler")
    public Result createScheduler(@RequestParam("password") String password, @RequestParam("email") String email) {

        try {

            UserAccount userAccount = new UserAccount();
            userAccount.setSerial(DataUtil.getComSerial());
            userAccount.setPassword(password);
            userAccount.setRole("dispatcher");

            return userAccountService.save(userAccount) ? Result.success("创建成功") : Result.error("创建失败");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.error(BaseMessage.服务器内部错误请联系管理员.name());
    }
}
