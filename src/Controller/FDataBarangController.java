/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Barangs;
import Model.Satuans;
import Services.SessionService;
import Utils.Currency;
import Utils.ValidateTextField;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;
import jdk.internal.util.xml.impl.Input;
import org.h2.engine.User;
import org.hibernate.criterion.Restrictions;
import sun.reflect.generics.tree.Tree;

/**
 * FXML Controller class
 *
 * @author Deceive
 */
public class FDataBarangController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTreeTableView<BarangProperty> table_view;
    public static ObservableList<BarangProperty> data_table = FXCollections.observableArrayList();
    public static ObservableList<String> data_satuan = FXCollections.observableArrayList();
    @FXML
    private StackPane dialog_pane;
    static JFXDialog dialog;
    static Button dialog_button_delete;
    static Button dialog_button_cancel;
    @FXML
    private JFXComboBox<String> insert_input_satuan;
    @FXML
    private JFXTextField insert_input_kode_barang;
    @FXML
    private JFXTextField insert_input_nama_barang;
    @FXML
    private JFXTextField insert_input_harga;
    @FXML
    private JFXButton btn_insert_barang;
    @FXML
    private TextField search_input_name;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tambahBarangEvent();
        fetchDataByName();
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
      
        dialogLayout.setHeading(new Text("Hapus Barang"));
        dialogLayout.setBody(new Text("APAKAH ANDA INGIN MENGHAPUS DATA INI ?"));
        dialog_button_delete = new Button("YES");
        dialog_button_cancel = new Button("NO");
        dialogLayout.setActions(dialog_button_delete,dialog_button_cancel);
        dialog = new JFXDialog(dialog_pane,dialogLayout, JFXDialog.DialogTransition.CENTER);
        dialog.setScaleX(2);
        dialog.setScaleY(2);
        JFXTreeTableColumn<BarangProperty, String> col_kode_barang = new JFXTreeTableColumn<>("Kode Barang");
        JFXTreeTableColumn<BarangProperty, String> col_nama_barang = new JFXTreeTableColumn<>("Nama Barang");
        JFXTreeTableColumn<BarangProperty, String> col_harga_barang = new JFXTreeTableColumn<>("Harga Barang");
        JFXTreeTableColumn<BarangProperty, String> col_satuan_barang = new JFXTreeTableColumn<>("Satuan Barang");
        col_kode_barang.setPrefWidth(100f);
        col_kode_barang.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        col_harga_barang.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        col_harga_barang.setOnEditStart((event) -> {
            System.out.println("Controller.FDataBarangController.initialize()");
            TreeItem<BarangProperty> current_item = table_view.getTreeItem(event.getTreeTablePosition().getRow());
            
            if(current_item  != null){
//                current_item.getValue().harga_in_format.set("asd");
                current_item.getValue().harga_barang.set(0);
                current_item.setValue(current_item.getValue());
//                data_table.get(0).harga_in_format.setValue("asdsad");
//                current_item.getValue().harga_in_format.setValue("sadsad");
            }
        });
        col_satuan_barang.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        col_kode_barang.setCellValueFactory((TreeTableColumn.CellDataFeatures<BarangProperty, String> param) -> {
            if (col_kode_barang.validateValue(param)) {
                return param.getValue().getValue().getKode_barang();
            } else {
                return col_kode_barang.getComputedValue(param);
            }
        });
        col_nama_barang.setCellValueFactory((TreeTableColumn.CellDataFeatures<BarangProperty, String> param) -> {
            if (col_kode_barang.validateValue(param)) {
                return param.getValue().getValue().nama_barang;
            } else {
                return col_kode_barang.getComputedValue(param);
            }
        });
        col_harga_barang.setCellValueFactory((TreeTableColumn.CellDataFeatures<BarangProperty, String> param) -> {
            if (col_kode_barang.validateValue(param)) {
                return param.getValue().getValue().getHargaInFormat();
            } else {
                return col_kode_barang.getComputedValue(param);
            }
        });
        col_satuan_barang.setCellValueFactory((TreeTableColumn.CellDataFeatures<BarangProperty, String> param) -> {
            if (col_satuan_barang.validateValue(param)) {
                return param.getValue().getValue().satuan_barang;
            } else {
                return col_satuan_barang.getComputedValue(param);
            }
        });
        fetchData();
        final TreeItem<BarangProperty> root = new RecursiveTreeItem<BarangProperty>(data_table, (param) -> {
            return param.getChildren();
        });

        JFXTreeTableColumn<BarangProperty, BarangProperty> col_action = new JFXTreeTableColumn<BarangProperty, BarangProperty>();
        col_action.setCellValueFactory(param -> new ReadOnlyObjectWrapper<BarangProperty>(param.getValue().getValue()));
//        Callback<TreeTableColumn<Barang,Barang>,TreeTableCell<Barang,Barang>> cellFactory = new Callback<TreeTableColumn<Barang, Barang>, TreeTableCell<Barang, Barang>>() {
//            @Override
//            public TreeTableCell<Barang, Barang> call(TreeTableColumn<Barang, Barang> param) {
//                final TreeTableCell<Barang,Barang> cell = new TreeTableCell<Barang,Barang>(){
//                     final Button btn = new Button("Just Do It");
//                     final Button btnIncreament = new Button("+");
//                     final Button btnDecrease = new Button("-");
//                     final Label label = new Label("2");
//                     HBox hBox = new HBox();
//                     
//                    @Override
//                    protected void updateItem(Barang item, boolean empty) {
//                        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
//                        if(empty){
//                            setText(null);
//                            setGraphic(null);
//                        }else{
//                            btnIncreament.setPrefWidth(25.0);
//                            btnDecrease.setPrefWidth(25.0);
//                            btnIncreament.setPrefHeight(25.0);
//                            btnDecrease.setPrefHeight(25.0);
//                            label.setPadding(new Insets(5, 10, 0, 10));
//                            hBox.getChildren().addAll(btnIncreament,label,btnDecrease);
//                            setGraphic(hBox);
//                        }
//                    }
//               };
//                       return cell;
//            }
//   
//        };
        col_action.setCellFactory(new Callback<TreeTableColumn<BarangProperty, BarangProperty>, TreeTableCell<BarangProperty, BarangProperty>>() {
            @Override
            public TreeTableCell<BarangProperty, BarangProperty> call(TreeTableColumn<BarangProperty, BarangProperty> param) {
                return new Cell();
            }
        });
        table_view.setShowRoot(false);
        table_view.getColumns().setAll(col_kode_barang, col_nama_barang, col_harga_barang, col_satuan_barang, col_action);
        table_view.setRoot(root);
        
        table_view.setEditable(true);
    }

    public void fetchData() {
        List<Barangs> tempData = SessionService.getInstance().getSession().createCriteria(Barangs.class).list();
        for (Barangs barang : tempData) {
//            System.out.println("Controller.FDataBarangController.fetchData()"+barang.getSatuan());
            if(barang.getDeleted() == 0){
                data_table.add(new BarangProperty(barang,barang.getKode_barang(),barang.getNama_barang(),barang.getHarga(),barang.getSatuan() ));
            }
        }
        for(Satuans satuan : Satuans.fetchData()){
            data_satuan.add(satuan.getNamaSatuan());
//              insert_input_satuan.getItems().add(new SatuanProperty(satuan.getNamaSatuan(),satuan.getId()));
        }
//        insert_input_satuan = new JFXComboBox(data_satuan);
        insert_input_satuan.setItems(data_satuan);
        insert_input_satuan.setEditable(false);

//        insert_input_satuan.getSelectionModel().selectFirst();
    }
    FilteredList<BarangProperty> filteredData = new FilteredList<>(data_table);
    SortedList<BarangProperty> sortedData = new SortedList<>(filteredData);
    void fetchDataByName() {
//        FilteredList<BarangProperty> filteredData = new FilteredList<>(data_table,p->true);
        search_input_name.textProperty().addListener((observable, oldValue, newValue) -> {
            table_view.setPredicate(new Predicate<TreeItem<BarangProperty>>() {
                @Override
                public boolean test(TreeItem<BarangProperty> result) {
                   Boolean isFound = result.getValue().nama_barang.getValue().contains(newValue);
                   return isFound;
                }
            });
        });
    }
    void TambahBarang(){
        if(insert_input_harga.getText().isEmpty() || insert_input_kode_barang.getText().isEmpty() || insert_input_nama_barang.getText().isEmpty() || insert_input_satuan.getSelectionModel().isEmpty()){
            DialogError("DATA TIDAK LENGKAP");
        }else{
            Barangs newBarang = new Barangs();
            newBarang.setSatuans((Satuans)SessionService.getInstance().getSession().createCriteria(Satuans.class).add(Restrictions.eq("namaSatuan", insert_input_satuan.getSelectionModel().getSelectedItem())).uniqueResult());
            newBarang.setKode_barang(insert_input_kode_barang.getText());
            newBarang.setNama_barang(insert_input_nama_barang.getText());
            newBarang.setHarga(Integer.parseInt(insert_input_harga.getText()));
            newBarang.Save();
            ResetTextfield();
            data_table.add(new BarangProperty(newBarang, newBarang.getKode_barang(), newBarang.getNama_barang(),newBarang.getHarga(), newBarang.getSatuan()));
            insert_input_kode_barang.requestFocus();
            System.out.println("INSERT DATA)");
        }
    }
    void DialogError(String error){
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        dialogContent.setBody(new Text(error));
        JFXDialog dialogError = new JFXDialog(dialog_pane, dialogContent, JFXDialog.DialogTransition.CENTER);
        dialogError.show();
    }
    void tambahBarangEvent(){
        insert_input_harga.textProperty().addListener(ValidateTextField.isNumberOnly(insert_input_harga));
        insert_input_nama_barang.textProperty().addListener(ValidateTextField.isLetterOnly(insert_input_nama_barang));
        insert_input_satuan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                insert_input_satuan.getSelectionModel().
               System.out.println(insert_input_satuan.getSelectionModel().getSelectedIndex()+1);
            }
        });
        insert_input_satuan.setOnKeyPressed((event) -> {
            if(event.getCode() == KeyCode.ENTER){
                TambahBarang();
            }
        });
        insert_input_harga.setOnKeyPressed((event) -> {
            if(event.getCode() == KeyCode.ENTER){
                TambahBarang();
            }
        });
        insert_input_nama_barang.setOnKeyPressed((event) -> {
            if(event.getCode() == KeyCode.ENTER){
                TambahBarang();
            }
        });
        insert_input_kode_barang.setOnKeyPressed((event) -> {
            if(event.getCode() == KeyCode.ENTER){
                TambahBarang();
            }
        });
        btn_insert_barang.setOnAction((event) -> {
            TambahBarang();
        });
    }
    void ResetTextfield(){
        insert_input_satuan.getSelectionModel().selectFirst();
        insert_input_harga.setText("");
        insert_input_nama_barang.setText("");
        insert_input_kode_barang.setText("");
        
    }
    static class Cell extends TreeTableCell<BarangProperty, BarangProperty> {

        HBox hbox = new HBox();
        Button btnDelete = new Button("DELETE");
        Button btnUpdate = new Button("UPDATE");
        Pane pane = new Pane();

        public Cell() {
            super();
            btnDelete.setPrefWidth(80.0);
            btnUpdate.setPrefWidth(80.0);
            hbox.getChildren().addAll(btnUpdate, btnDelete);
            hbox.setSpacing(10);
//            hbox.setPadding();
//            hbox.setHgrow(pane, Priority.ALWAYS);
        }

        @Override
        protected void updateItem(BarangProperty item, boolean empty) {
            super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
            btnDelete.setOnAction((event) -> {
                dialog.show();
                dialog_button_cancel.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                       dialog.close();
                    }
                });
                dialog_button_delete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                       SessionService.getInstance().getSession().beginTransaction();
                       item.barang_model.setDeleted(1);
                       SessionService.getInstance().getSession().update(item.barang_model);
                       SessionService.getInstance().getSession().getTransaction().commit();
                       data_table.remove(item);
                       dialog.close();
                    }
                });

            });
            if(!empty){
                setGraphic(hbox);
            }else{
                setGraphic(null);
            }
        }

    }
    static class CellCustom extends TreeTableCell<BarangProperty, String>{

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
            setStyle("-fx-font-size: 20px");
            setText(item);
        }
        
    }
    static class CallbackCustomCell implements Callback<BarangProperty,Callback<TreeTableColumn<Object, String>, TreeTableCell<Object, String>>> {

        private CallbackCustomCell() {
            //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Callback<TreeTableColumn<Object, String>, TreeTableCell<Object, String>> call(BarangProperty param) {
            return TextFieldTreeTableCell.forTreeTableColumn(); //To change body of generated methods, choose Tools | Templates.
        }



//        @Override
//        public Callback<TreeTableColumn<Object, String>, TreeTableCell<Object, String>> call(TreeTableColumn<Object, String> param) {
//           return TextFieldTreeTableCell.forTreeTableColumn(); //To change body of generated methods, choose Tools | Templates.
//        }


//        @Override
//        public TreeTableCell<String, Object> call(TreeTableColumn<String, Object> param) {
//            return TextFieldTreeTableCell.forTreeTableColumn(); //To change body of generated methods, choose Tools | Templates.
//        }
//        @Override
//        public void updateItem(Object item, boolean empty) {
//            super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
//            super.forTreeTableColumn();
//        }

     
        
    }
    public class BarangProperty extends RecursiveTreeObject<BarangProperty> {

        StringProperty kode_barang;
        StringProperty nama_barang;
        IntegerProperty harga_barang;
        StringProperty satuan_barang;
        Barangs barang_model;
        StringProperty harga_in_format;
        public BarangProperty(Barangs barang_model, String kode_barang, String nama_barang,Integer harga_barang,String satuan_barang) {
            this.kode_barang = new SimpleStringProperty(kode_barang);
            this.nama_barang = new SimpleStringProperty(nama_barang);
            this.harga_barang = new SimpleIntegerProperty(harga_barang);
            this.satuan_barang = new SimpleStringProperty(satuan_barang);
            this.barang_model = barang_model;
        }

        public StringProperty getKode_barang() {
//            kode_barang.setValue("asd");
            return kode_barang;
        }
        public StringProperty getHargaInFormat(){
            harga_in_format = new SimpleStringProperty(Currency.makeRupiahFormat(harga_barang.getValue()));
            return harga_in_format;
        }

    }
    public class SatuanProperty{
        StringProperty nama_satuan;
        IntegerProperty id_satuan;

        public SatuanProperty(String nama_satuan, Integer id_satuan) {
            this.nama_satuan = new SimpleStringProperty(nama_satuan);
            this.id_satuan = new SimpleIntegerProperty(id_satuan);
        }
        
    }
}
