package cookieTrace.forms.mainMenu;

import cookieTrace.forms.login.LoginController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuMain{


    public void start(LoginController controller) throws Exception{

                Scene root = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("forms/mainMenu/form.fxml")));
                controller.close();
                Stage stage = new Stage();
                stage.setHeight(800);
                stage.setWidth(1000);
                stage.setTitle("Shelter");
                stage.getIcons().add(new Image("forms/login/image.png"));
                stage.setScene(root);
                stage.show();


    }

}
