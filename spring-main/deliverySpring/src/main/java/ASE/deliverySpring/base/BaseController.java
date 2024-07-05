package ASE.deliverySpring.base;

import ASE.deliverySpring.service.*;
import ASE.deliverySpring.utils.JWT;
import org.slf4j.helpers.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 描述:
 *
 * @author
 * @version 1.0
 * 版权所有：
 * @className BaseController
 * @projectName deliverySpring
 * @date 2022/12/2
 */
public class BaseController {
    @Resource
    protected BoxService_hardware boxServiceHardware;
    @Resource
    protected BoxService boxService;
    @Resource
    protected EmailService emailService;

    @Resource
    protected LegitimacyTest legitimacyTest;
    @Resource
    protected UserAccountService userAccountService;

    @Resource
    protected ProductService productService;

    @Resource
    protected OrderService orderService;

    @Autowired
    protected JWT jwt;

    public HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取上下文
     *
     * @return
     */
    public HttpServletRequest getRequest() {

        return ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 返回当前用户账户对象
     * @param checker
     * @return
     */
//    public UserAccount getUserAccount(Result checker){
//        JSONObject json = (JSONObject) JSON.toJSON(checker.getData());
//        UserAccount userAccount = JSON.parseObject(json.toJSONString(), UserAccount.class);
//        return userAccount;
//    }
}
