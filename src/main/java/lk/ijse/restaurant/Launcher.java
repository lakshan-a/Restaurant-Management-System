package lk.ijse.restaurant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent parent =  FXMLLoader.load(getClass().getResource("/view/MenuWindow_form.fxml"));
        stage.setScene(new Scene(parent));
        stage.setTitle("Dashboard");
        stage.centerOnScreen();

        stage.show();
    }
}
