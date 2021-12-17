/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.com.client.component;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;

/**
 *
 * @author StarkLord
 */
public class PiTabMenu extends Tab {
    
    public Details det = new Details();
    public String title = "Title";
    public Tab tabSummary = new Tab();
    public Tabs tabsContent = new Tabs();
    public Tab[] tabsSuboptions;

    public PiTabMenu(VaadinIcon icon, String title, Tab... suboptions) {
        this.title = title;
        this.tabsSuboptions = suboptions;
        tabSummary.add(icon.create(),new Text(title));
        tabsContent.setOrientation(Tabs.Orientation.VERTICAL);
        tabsContent.add(suboptions);
        tabsContent.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        det.setSummary(tabSummary);
        det.setContent(tabsContent);
        this.add(det);
        initStyles();    
    }
    
    private void initStyles() {
        tabSummary.getStyle().set("padding", "0px");
        det.addThemeVariants(DetailsVariant.REVERSE);
        det.getElement().getStyle().set("width", "100%");
    }
    
    
    
}
