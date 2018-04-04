/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Table;

import Model.Barangs;
import Model.TransaksiDetails;
import Model.Transaksis;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Deceive
 */
public class TransaksiDetailProperty {
    private final IntegerProperty id;
    private final ObjectProperty<Barangs> barangs;
    private final ObjectProperty<Transaksis> transaksis;
    private final IntegerProperty jumlahBarang;
    private final IntegerProperty totalHarga;
    public TransaksiDetails model;

    public TransaksiDetailProperty(TransaksiDetails model,Integer id, Barangs b, Transaksis transaksis, int jumlahBarang, Integer totalHarga) {
        this.id = new SimpleIntegerProperty(id);
        this.barangs = new SimpleObjectProperty<>(b);
        this.transaksis = new SimpleObjectProperty<>(transaksis);
        this.jumlahBarang =  new SimpleIntegerProperty(jumlahBarang);
        this.totalHarga = new SimpleIntegerProperty(totalHarga);
        this.model = model;
    }
    public StringProperty getNamaBarang(){
        return new SimpleStringProperty(barangs.get().getNama_barang());
    }
    public IntegerProperty getId() {
        return id;
    }

    public ObjectProperty<Barangs> getBarangs() {
        return barangs;
    }

    public ObjectProperty<Transaksis> getTransaksis() {
        return transaksis;
    }

    public IntegerProperty getJumlahBarang() {
        return jumlahBarang;
    }

    public IntegerProperty getTotalHarga() {
        return new SimpleIntegerProperty(barangs.get().getHarga() * jumlahBarang.get());
    }
    public IntegerProperty getHargaBarang(){
        return new SimpleIntegerProperty(barangs.get().getHarga());
    }
    
}
