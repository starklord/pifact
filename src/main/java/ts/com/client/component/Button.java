/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.com.client.component;

import com.vaadin.flow.component.icon.VaadinIcon;
import ts.com.client.handler.ClickHandler;
import ts.com.service.util.Util;

/**
 *
 * @author rednave
 */
public class Button extends com.vaadin.flow.component.button.Button {


    
    
    public Button(String text){
        this(text,null,null,null);
    }
    
    public Button(String text, VaadinIcon icon) {
        this(text,icon,null,null);
    }
    
    public Button(String text, VaadinIcon icon, String title) {
        this(text,icon,title,null);
    }
    
    public Button(String text, VaadinIcon icon, ClickHandler clickHandler) {
        this(text,icon,null,clickHandler);
    }
    
    public Button(String text, VaadinIcon icon, String title, ClickHandler clickHandler) {
        this.addClickListener(e->this.setEnabled(true));
        this.setDisableOnClick(true);
        if(text!=null){
            this.setText(text);
        }
        if(icon!=null){
            this.setIcon(icon.create());
        }
        if(title!=null){
            this.getElement().setProperty("title", title);
        }
        if(clickHandler!=null){
            this.addClickListener(e-> clickHandler.onClick());
        }
//        this.getElement().addEventListener("mouseover", e->{
//            this.setStyle("cursor", "pointer");
//        });
    }
    
    public void setTitle(String title) {
        this.getElement().setProperty("title", title);
    }
    
    public Button setStyle(String name, String value) {
        this.getStyle().set(name, value);
        return this;
    }
    
    
    public Button setStyles(String... styles) {
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
    
    public Button() {
        
        
    }
    
}
