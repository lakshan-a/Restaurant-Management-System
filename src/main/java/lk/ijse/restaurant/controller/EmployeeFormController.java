package lk.ijse.restaurant.controller;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.restaurant.dto.EmployeeDto;
import lk.ijse.restaurant.dto.Tm.EmployeeTm;
import lk.ijse.restaurant.model.EmployeeModel;
import lk.ijse.restaurant.util.AlertController;
import lk.ijse.restaurant.util.DataValidateController;
import lk.ijse.restaurant.util.Navigation;

public class EmployeeFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton addBtn;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContactNo;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colNIC;

    @FXML
    private TableColumn<?, ?> colDOB;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContactNo;

    @FXML
    private DatePicker txtDob;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtEmpName;

    @FXML
    private TextField txtNIC;

    @FXML
    private TextField txtSearch;

    @FXML
    private JFXButton updateBtn;

    EmployeeDto employee;

    @FXML
    void btnEAttenOnAction(ActionEvent event) {
        Stage stage = new Stage();
        stage.resizableProperty().setValue(false);
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/EmployeeAttendance_form.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
         if (txtDob.getEditor().getText().isEmpty()) {
            AlertController.errormessage("dob can't be empty");
        }else{
             try {
                 employee.setEmpId(txtEmpId.getText());
                 employee.setEmpName(txtEmpName.getText());
                 employee.setAddress(txtAddress.getText());
                 employee.setDob(String.valueOf(txtDob.getValue()));
                 employee.setContactNo(txtContactNo.getText());
                 employee.setEmail(txtEmail.getText());
                 employee.setNic(txtNIC.getText());

                 boolean isSaved = EmployeeModel.save(employee);
                 if(isSaved){
                     defaultTxt();
                     addBtn.setDisable(true);
                     updateBtn.setDisable(true);
                     deleteBtn.setDisable(true);
                     setCellValueFactory();
                     getAll();
                     clearTxtField();
                     AlertController.confirmmessage("Employee added successfully");
                 }
             } catch (SQLIntegrityConstraintViolationException e) {
                 System.out.println(e);
                 AlertController.errormessage("Duplicate Employee ID");
             } catch(Exception exception){
                 AlertController.errormessage("something went wrong!");
                 System.out.println("EmpSave ="+exception);
             }
         }

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        boolean result = AlertController.okconfirmmessage("Are you sure you want to delete ?");
        if(result) {
            try {
                String empId = txtEmpId.getText();

                boolean isDeleted = EmployeeModel.delete(empId);
                if (isDeleted) {
                    addBtn.setDisable(true);
                    updateBtn.setDisable(true);
                    deleteBtn.setDisable(true);
                    setCellValueFactory();
                    getAll();
                    clearTxtField();
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
    void btnUpdateOnAction(ActionEvent event) {

            if (txtDob.getEditor().getText().isEmpty()) {
                AlertController.errormessage("dob can't be empty");
            }else{
                boolean result = AlertController.okconfirmmessage("Are you sure you want to update ?");
                if (result) {

                    employee.setEmpId(txtEmpId.getText());
                    employee.setEmpName(txtEmpName.getText());
                    employee.setAddress(txtAddress.getText());
                    employee.setDob(String.valueOf(txtDob.getValue()));
                    employee.setContactNo(txtContactNo.getText());
                    employee.setEmail(txtEmail.getText());
                    employee.setNic(txtNIC.getText());

                    try {
                        boolean isUpdated = EmployeeModel.update(employee);
                        if (isUpdated) {
                            AlertController.confirmmessage("Employee updated successfully");
                            defaultTxt();
                            addBtn.setDisable(true);
                            updateBtn.setDisable(true);
                            deleteBtn.setDisable(true);
                            getAll();
                            clearTxtField();
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

    }

    @FXML
    void empIdSearchBarOnAction(ActionEvent event) {
        String empId = txtSearch.getText();

        try {
            EmployeeDto employee = EmployeeModel.search(empId);

            if(employee != null){
                txtEmpId.setText(employee.getEmpId());
                txtEmpName.setText(employee.getEmpName());
                txtAddress.setText(employee.getAddress());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(employee.getDob(), formatter);
                txtDob.setValue(date);
                //txtDob.setValue(employee.getDob());


                txtContactNo.setText(employee.getContactNo());
                txtEmail.setText(employee.getEmail());
                txtNIC.setText(employee.getNic());
            }else{
                AlertController.errormessage("Invalid Id");
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            AlertController.errormessage("Something went wrong");
        }
    }

    @FXML
    void empIdSearchOnAction(ActionEvent event) {
        String empId = txtEmpId.getText();

        try {
            EmployeeDto employee = EmployeeModel.search(empId);

            if(employee != null){
                txtEmpId.setText(employee.getEmpId());
                txtEmpName.setText(employee.getEmpName());
                txtAddress.setText(employee.getAddress());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(employee.getDob(), formatter);
                txtDob.setValue(date);
                //txtDob.setValue(employee.getDob());


                txtContactNo.setText(employee.getContactNo());
                txtEmail.setText(employee.getEmail());
                txtNIC.setText(employee.getNic());
            }else{
                AlertController.errormessage("Invalid Id");
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            AlertController.errormessage("Something went wrong");
        }
    }

    @FXML
    void searchIconOnMouseClickedAction(MouseEvent event) {
        String empId = txtSearch.getText();

        try {
            EmployeeDto employee = EmployeeModel.search(empId);

            if(employee != null){
                txtEmpId.setText(employee.getEmpId());
                txtEmpName.setText(employee.getEmpName());
                txtAddress.setText(employee.getAddress());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(employee.getDob(), formatter);
                txtDob.setValue(date);
                //txtDob.setValue(employee.getDob());


                txtContactNo.setText(employee.getContactNo());
                txtEmail.setText(employee.getEmail());
                txtNIC.setText(employee.getNic());
            }else{
                AlertController.errormessage("Invalid Id");
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            AlertController.errormessage("Something went wrong");
        }
    }

    @FXML
    void tableOnMouseClicked(MouseEvent event) {
        addBtn.setDisable(false);
        updateBtn.setDisable(false);
        deleteBtn.setDisable(false);
        TablePosition pos=tblEmployee.getSelectionModel().getSelectedCells().get(0);
        int row=pos.getRow();

        ObservableList<TableColumn<EmployeeTm,?>> columns=tblEmployee.getColumns();

        txtEmpId.setText(columns.get(0).getCellData(row).toString());
        txtEmpName.setText(columns.get(1).getCellData(row).toString());
        txtAddress.setText(columns.get(2).getCellData(row).toString());
        txtContactNo.setText(columns.get(3).getCellData(row).toString());
        txtEmail.setText(columns.get(4).getCellData(row).toString());
        txtNIC.setText(columns.get(5).getCellData(row).toString());
        txtDob.setValue(LocalDate.parse(columns.get(6).getCellData(row).toString()));
    }

    @FXML
    void txtSearchOnKeyTypedAction(KeyEvent event) throws SQLException, ClassNotFoundException {
        String searchValue = txtSearch.getText().trim();
        ObservableList<EmployeeTm> obList = EmployeeModel.getAll();

        if (!searchValue.isEmpty()) {
            ObservableList<EmployeeTm> filteredData = obList.filtered(new Predicate<EmployeeTm>(){
                @Override
                public boolean test(EmployeeTm employeetm) {
                    return String.valueOf(employeetm.getEmpId()).toLowerCase().contains(searchValue.toLowerCase());
                }
            });
            tblEmployee.setItems(filteredData);
        } else {
            tblEmployee.setItems(obList);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
    }

    private void getAll(){
        try {
            ObservableList<EmployeeTm> obList = EmployeeModel.getAll();

            tblEmployee.setItems(obList);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void clearTxtField() {
        txtEmpId.setText("");
        txtEmpName.setText("");
        txtAddress.setText("");
        txtContactNo.setText("");
        txtEmail.setText("");
        txtNIC.setText("");
        txtDob.setValue(null);
    }

    @FXML
    void nameKeyTyped(KeyEvent event) {
        boolean isValidate = DataValidateController.customerNameValidate(txtEmpName.getText());
        addBtn.setDisable(!isValidate | txtNIC.getText().isEmpty() | txtEmail.getText().isEmpty()
                | txtAddress.getText().isEmpty() | txtEmpId.getText().isEmpty() |
                txtContactNo.getText().isEmpty());
        updateBtn.setDisable(!isValidate | txtNIC.getText().isEmpty() | txtEmail.getText().isEmpty()
                | txtAddress.getText().isEmpty() | txtEmpId.getText().isEmpty() |
                txtContactNo.getText().isEmpty());
        deleteBtn.setDisable(!isValidate | txtNIC.getText().isEmpty() | txtEmail.getText().isEmpty()
                | txtAddress.getText().isEmpty() | txtEmpId.getText().isEmpty() |
                txtContactNo.getText().isEmpty());
        if (isValidate){
            txtEmpName.setStyle("-fx-border-color: green");
        }else{
            txtEmpName.setStyle("-fx-border-color: red");
        }

    }

    @FXML
    void nicKeyTyped(KeyEvent event) {
        boolean isValidate = DataValidateController.nicValidate(txtNIC.getText());
        addBtn.setDisable(!isValidate | txtEmpName.getText().isEmpty() | txtEmail.getText().isEmpty()
                | txtAddress.getText().isEmpty() | txtEmpId.getText().isEmpty() |
                txtContactNo.getText().isEmpty());
        updateBtn.setDisable(!isValidate | txtEmpName.getText().isEmpty() | txtEmail.getText().isEmpty()
                | txtAddress.getText().isEmpty() | txtEmpId.getText().isEmpty() |
                txtContactNo.getText().isEmpty());
        deleteBtn.setDisable(!isValidate | txtEmpName.getText().isEmpty() | txtEmail.getText().isEmpty()
                | txtAddress.getText().isEmpty() | txtEmpId.getText().isEmpty() |
                txtContactNo.getText().isEmpty());
        if (isValidate){
            txtNIC.setStyle("-fx-border-color: green");
        }else{
            txtNIC.setStyle("-fx-border-color: red");
        }

    }
    @FXML
    void contactKeyTyped(KeyEvent event) {
        boolean isValidate = DataValidateController.contactCheck(txtContactNo.getText());
        addBtn.setDisable(!isValidate | txtEmpName.getText().isEmpty() | txtEmail.getText().isEmpty()
                | txtAddress.getText().isEmpty() | txtEmpId.getText().isEmpty() |
                txtNIC.getText().isEmpty());
        updateBtn.setDisable(!isValidate | txtEmpName.getText().isEmpty() | txtEmail.getText().isEmpty()
                | txtAddress.getText().isEmpty() | txtEmpId.getText().isEmpty() |
                txtNIC.getText().isEmpty());
        deleteBtn.setDisable(!isValidate | txtEmpName.getText().isEmpty() | txtEmail.getText().isEmpty()
                | txtAddress.getText().isEmpty() | txtEmpId.getText().isEmpty() |
                txtNIC.getText().isEmpty());
        if (isValidate){
            txtContactNo.setStyle("-fx-border-color: green");
        }else{
            txtContactNo.setStyle("-fx-border-color: red");
        }
    }

    @FXML
    void emailKeyTyped(KeyEvent event) {
        boolean isValidate = DataValidateController.emailCheck(txtEmail.getText());
        addBtn.setDisable(!isValidate | txtEmpName.getText().isEmpty() | txtContactNo.getText().isEmpty()
                | txtAddress.getText().isEmpty() | txtEmpId.getText().isEmpty() |
                txtNIC.getText().isEmpty());
        updateBtn.setDisable(!isValidate | txtEmpName.getText().isEmpty() | txtContactNo.getText().isEmpty()
                | txtAddress.getText().isEmpty() | txtEmpId.getText().isEmpty() |
                txtNIC.getText().isEmpty());
        deleteBtn.setDisable(!isValidate | txtEmpName.getText().isEmpty() | txtContactNo.getText().isEmpty()
                | txtAddress.getText().isEmpty() | txtEmpId.getText().isEmpty() |
                txtNIC.getText().isEmpty());
        if (isValidate){
            txtEmail.setStyle("-fx-border-color: green");
        }else{
            txtEmail.setStyle("-fx-border-color: red");
        }

    }

    @FXML
    void empIdKeyTyped(KeyEvent event) {
        boolean isValidate = DataValidateController.empIdValidate(txtEmpId.getText());
        addBtn.setDisable(!isValidate | txtEmpName.getText().isEmpty() | txtContactNo.getText().isEmpty()
                | txtAddress.getText().isEmpty() | txtEmail.getText().isEmpty() |
                txtNIC.getText().isEmpty());
        updateBtn.setDisable(!isValidate | txtEmpName.getText().isEmpty() | txtContactNo.getText().isEmpty()
                | txtAddress.getText().isEmpty() | txtEmail.getText().isEmpty() |
                txtNIC.getText().isEmpty());
        deleteBtn.setDisable(!isValidate | txtEmpName.getText().isEmpty() | txtContactNo.getText().isEmpty()
                | txtAddress.getText().isEmpty() | txtEmail.getText().isEmpty() |
                txtNIC.getText().isEmpty());
        if (isValidate){
            txtEmpId.setStyle("-fx-border-color: green");
        }else{
            txtEmpId.setStyle("-fx-border-color: red");
        }

    }
    @FXML
    void adressKeyTyped(KeyEvent event) {
        boolean isValidate = DataValidateController.addressValidate(txtAddress.getText());
        addBtn.setDisable(!isValidate | txtEmpName.getText().isEmpty() | txtContactNo.getText().isEmpty()
                | txtEmpId.getText().isEmpty() | txtEmail.getText().isEmpty() |
                txtNIC.getText().isEmpty());
        updateBtn.setDisable(!isValidate | txtEmpName.getText().isEmpty() | txtContactNo.getText().isEmpty()
                | txtEmpId.getText().isEmpty() | txtEmail.getText().isEmpty() |
                txtNIC.getText().isEmpty());
        deleteBtn.setDisable(!isValidate | txtEmpName.getText().isEmpty() | txtContactNo.getText().isEmpty()
                | txtEmpId.getText().isEmpty() | txtEmail.getText().isEmpty() |
                txtNIC.getText().isEmpty());
        if (isValidate){
            txtAddress.setStyle("-fx-border-color: green");
        }else{
            txtAddress.setStyle("-fx-border-color: red");
        }

    }

    void defaultTxt(){
        txtEmpId.setStyle("-fx-border-color: transparent");
        txtEmpName.setStyle("-fx-border-color: transparent");
        txtContactNo.setStyle("-fx-border-color: transparent");
        txtEmail.setStyle("-fx-border-color: transparent");
        txtAddress.setStyle("-fx-border-color: transparent");
        txtNIC.setStyle("-fx-border-color: transparent");
    }

    @FXML
    void initialize() {
        addBtn.setDisable(true);
        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);
        assert addBtn != null : "fx:id=\"addBtn\" was not injected: check your FXML file 'EmployeePage_form.fxml'.";
        assert colAddress != null : "fx:id=\"colAddress\" was not injected: check your FXML file 'EmployeePage_form.fxml'.";
        assert colContactNo != null : "fx:id=\"colContactNo\" was not injected: check your FXML file 'EmployeePage_form.fxml'.";
        assert colEmail != null : "fx:id=\"colEmail\" was not injected: check your FXML file 'EmployeePage_form.fxml'.";
        assert colId != null : "fx:id=\"colId\" was not injected: check your FXML file 'EmployeePage_form.fxml'.";
        assert colName != null : "fx:id=\"colName\" was not injected: check your FXML file 'EmployeePage_form.fxml'.";
        assert deleteBtn != null : "fx:id=\"deleteBtn\" was not injected: check your FXML file 'EmployeePage_form.fxml'.";
        assert tblEmployee != null : "fx:id=\"tblEmployee\" was not injected: check your FXML file 'EmployeePage_form.fxml'.";
        assert txtAddress != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'EmployeePage_form.fxml'.";
        assert txtContactNo != null : "fx:id=\"txtContactNo\" was not injected: check your FXML file 'EmployeePage_form.fxml'.";
        assert txtDob != null : "fx:id=\"txtDob\" was not injected: check your FXML file 'EmployeePage_form.fxml'.";
        assert txtEmail != null : "fx:id=\"txtEmail\" was not injected: check your FXML file 'EmployeePage_form.fxml'.";
        assert txtEmpId != null : "fx:id=\"txtEmpId\" was not injected: check your FXML file 'EmployeePage_form.fxml'.";
        assert txtEmpName != null : "fx:id=\"txtEmpName\" was not injected: check your FXML file 'EmployeePage_form.fxml'.";
        assert txtNIC != null : "fx:id=\"txtNIC\" was not injected: check your FXML file 'EmployeePage_form.fxml'.";
        assert txtSearch != null : "fx:id=\"txtSearch\" was not injected: check your FXML file 'EmployeePage_form.fxml'.";
        assert updateBtn != null : "fx:id=\"updateBtn\" was not injected: check your FXML file 'EmployeePage_form.fxml'.";

        employee = new EmployeeDto();

        setCellValueFactory();
        getAll();
    }


}
