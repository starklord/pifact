/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.com.client.view;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.flow.component.applayout.AppLayout;

import sistema.facturador.persistence.Documento;
import ts.com.client.factory.Alerts;
import ts.com.client.view.parametro.ParametroView;
import ts.com.server.Server;
import ts.com.service.impl.BandejaDocumentosServiceImpl;
import ts.com.service.util.Util;
import ts.com.service.util.db.CConexion;

/**
 *
 * @author StarkLord
 */

public class MenuView extends MenuUI {

    public static final String SRC = "D:/evan/ccna/dorothy.pdf";
    public static final String DEST = "D:/evan/ccna/evander.pdf";

    public AppLayout appLayout;

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
        // if (comprobantesView == null) {
        // comprobantesView = new ComprobantesView();
        // }
        // this.appLayout.setDrawerOpened(false);
        // this.appLayout.setContent(comprobantesView);
    }

    @Override
    public void onMantenimiento() {
        this.appLayout.setContent(new ParametroView());
    }

    @Override
    public void onNotasCredito() {
        try{
            runTestPiFact("01");
        }catch(Exception ex){
            ex.printStackTrace();
            Alerts.error("error: " + ex.getMessage());
        }
    }

    @Override
    public void onNotasDebito() {
        try{
            runTestPiFact("02");
        }catch(Exception ex){
            ex.printStackTrace();
            Alerts.error("error: " + ex.getMessage());
        }
    }

    public void testPiFact() {
        try {
            initDB();
            runTestPiFact("01");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void runTestPiFact(String opcion) throws Exception {

        // 20606793881-03-B004-00003903
        System.out.println("Empezando la accion");
        String ruc = "20606793881";
        String serie = "B004";
        String numero = Util.completeWithZeros("3903", 8);
        String nom = ruc+"-"+"03"+"-"+serie+"-"+numero;
        Documento doc = new Documento();
        doc.setNum_ruc(ruc);
        doc.setTip_docu("03");
        doc.setNum_docu(numero);
        doc.setInd_situ(opcion);
        doc.setTip_arch("TXT");
        doc.setNom_arch(nom);
        BandejaDocumentosServiceImpl bandejaDocumentosService = new BandejaDocumentosServiceImpl();
        if (opcion.equals("01")) {
            String firma = bandejaDocumentosService.generarComprobantePagoSunat(doc);
            System.out.println("Firma: " + firma);
        } else {
            bandejaDocumentosService.enviarComprobantePagoSunat(doc);
        }
        System.out.println("Accion finalizada");
    }

    private static void initDB() {
        System.out.println("inicializando datos de la base de datos...");

        String ip = "154.53.32.33";
        // String ip = "localhost";
        String port = "7077";
        String db_name = "copacabanadb";
        Server.DB_PWD = "evadb7007";
        // ------------------------------------------------
        Server.DB_DRIVER = "org.postgresql.Driver"; 
        Server.DB_USR = "postgres";
        Server.DB_URL = "jdbc:postgresql://" + ip + ":" + port + "/" + db_name;

        CConexion.strDriver = Server.DB_DRIVER;
        CConexion.strPwd = Server.DB_PWD;
        CConexion.strUrl = Server.DB_URL;
        CConexion.strUsr = Server.DB_USR;
    }

}
