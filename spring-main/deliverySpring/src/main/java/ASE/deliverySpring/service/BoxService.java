package ASE.deliverySpring.service;

import ASE.deliverySpring.dao.BoxDao;
import ASE.deliverySpring.dao.ProductDao;
import ASE.deliverySpring.entity.Box;
import ASE.deliverySpring.entity.Product;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BoxService {
    @Resource
    private BoxDao boxDao;

    /**
     * 新增
     * @param box /
     * @return /
     */
    public boolean save(Box box){
        return boxDao.save(box);
    }

    /**
     * 修改
     * @param box /
     * @return /
     */
    public boolean update(Box box){
        return boxDao.update(box);
    }

    /**
     * 删除
     * @param serial /
     * @return /
     */
    public boolean remove(String serial){
        return boxDao.delete(serial);
    }

    /**
     * 按编号查询
     * @param serial /
     * @return /
     */
    public Box findBySerial(String serial){
        return boxDao.findBySerial(serial);
    }

    /**
     * 列表查询
     * @return /
     */
    public List<Box> findAll(){
        return boxDao.findAll();
    }

    public List<Box> findAllByAccount(List serials){
        return boxDao.findAllByAccount(serials);
    }
}
