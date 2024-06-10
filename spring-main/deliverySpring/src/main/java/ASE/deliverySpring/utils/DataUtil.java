package ASE.deliverySpring.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class DataUtil implements Serializable {


    public static<T> List<T> JsonText2List(String jsonText,Class<T> clazz){

        List<T> array = JSONObject.parseArray(jsonText,clazz);

        return array;
    }

    public static<T> T JsonText2Java(String jsonText,Class<T> clazz){

        T t = JSONObject.parseObject(jsonText,clazz);

        return t;
    }

//    public static Map<String,Object> result2Map(Result object){
//
//        String jsonText = JSON.toJSONString(object.getData());
//
//        Map<String,Object> data = JSONObject.parseObject(jsonText,Map.class);
//
//        return data;
//    }
    /**
     * 获取公共序列号
     * @return
     */
    public static String getComSerial(){

        String[] temp = UUID.randomUUID().toString().split("-");

        StringBuilder uuid = new StringBuilder();

        for (String s : temp)
            uuid.append(s);

        return getCurDate()+uuid.toString();
    }

    /**
     * 获取当前时间202012
     * @return
     */
    public  static String getCurDate(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

        String dateStr = sdf.format(new Date());

        return dateStr;
    }

    /**
     * 按格式获取时间
     * @param format
     * @return
     */
    public static String getDateFormat(String format){

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        String dateStr = sdf.format(new Date());

        return dateStr;
    }
    /**
     * 获取文件后缀
     * @param fileupload
     * @return
     */
    public static String getFileSuffix(MultipartFile fileupload){

        String originalFilename = fileupload.getOriginalFilename();

        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        return suffix;
    }

    /**
     * 对象转换为字节码
     * @param obj
     * @return
     * @throws Exception
     */
    public static byte[] getBytesFromObject(Serializable obj) throws Exception {
        if (obj == null) {
            return null;
        }
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);
        return bo.toByteArray();
    }

    /**
     * 字节码转换为对象
     * @param objBytes
     * @return
     * @throws Exception
     */
    public static Object getObjectFromBytes(byte[] objBytes) throws Exception {
        if (objBytes == null || objBytes.length == 0) {
            return null;
        }
        ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
        ObjectInputStream oi = new ObjectInputStream(bi);
        return oi.readObject();
    }

    /**
     * 生成前缀 时间+任意字符串
     */
    public static String getPreFileForDate(String random){

        String now = getDateFormat("yyyyMMddHHmmss");

        if (StringUtils.isEmpty(random)){
            return now+"_";
        }else {
            return "";
        }
    }

    /**
     * 生成订单流水号 /
     * @return /
     */
    public static String getPayID(){

        String str = DataUtil.getDateFormat("yyyyMMddHHmmss");
        String random = ((int) ((Math.random() * 9 + 1) * 1000)) + "";
        return random+str;
    }

    /**
     * 执行密码加密
     * @param password 原始密码
     * @return 加密后的密文
     */
     public   static String getMd5Password(String password) {

        password = DigestUtils.md5DigestAsHex(password.getBytes()).toUpperCase();

        return password;
    }

}
