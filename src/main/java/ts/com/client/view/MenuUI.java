/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.com.client.view;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

import ts.com.client.component.PiTab;
import ts.com.client.component.PiTabMenu;

/**
 *
 * @author StarkLord
 */
public abstract class MenuUI extends VerticalLayout {

        public HorizontalLayout layOpciones = new HorizontalLayout();
        public Image img_logo = new Image("images/logo_pi.png", "logo_app  ");

        // OPCIONES
        public PiTab tabCambiarClave            = new PiTab(VaadinIcon.KEY, "Cambiar Clave", this::onCambiarClave);
        public PiTab tabAcercaDe                = new PiTab(VaadinIcon.KEY, "Acerca De", this::onAcercaDe);
        public PiTab tabSalir                   = new PiTab(VaadinIcon.KEY, "Salir", this::onSalir);
 
        // FACTURACION ELECTRONICA
        public PiTab tabComprobantes    =   new PiTab(VaadinIcon.INVOICE, "Comprobantes",this::onComprobantes);
        public PiTab tabNotasCredito    =   new PiTab(VaadinIcon.FILE, "Notas de Crédito",this::onNotasCredito);
        public PiTab tabNotasDebito     =   new PiTab(VaadinIcon.FILE_O, "Notas de Débito" ,this::onNotasDebito);
        
        // Mantenimiento
        public PiTab tabMantenimiento   =   new PiTab(VaadinIcon.TOOLS, "Mantenimiento" ,this::onMantenimiento);

        public PiTabMenu tabEfact       =  new PiTabMenu(VaadinIcon.INVOICE,"Facturacion",
        tabComprobantes,tabNotasCredito,tabNotasDebito
        );

        public Tab tabOpciones          = new PiTabMenu(VaadinIcon.COG, "Opciones", 
                                        tabCambiarClave,
                                        tabAcercaDe);

        public MenuUI() {
                final Tabs tabs = new Tabs(
                        tabOpciones,
                        tabEfact,
                        tabMantenimiento,
                        tabSalir
                        );

                tabs.setOrientation(Tabs.Orientation.VERTICAL);
                layOpciones.getStyle().set("overflow", "hidden");
                this.getStyle().set("overflow", "auto");
                tabs.setWidthFull();
                layOpciones.setWidthFull();
                layOpciones.add(tabs);
                add(img_logo, layOpciones);
                initStyles();

        }

        private void initStyles() {
                this.setSizeFull();
                this.setSpacing(false);
                this.setMargin(false);
                this.setPadding(false);
                this.setAlignItems(Alignment.CENTER);
                img_logo.setWidth("150px");
                img_logo.getStyle().set("margin-top", "10px");

        }
        public abstract void onAcercaDe();
        public abstract void onCambiarClave();
        public abstract void onSalir();
        public abstract void onComprobantes();
        public abstract void onNotasCredito();
        public abstract void onNotasDebito();
        public abstract void onMantenimiento();
}
