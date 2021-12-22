/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ts.com.service.factory;

import ts.com.service.EmpresaService;
import ts.com.service.ParametroService;
import ts.com.service.impl.EmpresaServiceImpl;
import ts.com.service.impl.ParametroServiceImpl;

/**
 *
 * @author evanl
 */
public class Services {
    
    private static final ParametroService parametro             = new ParametroServiceImpl();
    private static final EmpresaService empresa         = new EmpresaServiceImpl();

    public static ParametroService getParametro() {
        return parametro;
    }
    
    public static EmpresaService getEmpresa() {
        return empresa;
    }
}
