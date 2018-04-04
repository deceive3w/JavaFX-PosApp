/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Barangs;
import Model.Table.TransaksiDetailProperty;
import Model.TransaksiDetails;
import Model.Transaksis;
import Services.SessionService;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 * FXML Controller class
 *
 * @author Deceive
 */
public class FTransaksiController implements Initializable {

    @FXML
    private TextField input_kode_barang;
    @FXML
    private TextField input_nama_barang;
    @FXML
    private TextField input_jumlah_barang;
    List<TransaksiDetails> current_cart = new ArrayList<TransaksiDetails>();
    ObservableList<TransaksiDetailProperty> table_cart = FXCollections.observableArrayList();
    Transaksis current_transaksi;
    Barangs current_barang;
    @FXML
    private TableColumn<Integer, Integer> column_no;
    @FXML
    private TableColumn<TransaksiDetailProperty, String> column_nama;
    @FXML
    private TableColumn<TransaksiDetailProperty, Integer> column_jumlah;
    @FXML
    private TableColumn<TransaksiDetailProperty, Integer> column_total_harga;
    @FXML
    private TableView<TransaksiDetailProperty> cart_table;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Text label_total_harga;
    public StringProperty total_harga_prop = new SimpleStringProperty("0");
    public int total_harga_value;
    @FXML
    private TableColumn<TransaksiDetailProperty,Integer> column_harga_barang;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Controller.FTransaksiController.initialize()");
        // TODO
        total_harga_prop.addListener((obs, oldValue, newValue) -> label_total_harga.setText(newValue));
        cart_table.setItems(table_cart);
        column_jumlah.setCellValueFactory(cellData -> cellData.getValue().getJumlahBarang().asObject());
        column_nama.setCellValueFactory(cellData -> cellData.getValue().getNamaBarang());
        column_total_harga.setCellValueFactory(cellData -> cellData.getValue().getTotalHarga().asObject());
        column_harga_barang.setCellValueFactory(cellData -> cellData.getValue().getHargaBarang().asObject());
        column_no.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Integer, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Integer, Integer> param) {
                return new ReadOnlyObjectWrapper(cart_table.getItems().indexOf(param.getValue()) + 1 + "");
            }
        });
        TableColumn<TransaksiDetailProperty,TransaksiDetailProperty> actionCol = new TableColumn<TransaksiDetailProperty,TransaksiDetailProperty>("Action");
        TableColumn actionIncremeant = new TableColumn();
        actionCol.setCellValueFactory( param -> new ReadOnlyObjectWrapper<>(param.getValue()));
//        StackPane root = new StackPane(hbox, cart_table);
         Callback<TableColumn<TransaksiDetailProperty, TransaksiDetailProperty>, TableCell<TransaksiDetailProperty, TransaksiDetailProperty>> cellFactory
                =
                new Callback<TableColumn<TransaksiDetailProperty, TransaksiDetailProperty>, TableCell<TransaksiDetailProperty, TransaksiDetailProperty>>() {
            @Override
            public TableCell call(final TableColumn<TransaksiDetailProperty, TransaksiDetailProperty> param) {
                final TableCell<TransaksiDetailProperty, TransaksiDetailProperty> cell = new TableCell<TransaksiDetailProperty, TransaksiDetailProperty>() {

                    final Button btn = new Button("Just Do It");
                    @Override
                    public void updateItem(TransaksiDetailProperty item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {                               
                                try {
                                    SessionService.getInstance().getSession().beginTransaction();
                                    SessionService.getInstance().getSession().delete(item.model);
                                    SessionService.getInstance().getSession().getTransaction().commit();
                                } catch (Exception e) {
                                    System.out.println(".updateItem()" + e.getMessage());
                                }
                                total_harga_prop.setValue(String.valueOf((Integer.parseInt(total_harga_prop.get()) - item.getTotalHarga().get())));
                                table_cart.remove(item);
                                System.out.println(item.model.getBarangs().getNama_barang());
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }

        };
        cart_table.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv) -> {
            if (nv != null) {
//                StackPane.setMargin(hbox, new Insets(evt.getSceneY(), 0, 0, 0));
                System.out.println(ov.getNamaBarang().get());
            }
        });

        actionCol.setCellFactory(cellFactory);
        cart_table.getColumns().add(actionCol);
//        SessionService.getInstance();
//        init();
        input_kode_barang.textProperty().addListener((observable, oldValue, newValue) -> {
            getNamaBarang(newValue);
        });
    }    

//    @FXML
    private void getNamaBarang(String kode_barang) {
        Criteria barang = SessionService.getInstance().sessionFactory.openSession().createCriteria(Barangs.class);
        barang.add(Restrictions.eq("kode_barang", kode_barang));
        
        Barangs b = (Barangs) barang.uniqueResult();
        if(b != null){
            current_barang = b;
            input_nama_barang.setText(b.getNama_barang());
            input_jumlah_barang.requestFocus();
        }else{
            current_barang = null;
            input_nama_barang.setText("DATA TIDAK DITEMUKAN");
            input_kode_barang.requestFocus();
        }
    }
    public void init(){
        SessionService.getInstance().getSession().beginTransaction();
        Criteria transaksi = SessionService.getInstance().getSession().createCriteria(Transaksis.class);
        transaksi.add(Restrictions.eq("status",0));
        SessionService.getInstance().getSession().getTransaction().commit();
        if (transaksi.list().size() > 0) {
            System.out.println("data ada");
            for (Object object : transaksi.list()) {
                Transaksis t = (Transaksis) object;
                current_transaksi = t;
                for (TransaksiDetails transaksiDetailse : t.getTransaksiDetailses()) {
//                 current_cart.add(transaksiDetailse);
                   total_harga_value += transaksiDetailse.getTotal_harga();
                    System.out.println(total_harga_value);
                   total_harga_prop.setValue(String.valueOf((Integer.parseInt(total_harga_prop.get()) + transaksiDetailse.getTotal_harga())));
                   table_cart.add(new TransaksiDetailProperty(transaksiDetailse,transaksiDetailse.getId(), transaksiDetailse.getBarangs(), transaksiDetailse.getTransaksis(), transaksiDetailse.getJumlah_barang(), transaksiDetailse.getTotal_harga()));        
                }
            }

        }else{
            Transaksis t = new Transaksis();
            t.setStatus(0);
            t.setTanggal_transaksi(new Date());
            t.setTotalHarga(0);
            SessionService.getInstance().getSession().beginTransaction();
            SessionService.getInstance().getSession().save(t);
            SessionService.getInstance().getSession().getTransaction().commit();
        }
//        SessionService.getInstance().sessionFactory.close();
    }
    public void refreshTable(){
        
    }
    public void addToCartDao(Barangs tempBarang,int jumlah_barang){
        if(tempBarang != null){
            TransaksiDetails newDetail = new TransaksiDetails();
            newDetail.setBarangs(current_barang);
            newDetail.setJumlah_barang(jumlah_barang);
            newDetail.setTotal_harga(tempBarang.getHarga() * jumlah_barang);
            newDetail.setTransaksis(current_transaksi);
            try {
                SessionService.getInstance().getSession().beginTransaction();
                SessionService.getInstance().getSession().save(newDetail);
                SessionService.getInstance().getSession().getTransaction().commit();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println(newDetail.getId());
            total_harga_prop.setValue(String.valueOf((Integer.parseInt(total_harga_prop.get()) + newDetail.getTotal_harga())));
            table_cart.add(new TransaksiDetailProperty(newDetail,newDetail.getId(), newDetail.getBarangs(), newDetail.getTransaksis(), newDetail.getJumlah_barang(), newDetail.getTotal_harga()));
            reset();
//            SessionService.getInstance().sessionFactory.close();
        }
//                SessionService.getInstance().sessionFactory.close();
    }

    @FXML
    private void addToCart(ActionEvent event) {
//        System.out.println("ASDASD");
                addToCartDao(current_barang, Integer.parseInt(input_jumlah_barang.textProperty().get()));
    }
    public void reset(){
        input_jumlah_barang.textProperty().set("");
        input_nama_barang.textProperty().set("");
        input_kode_barang.textProperty().set("");
        input_kode_barang.requestFocus();
    }
    
}
