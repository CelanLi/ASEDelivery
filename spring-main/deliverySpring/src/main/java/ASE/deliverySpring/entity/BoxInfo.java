package ASE.deliverySpring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("customer")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class BoxInfo {

    private String boxSerial;

    private String rfid;

    private String role;

}