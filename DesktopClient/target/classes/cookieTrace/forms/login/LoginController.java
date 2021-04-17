package cookieTrace.forms.login;

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
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    NetConectorLogin net;

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
        

        new Thread(new Runnable() {

            public void run() {
                if(!email.getText().equals("") && !psswrd.getText().equals(""))
                    net.sendLogin(email.getText(),psswrd.getText());
                else
                    errormsg("Please enter an E-mail and a password");
            }
        }).start();

    }
    public void invocarMetodoClass(Method m){

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

    public void errormsg(String msg){
        loading.setVisible(false);
        errorText.setText(msg);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loading.setVisible(false);
        net = new NetConectorLogin(this);
    }
}
