package examplet.com.suppersystm.login;

/**
 * Created by pc on 2019/3/15.
 */

public interface ILogin {
    public void saveUserPsw(String user, String psw);
    public void saveRemberState(boolean isRember);
    public void loginNet();
}
