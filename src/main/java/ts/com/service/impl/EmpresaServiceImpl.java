package ts.com.service.impl;

import java.util.ArrayList;
import java.util.List;

import ts.com.service.EmpresaService;
import ts.com.service.model.efact.Empresa;
import ts.com.service.model.efact.Parametro;
import ts.com.service.util.Util;
import ts.com.service.util.db.Update;
import ts.com.service.util.db.server.CRUD;

public class EmpresaServiceImpl implements EmpresaService {

    @Override
    public List<Empresa> listEmpresas() {
        List<Empresa> list = new ArrayList<>();
        try {
            list = CRUD.list(Empresa.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void save(Empresa empresa, List<Parametro> parametros) throws Exception {
        try {
            Update.beginTransaction();
            CRUD.save(empresa);
            for (Parametro param : parametros) {
                param.ruc = empresa.ruc;
                CRUD.save(param);
            }
            Update.commitTransaction();
        } catch (Exception ex) {
            Update.rollbackTransaction();
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
    }

    public void update(Empresa empresa, List<Parametro> parametros) throws Exception {
        try {
            Update.beginTransaction();
            CRUD.update(empresa);
            for (Parametro param : parametros) {
                CRUD.update(param);
            }
            Update.commitTransaction();
        } catch (Exception ex) {
            Update.rollbackTransaction();
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public String crearDirectorioEfact(String appName) throws Exception {
        // String sourceDirectoryLocation = "/home/eva/pifact/SFS21";
        // String destinationDirectoryLocation = "/home/eva/pifact/" + appName +
        // "/SFS21";
        // Util.copyDirectory(sourceDirectoryLocation, destinationDirectoryLocation);
        // return destinationDirectoryLocation;
        String sourceDirectoryLocation = "/home/eva/pifact/SFS21";
        String destinationDirectoryLocation = "/var/lib/tomcat9/webapps/pifact/" + appName + "/SFS21";
        Util.copyDirectory(sourceDirectoryLocation, destinationDirectoryLocation);
        return destinationDirectoryLocation;
    }

}
