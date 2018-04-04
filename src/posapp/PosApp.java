/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posapp;

import Model.TransaksiDetails;
import Model.Transaksis;
import Services.SessionService;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.net.*;
import java.util.List;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.SimpleDoc;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.PageRanges;
import javax.print.attribute.standard.PrinterName;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Deceive
 */
public class PosApp implements Printable{
   private PrintService printService;
   private String text;
   public Transaksis transaksi;
 
    public PosApp(Transaksis transaksi) {
        this.transaksi = transaksi;
        this.printService = PrintServiceLookup.lookupDefaultPrintService();
    }
 
    public static void main(String[] args) {
        String testString = "Projecting surrounded literature yet delightful alteration but bed men. Open are from long why cold. If must snug by upon sang loud left. As me do preference entreaties compliment motionless ye literature. Day behaviour explained law remainder. Produce can cousins account you pasture. Peculiar delicate an pleasant provided do perceive. ";
        String test = "1234567890";
        String fixed[];
        int start = 0;
        int end = 5;
        for(int i = 0 ; i < testString.length(); i++){
            System.out.println(testString.substring(start,end));
            start += 5;
            end += 5;
        }

    }
 
    public void print() {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        System.out.println(printService.getName());
        try {
            printJob.setPrintService(printService);
            PageFormat pf = printJob.defaultPage();
            Paper paper = pf.getPaper();
            paper.setSize(4, 11.69);    
            paper.setImageableArea(-4 * 72, -5 * 72, 4 * 72, 10.5 * 72);
            pf.setPaper(paper);
            
            printJob.setPrintable(this,pf);
            printJob.print();
        } catch (PrinterException err) {
            System.err.println(err);
        }
    }
    public PageFormat getPageFormat(PrinterJob pj){
        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();    
        double middleHeight =1.0;
        double headerHeight = 5.0;
        double footerHeight = 5.0;
        double width = convert_CM_To_PPI(21);
        double height = convert_CM_To_PPI(headerHeight+middleHeight+footerHeight); 
        paper.setSize(width, height);
        paper.setImageableArea(
        convert_CM_To_PPI(0.5), convert_CM_To_PPI(0.5), width - convert_CM_To_PPI(0.35), height - convert_CM_To_PPI(1));
        pf.setOrientation(PageFormat.PORTRAIT);      
        pf.setPaper(paper);    
        return pf;
    }
    protected static double convert_CM_To_PPI(double cm) {            
        return toPPI(cm * 0.393600787);            
    }

    protected static double toPPI(double inch) {            
        return inch * 72d;            
    }
 
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        int result = NO_SUCH_PAGE;    
	if (pageIndex == 0) {                    
	Graphics2D g2d = (Graphics2D) graphics;                                 
	double width = pageFormat.getImageableWidth();
	double height = pageFormat.getImageableHeight();    
	g2d.translate((int) pageFormat.getImageableX(),(int) pageFormat.getImageableY()); 
	Font font = new Font("Monospaced",Font.PLAIN,10);       
	g2d.setFont(font);  
      	try{
        int y=0;
        g2d.drawString("====================", 0, 10);
	g2d.drawString("Toko Sinar", 0,20);  
	g2d.drawString("JLN KAUMAN NO 3", 0,30);                 //shift a line by adding 10 to y value                            //print date
	g2d.drawString("Cashier : Deceive", 0, 40);  
        g2d.drawString("====================", 0, 50);
        for (TransaksiDetails detail : transaksi.getTransaksiDetailses()) {
            String testString = "Projecting surrounded literature yet delightful alteration but bed men. Open are from long why cold. If must snug by upon sang loud left. As me do preference entreaties compliment motionless ye literature. Day behaviour explained law remainder. Produce can cousins account you pasture. Peculiar delicate an pleasant provided do perceive. ";
            for(int i = 0; i <  (testString.length()/5) ; i++){
//                g2d.drawString(testString.substring((testString.length()/5)), i, y);
            }
//            g2d.drawString(detail.getBarangs().getNama_barang() +"  "+ detail.getJumlah_barang() +" "+detail.getBarangs().getHarga()+"  "+detail.getTotal_harga(), 0 ,70 + y);
            y+= 10;
        }
        g2d.drawString("====================", 0,70 + y);
        g2d.drawString("TERIMA KASIH", 0, 80 + y);
        g2d.drawString("====================", 0,90 + y);
        }
        catch(Exception r){
        r.printStackTrace();
        }
  
	result = PAGE_EXISTS;    
	}    
	return result;    
    }
   }
