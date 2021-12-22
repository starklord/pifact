package ts.com.client.view.efact;

import ts.com.service.factory.Services;

public class EmpresasView extends EmpresasUI {

    public EmpresasView() {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        tbl.setList(Services.getEmpresa().listEmpresas());
    }
    
}
