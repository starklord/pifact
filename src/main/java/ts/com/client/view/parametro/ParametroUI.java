package ts.com.client.view.parametro;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.IntegerField;

import ts.com.client.component.Button;
import ts.com.client.component.Label;
import ts.com.client.component.ListBox;
import ts.com.client.component.PanelUI;
import ts.com.client.component.TextBox;

public abstract class ParametroUI extends PanelUI {


    public final Label lblEmpresa     = new Label("Datos de la Empresa").setStyles("color","lime");
    public final Checkbox chkBuyFractionable          = new Checkbox("Comprar por Fracciones");
    public final Checkbox chkShowMedicalPerspective   = new Checkbox("Ver como Perspectiva Medica");
    public final Checkbox chkBuyWithoutStock         = new Checkbox("Permitir dar Salida sin Stock");
    public final Checkbox chkRequireSalesPin             = new Checkbox("Requerir Pin para Grabar");
    public final Checkbox chkIsFastPOS                   = new Checkbox("Es Punto de Venta Rápido");
    public final TextBox txtLogoEnterprise            = new TextBox("Url del Logo Empresarial","http://...");
    public final TextBox txtLogoWidth                 = new TextBox("Tamaño en Pixeles del Logo","200px");
    public final TextBox txtCommercialName            = new TextBox("Nombre Comercial","");
    public final TextBox txtAppName                   = new TextBox("Nombre de la Aplicación","");
    public final TextBox txtPathWebapp                = new TextBox("Path WebApps","/var/lib/tomcat9/webapps","");    

    public final FormLayout pnlEmpresa          = new FormLayout(
        chkBuyFractionable,chkShowMedicalPerspective,chkBuyWithoutStock,chkRequireSalesPin,
        chkIsFastPOS,txtLogoEnterprise,txtLogoWidth,txtCommercialName,
        txtAppName,txtPathWebapp
    );
    public final Label lblSucursal     = new Label("Datos de la Sucursal").setStyles("color","lime");

    public final TextBox txtCodigo                  = new TextBox("Codigo", "");
    public final TextBox txtDescripcion             = new TextBox("Descripcion", "");
    public final IntegerField txtInvoicePort             = new IntegerField("Invoice Puerto");
    public final TextBox txtInvoiceRuc              = new TextBox("Invoice RUC", "");
    public final TextBox txtInvoiceCommercialName   = new TextBox("Invoice Nombre Comercial", "");
    public final TextBox txtInvoiceLogoUrl          = new TextBox("Invoice Logo Url", "");
    public final TextBox txtInvoicePathSunat        = new TextBox("Invoice Path Sunat", "/home/...","");


    public final FormLayout pnlSucursal     = new FormLayout(
        txtCodigo,txtDescripcion,txtInvoicePort,txtInvoiceRuc,txtInvoiceCommercialName,
        txtInvoiceLogoUrl,txtInvoicePathSunat
    );
    
    public ParametroUI() {
        super(VaadinIcon.TOOLS, "Mantenimiento del Sistema");
        getBody().add(lblEmpresa,pnlEmpresa,lblSucursal,pnlSucursal,getToolBar());
        getToolBar().add(getBtnSave(),getBtnClose());
        initStyles();
    }

    private void initStyles(){
        pnlEmpresa.setResponsiveSteps(
        new ResponsiveStep("1px", 1),
        new ResponsiveStep("400px", 2),
        new ResponsiveStep("800px", 3));
        pnlSucursal.setResponsiveSteps(
        new ResponsiveStep("1px", 1),
        new ResponsiveStep("400px", 2),
        new ResponsiveStep("800px", 3));
        getBtnSave().setWidthFull();
    }


}
