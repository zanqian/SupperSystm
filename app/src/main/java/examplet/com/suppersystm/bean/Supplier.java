package examplet.com.suppersystm.bean;

import java.io.Serializable;

/**
 * Created by pc on 2019/5/5.
 */
//供货商
public class Supplier implements Serializable {
    private String id;//供货商id
    private String code;//编号
    private String name;//供货商名
    private String address;//地址
    private String phone;//供货商联系电话
    private String cPersonPhone;//
    private String cPerson;
    private String legalPerson;//合法人

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getcPersonPhone() {
        return cPersonPhone;
    }

    public void setcPersonPhone(String cPersonPhone) {
        this.cPersonPhone = cPersonPhone;
    }

    public String getcPerson() {
        return cPerson;
    }

    public void setcPerson(String cPerson) {
        this.cPerson = cPerson;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }
}
