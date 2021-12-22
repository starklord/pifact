package ts.com.service;

import java.util.List;

import ts.com.service.model.efact.Empresa;
import ts.com.service.model.efact.Parametro;

public interface EmpresaService {
    
    public List<Empresa> listEmpresas();
    public void save(Empresa empresa, List<Parametro> parametros) throws Exception;
    public void update(Empresa empresa, List<Parametro> parametros) throws Exception;

    public String crearDirectorioEfact(String appName) throws Exception;
}
