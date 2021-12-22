package ts.com.client.view.efact;

import ts.com.client.factory.Alerts;
import ts.com.service.factory.Services;
import ts.com.service.model.efact.Empresa;

public class EmpresaView extends EmpresaUI {

    @Override
    public void onSave() {
        Empresa empresa = new Empresa();
        empresa.activo = true;
        empresa.creador = "root";
        empresa.nombre_comercial= txtNomCom.getValue();
        empresa.razon_Social = txtRazon.getValue();
        empresa.ruc = txtNumRuc.getValue();
        try {
            Services.getEmpresa().save(empresa);
            saveAndClose(); 
        } catch (Exception e) {
            Alerts.error(e.getMessage());
            e.printStackTrace();
        }
    }
    
}
