/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.com.client.component;

import com.vaadin.flow.component.timepicker.TimePicker;
import java.sql.Time;
import java.time.LocalDate;
import ts.com.service.util.Util;

/**
 *
 * @author evanl
 */
public class TimeBox extends TimePicker {
    
    public TimeBox(){
        super();
    }
    
    public TimeBox(String label) {
        super(label);
    }
    
    public TimeBox setStyles(String... styles) {
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
    
    public Time getValueAsTime() {
        return Util.asTime(this.getValue());
    }
    
}
