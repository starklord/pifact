/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ts.com.service.factory;

import ts.com.service.ErrorService;
import ts.com.service.ParametroService;
import ts.com.service.impl.ErrorServiceImpl;
import ts.com.service.impl.ParametroServiceImpl;

/**
 *
 * @author evanl
 */
public class Services {
    
    private static final ParametroService parametro             = new ParametroServiceImpl();
    private static final ErrorService error                 = new ErrorServiceImpl();

    public static ParametroService getParametro() {
        return parametro;
    }

    public static ErrorService getError() {
        return error;
    }
    
    
}
