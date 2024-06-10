package ASE.deliverySpring.service;

import ASE.deliverySpring.dao.OrderDao;
import ASE.deliverySpring.dao.ProductDao;
import ASE.deliverySpring.entity.Order;
import ASE.deliverySpring.entity.Product;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述:
 *
 * @author
 * @version 1.0
 * 版权所有：
 * @className OrderService
 * @projectName deliverySpring
 * @date 2022/12/11
 */

@Service
public class OrderService {

    @Resource
    private OrderDao orderDao;

    /**
     * 新增
     * @param order /
     * @return /
     */
    public boolean save(Order order){
        return orderDao.save(order);
    }

    /**
     * 修改
     * @param order /
     * @return /
     */
    public boolean update(Order order){
        return orderDao.update(order);
    }

    /**
     * 删除
     * @param serial /
     * @return /
     */
    public boolean remove(String serial){
        return orderDao.delete(serial);
    }

    /**
     * 按编号查询
     * @param serial /
     * @return /
     */
    public Order findBySerial(String serial){
        return orderDao.findBySerial(serial);
    }

    /**
     * 列表查询
     * @return /
     */
    public List<Order> findAll(){
        return orderDao.findAll();
    }

    /**
     * 指派送货员
     * @param serial /
     * @param deliverySerial /
     * @param deliveryName /
     * @return /
     */
    public boolean updateByDelivery(String serial,String deliverySerial,String deliveryName){
        return orderDao.updateByDelivery(serial,deliverySerial,deliveryName);
    }
    public  List<Order> findByUser(String uerSerial, String role){
        return orderDao.findByUser(uerSerial,role);
    }
}
