package ASE.deliverySpring.utils;

import ASE.deliverySpring.entity.UserAccount;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.*;

/**
 * 描述:
 *
 * @author
 * @version 1.0
 * 版权所有：
 * @className ExcelUtil
 * @projectName deliverySpring
 * @date 2022/12/14
 */
public class ExcelUtil {

    public static List<UserAccount> readExecl(){

        List<UserAccount> maps = new ArrayList<>();

        try{

            XSSFWorkbook book = new XSSFWorkbook("/Users/sunzhe/Desktop/mongodb_user.xlsx");
            XSSFSheet sheet = book.getSheetAt(0);

            //获取excel总行数
            int rows = sheet.getPhysicalNumberOfRows();
            //从第一行开始遍历，第0行是表头，如果不要表头，则从第0行开始就行
            for (int i=1;i<rows;i++){

                UserAccount m1 = new UserAccount();
                m1.setSerial(DataUtil.getComSerial());

                //根据行号获取每一行的列数
                int cols = sheet.getRow(i).getPhysicalNumberOfCells();

                //遍历每一列，每一列相当于实体类的一个字段
                for (int j=0;j<cols;j++){

                    //取出列值
                    Cell value = sheet.getRow(i).getCell(j);

                    //通过列的下标来决定你取的是哪个字段，这里是用户的数据，这和你自己做的表格有关系
                    //第一列
                    if (j == 0){
                        m1.setAccount(value.getStringCellValue());

                        //第二列
                    }else if ( j == 1){
                        //密码如果是数字则转换为字符串
                        if (value.getCellType() == CellType.NUMERIC){
                            value.setCellType(CellType.STRING);
                        }
                        m1.setPassword(value.getStringCellValue());

                        //第三列
                    }else if (j == 2){
                        m1.setRealName(value.getStringCellValue());

                        //第四列
                    }else if (j==3){
                        m1.setRole(value.getStringCellValue());
                    }
                }
                maps.add(m1);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return maps;
    }
}
