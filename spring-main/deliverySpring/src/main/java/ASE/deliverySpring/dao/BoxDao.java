package ASE.deliverySpring.dao;


import ASE.deliverySpring.entity.Box;
import ASE.deliverySpring.entity.Product;

import java.util.List;

public interface BoxDao {
    boolean save(Box box);

    boolean delete(String serial);


    boolean update(Box box);

    List<Box> findAll();


    Box findBySerial(String serial);

    boolean updateStatusBySerial(String serial, String status);

    List<Box> findAllByAccount(List serials);
}
