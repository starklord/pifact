/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.com.client.view;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ts.com.client.factory.Alerts;

/**
 * 
 * @author StarkLord
 */
public class LoginView extends LoginUI {

    public App app;

    public LoginView(App app) {
        this.app = app;
        List<String> empresas = new ArrayList<>();
        List<String> sucursales = new ArrayList<>();
        try {
            empresas.add("PiSoft Technologies");
            lbxEmpresas.setItems(empresas);
            sucursales.add("Perú");
            lbxSucursales.setItems(sucursales);
            lbxEmpresas.setValue(empresas.get(0));
            lbxSucursales.setValue(sucursales.get(0));
            // txtUser.setValue("admin");
            // txtPass.setValue("123456");
        } catch (Exception ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void onBtnConnect() {
        String user = txtUser.getValue();
        String pass = txtPass.getValue();
        if (user.isEmpty()) {
            txtUser.focus();
            return;
        }
        if (pass.isEmpty()) {
            txtPass.focus();
            return;
        }
        if (user.equals("admin") && pass.equals("123456")) {
            this.app.add(this.app.appLayout);
            this.app.lblPersona.setText("Bienvenido(a) Administrador");
            this.close();
        } else {
            Alerts.error("No se pudo validar el usuario y/o clave");
        }

    }

    @Override
    public void onBtnSubscribe() {
    }

}
