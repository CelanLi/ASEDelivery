package ASE.deliverySpring.dao.impl;

import ASE.deliverySpring.dao.ProductDao;
import ASE.deliverySpring.entity.Product;
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
 * @className ProductDaoImpl
 * @projectName deliverySpring
 * @date 2022/12/3
 */

@Service
public class ProductDaoImpl implements ProductDao {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public boolean save(Product product) {

        product.setSubTime(DataUtil.getDateFormat("yyyy-MM-dd HH:mm:ss"));
        Product dest = mongoTemplate.save(product);
        return dest!=null ? true : false;
    }

    @Override
    public boolean delete(String serial) {

        Query query = new Query(Criteria.where("serial").is(serial));

        long count = mongoTemplate.remove(query,Product.class).getDeletedCount();

        return count==0 ? false : true;
    }

    @Override
    public List<Product> findAll() {

        List<Product> products = mongoTemplate.findAll(Product.class);

        return products;

    }

    @Override
    public boolean update(Product product) {

        Query query = new Query(Criteria.where("serial").is(product.getSerial()));

        Update update = Update.update("name",product.getName())
                .set("pic",product.getPic())
                .set("vol",product.getVol())
                .set("wet",product.getWet())
                .set("num",product.getNum())
                .set("updateTime",product.getUpdateTime());

        long count = mongoTemplate.updateFirst(query,update,Product.class).getModifiedCount();

        return count==0 ? false : true;

    }

    @Override
    public Product findBySerial(String serial) {

        Query query = new Query(Criteria.where("serial").is(serial));

        Product product = mongoTemplate.findOne(query,Product.class);

        return product;
    }
}
