package lk.ijse.restaurant.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.restaurant.dto.AttendeDto;
import lk.ijse.restaurant.dto.Tm.AttendTm;
import lk.ijse.restaurant.model.AttendanceModel;
import lk.ijse.restaurant.model.EmployeeModel;
import lk.ijse.restaurant.util.AlertController;
import lk.ijse.restaurant.util.DataValidateController;

public class EmployeeAttendanceFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private ComboBox<String> cmbEmpId;

    @FXML
    private TableColumn<?, ?> colAttend;

    @FXML
    private TableColumn<?, ?> colAttendId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colWorkingHourese;

    @FXML
    private TableView<AttendTm> tblEmpAttend;

    @FXML
    private TextField txtAttend;

    @FXML
    private TextField txtAttendId;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtWorkingHourse;

    AttendeDto attend ;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if(cmbEmpId.getSelectionModel().isEmpty() || txtDate.getEditor().getText().isEmpty()){
            if(cmbEmpId.getSelectionModel().isEmpty() && txtDate.getEditor().getText().isEmpty()){
                AlertController.errormessage("Employee Id and Date are empty");
            } else if(cmbEmpId.getSelectionModel().isEmpty()){
                AlertController.errormessage("Employee Id is empty");
            }else if (txtDate.getEditor().getText().isEmpty()){
                AlertController.errormessage("Date is empty");
            }
        }else {
            attend.setAID(txtAttendId.getText());
            attend.setEID(cmbEmpId.getValue());
            attend.setAttendance(txtAttend.getText());
            attend.setWorkingHourse(Integer.valueOf(txtWorkingHourse.getText()));
            attend.setDate(String.valueOf(txtDate.getValue()));

            try {
                boolean isSaved = AttendanceModel.save(attend);
                if(isSaved){
                    AlertController.confirmmessage("Saved successfully");
                    defaultTxt();
                    btnAdd.setDisable(true);
                    btnUpdate.setDisable(true);
                    btnDelete.setDisable(true);
                    getAll();
                    clearTextField();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        boolean result = AlertController.okconfirmmessage("Are you sure you want to delete ?");
        if(result) {
            try {
                String attendId = txtAttendId.getText();

                boolean isDeleted = AttendanceModel.delete(attendId);
                if (isDeleted) {
                    getAll();
                    clearTextField();
                } else {
                    AlertController.errormessage("Not Deleted");
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
                AlertController.errormessage("Something went wrong");
            }
        }

    }

    @FXML
    void btnUpdatOnAction(ActionEvent event) {
        boolean result = AlertController.okconfirmmessage("Are you sure you want to update ?");
        if (result) {

            attend.setAID(txtAttendId.getText());
            attend.setEID(cmbEmpId.getValue());
            attend.setAttendance(txtAttend.getText());
            attend.setWorkingHourse(Integer.valueOf(txtWorkingHourse.getText()));
            attend.setDate(String.valueOf(txtDate.getValue()));

            try {
                boolean isUpdated = AttendanceModel.update(attend);
                if (isUpdated) {
                    AlertController.confirmmessage("updated successfully");
                    defaultTxt();
                    btnAdd.setDisable(true);
                    btnUpdate.setDisable(true);
                    btnDelete.setDisable(true);
                    getAll();
                    clearTextField();
                } else {
                    AlertController.errormessage("Not Updated");
                }

            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println(e);
                AlertController.errormessage(e.getMessage());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                AlertController.errormessage("Something went wrong");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void tblAttendOnMouseClicked(MouseEvent event) {
        btnAdd.setDisable(false);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);

        TablePosition pos=tblEmpAttend.getSelectionModel().getSelectedCells().get(0);
        int row=pos.getRow();

        ObservableList<TableColumn<AttendTm,?>> columns=tblEmpAttend.getColumns();

        txtAttendId.setText(columns.get(0).getCellData(row).toString());
        cmbEmpId.setValue(columns.get(1).getCellData(row).toString());
        txtWorkingHourse.setText(columns.get(2).getCellData(row).toString());
        txtAttend.setText(columns.get(3).getCellData(row).toString());
        txtDate.setValue(LocalDate.parse(columns.get(4).getCellData(row).toString()));

    }

    private void clearTextField() {
        txtAttendId.setText("");
        txtAttend.setText("");
        txtWorkingHourse.setText("");
        txtDate.setValue(null);
        cmbEmpId.setValue(null);
    }

    private void setCellValueFactory() {
        colAttendId.setCellValueFactory(new PropertyValueFactory<>("AID"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("EID"));
        colAttend.setCellValueFactory(new PropertyValueFactory<>("attendance"));
        colWorkingHourese.setCellValueFactory(new PropertyValueFactory<>("workingHourse"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void getAll() {
        try {
            ObservableList<AttendTm> obList = AttendanceModel.getAll();

            tblEmpAttend.setItems(obList);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadEmpIds() {
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

    void defaultTxt(){
        txtAttendId.setStyle("-fx-border-color: transparent");
        txtAttend.setStyle("-fx-border-color: transparent");
        txtWorkingHourse.setStyle("-fx-border-color: transparent");
    }

    @FXML
    void initialize() {
        attend = new AttendeDto();

        btnAdd.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        setCellValueFactory();
        getAll();
        loadEmpIds();
    }

    @FXML
    void txtAttendIdOnKeytypedAction(KeyEvent event) {
        boolean isValidate= DataValidateController.attendIdValidate(txtAttendId.getText());
        btnAdd.setDisable(!isValidate|txtAttendId.getText().isEmpty()|txtWorkingHourse.getText().isEmpty() |txtAttend.getText().isEmpty());
        btnUpdate.setDisable(!isValidate|txtAttendId.getText().isEmpty()|txtWorkingHourse.getText().isEmpty() |txtAttend.getText().isEmpty());
        btnDelete.setDisable(!isValidate|txtAttendId.getText().isEmpty()|txtWorkingHourse.getText().isEmpty() |txtAttend.getText().isEmpty());
        if (isValidate){
            txtAttendId.setStyle("-fx-border-color: green");
        }else {
            txtAttendId.setStyle("-fx-border-color: red");
        }
    }

    @FXML
    void txtAttendanceOnKeyTypedAction(KeyEvent event) {
        boolean isValidate= DataValidateController.yesNoCheck(txtAttend.getText());
        btnAdd.setDisable(!isValidate|txtAttendId.getText().isEmpty()|txtWorkingHourse.getText().isEmpty() |txtAttend.getText().isEmpty());
        btnUpdate.setDisable(!isValidate|txtAttendId.getText().isEmpty()|txtWorkingHourse.getText().isEmpty() |txtAttend.getText().isEmpty());
        btnDelete.setDisable(!isValidate|txtAttendId.getText().isEmpty()|txtWorkingHourse.getText().isEmpty() |txtAttend.getText().isEmpty());
        if (isValidate){
            txtAttend.setStyle("-fx-border-color: green");
        }else {
            txtAttend.setStyle("-fx-border-color: red");
        }
    }

    @FXML
    void txtWorkinghoursOnKeyTypedAction(KeyEvent event) {
        boolean isValidate= DataValidateController.quantityValidate(txtWorkingHourse.getText());
        btnAdd.setDisable(!isValidate|txtAttendId.getText().isEmpty()|txtWorkingHourse.getText().isEmpty() |txtAttend.getText().isEmpty());
        btnUpdate.setDisable(!isValidate|txtAttendId.getText().isEmpty()|txtWorkingHourse.getText().isEmpty() |txtAttend.getText().isEmpty());
        btnDelete.setDisable(!isValidate|txtAttendId.getText().isEmpty()|txtWorkingHourse.getText().isEmpty() |txtAttend.getText().isEmpty());
        if (isValidate){
            txtWorkingHourse.setStyle("-fx-border-color: green");
        }else {
            txtWorkingHourse.setStyle("-fx-border-color: red");
        }
    }

}
