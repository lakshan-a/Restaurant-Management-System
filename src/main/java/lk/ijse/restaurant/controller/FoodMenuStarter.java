package lk.ijse.restaurant.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class FoodMenuStarter {

    @FXML
    private Button btnNext;

    @FXML
    private Button btnBack;;

    @FXML
    private AnchorPane starter;

    @FXML
    void btnNextOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/FoodMenu/FoodMenuSeaFood_form.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            starter.getChildren().clear();
            starter.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void btnBackOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/FoodMenu/FoodMenuMainDishes_form.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            starter.getChildren().clear();
            starter.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
