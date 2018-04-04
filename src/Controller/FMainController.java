/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import posapp.ViewMain;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Deceive
 */
public class FMainController implements Initializable {

    @FXML
    private JFXButton btn_transaksi;
    @FXML
    private JFXButton btn_barang;
    @FXML
    private JFXButton btn_data_transaksi;
    @FXML
    public StackPane main_content;
    
    static AnchorPane form_transaksi;
    static AnchorPane form_data_barang;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
//             JFXSnackbar bar = new JFXSnackbar(main_content);
//             bar.enqueue(new SnackbarEvent("Notification Msg"));
//            form_transaksi = (AnchorPane)FXMLLoader.load(getClass().getResource("/Views/FTransaksi.fxml"));
            form_data_barang = (AnchorPane)FXMLLoader.load(getClass().getResource("/Views/FDataBarang.fxml"));
            ChangePane(form_data_barang);
        } catch (IOException ex) {
            Logger.getLogger(FMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void ChangePane(AnchorPane newForm){
        if(this.main_content.getChildren().size() > 0){
            this.main_content.getChildren().set(0, newForm);
        }else{
            this.main_content.getChildren().add(newForm);
        }
    }

    @FXML
    private void OpenTransaksi(ActionEvent event) {
        ChangePane(form_transaksi);
    }

    @FXML
    private void OpenDataBarang(ActionEvent event) {
        ChangePane(form_data_barang);
    }
}
