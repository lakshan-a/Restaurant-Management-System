package lk.ijse.restaurant.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class HomePage3 {

    @FXML
    private AnchorPane Homepage3;

    @FXML
    private Button btnhome1;

    @FXML
    private Button btnhome2;

    @FXML
    private Button btnhome3;

    @FXML
    void btnhome1OnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/HomePage/HomePage1_form.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            Homepage3.getChildren().clear();
            Homepage3.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnhome2OnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/HomePage/HomePage2_form.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            Homepage3.getChildren().clear();
            Homepage3.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnhome3OnAction(ActionEvent event) throws IOException {


    }

}