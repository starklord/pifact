package ts.com.client.view.efact;

import com.vaadin.flow.component.applayout.AppLayout;

import ts.com.client.factory.Alerts;
import ts.com.service.factory.Services;
import ts.com.service.model.efact.Empresa;

public class EmpresasView extends EmpresasUI {

    public AppLayout appLayout;

    public EmpresasView(AppLayout appLayout) {
        this.appLayout = appLayout;
        onRefresh();
    }

    @Override
    public void onAdd() {
        EmpresaView view = new EmpresaView();
        view.addCloseHandler(()->{
            this.appLayout.setContent(this);
            onRefresh();
        });
        this.appLayout.setContent(view);
    }

    @Override
    public void onEdit() {
        Empresa empresa = tbl.getValue();
        if(empresa==null){
            Alerts.error("Debe elegir una empresa primero");
        }
        EmpresaView view = new EmpresaView(empresa);
        view.addCloseHandler(()->{
            this.appLayout.setContent(this);
            onRefresh();
        });
        this.appLayout.setContent(view);
    }

    @Override
    public void onRefresh() {
        tbl.setList(Services.getEmpresa().listEmpresas());
    }
    
}
