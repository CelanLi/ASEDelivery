package ASE.deliverySpring.service;


import ASE.deliverySpring.dao.BoxDao;
import ASE.deliverySpring.dao.OrderDao;
import ASE.deliverySpring.dao.UserAccountDao;
import ASE.deliverySpring.entity.BoxInfo;
import ASE.deliverySpring.entity.Order;
import ASE.deliverySpring.entity.UserAccount;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BoxService_hardware {

    @Resource
    private OrderDao orderDao;

    @Resource
    private EmailService emailService;
    @Resource
    private BoxDao boxDao;
    @Resource
    private UserAccountDao userAccountDao;

    public boolean verify(BoxInfo boxInfo){
        String customerSerial=userAccountDao.findByRfid(boxInfo.getRfid()).getSerial();
        List<Order> list = null;
        if (boxInfo.getRole().equals("deliverer")){
            list =orderDao.findByDelivererAndBoxSerialAndStatus(customerSerial,boxInfo.getBoxSerial());

        }
        else if(boxInfo.getRole().equals("customer")){
            list=orderDao.findByCustomerAndBoxSerialAndStatus(customerSerial,boxInfo.getBoxSerial());
        }
        return list.isEmpty() ? false : true;

    }

    public boolean statusChange(BoxInfo boxInfo){
        String customerSerial=userAccountDao.findByRfid(boxInfo.getRfid()).getSerial();
        if (boxInfo.getRole().equals("deliverer")){
            List<Order> list =orderDao.findByDelivererAndBoxSerialAndStatus(customerSerial,boxInfo.getBoxSerial());
            orderDao.updateStatusByPlace(customerSerial, boxInfo.getBoxSerial());
            boxDao.updateStatusBySerial(boxInfo.getBoxSerial(),"occupied");
            System.out.println(list);
            for(Order item :list){

                emailService.statusNotificationMail(item,"deliverer");
            }
            return true;
        }




        else if(boxInfo.getRole().equals("customer")){
            List<Order> list =orderDao.findByCustomerAndBoxSerialAndStatus(customerSerial,boxInfo.getBoxSerial());
            orderDao.updateStatusByPick(customerSerial, boxInfo.getBoxSerial());
            boxDao.updateStatusBySerial(boxInfo.getBoxSerial(),"free");
            for(Order item :list){
                emailService.statusNotificationMail(item,"customer");
            }
            return true;

        }
        return false;

    }

}