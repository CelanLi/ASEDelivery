package ASE.deliverySpring.service;


import ASE.deliverySpring.dao.OrderDao;
import ASE.deliverySpring.entity.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LegitimacyTest {

    @Resource
    OrderDao orderDao;
    public  boolean customerUniquenessTest(Order order){

//        find all delieveries of on the way or in the box
        List<Order> list=orderDao.findByBoxSerialAndStatus(order.getBoxSerial());
        if(!list.isEmpty()){
            if(!list.get(0).getUserAccountSerial().equals(order.getUserAccountSerial())){
                return false;
            }
        }
        return true;
    }
}
