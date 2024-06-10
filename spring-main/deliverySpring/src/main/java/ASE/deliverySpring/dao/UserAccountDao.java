package ASE.deliverySpring.dao;

import ASE.deliverySpring.entity.UserAccount;

import java.util.List;

/**
 * 描述:
 *
 * @author
 * @version 1.0
 * 版权所有：
 * @className UserAccountDao
 * @projectName deliverySpring
 * @date 2022/12/2
 */
public interface UserAccountDao {

     boolean save(UserAccount userAccount);

     boolean delete(String serial);

     List<UserAccount> findAll();

     boolean update(UserAccount userAccount);

     UserAccount findBySerial(String serial);

    UserAccount findByRfid(String serial);

    UserAccount findByPwd(String account, String password);


    UserAccount findByAccount(String account);
}
