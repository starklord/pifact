package ts.com.client.component;

import java.util.Date;

import ts.com.client.handler.ValueChangeHandler;

public class PiDatePicker extends HorizontalPanel {

    public final DateBox dbxInicio  = new DateBox();
    public final DateBox dbxFin     = new DateBox();
    public ValueChangeHandler handler = () -> {};

    public PiDatePicker(){
        this.add(dbxInicio,dbxFin);
        dbxInicio   .addValueChangeListener(e-> handler.onValueChange());
        dbxFin      .addValueChangeListener(e-> handler.onValueChange());
        initStyles();
    }

    private final void initStyles() {
    }

    public Date getInicio() {
        Date date = dbxInicio.getValueAsDate();
        date.setSeconds(0);
        date.setMinutes(0);
        date.setHours(0);
        return date;
    }

    public Date getFin() {
        Date date = dbxFin.getValueAsDate();
        date.setSeconds(59);
        date.setMinutes(59);
        date.setHours(23);
        return date;
    }

    public void addValueChangeHandler(ValueChangeHandler handler){
        this.handler = handler;
    }

}
