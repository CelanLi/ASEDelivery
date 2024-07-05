package ASE.deliverySpring.dao.impl;



import ASE.deliverySpring.dao.OrderDao;
import ASE.deliverySpring.entity.Order;
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
 * 订单管理
 * @author
 * @version 1.0
 * 版权所有：
 * @className OrderDaoImpl
 * @projectName deliverySpring
 * @date 2022/12/11
 */

@Service
public class OrderDaoImpl implements OrderDao {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 新增订单
     * @param order /
     * @return /
     */
    @Override
    public boolean save(Order order) {

        order.setSubTime(DataUtil.getDateFormat("yyyy-MM-dd HH:mm:ss"));

        Order dest = mongoTemplate.save(order);

        return dest != null ? true : false;
    }

    /**
     * 删除订单
     * @param serial /
     * @return /
     */
    @Override
    public boolean delete(String serial) {

        Query query = new Query(Criteria.where("serial").is(serial));

        long count = mongoTemplate.remove(query, Order.class).getDeletedCount();

        return count==0 ? false : true;
    }

    /**
     * 查询订单列表 /
     * @return /
     */
    @Override
    public List<Order> findAll() {

        List<Order> orders = mongoTemplate.findAll(Order.class);

        return orders;

    }

    /**
     * 修改订单状态
     * @param order /
     * @return /
     */
    @Override
    public boolean update(Order order) {

        Query query = new Query(Criteria.where("serial").is(order.getSerial()));

        Update update = Update.update("status",order.getStatus())
                .set("updateTime",order.getUpdateTime())
                .set("boxSerial",order.getBoxSerial())
                .set("productSerial",order.getProductSerial())
                .set("userAccountSerial",order.getUserAccountSerial())
                .set("deliverySerial",order.getDeliverySerial())
                .set("realName",order.getRealName())
                .set("deliveryName",order.getDeliveryName());

        long count = mongoTemplate.updateFirst(query,update, Order.class).getModifiedCount();

        return count==0 ? false : true;
    }

    /**
     * 按编号查订单
     * @param serial /
     * @return /
     */
    @Override
    public Order findBySerial(String serial) {

        Query query = new Query(Criteria.where("serial").is(serial));

        Order order = mongoTemplate.findOne(query,Order.class);

        return order;

    }

    /**
     * 调度员指派送货员
     * @param serial
     * @param deliverySerial
     * @param deliveryName
     * @return
     */
    @Override
    public boolean updateByDelivery(String serial, String deliverySerial, String deliveryName) {

        Query query = new Query(Criteria.where("serial").is(serial));

        Update update = Update.update("deliverySerial",deliverySerial)
                .set("deliveryName",deliveryName);

        long count = mongoTemplate.updateFirst(query,update,Order.class).getModifiedCount();

        return count==0 ? false : true;

    }

    @Override
    public boolean updateStatus(String delivererSerial, String boxSerial, String newStatus) {
        return false;
    }

    @Override
    public List<Order> findByCustomerAndBoxSerialAndStatus(String customerSerial,String boxSerial){
        Criteria criteria=new Criteria();
        criteria.andOperator(Criteria.where("userAccountSerial").is(customerSerial),
                             Criteria.where("boxSerial").is(boxSerial),
                             Criteria.where("status").is("placed"));
        Query query=new Query(criteria);
        List<Order> list=mongoTemplate.find(query,Order.class);
        return list;
    }

    @Override
    public List<Order> findByDelivererAndBoxSerialAndStatus(String delivererSerial,String boxSerial){
//        Criteria criteria=Criteria.where("deliverySerial").is(delivererSerial).and("box").is(boxSerial);
        Criteria criteria=new Criteria();
        criteria.andOperator(
                Criteria.where("deliverySerial").is(delivererSerial),
                Criteria.where("boxSerial").is(boxSerial),
                Criteria.where("status").is("delivering"));
        Query query=new Query(criteria);
        List<Order> list=mongoTemplate.find(query,Order.class);

        return list;
    }

    @Override
    public boolean updateStatusByPick(String customerSerial, String boxSerial) {

        Criteria criteria=new Criteria();
        criteria.andOperator(Criteria.where("userAccountSerial").is(customerSerial),
                Criteria.where("boxSerial").is(boxSerial),
                Criteria.where("status").is("placed"));
        Query query=new Query(criteria);

        Update update = Update.update("status","picked");
        long count = mongoTemplate.updateMulti(query,update, Order.class).getModifiedCount();

        return count==0 ? false : true;
    }
    @Override
    public boolean updateStatusByPlace(String delivererSerial, String boxSerial) {

        Criteria criteria=new Criteria();
        criteria.andOperator(Criteria.where("deliverySerial").is(delivererSerial),
                Criteria.where("boxSerial").is(boxSerial),
                Criteria.where("status").is("delivering"));
        Query query=new Query(criteria);

        Update update = Update.update("status","placed");
        long count = mongoTemplate.updateMulti(query,update, Order.class).getModifiedCount();

        return count==0 ? false : true;
    }
    @Override
    public  List<Order> findByBoxSerialAndStatus(String boxSerial){
        Criteria criteria=new Criteria();
        criteria.andOperator(Criteria.where("status").ne("picked"),
                Criteria.where("boxSerial").is(boxSerial));
        Query query=new Query(criteria);
        List<Order> list=mongoTemplate.find(query,Order.class);
        return list;

    }
    @Override
    public  List<Order> findByUser(String uerSerial, String role){
        Query query=null;
        if (role.equals("deliverer")){
            query = new Query(Criteria.where("deliverySerial").is(uerSerial));
        }
        else if(role.equals("customer")){
            query = new Query(Criteria.where("userAccountSerial").is(uerSerial));
        }
        List<Order> list=mongoTemplate.find(query,Order.class);
        return list;

    }
}
