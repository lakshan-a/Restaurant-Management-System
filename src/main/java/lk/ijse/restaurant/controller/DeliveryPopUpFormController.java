package lk.ijse.restaurant.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.restaurant.dto.DeliveryDto;
import lk.ijse.restaurant.model.DeliveryModel;
import lk.ijse.restaurant.model.EmployeeModel;
import lk.ijse.restaurant.model.OrderModel;
import lk.ijse.restaurant.model.PlaceOrderModel;
import lk.ijse.restaurant.util.AlertController;

public class DeliveryPopUpFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane ancpPopUpDelivery;


    @FXML
    private JFXButton btnSave;

    @FXML
    private ComboBox<String> cmbEmployeeId;

    @FXML
    private DatePicker dtpickerDueDate;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private TextField txtDeliveryId;

    @FXML
    private TextField txtLocation;

    DeliveryDto delivery;

    @FXML
    void cmbCustIdOnAction(ActionEvent event) {

    }

    @FXML
    void saveOnAction(ActionEvent event) {
        delivery.setDeliveryId(txtDeliveryId.getText());
        delivery.setEmpId(cmbEmployeeId.getValue());
        delivery.setOrderId(lblOrderId.getText());
        delivery.setLocation(txtLocation.getText());
        delivery.setDeliveryDate(lblDate.getText());
        delivery.setDueDate(String.valueOf(dtpickerDueDate.getValue()));

        PlaceOrderModel.saveDelivery(delivery);

        ancpPopUpDelivery.getScene().getWindow().hide();
    }

    private void loadEmployeeIds() {
        try {
            ObservableList<String> empIds = FXCollections.observableArrayList();
            List<String> ids = EmployeeModel.loadEmpIds();

            for(String id : ids){
                empIds.add(id);
            }

            cmbEmployeeId.setItems(empIds);
        } catch (SQLException | ClassNotFoundException throwables) {
            AlertController.errormessage("loadEmployeeIds() "+throwables);
        }
    }

    private void generateNextDeliveryId() {
        try {
            String id = DeliveryModel.getNextDeliveryId();
            txtDeliveryId.setText(id);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void generateNextOrderId() {
        try {
            String id = OrderModel.getNextOrderId();
            lblOrderId.setText(id);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'DeliveryPopUp_form.fxml'.";
        assert cmbEmployeeId != null : "fx:id=\"cmbEmployeeId\" was not injected: check your FXML file 'DeliveryPopUp_form.fxml'.";
        assert dtpickerDueDate != null : "fx:id=\"dtpickerDueDate\" was not injected: check your FXML file 'DeliveryPopUp_form.fxml'.";
        assert lblDate != null : "fx:id=\"lblDate\" was not injected: check your FXML file 'DeliveryPopUp_form.fxml'.";
        assert lblOrderId != null : "fx:id=\"lblOrderId\" was not injected: check your FXML file 'DeliveryPopUp_form.fxml'.";
        assert txtDeliveryId != null : "fx:id=\"txtDeliveryId\" was not injected: check your FXML file 'DeliveryPopUp_form.fxml'.";
        assert txtLocation != null : "fx:id=\"txtLocation\" was not injected: check your FXML file 'DeliveryPopUp_form.fxml'.";

        delivery = new DeliveryDto();

        loadEmployeeIds();
        generateNextOrderId();
        generateNextDeliveryId();
        lblDate.setText(String.valueOf(LocalDate.now()));
    }
}
