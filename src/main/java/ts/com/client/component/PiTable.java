/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.com.client.component;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.function.ValueProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author User
 */
public class PiTable<T> extends Grid<T> {

    private List<T> list = new ArrayList<>();
    private boolean isNumbered = false;
    private boolean isMultiSelect = false;

    public PiTable() {
        this(false,false);
    }
    public PiTable(boolean isMultiSelect) {
        this(false,isMultiSelect);
    }

    public PiTable(boolean isNumbered, boolean isMultiSelect) {
        super();
        this.isNumbered = isNumbered;
        this.isMultiSelect = isMultiSelect;
        if (this.isMultiSelect) {
            this.setSelectionMode(Grid.SelectionMode.MULTI);
        }
        this.setList(list);
        if(this.isNumbered){
            this.addCol(this::getIndexOfValue, "#");
        }
        // this.addThemeVariants(GridVariant.LUMO_COMPACT);
        // this.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        // this.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS);
        
    }

    public List<T> getList() {
        return list;
    }
    
    public int getIndexOfValue(T value) {
      return list.indexOf(value)+1;
    };
    
    public void GetIsNumbered(){
        this.addCol(this::getIndexOfValue, "#").setWidth("25px");
    }
    
    public void SetRemoveAllColumns(){
        this.removeAllColumns();
    }
    
    public T getValue() {
        Optional<T> fsitem = this.getSelectionModel().getFirstSelectedItem();
        if(fsitem==null){
            return null;
        }
        return fsitem.isPresent()?fsitem.get():null;
    }

    public List<T> getValues() {
        Set<T> set = this.getSelectionModel().getSelectedItems();
        ArrayList<T> list = new ArrayList<>(set);
        return list;
    }

    public void setList(List<T> list) {
        System.out.println("TAMAÑO LISTA: " + list.size());
        this.list.clear();
        this.list.addAll(list);
        this.setItems(list);
    }
    
    public void ListClear() {
        this.list.clear();
        List<T> c = new ArrayList<>();
        this.setList(c);
    }

    public Grid.Column<T> addCol(ValueProvider<T, ?> valueProvider, String header) {
        Grid.Column<T> column = this.addColumn(valueProvider);
        column.setHeader(header);
        column.setAutoWidth(true);
        column.setSortable(true);
        column.setResizable(true);
        return column;
    }

}
