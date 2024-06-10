package ASE.deliverySpring.dao;




import ASE.deliverySpring.entity.Order;

import java.util.List;

/**
 * 描述:
 *
 * @author
 * @version 1.0
 * 版权所有：
 * @className OrderDao
 * @projectName deliverySpring
 * @date 2022/12/11
 */
public interface OrderDao {

    boolean save(Order order);

    boolean delete(String serial);

    List<Order> findAll();

    boolean update(Order order);

    Order findBySerial(String serial);

    boolean updateByDelivery(String serial,String deliverySerial,String deliveryName);



    boolean updateStatus(String delivererSerial, String boxSerial, String newStatus);


    boolean updateStatusByPick(String delivererSerial, String boxSerial);

    boolean updateStatusByPlace(String delivererSerial, String boxSerial);

    List<Order> findByCustomerAndBoxSerialAndStatus(String customerSerial,String boxSerial);

    List<Order> findByDelivererAndBoxSerialAndStatus(String delivererSerial,String boxSerial);

    List<Order> findByBoxSerialAndStatus(String boxSerial);

    List<Order> findByUser(String uerSerial, String role);
}
