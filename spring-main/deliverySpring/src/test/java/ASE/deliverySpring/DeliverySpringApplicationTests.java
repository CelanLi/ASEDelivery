package ASE.deliverySpring;



import ASE.deliverySpring.dao.BoxDao;
import ASE.deliverySpring.dao.OrderDao;
import ASE.deliverySpring.dao.UserAccountDao;

import ASE.deliverySpring.entity.UserAccount;
import ASE.deliverySpring.service.BoxService;
import ASE.deliverySpring.service.EmailService;
import ASE.deliverySpring.service.LegitimacyTest;
import ASE.deliverySpring.service.UserAccountService;
import ASE.deliverySpring.utils.Result;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@SpringBootTest
@RunWith(SpringRunner.class)
class DeliverySpringApplicationTests {

	@Resource
	BoxService boxService;

	@Resource
	OrderDao orderDao;
	@Resource
	BoxDao boxDao;
	@Resource
	UserAccountDao userAccountDao;

	@Resource
	EmailService emailService;

	@Resource
	LegitimacyTest legitimacyTest;

	@Resource
	UserAccountService userAccountService;
	@Test
	void contextLoads() {

//		try{
            //Order order=new Order("9","21","123","AD",4,"delivering","9:00","10:00","123","sdad","345","wang","123");
			//Order order2=new Order(null,"24","122","AD",4,"delivering","9:00","10:00","123","sdad","345","wang","123");
			//orderDao.save(order1);
			//orderDao.save(order2);
			//Box box1=new Box("5","789","garching","g1",null,"9:00");
//			boxDao.save(box1);
			//UserAccount userAccount=new UserAccount("8","2434","6","1231","customer","wang","123","123","123","804667287@qq.com");
//			userAccountDao.save(userAccount);
//			BoxInfo boxInfo=new BoxInfo();
//
//			boxInfo.setBoxSerial("123");
//			boxInfo.setRole("customer");
//			boxInfo.setUserSerial("123");


//			boxService.statusChange(boxInfo);
//			if(!legitimacyTest.customerUniquenessTest(order)){
//				System.out.println("The box is allocated,please choose another one");
//			}
//
//            else if(orderDao.save(order)){
//
//                System.out.println("order placting successful");
//                emailService.orderCreationMail(order);
//            }
//            else{
//                System.out.println("failed");
//            }

//			order.setStatus("collected");

//			if (orderDao.update(order)){
//				emailService.deliveryCollectionMail(order);
//				System.out.println("successful");
//			}
//
//			else{
//				System.out.println("failed status update");
//			}
//			if(userAccountDao.save(userAccount)){
//				System.out.println("successful");
//				emailService.userCreationMail(userAccount);
//			}
//		}catch (Exception e){
//			e.printStackTrace();
//		}

		UserAccount userAccount = userAccountService.findByPassword("dispatcher","1231");

		if (userAccount == null){
			System.out.println("账号密码错误");
		}

		System.out.println(userAccount);


	}

}
