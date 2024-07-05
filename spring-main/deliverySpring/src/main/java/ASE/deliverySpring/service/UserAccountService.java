package ASE.deliverySpring.service;

import ASE.deliverySpring.dao.UserAccountDao;
import ASE.deliverySpring.entity.UserAccount;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述:
 * 用户服务
 * @author
 * @version 1.0
 * 版权所有：
 * @className UserAccountService
 * @projectName deliverySpring
 * @date 2022/12/2
 */
@Service
public class UserAccountService {

    @Resource
    private UserAccountDao userAccountDao;

    /**
     * 新增用户
     * @param userAccount /
     * @return /
     */
    public boolean save(UserAccount userAccount){
        return userAccountDao.save(userAccount);
    }

    /**
     * 删除用户
     * @param serial /
     * @return /
     */
    public boolean remove(String serial){
        return userAccountDao.delete(serial);
    }

    /**
     * 修改用户
     * @param userAccount /
     * @return /
     */
    public boolean update(UserAccount userAccount){
        return userAccountDao.update(userAccount);
    }

    /**
     * 查询所有
     * @return
     */
    public List<UserAccount> findAll() {
        return userAccountDao.findAll();
    }

    /**
     * 按编号查
     * @param serial /
     * @return /
     */
    public UserAccount findBySerial(String serial){
        return userAccountDao.findBySerial(serial);
    }

    /**
     * search by account and password
     * @param account /
     * @param password /
     * @return /
     */
    public UserAccount findByPassword(String account,String password){
        return userAccountDao.findByPwd(account,password);
    }
    public UserAccount findByAccount(String account){
        return userAccountDao.findByAccount(account);
    }

}
