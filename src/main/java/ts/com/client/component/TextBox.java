/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.com.client.component;

import com.vaadin.flow.component.textfield.TextField;

import ts.com.service.util.Util;

/**
 *
 * @author User
 */
public class TextBox extends TextField {

    public TextBox(String label, String placeholder) {
        super(label, placeholder);
    }
    public TextBox(String label, String initialValue, String placeholder) {
        super(label, initialValue,placeholder);
    }
    
    
    public TextBox setStyles(String... styles) {
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

    
}
