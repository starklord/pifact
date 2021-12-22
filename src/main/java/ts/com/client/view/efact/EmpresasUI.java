package ts.com.client.view.efact;

import com.vaadin.flow.component.icon.VaadinIcon;

import ts.com.client.component.PanelUI;
import ts.com.client.component.PiTable;
import ts.com.service.model.efact.Empresa;

public class EmpresasUI extends PanelUI {

    public PiTable<Empresa> tbl = new PiTable<>(true,false);


    public EmpresasUI() {
        super(VaadinIcon.BUILDING,"Relacion de Empresas");
        tbl.addCol(Empresa::getRuc, "RUC");
        tbl.addCol(Empresa::getRazonSocial, "Razon Social");
        tbl.addCol(Empresa::getNombreComercial, "Nombre Comercial");
        getToolBar().add(getBtnRefresh());
        getHeader().add(getToolBar());
        getBody().add(tbl);
        
    }
    
}
