package ASE.deliverySpring.entity;

import ASE.deliverySpring.entity.supe.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 描述:
 * 用户实体类
 * @author
 * @version 1.0
 * 版权所有：
 * @className UserAccount
 * @projectName deliverySpring
 * @date 2022/12/2
 */

@Data
@NoArgsConstructor
@Document("tbl_user_account")
public class UserAccount {

    @Id
    private String id;

    /**
     * 用户账号
     */
    @Indexed(unique = true)
    private String account;

    /**
     * 唯一编号
     */
    @Indexed(unique = true)
    private String serial;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色
     */
    private String role;

    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     * 注册时间
     */
    private String subTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 读卡器
     */
    private String rfid;

    /**
     * 邮箱
     */
    private String email;

}
