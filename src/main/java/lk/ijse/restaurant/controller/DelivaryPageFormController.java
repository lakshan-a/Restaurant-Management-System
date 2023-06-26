package lk.ijse.restaurant.controller;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.restaurant.dto.DeliveryDto;
import lk.ijse.restaurant.dto.Tm.DeliveryTm;
import lk.ijse.restaurant.model.CustomerModel;
import lk.ijse.restaurant.model.DeliveryModel;
import lk.ijse.restaurant.model.EmployeeModel;
import lk.ijse.restaurant.util.AlertController;
import lk.ijse.restaurant.util.Navigation;

public class DelivaryPageFormController implements Initializable{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private ComboBox<String> cmbEmpId;

    @FXML
    private TableColumn<?, ?> colDeliveryDate;

    @FXML
    private TableColumn<?, ?> colDeliveryId;

    @FXML
    private TableColumn<?, ?> colDeliveryStatus;

    @FXML
    private TableColumn<?, ?> colDueDate;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colLocation;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private Label lblDeliveryId;

    @FXML
    private RadioButton radioBtnNo;

    @FXML
    private RadioButton radioBtnYes;

    @FXML
    private TableView<DeliveryTm> tblDelivery;

    @FXML
    private DatePicker txtDeliveryDate;

    @FXML
    private TextField txtLocation;

    @FXML
    private TextField txtSearch;

    DeliveryDto delivery;

    @FXML
    void updateOnAction(ActionEvent event) {
        if(radioBtnYes.isSelected() || radioBtnNo.isSelected()){
            delivery.setDeliveryId(lblDeliveryId.getText());
            delivery.setEmpId(cmbEmpId.getValue());
            delivery.setLocation(txtLocation.getText());
            delivery.setDeliveryDate(String.valueOf(txtDeliveryDate.getValue()));

            boolean result = AlertController.okconfirmmessage("Are you sure you want to update ?");
            if (result) {
                try {
                    boolean isUpdated = DeliveryModel.update(delivery);
                    if (isUpdated) {
                        setCellValueFactory();
                        getAll();
                        clearTxtField();
                        AlertController.confirmmessage("Update successful");
                    }else{
                        AlertController.errormessage("Update unsuccessful");
                    }
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }

        }else{
            AlertController.errormessage("Please select delivery status");
        }
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        String id = lblDeliveryId.getText();

        boolean result = AlertController.okconfirmmessage("Are you sure you want to delete ?");
        if(result) {
            try {
                boolean isDeleted = DeliveryModel.delete(id);
                if (isDeleted) {
                    setCellValueFactory();
                    getAll();
                    clearTxtField();
                    AlertController.confirmmessage("Delete successful");
                }

            } catch (SQLException | ClassNotFoundException throwables) {
                //throwables.printStackTrace();
                System.out.println("delivery delete = " + throwables);
            }
        }
    }

    @FXML
    void searchBarOnAction(ActionEvent event) {
        try {
            delivery = DeliveryModel.search(txtSearch.getText());
            if(delivery != null){
                lblDeliveryId.setText(delivery.getDeliveryId());
                cmbEmpId.setValue(delivery.getEmpId());
                txtLocation.setText(delivery.getLocation());
                txtDeliveryDate.setValue(LocalDate.parse(delivery.getDeliveryDate()));

                if(delivery.getDeliveryStaus().equals("Yes")){
                    radioBtnYes.setSelected(true);
                }else if(delivery.getDeliveryStaus().equals("No")){
                    radioBtnNo.setSelected(true);
                }else {
                    radioBtnNo.setSelected(false);
                    radioBtnYes.setSelected(false);
                }

            }else{
                AlertController.errormessage("Invalid Id");
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    void radioBtnNoOnAction(ActionEvent event) {
        if(radioBtnNo.isSelected()){
            delivery.setDeliveryStaus("No");
        }
    }

    @FXML
    void radioBtnYesOnAction(ActionEvent event) {
        if(radioBtnYes.isSelected()){
            delivery.setDeliveryStaus("Yes");
        }
    }

    @FXML
    void searchIconOnMouseClickedAction(MouseEvent event) {
        try {
            delivery = DeliveryModel.search(txtSearch.getText());
            if(delivery != null){
                lblDeliveryId.setText(delivery.getDeliveryId());
                cmbEmpId.setValue(delivery.getEmpId());
                txtLocation.setText(delivery.getLocation());
                txtDeliveryDate.setValue(LocalDate.parse(delivery.getDeliveryDate()));

                if(delivery.getDeliveryStaus().equals("Yes")){
                    radioBtnYes.setSelected(true);
                }else if(delivery.getDeliveryStaus().equals("No")){
                    radioBtnNo.setSelected(true);
                }else {
                    radioBtnNo.setSelected(false);
                    radioBtnYes.setSelected(false);
                }

            }else{
                AlertController.errormessage("Invalid Id");
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    void tableOnMouseClicked(MouseEvent event) {
        TablePosition pos=tblDelivery.getSelectionModel().getSelectedCells().get(0);
        int row=pos.getRow();

        ObservableList<TableColumn<DeliveryTm,?>> columns=tblDelivery.getColumns();

        lblDeliveryId.setText(columns.get(0).getCellData(row).toString());
        cmbEmpId.setValue(columns.get(1).getCellData(row).toString());
        txtLocation.setText(columns.get(4).getCellData(row).toString());
        txtDeliveryDate.setValue(LocalDate.parse(columns.get(5).getCellData(row).toString()));
    }

    @FXML
    void txtSearchOnKeyTypedAction(KeyEvent event) {
        String searchValue = txtSearch.getText().trim();

        try {
            ObservableList<DeliveryTm> obList= DeliveryModel.getAll();

            if (!searchValue.isEmpty()) {
                ObservableList<DeliveryTm> filteredData = obList.filtered(new Predicate<DeliveryTm>(){
                    @Override
                    public boolean test(DeliveryTm deliveryTM) {
                        return String.valueOf(deliveryTM.getDeliveryId()).toLowerCase().contains(searchValue.toLowerCase());
                    }
                });
                tblDelivery.setItems(filteredData);
            } else {
                tblDelivery.setItems(obList);
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadEmployeeIds() {

        try {
            ObservableList<String> empIds = FXCollections.observableArrayList();
            List<String> ids = EmployeeModel.loadEmpIds();

            for(String id : ids){
                empIds.add(id);
            }

            cmbEmpId.setItems(empIds);
        } catch (SQLException | ClassNotFoundException throwables) {
            AlertController.errormessage("loadEmployeeIds() "+throwables);
        }
    }

    private void setCellValueFactory() {
        colDeliveryId.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colDeliveryDate.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colDeliveryStatus.setCellValueFactory(new PropertyValueFactory<>("deliveryStaus"));
    }

    private void getAll() {
        try {
            ObservableList<DeliveryTm> oblist = DeliveryModel.getAll();

            tblDelivery.setItems(oblist);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void clearTxtField() {
        lblDeliveryId.setText("");
        cmbEmpId.setValue(null);
        txtLocation.setText("");
        txtDeliveryDate.setValue(null);
        radioBtnYes.setSelected(false);
        radioBtnNo.setSelected(false);
    }

    @FXML
    void initialize() {
        delivery = new DeliveryDto();
        loadEmployeeIds();
        setCellValueFactory();
        getAll();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
