package ts.com.client.view.efact;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.server.InitParameters;

import ts.com.client.factory.Alerts;
import ts.com.server.Server;
import ts.com.service.factory.Services;
import ts.com.service.model.efact.Empresa;
import ts.com.service.model.efact.Parametro;

public class EmpresaView extends EmpresaUI {

    public Empresa entity = new Empresa();
    public Parametro paramUrbaniza = new Parametro("URBANIZA", "CERCADO", "Urbanizacion de direccion de Emisor");
    public Parametro paramDistr = new Parametro("DISTR", "AREQUIPA", "Distrito direccion de Emisor");
    public Parametro paramProvin = new Parametro("PROVIN", "AREQUIPA", "Provincia direccion de Emisor");
    public Parametro paramDepar = new Parametro("DEPAR", "AREQUIPA", "Departamento direccion de Emisor");
    public Parametro paramDirecc = new Parametro("DIRECC", "AREQUIPA", "Dirección del Emisor");
    public Parametro paramUbigeo = new Parametro("UBIGEO", "040101", "Ubigeo Dirección del Emisor");
    public Parametro paramNomCom = new Parametro("NOMCOM", "-", "Nombre Comercial del Emisor");
    public Parametro paramPrkCrt = new Parametro("PRKCRT", "-", "Contraseña del Certificado Emisor");
    public Parametro paramNomCert = new Parametro("NOMCERT", "-", "Nombre del Certificado");
    public Parametro paramRutSol = new Parametro("RUTSOL", "-", "Ruta de la solución de software");
    public Parametro paramRazon = new Parametro("RAZON", "-", "Razon Social o Nombres");
    public Parametro paramTimeEnvia = new Parametro("TIMEENVIA", "", "Valor Temporizador del facturador - Enviar");
    public Parametro paramTimeGenera = new Parametro("TIMEGENERA", "", "Valor Temporizador del facturador - Generar");
    public Parametro paramFuncio = new Parametro("FUNCIO", "02", "Tipo Funcionamiento del facturador");
    public Parametro paramClasol = new Parametro("CLASOL", "-", "Clave SOL del Contribuyente Emisor");
    public Parametro paramUsusol = new Parametro("USUSOL", "-", "Usuario SOL del Contribuiyente Emisor");
    public Parametro paramNumRuc = new Parametro("NUMRUC", "-", "Ruc del Contribuyente o Emisor");
    public boolean save = false;

    public EmpresaView() {
        this(null);
    }

    public EmpresaView(Empresa empresa) {
        this.entity = empresa;
        this.save = this.entity == null;
        onRefresh();
    }

    @Override
    public void onRefresh() {
        if (!save) {
            try {
                this.label.setText(this.label.getText() + " " + entity.app_name);
                initParametros();
                this.txtAppName.setValue(entity.app_name);
            } catch (Exception ex) {
                Alerts.error(ex.getMessage());
            }
        }
        this.txtNumRuc.setValue(paramNumRuc.val_para);
        this.txtRazon.setValue(paramRazon.val_para);
        this.txtNomCom.setValue(paramNomCom.val_para);
        this.txtDepar.setValue(paramDepar.val_para);
        this.txtProvin.setValue(paramProvin.val_para);
        this.txtDistr.setValue(paramDistr.val_para);
        this.txtUrbaniza.setValue(paramUrbaniza.val_para);
        this.txtDirecc.setValue(paramDirecc.val_para);
        this.txtUbigeo.setValue(paramUbigeo.val_para);
        this.txtRutsol.setValue(paramRutSol.val_para);
        this.txtNomCert.setValue(paramNomCert.val_para);
        this.txtPrkCrt.setValue(Server.Desencriptar(paramPrkCrt.val_para));
        this.txtUsusol.setValue(paramUsusol.val_para);
        this.txtClasol.setValue(Server.Desencriptar(paramClasol.val_para));
    }

    public void initParametros() throws Exception {
        paramUrbaniza = Services.getParametro().getParametro("URBANIZA", entity.ruc);
        paramDistr = Services.getParametro().getParametro("DISTR", entity.ruc);
        paramProvin = Services.getParametro().getParametro("PROVIN", entity.ruc);
        paramDepar = Services.getParametro().getParametro("DEPAR", entity.ruc);
        paramDirecc = Services.getParametro().getParametro("DIRECC", entity.ruc);
        paramUbigeo = Services.getParametro().getParametro("UBIGEO", entity.ruc);
        paramNomCom = Services.getParametro().getParametro("NOMCOM", entity.ruc);
        paramPrkCrt = Services.getParametro().getParametro("PRKCRT", entity.ruc);
        paramNomCert = Services.getParametro().getParametro("NOMCERT", entity.ruc);
        paramRutSol = Services.getParametro().getParametro("RUTSOL", entity.ruc);
        paramRazon = Services.getParametro().getParametro("RAZON", entity.ruc);
        paramTimeEnvia = Services.getParametro().getParametro("TIMEENVIA", entity.ruc);
        paramTimeGenera = Services.getParametro().getParametro("TIMEGENERA", entity.ruc);
        paramFuncio = Services.getParametro().getParametro("FUNCIO", entity.ruc);
        paramClasol = Services.getParametro().getParametro("CLASOL", entity.ruc);
        paramUsusol = Services.getParametro().getParametro("USUSOL", entity.ruc);
        paramNumRuc = Services.getParametro().getParametro("NUMRUC", entity.ruc);
    }

    @Override
    public void onSave() {
        if (save) {
            entity = new Empresa();
            entity.activo = true;
            entity.creador = "root";
        }
        entity.nombre_comercial = txtNomCom.getValue();
        entity.razon_Social = txtRazon.getValue();
        entity.ruc = txtNumRuc.getValue();
        entity.app_name         = txtAppName.getValue();
        paramNumRuc.val_para    = txtNumRuc.getValue();
        paramRazon.val_para     = txtRazon.getValue();
        paramNomCom.val_para    = txtNomCom.getValue();
        paramDepar.val_para     = txtDepar.getValue();
        paramProvin.val_para    = txtProvin.getValue();
        paramDistr.val_para     = txtDistr.getValue();
        paramUrbaniza.val_para  = txtUrbaniza.getValue();
        paramDirecc.val_para    = txtDirecc.getValue();
        paramUbigeo.val_para    = txtUbigeo.getValue();
        paramRutSol.val_para    = txtRutsol.getValue();
        paramNomCert.val_para   = txtNomCert.getValue();
        paramPrkCrt.val_para    = Server.Encriptar(txtPrkCrt.getValue());
        paramUsusol.val_para    = txtUsusol.getValue();
        paramClasol.val_para    = Server.Encriptar(txtClasol.getValue());
        List<Parametro> parametros = new ArrayList<>();
        parametros.add(paramUrbaniza);
        parametros.add(paramDistr);
        parametros.add(paramProvin);
        parametros.add(paramDepar);
        parametros.add(paramDirecc);
        parametros.add(paramUbigeo);
        parametros.add(paramNomCom);
        parametros.add(paramPrkCrt);
        parametros.add(paramNomCert);
        parametros.add(paramRutSol);
        parametros.add(paramRazon);
        parametros.add(paramTimeEnvia);
        parametros.add(paramTimeGenera);
        parametros.add(paramFuncio);
        parametros.add(paramClasol);
        parametros.add(paramUsusol);
        parametros.add(paramNumRuc);
        try {
            if(save){
                Services.getEmpresa().save(entity,parametros);
            }else{
                Services.getEmpresa().update(entity,parametros);
            }
            saveAndClose();
        } catch (Exception e) {
            Alerts.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onBtnCrearDirectorio() {
        String appName = txtAppName.getValue();
        Alerts.confirm("Se creará el directorio considerando el Nombre de la Aplicacion: " + appName, () -> {
            String rutSol;
            try {
                rutSol = Services.getEmpresa().crearDirectorioEfact(appName);
                txtRutsol.setValue(rutSol);
                Alerts.info("Directorio creado/actualizado satisfactoriamente");
            } catch (Exception e) {
                e.printStackTrace();
                Alerts.error(e.getMessage());
            }

        });

    }

    @Override
    public void onUploadSubirAlmacert(SucceededEvent event) {
        try {
            InputStream is = bufferSubirAlmcert.getInputStream(event.getFileName());
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            String path = txtRutsol.getValue() + "/sunat_archivos/sfs/ALMCERT/" + event.getFileName();
            File newFile = new File(path);
            OutputStream out = new FileOutputStream(newFile);
            out.write(buffer);
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onUploadSubirCert(SucceededEvent event) {
        try {
            txtNomCert.setValue(event.getFileName());
            InputStream is = bufferSubirCert.getInputStream(event.getFileName());
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            String path = txtRutsol.getValue() + "/sunat_archivos/sfs/CERT/" + event.getFileName();
            File newFile = new File(path);
            OutputStream out = new FileOutputStream(newFile);
            out.write(buffer);
            out.close();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
