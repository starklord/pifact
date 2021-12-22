package ts.com.service;

import java.util.List;

import ts.com.service.model.efact.Empresa;

public interface EmpresaService {
    
    public List<Empresa> listEmpresas();
    public void save(Empresa empresa) throws Exception;
    public void update(Empresa empresa) throws Exception;
}
