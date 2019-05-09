package examplet.com.suppersystm.bean;

import java.io.Serializable;

/**
 * Created by pc on 2019/5/5.
 */
//会员
public class Member implements Serializable{
    private String id;//id
    private String code;//编号
    private String name;//姓名
    private String idCard;//身份证
    private String phone;//联系电话
    private String integral;//
    private String birthday;//出生日期
    private String intime;//注册日期
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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIntime() {
        return intime;
    }

    public void setIntime(String intime) {
        this.intime = intime;
    }

    @Override
    public String toString(){
        return "Member {id:"+ id +",name: "+ name +",code: " + code +",idcard :"
                + idCard +",phone: "+ phone+",integral:" +integral +",birthday :"
                +birthday+ ",intime:" +intime+"}";
    }

}
