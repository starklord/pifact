package ts.com.service.impl;

import java.util.ArrayList;
import java.util.List;

import ts.com.service.EmpresaService;
import ts.com.service.model.efact.Empresa;
import ts.com.service.util.db.server.CRUD;

public class EmpresaServiceImpl implements EmpresaService {

    @Override
    public List<Empresa> listEmpresas() {
        List<Empresa> list = new ArrayList<>();
        try {
            list =  CRUD.list(Empresa.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void save(Empresa empresa) throws Exception {
        CRUD.save(empresa);
    }

    @Override
    public void update(Empresa empresa) throws Exception {
        CRUD.update(empresa);
    }
    
}
