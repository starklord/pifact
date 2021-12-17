/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.com.client.view;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.flow.component.applayout.AppLayout;

import ts.com.client.factory.Alerts;
import ts.com.client.view.parametro.ParametroView;

/**
 *
 * @author StarkLord
 */

public class MenuView extends MenuUI {

    public static final String SRC = "D:/evan/ccna/dorothy.pdf";
    public static final String DEST = "D:/evan/ccna/evander.pdf";


    public AppLayout appLayout;

    public MenuView(AppLayout appLayout){
        this.appLayout = appLayout;
    }

    @Override
    public void onAcercaDe() { 
        try {
            Alerts.info("Desarrollado por Pirú Technologies S.A.C.");
        } catch (Exception ex) {
            Logger.getLogger(MenuView.class.getName()).log(Level.SEVERE, null, ex);
            Alerts.error(ex.getMessage());
        }

    }

    @Override
    public void onCambiarClave() {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    public void onSalir() {
        Alerts.confirm("Desea salir de la aplicacion ? ", () -> {

            try {
                App.closeSession();
            } catch (Exception ex) {
                Alerts.error("No se pudo cerrar la sesion");
                Logger.getLogger(MenuView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void onComprobantes() {
        if (comprobantesView == null) {
            comprobantesView = new ComprobantesView();
        }
        this.appLayout.setDrawerOpened(false);
        this.appLayout.setContent(comprobantesView);
    }

    @Override
    public void onMantenimiento() {
        this.appLayout.setContent(new ParametroView());
    }

    @Override
    public void onNotasCredito() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onNotasDebito() {
        // TODO Auto-generated method stub

    }

}
