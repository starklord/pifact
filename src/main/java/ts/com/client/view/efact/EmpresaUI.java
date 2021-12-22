package ts.com.client.view.efact;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;

import ts.com.client.component.Button;
import ts.com.client.component.Label;
import ts.com.client.component.PanelUI;
import ts.com.client.component.PasswordTextBox;
import ts.com.client.component.TextBox;

public abstract class EmpresaUI extends PanelUI {

    public final TextBox txtNumRuc = new TextBox("RUC", "", "");
    public final TextBox txtRazon = new TextBox("Razon Social", "", "");
    public final TextBox txtNomCom = new TextBox("Nombre Comercial", "", "");
    public final TextBox txtDepar = new TextBox("Departamento", "AREQUIPA", "");
    public final TextBox txtProvin = new TextBox("Provincia", "AREQUIPA", "");
    public final TextBox txtDistr = new TextBox("Distrito", "AREQUIPA", "");
    public final TextBox txtUrbaniza = new TextBox("Urbanizacion", "CERCADO", "");
    public final TextBox txtDirecc = new TextBox("Direccion", "MI DIRECCION", "");
    public final TextBox txtUbigeo = new TextBox("Ubigeo", "040101", "");
    
    public final TextBox txtAppName = new TextBox("Nombre de la Aplicacion", "app", "");
    public final TextBox txtRutsol = new TextBox("Rutal de la Solucion", "/home/pisoft/miempresa/fs14", "");
    public final TextBox txtNomCert = new TextBox("Nombre del Certificado", "", "");
    public final PasswordTextBox txtPrkCrt = new PasswordTextBox("Contraseña Certificado", "");
    public final TextBox txtUsusol = new TextBox("Usuario Sol", "", "");
    public final PasswordTextBox txtClasol = new PasswordTextBox("Clave Sol", "");

    public final Button btnCrearDirectorio  = new Button("Crear Directorio", VaadinIcon.FOLDER_ADD,this::onBtnCrearDirectorio);
    public final Button btnSubirCert        = new Button("Subir Certificado", VaadinIcon.UPLOAD,"Subir Certificado");
    public final Button btnSubirAlmcert     = new Button("Subir FacturadorKey", VaadinIcon.UPLOAD,"Subir Alm Certificado");
    public final MultiFileMemoryBuffer bufferSubirAlmcert = new MultiFileMemoryBuffer();
    public final MultiFileMemoryBuffer bufferSubirCert = new MultiFileMemoryBuffer();
    public final Upload uploadSubirAlmcert = new Upload(bufferSubirAlmcert);
    public final Upload uploadSubirCert = new Upload(bufferSubirCert);
    

    public Label lblEmpresa = new Label("Datos de la Empresa").setStyles("color","lime");
    public Label lblCertificado = new Label("Datos del Certificado").setStyles("color","lime");

    public final FormLayout pnlParametros = new FormLayout(
            txtNumRuc, txtRazon, txtNomCom, txtDepar, txtProvin, txtDistr, txtUrbaniza, txtDirecc, txtUbigeo,
            txtAppName, btnCrearDirectorio, txtRutsol );
    public final FormLayout pnlCertificado = new FormLayout(
            uploadSubirAlmcert,uploadSubirCert,txtNomCert, txtPrkCrt, txtUsusol, txtClasol
    );

    public EmpresaUI() {
        super(VaadinIcon.BUILDING, "Empresa");
        //para el upload:
        uploadSubirAlmcert.setAutoUpload(true);
        uploadSubirAlmcert.setDropAllowed(false);
        uploadSubirAlmcert.addSucceededListener(this::onUploadSubirAlmacert);
        uploadSubirAlmcert.setUploadButton(btnSubirAlmcert);

        uploadSubirCert.setAutoUpload(true);
        uploadSubirCert.setDropAllowed(false);
        uploadSubirCert.addSucceededListener(this::onUploadSubirCert);
        uploadSubirCert.setUploadButton(btnSubirCert);

        getBody().add(
            lblEmpresa,
            pnlParametros,
            lblCertificado,
            pnlCertificado, 
            getToolBar());
        getToolBar().add(getBtnSave(), getBtnClose());
        txtNumRuc.focus();
        this.initStyles();
    }

    private void initStyles() {
        txtRutsol.setReadOnly(true);
        txtNomCert.setReadOnly(true);
        btnSubirAlmcert.setWidthFull();
        btnSubirCert.setWidthFull();
        getBtnSave().setWidthFull();
        uploadSubirAlmcert.setWidthFull();
        uploadSubirCert.setWidthFull();
        pnlParametros.setResponsiveSteps(
            new ResponsiveStep("1px", 1),
            new ResponsiveStep("400px", 2),
            new ResponsiveStep("800px", 3)
        );
        pnlCertificado.setResponsiveSteps(
            new ResponsiveStep("1px", 1),
            new ResponsiveStep("400px", 2),
            new ResponsiveStep("800px", 3)
        );
    }

    public abstract void onBtnCrearDirectorio();
    public abstract void onUploadSubirAlmacert(SucceededEvent event);
    public abstract void onUploadSubirCert(SucceededEvent event);

}
