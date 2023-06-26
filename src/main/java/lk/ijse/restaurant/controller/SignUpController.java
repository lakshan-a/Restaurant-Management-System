package lk.ijse.restaurant.controller;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.restaurant.dto.AdminDto;
import lk.ijse.restaurant.model.AdminModel;
import lk.ijse.restaurant.util.AlertController;
import lk.ijse.restaurant.util.Navigation;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Hyperlink alreadyHaveAnAccount;

    @FXML
    private TextField confrimPasswordTxt;

    @FXML
    private TextField emailTxt;

    @FXML
    private TextField passwordTxt;

    @FXML
    private AnchorPane root;

    @FXML
    private JFXButton signUpBtn;

    @FXML
    private TextField userNameTxt;

    @FXML
    void alreadyHaveAnAccountClick(ActionEvent event) throws IOException {
        Navigation.switchNavigation("Login_form.fxml",event);

    }

    @FXML
    void signUpBtnOnAction(ActionEvent event) throws IOException {
        if (userNameTxt.getText().isEmpty()|passwordTxt.getText().isEmpty()|confrimPasswordTxt.getText().isEmpty()|
        emailTxt.getText().isEmpty()){
            AlertController.errormessage("please fill all empty fields before sign up");
            userNameTxt.setStyle("-fx-border-color: red");
            passwordTxt.setStyle("-fx-border-color: red");
            confrimPasswordTxt.setStyle("-fx-border-color: red");
            emailTxt.setStyle("-fx-border-color: red");
        }else{
            String username = userNameTxt.getText();
            String password = passwordTxt.getText();
            String email = emailTxt.getText();

            AdminDto user = new AdminDto(password, username, email);

            if (passwordTxt.getText().equals(confrimPasswordTxt.getText())) {
                boolean isSaved = false;

                try {
                    isSaved = AdminModel.save(user);
                    System.out.println("saved");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (isSaved) {
                    signUpBtn.getScene().getWindow().hide();
                    Navigation.switchNavigation("Login_form.fxml",event);
                    emailTxt.setText("");
                    userNameTxt.setText("");
                    passwordTxt.setText("");
                    confrimPasswordTxt.setText("");

                }


            } else {
                AlertController.errormessage("Password dont match.Please check your password!!");
            }
        }


    }

    @FXML
    void initialize() {


    }

    public void btnBackOnAction(ActionEvent event) {
        try {
            Navigation.switchNavigation("Login_form.fxml", event);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
