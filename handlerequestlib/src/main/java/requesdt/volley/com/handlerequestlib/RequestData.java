package requesdt.volley.com.handlerequestlib;

/**
 * Created by baby on 2017/5/26.
 */

public class RequestData
{
    private String name;
    private String password;

    @Override
    public String toString() {
        return "RequestData{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RequestData(String name, String password) {
        this.name = name;
        this.password = password;


    }
}
