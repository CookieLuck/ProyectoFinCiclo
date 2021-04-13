package cookieTrace.forms.login;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    NetConectorLogin net;

    @FXML
    Pane loading;

    @FXML
    Button loginButton;

    @FXML
    Text errorText;

    public LoginController(){
        
    }

    @FXML
    public void sendRequest() throws InterruptedException {
        loading.setVisible(true);
        net.sendLogin("TEST");
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
        net.start();
    }
}
