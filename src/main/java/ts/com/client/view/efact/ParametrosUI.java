package ts.com.client.view.efact;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

import ts.com.client.component.ListBox;
import ts.com.client.component.PanelUI;
import ts.com.client.component.PiTable;
import ts.com.service.model.efact.Parametro;

public class ParametrosUI extends PanelUI {

    public final ListBox<String> lbxRucs = new ListBox<>("Empresas");
    public final PiTable<Parametro> tbl  = new PiTable<>(true, false);

    public ParametrosUI() {
        super(VaadinIcon.BULLETS, "Parametros Iniciales para las empresas");
        tbl.addCol(Parametro::getIdPara     , "Id");
        tbl.addCol(Parametro::getCodPara    , "Codigo");
        tbl.addCol(Parametro::getNomPara    , "Nombre");
        tbl.addCol(Parametro::getTipPara    , "Tipo");
        tbl.addCol(Parametro::getValPara    , "Valor");
        tbl.addCol(Parametro::getIndEstaPara, "Ind");
        getBody().add(lbxRucs,tbl);
        initStyles();
    }

    private void initStyles() {
        lbxRucs.setWidthFull();
    }
    
}
