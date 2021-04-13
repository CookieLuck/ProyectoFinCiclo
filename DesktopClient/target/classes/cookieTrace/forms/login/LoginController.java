package cookieTrace.forms.login;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

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
        Conectioncloser cc = new Conectioncloser(net.socket);

        new Thread(new Runnable() {

            public void run() {
                    net.sendLogin(email.getText()+" "+psswrd.getText(), cc);
            }
        }).start();

        cc.start();
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
