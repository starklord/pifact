/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ts.com.service;

import java.util.List;
import ts.com.service.model.efact.Parametro;

/**
 *
 * @author evanl
 */
public interface ParametroService {
    
    public List<Parametro> consultarParametroById(String id_para, String cod_para);

    public List<Parametro> consultarParametro(String id_para);

    public void insertarParametro(Parametro parametro)throws Exception;

    public void actualizarParametro(Parametro parametro)throws Exception;
    public Parametro getParametro(String cod_para, String ruc) throws Exception;
    
}
