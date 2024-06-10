package ASE.deliverySpring.service;


import ASE.deliverySpring.dao.BoxDao;
import ASE.deliverySpring.dao.OrderDao;
import ASE.deliverySpring.dao.UserAccountDao;
import ASE.deliverySpring.entity.Box;
import ASE.deliverySpring.entity.Order;
import ASE.deliverySpring.entity.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UserAccountDao userAccountDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private BoxDao boxDao;

//    public void sendSimpleMessage(
//            EmailParas emailParas) {
//        String subject="";
//        String text="";
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("wangzidongchina@gmail.com");
//        message.setTo(emailParas.getEmailAddress());
//
//        if (emailParas.getEmailType().equals("pick-up")){
//            subject="pick-up";
//            text="Dear "+emailParas.getUserName()+",\nYou have successfully picked up your packet"+emailParas.getPacketID();
//        }
//        message.setSubject(subject);
//        message.setText(text);
//        emailSender.send(message);
//    }
//    send mail when creating an account
    public void userCreationMail(UserAccount userAccount) {
        String subject="accountCreation";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("wangzidongchina@gmail.com");
        message.setTo(userAccount.getEmail());
        String text="Dear "+userAccount.getRealName()+",\nYour account is successfully created. "
        +"Your account number is "+userAccount.getAccount()+", and the password is "+userAccount.getPassword()+". Now you can log in on our website to track.";
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

//    send  mail when creating delivery
    public void orderCreationMail(Order order) {
        UserAccount userAccount= userAccountDao.findBySerial(order.getUserAccountSerial());
        String subject="deliveryCreation";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("wangzidongchina@gmail.com");
        message.setTo(userAccount.getEmail());
        String text="Dear "+userAccount.getRealName()+",\nYour packet will be delivered. "
                +"You can use the order serial "+order.getSerial()+" to track the packet on our website.";
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    //send mail when the delivery is collected
    public void deliveryCollectionMail(Order order) {
        UserAccount userAccount= userAccountDao.findBySerial(order.getUserAccountSerial());
        String subject="deliveryCollection";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("wangzidongchina@gmail.com");
        message.setTo(userAccount.getEmail());
        String text="Dear "+userAccount.getRealName()+",\nYour packet(order serial: "+order.getSerial()+" ) is collected and on the way to you";
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    //send mail when the delivery is placed or picked up
    public void statusNotificationMail(Order order,String role) {
        UserAccount userAccount= userAccountDao.findBySerial(order.getUserAccountSerial());
        Box box=boxDao.findBySerial(order.getBoxSerial());
        String text="";
        String subject="deliveryStatusChange";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("wangzidongchina@gmail.com");
        message.setTo(userAccount.getEmail());
        if (role.equals("deliverer")){
            text="Dear "+userAccount.getRealName()+",\nYour packet (order serial: "+order.getSerial()+" ) is placed in box "+
                 box.getSerial()+" at "+box.getAddress() +" Please pick the packet up with your token.";}
        else if(role.equals("customer")){
            text="Dear "+userAccount.getRealName()+",\nYour packet (order serial: "+order.getSerial()+" ) is successfully picked up"+
                 " from box "+box.getSerial()+" at "+box.getAddress();}

        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
