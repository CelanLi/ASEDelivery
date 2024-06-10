package ASE.deliverySpring.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 描述:
 * 货物实体类
 * @author
 * @version 1.0
 * 版权所有：
 * @className Product
 * @projectName deliverySpring
 * @date 2022/12/3
 */

@Data
@NoArgsConstructor
@Document("tbl_product")
public class Product {

    @Id
    private String id;

    /**
     * 唯一编号
     */
    @Indexed(unique = true)
    private String serial;

    /**
     * 货物名称
     */
    private String name;

    /**
     * 货物图片
     */
    private String pic;

    /**
     * 体积
     */
    private String vol;

    /**
     * 货物数量
     */
    private int num;

    /**
     * 重量
     */
    private String wet;

    private String subTime;

    private String updateTime;
}
