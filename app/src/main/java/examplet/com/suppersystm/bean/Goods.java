package examplet.com.suppersystm.bean;

import java.io.Serializable;

/**
 * Created by pc on 2019/4/3.
 */

public class Goods implements Serializable {
    private String id;//id
    private String code;//编码
    private String name;//名称
    private String stock;//库存
    private String bid;//进价
    private String price;//现价
    private String supplierId;//供货商id
    private String shelfId;//货架id


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id=id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getStock() {
        return stock;
    }
    public void setStock(String stock) {
        this.stock = stock;
    }
    public String getBid() {
        return bid;
    }
    public void setBid(String bid) {
        this.bid = bid;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getSupplierId() {
        return supplierId;
    }
    public void setSupplierId(String supplier) {
        this.supplierId = supplier;
    }
    public String getShelfId() {
        return shelfId;
    }
    public void setShelfId(String shelfId) {
        this.shelfId = shelfId;
    }

    @Override
    public String toString(){
        return "Goods {id:"+ id +",name: "+ name +",code: " + code +",stock :"
                + stock +",bid: "+ bid+",price:" +price +",supplierId :"
                +supplierId+ ",shelfId:" +shelfId+"}";
    }


}