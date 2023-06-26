package lk.ijse.restaurant.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.restaurant.dto.AdminDto;
import lk.ijse.restaurant.model.AdminModel;
import lk.ijse.restaurant.util.AlertController;
import lk.ijse.restaurant.util.LoginMessageController;
import lk.ijse.restaurant.util.Navigation;

public class LoginFormController {
    String username;
    String selectJob;
    String password;
    String jobTitle;
    String userr;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Hyperlink dontHaveAnAccount;


    @FXML
    private AnchorPane root;
    @FXML
    private TextField passsowrdTxt;
    @FXML
    private TextField userNameTxt;
    @FXML
    void dontHaveAnAcoountClick(ActionEvent event) throws IOException {
        Navigation.switchNavigation("signUpForm.fxml",event);

    }

    @FXML
    void loginBtnOnAction(ActionEvent event) throws IOException {
        username = userNameTxt.getText();
        password = null;
        try {
            AdminDto user = AdminModel.SearchById(username);
            password = user.getPassword();
            userr = user.getUsername();
        } catch (Exception e) {
        }
        if (passsowrdTxt.getText().equals(password) ) {
            HomepageController.getUsername(userr);
           //AlertController.confirmmessage("Login sucessfull");
            LoginMessageController.loginsuccessfulmsg();
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event1 -> {
                try {
                    Navigation.switchNavigation("MenuWindow_form.fxml",event);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }));
            timeline.play();
        }else if (userNameTxt.getText().isEmpty() && passsowrdTxt.getText().isEmpty()) {
            AlertController.errormessage("Username field and Password field can't be empty");
            passsowrdTxt.setStyle("-fx-border-color: red; -fx-border-width: 3 3 3 3;");
            userNameTxt.setStyle("-fx-border-color: red; -fx-border-width: 3 3 3 3;");


        }else if (!userNameTxt.getText().equals(userr) && !passsowrdTxt.getText().equals(password)) {
            AlertController.errormessage("Username or password is incorrect.please check your details again!!");
            userNameTxt.setStyle("-fx-border-color: red; -fx-border-width: 3 3 3 3;");
            passsowrdTxt.setStyle("-fx-border-color: red; -fx-border-width: 3 3 3 3;");


        }



    }


    @FXML
    void initialize() {


    }

}
