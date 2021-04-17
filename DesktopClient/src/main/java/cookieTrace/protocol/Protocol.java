package cookieTrace.protocol;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.lang.reflect.Field;
import java.util.Map;

public class Protocol {

    int HEADER;
    String body;
    String action;
    Map<String, String> args;
    String token;

    public Protocol(String xml){
        Gson gson = new Gson();
        Protocol prol = gson.fromJson(xml,Protocol.class);
        for (Field field : prol.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                field.set(this, field.get(prol));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


    }

    public Protocol(){
        super();
    }

    public int getHEADER() {
        return HEADER;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setHEADER(int HEADER) {
        this.HEADER = HEADER;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map<String, String> getArgs() {
        return args;
    }

    public void setArgs(Map<String, String> args) {
        this.args = args;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String toString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
