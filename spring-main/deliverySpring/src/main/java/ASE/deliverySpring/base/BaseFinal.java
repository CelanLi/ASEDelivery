package ASE.deliverySpring.base;

/**
 * 描述:
 *
 * @author
 * @version 1.0
 * 版权所有：
 * @className BaseFinal
 * @projectName deliverySpring
 * @date 2022/12/2
 */
public class BaseFinal {

    public final static String ACCOUNT_PREFIX="m_";

    public final static String AVATER_MEMBER="";

    public final static String MESSAGE_SERVER_ERROR = "服务器开小差啦，请稍后再试";//

    public final static String MESSAGE_MEMBER_NOT_FOUND = "尚未发现会员信息，请先登录";

    public final static String MODULE_LABLE_REGEX = "\\{pa:model\\}([\\s\\S]*?)\\{/pa:model\\}";//提取模块标签正则

    public final static String SUBFFIX_HTML=".html";//系统访问
    public final static String SUBFFIX_SHTML=".shtml";//站点访问

    public final static String LOGIN_USER="login_user";
    public final static String LOGIN_MEMBER="login_member";
    public final static String LOGIN_COMPANY="login_company";
    public final static String LOGIN_SHOP="login_shop";
    public final static String LOGIN_SUPERVISION="login_supervision";
    public final static String LOGIN_ADMIN="login_admin";
    public final static String CUR_SITE="cur_site";
    public final static String RESULT_OK="ok";
    public final static String RESULT_FAILED="failed";
    public final static String INVEST_ID="investId";

    public final static String DATE_FORMAT1="yyyy-MM-dd HH:mm:ss";
    public final static String DATE_FORMAT2="yyyy年MM月dd日  HH时mm分ss秒";
    public final static String DATE_FORMAT3="yyyyMMddHHmmss";
    public final static String DATE_FORMAT4="yyyyMMdd";
    public final static String DATE_FORMAT5="yyyy-MM-dd";

    public final static int SHIRO_HASH_ITERATIONS=2;

    public final static int NEWS_BASE_ID=12012;

    /******************* 页面大小 *********************/
    public final static int DEFAULT_PAGE_SIZE=20;
    public final static int PAGE_SIZE_20=20;
    public final static int PAGE_SIZE_20_NEXT=21;
    public final static int PAGE_SIZE_15=15;
    public final static int PAGE_SIZE_15_NEXT=16;
    public final static int PAGE_SIZE_10=10;
    public final static int PAGE_SIZE_10_NEXT=11;
    public final static int PAGE_SIZE_MAX=100;

    /**
     * 用户登录
     */
    public final static String LOG_TYPE_LOGIN="用户登录";
    /**
     * 用户操作
     */
    public final static String LOG_TYPE_OPTION="用户操作";
    /**
     * 用户注册
     */
    public final static String LOG_TYPE_REGISTER="用户注册";
    /**
     * 找回密码
     */
    public final static String LOG_TYPE_FINDPWD="找回密码";
    /**
     * 设置交易密码
     */
    public final static String LOG_TYPE_TRACEPWD="设置交易密码";
    /**
     * 邮箱认证
     */
    public final static String LOG_TYPE_EMAIL="邮箱认证";
    /**
     * 短信发送
     */
    public final static String LOG_TYPE_SMS_SEND="短信发送";
    /**
     * 邮件发送
     */
    public final static String LOG_TYPE_MAIL_SEND="邮件发送";
    /**
     * 通知发送
     */
    public final static String LOG_TYPE_NOTICE_SEND="通知发送";

    /**
     * 页面不存在
     */
    public final static String LOG_TYPE_NOTFOUND="页面不存在";

    /**
     * 服务异常
     */
    public final static String LOG_TYPE_EXCEPTION="服务异常";


    /**
     * 日志类型
     */
    public final static String[] LOG_OPT={LOG_TYPE_OPTION,LOG_TYPE_LOGIN,LOG_TYPE_REGISTER,LOG_TYPE_FINDPWD,LOG_TYPE_TRACEPWD,LOG_TYPE_EMAIL,LOG_TYPE_SMS_SEND,
            LOG_TYPE_MAIL_SEND,LOG_TYPE_NOTICE_SEND,LOG_TYPE_NOTFOUND,LOG_TYPE_EXCEPTION};
}
