package lk.ijse.restaurant.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lk.ijse.restaurant.dto.FoodDto;
import lk.ijse.restaurant.dto.Tm.FoodTm;
import lk.ijse.restaurant.model.FoodModel;
import lk.ijse.restaurant.model.ItemModel;
import lk.ijse.restaurant.util.AlertController;
import lk.ijse.restaurant.util.DataValidateController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Predicate;

public class FoodFormController {

    @FXML
    private JFXButton addBtn;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private JFXButton updateBtn;

    @FXML
    private ComboBox<String> cmbDescription;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colFoodCode;

    @FXML
    private TableColumn<?, ?> colItemId;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private ComboBox<String> comItemId;

    @FXML
    private AnchorPane foodForm;

    @FXML
    private TableView<FoodTm> tblFood;

    @FXML
    private TextField txtFoodnum;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TextField txtSearch;


    @FXML
    void btnClickMenuOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/FoodMenu/FoodMenuMainDishes_form.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            foodForm.getChildren().clear();
            foodForm.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    public void updateBtnOnAction(ActionEvent event) {
        String id = txtFoodnum.getText();
        String description = cmbDescription.getValue();
        Double price = Double.valueOf(txtUnitPrice.getText());
        Integer quantity = Integer.valueOf(txtQtyOnHand.getText());
        String itemId = comItemId.getValue();


        FoodDto food = new FoodDto(id, description,price, quantity, itemId);


        try {
            boolean isUpdated = FoodModel.update(food);
            if (isUpdated) {
                AlertController.confirmmessage("Food updated sucessfully");
                defaultTxt();
                addBtn.setDisable(true);
                updateBtn.setDisable(true);
                deleteBtn.setDisable(true);
                txtFoodnum.setText("");
                cmbDescription.setValue("");
                txtUnitPrice.setText("");
                txtQtyOnHand.setText("");
                comItemId.setValue("");
                getAll();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void btnAddOnAction(ActionEvent event) {
        String id = txtFoodnum.getText();
        String description = cmbDescription.getValue();
        Double price = Double.valueOf(txtUnitPrice.getText());
        Integer quantity = Integer.valueOf(txtQtyOnHand.getText());
        String itemId = comItemId.getValue();



        FoodDto food = new FoodDto(id, description,price, quantity, itemId);


        try {
            boolean isSaved = FoodModel.save(food);
            if (isSaved) {
                AlertController.confirmmessage("Food saved sucessfully");

                defaultTxt();
                addBtn.setDisable(true);
                updateBtn.setDisable(true);
                deleteBtn.setDisable(true);
                txtFoodnum.setText("");
                cmbDescription.setValue("");
                txtUnitPrice.setText("");
                txtQtyOnHand.setText("");
                comItemId.setValue("");
                getAll();


            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public void tableOnMouseClicked(MouseEvent mouseEvent) {
        addBtn.setDisable(false);
        updateBtn.setDisable(false);
        deleteBtn.setDisable(false);
        TablePosition pos=tblFood.getSelectionModel().getSelectedCells().get(0);
        int row=pos.getRow();

        ObservableList<TableColumn<FoodTm,?>> columns=tblFood.getColumns();

        txtFoodnum.setText(columns.get(0).getCellData(row).toString());
        cmbDescription.setValue(columns.get(1).getCellData(row).toString());
        txtUnitPrice.setText(columns.get(2).getCellData(row).toString());
        txtQtyOnHand.setText(columns.get(3).getCellData(row).toString());
        comItemId.setValue(columns.get(4).getCellData(row).toString());
    }

    public void empIdSearchBarOnAction(ActionEvent event) {
        String id = txtSearch.getText();

        try {
            FoodDto food = FoodModel.search(id);

            if(food != null){
                txtFoodnum.setText(food.getFoodNumber());
                cmbDescription.setValue(food.getDescription());
                txtUnitPrice.setText(String.valueOf(food.getPrice()));
                txtQtyOnHand.setText(String.valueOf(food.getQtyOnHand()));
                comItemId.setValue(food.getItemId());

            }else{
                AlertController.errormessage("Invalid Id");
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            AlertController.errormessage("Something went wrong");
        }
    }

    public void txtSearchOnKeyTypedAction(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        String searchValue = txtFoodnum.getText().trim();
        ObservableList<FoodTm> obList = FoodModel.getAll();

        if (!searchValue.isEmpty()) {
            ObservableList<FoodTm> filteredData = obList.filtered(new Predicate<FoodTm>(){
                @Override
                public boolean test(FoodTm foodtm) {
                    return String.valueOf(foodtm.getFoodNumber()).toLowerCase().contains(searchValue.toLowerCase());
                }
            });
            tblFood.setItems(filteredData);
        } else {
            tblFood.setItems(obList);
        }

    }

    public void searchIconOnMouseClickedAction(MouseEvent mouseEvent) {
        String id = txtSearch.getText();

        try {
            FoodDto food = FoodModel.search(id);

            if(food != null){
                txtFoodnum.setText(food.getFoodNumber());
                cmbDescription.setValue(food.getDescription());
                txtUnitPrice.setText(String.valueOf(food.getPrice()));
                txtQtyOnHand.setText(String.valueOf(food.getQtyOnHand()));
                comItemId.setValue(food.getItemId());

            }else{
                AlertController.errormessage("Invalid Id");
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            AlertController.errormessage("Something went wrong");
        }
    }


    @FXML
    void searchFoodOnAction(ActionEvent event) {
        String id = txtFoodnum.getText();

        try {
            FoodDto food = FoodModel.search(id);

            if(food != null){
                txtFoodnum.setText(food.getFoodNumber());
                cmbDescription.setValue(food.getDescription());
                txtUnitPrice.setText(String.valueOf(food.getPrice()));
                txtQtyOnHand.setText(String.valueOf(food.getQtyOnHand()));
                comItemId.setValue(food.getItemId());

            }else{
                AlertController.errormessage("Invalid Id");
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            AlertController.errormessage("Something went wrong");
        }

    }

    @FXML
    void cmbItemIdOnAction(ActionEvent event) {
        String id = (String) comItemId.getValue();
        try {
            FoodDto food = FoodModel.searchById(id);


            txtFoodnum.requestFocus();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }

    private void addFoodcmb() {
        String[] jobRoles = new String[]{"Soft shell crab", "Fish pie", "Victoria's Filet Mignon", "Prime Cuts of Beef", "Miso Chicken",
                "Garden salad ","Salmon Riverland","Solw-Roasted Prime Rib",
                "Fried Potatoes","Steak Filet","Meatball tagliatelle","Doner Burger",
                "Cayenne Shrimp","Lobster with melted mozarella","Filet De Saumon","Steak Filet",
                "Butterrfly friend shrimps platter", "Chocolate Cake","Creme Brulee","Ice Cream",
                "Tarte Tatin","Lemon Meringue","Apple Pie","Kiwi Cocatil","Red Mojitos",
                "Chapel Down","Coffee","Summer Beer","Cabernet Sauvignon","Tea"};


        ObservableList<String> food = FXCollections.observableArrayList();

        for(String role : jobRoles){
            food.add(role);
        }
        cmbDescription.setItems(food);
    }

    private void loadItemIds() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> ids = ItemModel.loadItemCodes();

            for (String id: ids) {
                obList.add(id);
            }
            comItemId.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }

    @FXML
    void cmbcomDescriptionOnAction(ActionEvent event) {

    }



    private void getAll() {
        try {
            ObservableList<FoodTm> FoodData = FoodModel.getAll();
            tblFood.setItems(FoodData);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    void getCellValueFactory() {
        colFoodCode.setCellValueFactory(new PropertyValueFactory<>("foodNumber"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
    }

    @FXML
    void initialize() {
        getAll();
        getCellValueFactory();
        addFoodcmb();
        loadItemIds();

        addBtn.setDisable(true);
        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);
    }

    void defaultTxt(){
        txtFoodnum.setStyle("-fx-border-color: transparent");
        cmbDescription.setStyle("-fx-border-color: transparent");
        txtUnitPrice.setStyle("-fx-border-color: transparent");
        txtQtyOnHand.setStyle("-fx-border-color: transparent");
        comItemId.setStyle("-fx-border-color: transparent");
    }

    @FXML
    void idKeyTyped(KeyEvent event) {
        boolean isValid= DataValidateController.FoodIdValidate(txtFoodnum.getText());
        addBtn.setDisable(!isValid|txtFoodnum.getText().isEmpty()|txtUnitPrice.getText().isEmpty() |txtQtyOnHand.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()
        updateBtn.setDisable(!isValid|txtFoodnum.getText().isEmpty()|txtUnitPrice.getText().isEmpty() |txtQtyOnHand.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()
        deleteBtn.setDisable(!isValid|txtFoodnum.getText().isEmpty()|txtUnitPrice.getText().isEmpty() |txtQtyOnHand.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()

        if (isValid){
            txtFoodnum.setStyle("-fx-border-color: green");

        }else{
            txtFoodnum.setStyle("-fx-border-color: red");

        }

    }

    @FXML
    void QtyKeyTyped(KeyEvent event) {
        boolean isValid= DataValidateController.quantityValidate(txtQtyOnHand.getText());
        addBtn.setDisable(!isValid|txtFoodnum.getText().isEmpty() |txtUnitPrice.getText().isEmpty() |txtQtyOnHand.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()
        updateBtn.setDisable(!isValid|txtFoodnum.getText().isEmpty()|txtUnitPrice.getText().isEmpty() |txtQtyOnHand.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()
        deleteBtn.setDisable(!isValid|txtFoodnum.getText().isEmpty()|txtUnitPrice.getText().isEmpty() |txtQtyOnHand.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()

        if (isValid){
            txtQtyOnHand.setStyle("-fx-border-color: green");

        }else{
            txtQtyOnHand.setStyle("-fx-border-color: red");

        }

    }

    public void PriceKeyTyped(KeyEvent keyEvent) {
        boolean isValid= DataValidateController.priceValidate(txtUnitPrice.getText());
        addBtn.setDisable(!isValid|txtFoodnum.getText().isEmpty()|txtUnitPrice.getText().isEmpty() |txtQtyOnHand.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()
        updateBtn.setDisable(!isValid|txtFoodnum.getText().isEmpty()|txtUnitPrice.getText().isEmpty() |txtQtyOnHand.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()
        deleteBtn.setDisable(!isValid|txtFoodnum.getText().isEmpty()|txtUnitPrice.getText().isEmpty() |txtQtyOnHand.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()

        if (isValid){
            txtUnitPrice.setStyle("-fx-border-color: green");

        }else{
            txtUnitPrice.setStyle("-fx-border-color: red");

        }

    }

    public void deleteBtnOnAction(ActionEvent event) {
        String id = txtFoodnum.getText();


        try {
            boolean isDelete = FoodModel.delete(id);
            if (isDelete) {
                AlertController.confirmmessage("Food deleted sucessfully");

                addBtn.setDisable(true);
                updateBtn.setDisable(true);
                deleteBtn.setDisable(true);
                txtFoodnum.setText("");
                cmbDescription.setValue("");
                txtUnitPrice.setText("");
                txtQtyOnHand.setText("");
                comItemId.setValue("");

                getAll();

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


}
