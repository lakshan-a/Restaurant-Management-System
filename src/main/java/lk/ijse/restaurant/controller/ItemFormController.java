package lk.ijse.restaurant.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.restaurant.dto.ItemDto;
import lk.ijse.restaurant.dto.Tm.ItemTm;
import lk.ijse.restaurant.model.ItemModel;
import lk.ijse.restaurant.util.AlertController;
import lk.ijse.restaurant.util.DataValidateController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ItemFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton addBtn;

    @FXML
    private TableColumn<?, ?> colColour;

    @FXML
    private TableColumn<?, ?> colItemId;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colMaterialType;

    @FXML
    private TableColumn<?, ?> colQTY;

    @FXML
    private TableColumn<?, ?> colSize;

    @FXML
    private TableColumn<?, ?> colUnitPrize;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private TableView<ItemTm> tblItem;

    @FXML
    private TextField txtColour;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtMaterial;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtQuntity;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtSize;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private JFXButton updateBtn;


    @FXML
    void btnAddOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        Integer price = Integer.valueOf(txtUnitPrice.getText());
        Integer QTY = Integer.valueOf(txtQuntity.getText());

        ItemDto item = new ItemDto(id, name, price, QTY);


        try {
            boolean isSaved = ItemModel.save(item);
            if (isSaved) {
                AlertController.confirmmessage("Item saved sucessfully");
                defaultTxt();
                addBtn.setDisable(true);
                updateBtn.setDisable(true);
                deleteBtn.setDisable(true);
                txtName.setText("");
                txtId.setText("");
                txtUnitPrice.setText("");
                txtQuntity.setText("");
                getAll();

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void deleteBtnOnAction(ActionEvent event) {


        try {
            boolean isDeleted = ItemModel.delete(txtId.getText());
            if (isDeleted) {
                AlertController.confirmmessage("Item deleted sucessfully");

                addBtn.setDisable(true);
                updateBtn.setDisable(true);
                deleteBtn.setDisable(true);
                txtId.setText("");
                txtName.setText("");
//                txtMaterial.setText("");
//                txtSize.setText("");
//                txtColour.setText("");
                txtUnitPrice.setText("");
                txtQuntity.setText("");

                getAll();

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void empIdSearchBarOnAction(ActionEvent event) {
        try {
            ItemDto itemDto = ItemModel.searchById(txtSearch.getText());
            if (itemDto != null) {
                txtId.setText(itemDto.getIID());
                txtName.setText(itemDto.getItemName());
                txtUnitPrice.setText(String.valueOf(itemDto.getUnitPrize()));
                txtQuntity.setText(String.valueOf(itemDto.getQtyOnHand()));
            } else {
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void itemTblClick(MouseEvent event) {
        addBtn.setDisable(false);
        updateBtn.setDisable(false);
        deleteBtn.setDisable(false);
        TablePosition pos = tblItem.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        // Get the data from the selected row
        ObservableList<TableColumn<ItemTm, ?>> columns = tblItem.getColumns();

        txtId.setText(columns.get(0).getCellData(row).toString());
        txtName.setText(columns.get(1).getCellData(row).toString());
//        txtMaterial.setText(columns.get(2).getCellData(row).toString());
//        txtSize.setText(columns.get(3).getCellData(row).toString());
//        txtColour.setText(columns.get(4).getCellData(row).toString());
        txtUnitPrice.setText(columns.get(2).getCellData(row).toString());
        txtQuntity.setText(columns.get(3).getCellData(row).toString());

    }


    @FXML
    void searchIconOnMouseClickedAction(MouseEvent event) {

        try {
            ItemDto itemDto = ItemModel.searchById(txtSearch.getText());
            if (itemDto != null) {
                txtId.setText(itemDto.getIID());
                txtName.setText(itemDto.getItemName());
//                txtMaterial.setText(itemDto.getMatirialType());
//                txtSize.setText(itemDto.getSize());
//                txtColour.setText(itemDto.getColor());
                txtUnitPrice.setText(String.valueOf(itemDto.getUnitPrize()));
                txtQuntity.setText(String.valueOf(itemDto.getQtyOnHand()));
            } else {
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void txtSearchOnKeyTypedAction(KeyEvent event) throws SQLException, ClassNotFoundException {
        String searchValue = txtSearch.getText().trim();
        ObservableList<ItemTm> obList = ItemModel.getAll();

        if (!searchValue.isEmpty()) {
            ObservableList<ItemTm> filteredData = obList.filtered(new Predicate<ItemTm>() {
                @Override
                public boolean test(ItemTm itemTm) {
                    return String.valueOf(itemTm.getIID()).toLowerCase().contains(searchValue.toLowerCase());
                }
            });
            tblItem.setItems(filteredData);
        } else {
            tblItem.setItems(obList);
        }

    }

    @FXML
    void updateBtnOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
//        String material = txtMaterial.getText();
//        String size = txtSize.getText();
//        String colour = txtColour.getText();
        Integer unitPrice = Integer.valueOf(txtUnitPrice.getText());
        Integer quantity = Integer.valueOf(txtQuntity.getText());

        ItemDto item = new ItemDto(id, name,  unitPrice, quantity);//material, size, colour,



        try {
            boolean isUpdated =  ItemModel.update(item);
            if (isUpdated) {
                AlertController.confirmmessage("Item updated sucessfully");

                defaultTxt();
                addBtn.setDisable(true);
                updateBtn.setDisable(true);
                deleteBtn.setDisable(true);
                txtId.setText("");
                txtName.setText("");
//                txtMaterial.setText("");
//                txtSize.setText("");
//                txtColour.setText("");
                txtUnitPrice.setText("");
                txtQuntity.setText("");

                getAll();


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    void getAll() {

        try {
            ObservableList<ItemTm> itemData = ItemModel.getAll();
            tblItem.setItems(itemData);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    void getCellValueFactory() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("IID"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
//        colMaterialType.setCellValueFactory(new PropertyValueFactory<>("MatirialType"));
//        colSize.setCellValueFactory(new PropertyValueFactory<>("Size"));
//        colColour.setCellValueFactory(new PropertyValueFactory<>("color"));
        colUnitPrize.setCellValueFactory(new PropertyValueFactory<>("UnitPrize"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("QtyOnHand"));
    }

    @FXML
    void initialize() {
        getAll();
        getCellValueFactory();

        addBtn.setDisable(true);
        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);
    }

    void defaultTxt(){
        txtId.setStyle("-fx-border-color: transparent");
        txtName.setStyle("-fx-border-color: transparent");
//        txtMaterial.setStyle("-fx-border-color: transparent");
//        txtSize.setStyle("-fx-border-color: transparent");
//        txtColour.setStyle("-fx-border-color: transparent");
        txtUnitPrice.setStyle("-fx-border-color: transparent");
        txtQuntity.setStyle("-fx-border-color: transparent");
    }

    @FXML
    void idKeyTyped(KeyEvent event) {
        boolean isValid= DataValidateController.itemIdValidate(txtId.getText());
        addBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtName.getText().isEmpty()|txtName.getText().isEmpty()|txtUnitPrice.getText().isEmpty() |txtQuntity.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()
        updateBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtName.getText().isEmpty()|txtName.getText().isEmpty()|txtUnitPrice.getText().isEmpty() |txtQuntity.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()
        deleteBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtName.getText().isEmpty()|txtName.getText().isEmpty() |txtUnitPrice.getText().isEmpty() |txtQuntity.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()

        if (isValid){
            txtId.setStyle("-fx-border-color: green");

        }else{
            txtId.setStyle("-fx-border-color: red");

        }

    }

    @FXML
    void unitPriceKeyTyped(KeyEvent event) {
        boolean isValid= DataValidateController.priceValidate(txtUnitPrice.getText());
        addBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtName.getText().isEmpty()|txtName.getText().isEmpty() |txtUnitPrice.getText().isEmpty() |txtQuntity.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()
        updateBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtName.getText().isEmpty()|txtName.getText().isEmpty() |txtUnitPrice.getText().isEmpty() |txtQuntity.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()
        deleteBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtName.getText().isEmpty()|txtName.getText().isEmpty() |txtUnitPrice.getText().isEmpty() |txtQuntity.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()

        if (isValid){
            txtUnitPrice.setStyle("-fx-border-color: green");

        }else{
            txtUnitPrice.setStyle("-fx-border-color: red");

        }
    }

    @FXML
    void sizeKeyTyped(KeyEvent event) {
        boolean isValid= DataValidateController.priceValidate(txtSize.getText());
        addBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtName.getText().isEmpty()|txtName.getText().isEmpty()|txtUnitPrice.getText().isEmpty() |txtQuntity.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()
        updateBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtName.getText().isEmpty()|txtName.getText().isEmpty()|txtUnitPrice.getText().isEmpty() |txtQuntity.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()
        deleteBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtName.getText().isEmpty()|txtName.getText().isEmpty() |txtUnitPrice.getText().isEmpty() |txtQuntity.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()

        if (isValid){
            txtSize.setStyle("-fx-border-color: green");

        }else{
            txtSize.setStyle("-fx-border-color: red");

        }
    }

    @FXML
    void txtColourKeyTyped(KeyEvent event) {
        boolean isValid= DataValidateController.stringValidate(txtColour.getText());
        addBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtName.getText().isEmpty()|txtName.getText().isEmpty()|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty() |txtUnitPrice.getText().isEmpty() |txtQuntity.getText().isEmpty());
        updateBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtName.getText().isEmpty()|txtName.getText().isEmpty()|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty() |txtUnitPrice.getText().isEmpty() |txtQuntity.getText().isEmpty());
        deleteBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtName.getText().isEmpty()|txtName.getText().isEmpty()|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty() |txtUnitPrice.getText().isEmpty() |txtQuntity.getText().isEmpty());

        if (isValid){
            txtColour.setStyle("-fx-border-color: green");

        }else{
            txtColour.setStyle("-fx-border-color: red");

        }
    }

    @FXML
    void materialKeyTyped(KeyEvent event) {
        boolean isValid= DataValidateController.customerNameValidate(txtMaterial.getText());
        addBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtName.getText().isEmpty()|txtName.getText().isEmpty() |txtUnitPrice.getText().isEmpty() |txtQuntity.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()
        updateBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtName.getText().isEmpty()|txtName.getText().isEmpty() |txtUnitPrice.getText().isEmpty() |txtQuntity.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()
        deleteBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtName.getText().isEmpty()|txtName.getText().isEmpty() |txtUnitPrice.getText().isEmpty() |txtQuntity.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()

        if (isValid){
            txtMaterial.setStyle("-fx-border-color: green");

        }else{
            txtMaterial.setStyle("-fx-border-color: red");

        }
    }

    @FXML
    void nameKeyTyped(KeyEvent event) {
        boolean isValid= DataValidateController.customerNameValidate(txtName.getText());
        addBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtName.getText().isEmpty()|txtName.getText().isEmpty() |txtUnitPrice.getText().isEmpty() |txtQuntity.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()
        updateBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtName.getText().isEmpty()|txtName.getText().isEmpty() |txtUnitPrice.getText().isEmpty() |txtQuntity.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()
        deleteBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtName.getText().isEmpty()|txtName.getText().isEmpty()|txtUnitPrice.getText().isEmpty() |txtQuntity.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()

        if (isValid){
            txtName.setStyle("-fx-border-color: green");

        }else{
            txtName.setStyle("-fx-border-color: red");

        }
    }

    @FXML
    void quantityKeyTyped(KeyEvent event) {
        boolean isValid= DataValidateController.quantityValidate(txtQuntity.getText());
        addBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtName.getText().isEmpty()|txtName.getText().isEmpty() |txtUnitPrice.getText().isEmpty() |txtQuntity.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()
        updateBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtName.getText().isEmpty()|txtName.getText().isEmpty() |txtUnitPrice.getText().isEmpty() |txtQuntity.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()
        deleteBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtName.getText().isEmpty()|txtName.getText().isEmpty()|txtUnitPrice.getText().isEmpty() |txtQuntity.getText().isEmpty());//|txtMaterial.getText().isEmpty() |txtSize.getText().isEmpty() |txtColour.getText().isEmpty()

        if (isValid){
            txtQuntity.setStyle("-fx-border-color: green");

        }else{
            txtQuntity.setStyle("-fx-border-color: red");

        }
    }

}
