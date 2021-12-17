/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.com.client.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 *
 * @author evanl
 */
public class VerticalPanel extends VerticalLayout {
     public VerticalPanel(Component... children) {
        super(children);
    }
    
    
    
    public VerticalPanel setStyles(String... styles) {
        for(int i = 0, j=1 ; i < styles.length; i+=2, j+=2){
            String name = styles[i];
            String value = styles[j];
            this.getStyle().set(name, value);
        }
        return this;
    }
    
}
