package examplet.com.suppersystm.bean;

import java.io.Serializable;

/**
 * Created by pc on 2019/5/5.
 */
//货架信息
public class Shelf implements Serializable {
    private String id;//货架id
    private String code;//货架编号
    private String describe;//描述
    private String position;//位置
    private String cPerson;//
    private String cPersonPhone;
    public String getcPerson() {
        return cPerson;
    }

    public void setcPerson(String cPerson) {
        this.cPerson = cPerson;
    }

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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getcPersonPhone() {
        return cPersonPhone;
    }

    public void setcPersonPhone(String cPersonPhone) {
        this.cPersonPhone = cPersonPhone;
    }


    @Override
    public String toString() {
        return "Shelf {id:"+ id +",code: " + code +",describe :"
                + describe +",position: "+ position+",cperson:" +cPerson +",cpersonphone:" +cPersonPhone+"}";
    }
}
