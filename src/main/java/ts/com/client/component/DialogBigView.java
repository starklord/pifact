/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.com.client.component;

import com.vaadin.flow.component.dialog.Dialog;
import ts.com.client.handler.CloseHandler;

/**
 *
 * @author Enrique
 */
public class DialogBigView extends Dialog {
    
    public PanelUI panelView = null;
    public CloseHandler closeHandler = () -> {
		// TODO Auto-generated method stub
		
	};

    public DialogBigView(PanelUI view) {
        this.setWidth("70%");
        this.setHeight("70%");
        this.setCloseOnEsc(true);
        this.setCloseOnOutsideClick(false);
        this.add(view,view.getToolBar());
    }
    
    
    
}