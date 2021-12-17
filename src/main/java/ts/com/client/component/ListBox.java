/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.com.client.component;

import com.vaadin.flow.component.combobox.ComboBox;
import java.util.ArrayList;
import java.util.List;
import ts.com.service.util.Util;

/**
 *
 * @author User
 */
public class ListBox<T> extends ComboBox<T> {

    public final ArrayList<T> list = new ArrayList<>();

    public ListBox() {
        this("");
    }

    public ListBox(String label) {
        this(label, new ArrayList());
    }

    public ListBox(String label, List<T> list) {
        super(label);
        this.setList(list);
        
    }

    public void setList(T... list) {
        
        List<T> newList = new ArrayList<>();
        
        for(T item : list){
            newList.add(item);
        }
        setList(newList);
    }
    
    public void setList(List<T> list) {
        this.list.clear();
        this.list.addAll(list);
        this.setItems(this.list);
        if(!this.list.isEmpty()){
            this.setValue(this.list.get(0));
        }
    }
    
    public List<T> getList(){
        return this.list;
    }

    public ListBox<T> setStyles(String... styles) {
        for (int i = 0, j = 1; i < styles.length; i += 2, j += 2) {
            String name = styles[i];
            String value = styles[j];
            if (name.toLowerCase().equals(Util.WIDTH_FULL.toLowerCase())) {
                this.setWidthFull();
            } else {
                this.getStyle().set(name, value);
            }
        }
        return this;
    }

}
