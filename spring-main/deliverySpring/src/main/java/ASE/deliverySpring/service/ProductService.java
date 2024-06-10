package ASE.deliverySpring.service;

import ASE.deliverySpring.dao.ProductDao;
import ASE.deliverySpring.entity.Product;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述:
 * 货物服务
 * @author
 * @version 1.0
 * 版权所有：
 * @className ProductService
 * @projectName deliverySpring
 * @date 2022/12/3
 */

@Service
public class ProductService {

    @Resource
    private ProductDao productDao;

    /**
     * 新增
     * @param product /
     * @return /
     */
    public boolean save(Product product){
        return productDao.save(product);
    }

    /**
     * 修改
     * @param product /
     * @return /
     */
    public boolean update(Product product){
        return productDao.update(product);
    }

    /**
     * 删除
     * @param serial /
     * @return /
     */
    public boolean remove(String serial){
        return productDao.delete(serial);
    }

    /**
     * 按编号查询
     * @param serial /
     * @return /
     */
    public Product findBySerial(String serial){
        return productDao.findBySerial(serial);
    }

    /**
     * 列表查询
     * @return /
     */
    public List<Product> findAll(){
        return productDao.findAll();
    }
}
