package ASE.deliverySpring.dao;

import ASE.deliverySpring.entity.Product;
import ASE.deliverySpring.entity.UserAccount;

import java.util.List;

/**
 * 描述:
 * 获取持久层类
 * @author
 * @version 1.0
 * 版权所有：
 * @className ProductDao
 * @projectName deliverySpring
 * @date 2022/12/3
 */

public interface ProductDao {

    boolean save(Product product);

    boolean delete(String serial);

    List<Product> findAll();

    boolean update(Product product);

    Product findBySerial(String serial);
}
