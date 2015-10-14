package eizougraphic.sintret.finsrecipe.sql;


public class Order {

    public String id;
    public String customer;
    public String address;
    public String address2;
    public String phone;
    public String phone2;
    public String kodepos;
    public String provinsi;
    public String kabupaten;
    public String kecamatan;
    public String subTotal;
    public String discount;
    public String total;
    public String shippingFee;
    public String paymentMethod;
    public String deliveryDate;
    public String deliveryHour;
    public String deliveryTime;
    public String remark;
    public String items;

    public Order(String id, String customer, String address, String address2, String phone, String phone2, String kodepos,
                 String provinsi, String kabupaten, String kecamatan, String subTotal,String discount, String total, String shippingFee, String paymentMethod,
                 String deliveryDate, String deliveryHour, String deliveryTime, String remark, String items){

        this.id=id;
        this.customer=customer;
        this.address=address;
        this.address2=address2;
        this.phone=phone;
        this.phone2=phone2;
        this.kodepos=kodepos;
        this.provinsi=provinsi;
        this.kabupaten=kabupaten;
        this.kecamatan=kecamatan;
        this.subTotal=subTotal;
        this.discount=discount;
        this.total=total;
        this.shippingFee=shippingFee;
        this.paymentMethod=paymentMethod;
        this.deliveryDate=deliveryDate;
        this.deliveryHour=deliveryHour;
        this.deliveryTime=deliveryTime;
        this.remark=remark;
        this.items=items;


    }


}
