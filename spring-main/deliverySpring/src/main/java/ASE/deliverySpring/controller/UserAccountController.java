package ASE.deliverySpring.controller;

import ASE.deliverySpring.base.BaseController;
import ASE.deliverySpring.base.BaseMessage;
import ASE.deliverySpring.entity.Order;
import ASE.deliverySpring.entity.UserAccount;
import ASE.deliverySpring.utils.DataUtil;
import ASE.deliverySpring.utils.JWT;
import ASE.deliverySpring.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
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
     * get all users
     * 
     * @return
     */
    @RequestMapping("/find/all")
    public Result findAll() {
        List<UserAccount> userAccounts = userAccountService.findAll();
        return Result.success("successful query", userAccounts);
    }

    /**
     * save user
     * 
     * @param userAccount 用户对象
     * @return /
     */
    @RequestMapping("/publish")
    public Result publish(@RequestBody UserAccount userAccount) {
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
    }

    /**
     * delete user
     * 
     * @param requestUserAccount
     * @return /
     */
    @RequestMapping("/remove")
    public Result remove(@RequestBody UserAccount requestUserAccount) {
        UserAccount userAccount = userAccountService.findBySerial(requestUserAccount.getSerial());
        if (userAccount == null) {
            return Result.error("this account does not exists");
        }
        return userAccountService.remove(requestUserAccount.getSerial()) ? Result.success("successful delete")
                : Result.error("failed delete");
    }

    @RequestMapping("/update")
    public Result update(@RequestBody UserAccount requestUserAccount) {
        UserAccount userAccount = userAccountService.findBySerial(requestUserAccount.getSerial());
        if (userAccount == null) {
            return Result.error("This account does not exists.");
        }
        return userAccountService.update(requestUserAccount) ? Result.success("successful update")
                : Result.error("failed update");
    }

    /**
     * user login
     * 
     * @param requestUserAccount
     * @return /
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserAccount requestUserAccount) {
        System.out.println("login: " + requestUserAccount.getAccount() + ", " + requestUserAccount.getPassword());
        UserAccount userAccount = userAccountService.findByPassword(requestUserAccount.getAccount(),
                requestUserAccount.getPassword());
        System.out.println("login: " + userAccount);
        if (userAccount == null) {
            return Result.error("wrong username or password");
        }
        String token = jwt.genJWT(userAccount);    // generate jwt, return a jwt instead of user account
        //return Result.success("login",token);
        return Result.success("login",userAccount);

    }

    /**
     * dispatcher assign rfid
     * 
     * @param email
     * @param rfid
     * @param password
     * @param role
     * @return
     */
    @RequestMapping("/share/rfid")
    public Result shareRfid(@RequestParam("email") String email, @RequestParam("rfid") String rfid,
        @RequestParam("password") String password, String role, String serial) {
        UserAccount current = userAccountService.findBySerial(serial);
        if (current == null) {
            return Result.error("User does not exists!");
        }
        if (!"dispatcher".equals(current.getRole())) {
            return Result.error("No authorization");
        }
        if (StringUtils.isEmpty(role)) {
            return Result.error("Please assign role");
        }
        UserAccount userAccount = new UserAccount();
        userAccount.setSerial(DataUtil.getComSerial());
        userAccount.setPassword(password);
        userAccount.setEmail(email);
        userAccount.setRfid(rfid);
        userAccount.setRole(role);

        return userAccountService.save(userAccount) ? Result.success("Assigned Successfully!") : Result.error("Assigned Failed!");
    }

    /**
     * create new dispatcher
     * 
     * @param password
     * @param email
     * @return /
     */
    @RequestMapping("/create/scheduler")
    public Result createScheduler(@RequestParam("password") String password, @RequestParam("email") String email) {
        UserAccount userAccount = new UserAccount();
        userAccount.setSerial(DataUtil.getComSerial());
        userAccount.setPassword(password);
        userAccount.setRole("dispatcher");

        return userAccountService.save(userAccount) ? Result.success("Created Successfully!") : Result.error("Created Failed!");
    }
}
