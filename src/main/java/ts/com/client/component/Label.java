/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.com.client.component;

/**
 *
 * @author User
 */
public class Label extends com.vaadin.flow.component.html.Label {

    public Label(String text) {
        super(text);
    }
    
    
    
    public Label setStyles(String... styles) {
        for(int i = 0, j=1 ; i < styles.length; i+=2, j+=2){
            String name = styles[i];
            String value = styles[j];
            this.getStyle().set(name, value);
        }
        return this;
    }
    
}
