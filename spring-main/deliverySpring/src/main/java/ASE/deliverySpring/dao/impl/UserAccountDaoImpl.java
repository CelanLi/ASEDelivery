package ASE.deliverySpring.dao.impl;

import ASE.deliverySpring.dao.UserAccountDao;
import ASE.deliverySpring.entity.UserAccount;
import ASE.deliverySpring.utils.DataUtil;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述:
 *
 * @author
 * @version 1.0
 * 版权所有：
 * @className UserAccountDaoImpl
 * @projectName deliverySpring
 * @date 2022/12/2
 */

@Service
public class UserAccountDaoImpl implements UserAccountDao {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public boolean save(UserAccount userAccount) {

        userAccount.setSubTime(DataUtil.getDateFormat("yyyy-MM-dd HH:mm:ss"));
        //对密码加密
//        userAccount.setPassword(DataUtil.getMd5Password(userAccount.getPassword()));
        UserAccount dest = mongoTemplate.save(userAccount);

        return dest==null ? false : true;

    }

    @Override
    public boolean delete(String serial) {

        Query query = new Query(Criteria.where("serial").is(serial));

        long count = mongoTemplate.remove(query,UserAccount.class).getDeletedCount();

        return count==0 ? false : true;
    }

    @Override
    public List<UserAccount> findAll() {

        List<UserAccount> userAccounts = mongoTemplate.findAll(UserAccount.class);

        return userAccounts;
    }

    @Override
    public boolean update(UserAccount userAccount) {

        Query query = new Query(Criteria.where("serial").is(userAccount.getSerial()));

        Update update = Update.update("account",userAccount.getAccount())
                .set("password",userAccount.getPassword())
                .set("realName",userAccount.getRealName())
                .set("role",userAccount.getRole())
                .set("updateTime",userAccount.getUpdateTime())
                .set("email",userAccount.getEmail())
                .set("rfid",userAccount.getRfid());

        long count = mongoTemplate.updateFirst(query,update, UserAccount.class).getModifiedCount();

        return count==0 ? false : true;
    }

    @Override
    public UserAccount findBySerial(String serial) {

        Query query = new Query(Criteria.where("serial").is(serial));

        UserAccount userAccount = mongoTemplate.findOne(query,UserAccount.class);

        return userAccount;
    }
    @Override
    public UserAccount findByRfid(String rfid) {

        Query query = new Query(Criteria.where("serial").is(rfid));

        UserAccount userAccount = mongoTemplate.findOne(query,UserAccount.class);

        return userAccount;
    }
    @Override
    public UserAccount findByPwd(String account, String password) {

        Query query = new Query(Criteria.where("account").is(account).and("password").is(password));

        UserAccount userAccount = mongoTemplate.findOne(query,UserAccount.class);

        return userAccount;
    }
    @Override
    public UserAccount findByAccount(String account) {

        Query query = new Query(Criteria.where("account").is(account));

        UserAccount userAccount = mongoTemplate.findOne(query,UserAccount.class);

        return userAccount;
    }
}
