/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.com.client.component;

import com.vaadin.flow.component.grid.Grid;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author evanl
 */
public class Table<T> extends Grid<T> {

    public final ArrayList<T> list = new ArrayList<>();
    
    public Table() {
        this(false);
    }
    
    public Table(boolean isMultiSelect){
        super();
        if(isMultiSelect){
            this.setSelectionMode(SelectionMode.MULTI);
        }
        this.setList(list);
    }
    
    public Table(List<T> list) {
        this();
        this.setList(list);
    }
    
    public void setList(List<T> list) {
        this.list.clear();
        this.list.addAll(list);
        this.getDataProvider().refreshAll();
        
    }
    
    
}
