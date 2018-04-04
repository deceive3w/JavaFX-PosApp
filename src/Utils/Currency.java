/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Deceive
 */
public class Currency {
    
    public static String makeRupiahFormat(Integer value){
        double doubleValue = value.doubleValue();
        DecimalFormat newFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        newFormat.setDecimalFormatSymbols(formatRp);
        return newFormat.format(doubleValue);
    }
     public static double rupiahToDouble(String value){
        try {
            DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
            
            formatRp.setCurrencySymbol("Rp. ");
            formatRp.setMonetaryDecimalSeparator(',');
            formatRp.setGroupingSeparator('.');
            
            kursIndonesia.setDecimalFormatSymbols(formatRp);
            Number number = kursIndonesia.parse(value);
            double nilai = number.doubleValue();
            return nilai;
        } catch (ParseException ex) {
            Logger.getLogger(Currency.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
     
}
