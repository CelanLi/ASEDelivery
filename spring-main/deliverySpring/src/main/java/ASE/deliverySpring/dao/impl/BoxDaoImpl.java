package ASE.deliverySpring.dao.impl;

import ASE.deliverySpring.dao.BoxDao;
import ASE.deliverySpring.entity.Box;
import ASE.deliverySpring.entity.Product;
import ASE.deliverySpring.utils.DataUtil;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BoxDaoImpl implements BoxDao {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public boolean save(Box box) {
        box.setSubTime(DataUtil.getDateFormat("yyyy-MM-dd HH:mm:ss"));
        Box dest = mongoTemplate.save(box);

        return dest != null ? true : false;
    }

    @Override
    public boolean delete(String serial) {
        Query query = new Query(Criteria.where("serial").is(serial));

        long count = mongoTemplate.remove(query, Box.class).getDeletedCount();

        return count == 0 ? false : true;
    }

    @Override
    public boolean update(Box box) {
        Query query = new Query(Criteria.where("serial").is(box.getSerial()));

        Update update = new Update()
                .set("name", box.getName())
                .set("address", box.getAddress())
                .set("status", box.getStatus())
                .set("subTime", box.getSubTime());

        long count = mongoTemplate.updateFirst(query, update, Box.class).getMatchedCount();

        System.out.println("count" + count);

        return count != 0;
    }

    @Override
    public List<Box> findAll() {
        List<Box> boxes = mongoTemplate.findAll(Box.class);

        return boxes;
    }

    @Override
    public Box findBySerial(String serial) {
        Query query = new Query(Criteria.where("serial").is(serial));

        Box box = mongoTemplate.findOne(query, Box.class);

        return box;
    }

    @Override
    public boolean updateStatusBySerial(String serial, String status) {

        Query query = new Query(Criteria.where("serial").is(serial));

        Update update = Update.update("status", status);

        long count = mongoTemplate.updateFirst(query, update, Box.class).getModifiedCount();

        return count == 0 ? false : true;

    }

    @Override
    public List<Box> findAllByAccount(List serials) {
        Query query = new Query(Criteria.where("serial").in(serials));

        List<Box> boxes = mongoTemplate.find(query, Box.class);

        return boxes;
    }

}
