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
import ts.com.client.view.efact.EmpresaView;
import ts.com.client.view.efact.EmpresasView;
import ts.com.client.view.efact.ParametroView;
import ts.com.client.view.efact.ParametrosView;

/**
 *
 * @author StarkLord
 */

public class MenuView extends MenuUI {

    public AppLayout appLayout;
    public ParametrosView parametrosView;
    public EmpresasView empresasView;

    public MenuView(AppLayout appLayout) {
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
    public void onParametro() {
        this.appLayout.setContent(new ParametroView());
    }

    @Override
    public void onParametros() {
        if(parametrosView==null){
            parametrosView = new ParametrosView();
        }
        this.appLayout.setContent(parametrosView);
    }

    @Override
    public void onEmpresa() {
        this.appLayout.setContent(new EmpresaView());
    }

    @Override
    public void onEmpresas() {
        if(empresasView == null){
            empresasView = new EmpresasView();
        }
        this.appLayout.setContent(empresasView);
    }


}
