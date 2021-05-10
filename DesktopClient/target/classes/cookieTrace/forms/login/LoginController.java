package cookieTrace.forms.login;

import cookieTrace.ActualMain;
import cookieTrace.forms.mainMenu.MainMenuMain;
import cookieTrace.netConector.NetConector;
import cookieTrace.netConector.NetProcess;
import cookieTrace.protocol.Protocol;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
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

        Protocol prol = new Protocol(0,null,"Login",
                "user",email.getText(),"pass",psswrd.getText());

        NetProcess np = new NetProcess(this,net,prol,"successLogin","errormsg","timeoutmsg");


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
        errorText.setText((String) prol.getBody());
    }

    public void successLogin(Protocol prol){
        MainMenuMain main = new MainMenuMain();
        LoginController controller = this;
        try {

            Platform.runLater(new Runnable(){

                @Override
                public void run() {
                    try {
                        main.start(controller);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(){
        Stage stage = (Stage) errorText.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    public void timeoutmsg(){
        loading.setVisible(false);
        errorText.setText("Cloud not connect to server. Try again later");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loading.setVisible(false);
        ActualMain.net = new NetConector();
        net = ActualMain.net;
    }
}
