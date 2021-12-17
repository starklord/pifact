/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.com.client.view;

import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import ts.com.client.component.Button;
import ts.com.client.component.Image;
import ts.com.client.component.ListBox;
import ts.com.client.component.PanelUI;
import ts.com.client.component.PasswordTextBox;
import ts.com.client.component.TextBox;
import ts.com.server.Server;

/**
 *
 * @author StarkLord
 */
public abstract class LoginUI extends PanelUI {

        public final Image image = new Image("images/logo_pi.png", "logo_app ").setStyles("maxWidth", "250px");
        // public final Image image = new Image("img/logo_cetpro.png",
        // "logo_app").setStyles(
        // "maxWidth","300px"
        // );

        public final ListBox<String> lbxEmpresas = new ListBox("Empresa").setStyles("width", "100%");
        public final ListBox<String> lbxSucursales = new ListBox("Sucursal").setStyles("width", "100%");
        public final TextBox txtUser = new TextBox("Usuario", "Digitar Usuario").setStyles("width", "100%");
        public final PasswordTextBox txtPass = new PasswordTextBox("Clave", "Digitar Clave").setStyles("width", "100%");
        public final Button btnConnect = new Button("Ingresar", VaadinIcon.KEY, this::onBtnConnect).setStyles("width",
                        "100%", "font-size", "20px", "height", "50px");
        public final Button btnSubscribe = new Button("No tengo usuario. Deseo Inscribirme Ya!").setStyle("color",
                        "#fe4164");
        // public final VerticalLayout pnl = new VerticalLayout(
        // image, lbxEmpresas, lbxSucursales, txtUser, txtPass, btnConnect
        // );
        public final VerticalLayout pnl = new VerticalLayout(image, lbxEmpresas, lbxSucursales, txtUser, txtPass,
                        btnConnect);

        public DatePicker datePicker = new DatePicker();

        public LoginUI() {
                super(VaadinIcon.SHOP, "PiSoft Technologies S.A.C.");

                datePicker.setOpened(true);
                txtUser.addValueChangeListener(e-> txtPass.focus());
                txtPass.addValueChangeListener(e -> btnConnect.focus());
                btnSubscribe.addClickListener(e -> this.onBtnSubscribe());
                btnSubscribe.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
                getBody().add(pnl);
                pnl.setAlignItems(FlexComponent.Alignment.CENTER);
                // init styles
                btnConnect.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                this.getStyle().set("maxWidth", "600px");
                this.getStyle().set("border-radius", "10px ");
                this.setPadding(true);
                txtUser.focus();

        }

        public abstract void onBtnConnect();

        public abstract void onBtnSubscribe();

}
