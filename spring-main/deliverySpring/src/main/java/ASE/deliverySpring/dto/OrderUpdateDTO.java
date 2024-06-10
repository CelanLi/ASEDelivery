package ASE.deliverySpring.dto;

public class OrderUpdateDTO {
    private String serial;
    private String userAccountSerial;
    private String deliverySerial;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getDeliverySerial() {
        return deliverySerial;
    }

    public void setDeliverySerial(String deliverySerial) {
        this.deliverySerial = deliverySerial;
    }

    public String getUserAccountSerial() {
        return userAccountSerial;
    }

    public void setUserAccountSerial(String userAccountSerial) {
        this.userAccountSerial = userAccountSerial;
    }

    @Override
    public String toString() {
        return "OrderUpdateDTO{" +
                " serial='" + serial + '\'' +
                ", userAccountSerial='" + userAccountSerial + '\'' +
                ", deliverySerial='" + deliverySerial + '\'' +
                '}';
    }
}
