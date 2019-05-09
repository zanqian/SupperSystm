package examplet.com.suppersystm.bean;

import java.io.Serializable;

/**
 * Created by pc on 2019/3/14.
 */

public class Account implements Serializable{
    private int id;//id
    private String username;//用户账号
    private String password;//密码
    private String state;//等级
    private String type;//职位
    private String name;//姓名

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}