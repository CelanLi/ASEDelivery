package ASE.deliverySpring.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 描述:
 * 订单实体类
 * @author
 * @version 1.0
 * 版权所有：
 * @className Order
 * @projectName deliverySpring
 * @date 2022/12/11
 */
@Data
@NoArgsConstructor
@Document("tbl_order")
public class Order {

    @Id
    private String id;

    /**
     * 唯一编号
     */
    @Indexed(unique = true)
    private String serial;

    /**
     * 货物编号
     */
    private String productSerial;

    /**
     * 货物名称
     */
    private String productName;

    /**
     * 货物数量
     */
    private int num;

    /**
     * 订单状态
     */
    private String status;

    /**
     * 提交时间
     */
    private String subTime;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 所属货物对象
     */
    private Product product;

    /**
     * 下单客户的编号
     */
    private String userAccountSerial;

    /**
     * 下单客户的名称
     */
    private String realName;

    /**
     * 送货员编号
     */
    private String deliverySerial;

    /**
     * 送货员名称
     */
    private String deliveryName;

    private String boxSerial;


}
