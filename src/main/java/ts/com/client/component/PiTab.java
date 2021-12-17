package ts.com.client.component;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;

import ts.com.Client;
import ts.com.client.handler.ClickHandler;

public class PiTab extends Tab {

    public PiTab(VaadinIcon icon, String title, ClickHandler clickHandler) {
        super(icon.create(),new Label(title));
        if(clickHandler!=null){
            this.getElement().addEventListener(Client.CLICK, e->clickHandler.onClick());
        }
    }
    
}
