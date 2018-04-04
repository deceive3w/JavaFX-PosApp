/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 *
 * @author Deceive
 */
public class ValidateTextField {
    TextField textField;
    
    public static ChangeListener<String> isNumberOnly(TextField target){
         return new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                if(newValue){
                    if(!target.getText().matches("^[0-9]*$")){
                        target.setText(oldValue);
                    }
//                }
            }
        };
    }
    public static ChangeListener<String> isLetterOnly(TextField target){
        return new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!target.getText().matches("^[a-zA-Z\\s]*$")){
                    target.setText(oldValue);
                }
            }
        };
    }
}
