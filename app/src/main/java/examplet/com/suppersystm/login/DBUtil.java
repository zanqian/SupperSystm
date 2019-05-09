package examplet.com.suppersystm.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import examplet.com.suppersystm.bean.Account;


/**
 * Created by pc on 2019/3/19.
 */

public class DBUtil {
    private String name=null;
    private String pass=null;
    public DBUtil(String m, String p){
        this.name=m;
        this.pass=p;
    }
    private static Connection getSQLConnection(String ip, String user, String pwd, String db){
        Connection con = null;
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:jtds:sqlserver://"+ip+":1433/"+db+";chatset=utf8",user,pwd);

        }catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch(SQLException e)
        {

            e.printStackTrace();
        }
        return con;
    }
    public Account QuerySQL() {
//        String result = "";
//        String s1="1";

        Account account=new Account();
        try{
            Connection conn = getSQLConnection("192.168.6.158","sa","13208196191","user");
            String sql = "select id,username,password,type from t_base_account where username=? and password=?";
//            String sql = "select username from t_base_account where username=? and password=?";
            PreparedStatement stat= conn.prepareStatement(sql);
//            Statement stat=conn.createStatement();
            stat.setString(1,name);
            stat.setString(2,pass);
            ResultSet rs=stat.executeQuery();
            while(rs.next()){

                account.setId(rs.getInt("id"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                String type=rs.getString("type");
                switch (type){
                    case "1":
                        account.setType("老板");
                        break;
                    case "2":
                        account.setType("经理");
                        break;
                    case "3":
                        account.setType("营业员");
                        break;
                    default:
                        account.setType("普通员工");
                        break;
                }

//                String mzh=rs.getString("username");
//                String mpw=rs.getString("password");
//                s1=mzh;
//                result="1";
            }
            rs.close();//关闭通道
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
//            result += "查询异常！"+e.getMessage();
        }
        return account;
//        return  s1;
//        return result;
    }
    public static void main(String[] args) {

    }
}
