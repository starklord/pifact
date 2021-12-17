/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.com.client.component;

import com.vaadin.flow.component.datepicker.DatePicker;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import ts.com.service.util.Util;

/**
 *
 * @author evanl
 */
public class DateBox extends DatePicker {
    
    public DateBox() {
        this("");
        
    }

    public DateBox(String label) {
        super(label);
        this.setLocale(Locale.forLanguageTag("es-PE"));
        setValue(LocalDate.now());
    }
    
    
    public DateBox setStyles(String... styles) {
        for(int i = 0, j=1 ; i < styles.length; i+=2, j+=2){
            String name = styles[i];
            String value = styles[j];
            if(name.toLowerCase().equals(Util.WIDTH_FULL.toLowerCase())){
                this.setWidthFull();
            }else{
                this.getStyle().set(name, value);
            }
        }
        return this;
    }
    
    public Date getValueAsDate(){
        return Util.asDate(getValue());
    }
    
    public void setValueAsDate(Date date){
        this.setValue(Util.asLocalDate(date));
    }
}
