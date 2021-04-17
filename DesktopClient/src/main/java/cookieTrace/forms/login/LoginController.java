package cookieTrace.forms.login;

import cookieTrace.netConector.NetConector;
import cookieTrace.netConector.NetProcess;
import cookieTrace.protocol.Protocol;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.DatagramSocket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    NetConector net;

    @FXML
    TextField email;

    @FXML
    PasswordField psswrd;

    @FXML
    Pane loading;

    @FXML
    Button loginButton;

    @FXML
    Text errorText;

    public LoginController(){
        
    }

    @FXML
    public void sendRequest(){
        loading.setVisible(true);

        Class controllerClass = this.getClass();
        Object controller = this;
        
        Protocol prol = new Protocol(0,null,"Login",null,
                "user",email.getText(),"pass",psswrd.getText());

        NetProcess np = new NetProcess(this,net,prol,"errormsg","errormsg","errormsg");
    }
    @FXML
    public void sendRequestKey(KeyEvent event){

        if (event.getCode() == KeyCode.ENTER) {
            if(!loading.isVisible())
                sendRequest();
        }
    }

    public void notLoading(){
        loading.setVisible(false);
    }

    public void errormsg(Protocol prol){
        loading.setVisible(false);
        errorText.setText(prol.getBody());

    }
    public void errormsg(){
        loading.setVisible(false);
        errorText.setText("Time out");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loading.setVisible(false);
        net = new NetConector();
        //net = new NetConectorLogin(this);
    }
}
