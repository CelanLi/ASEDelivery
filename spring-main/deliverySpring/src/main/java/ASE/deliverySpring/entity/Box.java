package ASE.deliverySpring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("tbl_box")
public class Box {

    @Id
    private String id;


    /**
     * 唯一编号
     */
    @Indexed(unique = true)
    private String serial;

    /**
     * 快递箱地址
     */
    private String address;

    /**
     * 快递箱名字
     */
    private String name;

    /**
     * 是否为空
     */
    private String status;

    /**
     * 注册时间
     */
    private String subTime;


}