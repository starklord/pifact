/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ts.com.service.impl;

import java.util.ArrayList;
import java.util.List;
import ts.com.service.ErrorService;
import ts.com.service.model.efact.Error;
import ts.com.service.util.db.server.CRUD;

/**
 *
 * @author evanl
 */
public class ErrorServiceImpl implements ErrorService {

    @Override
    public Error consultarErrorById(int idError) {
        List<Error> list =  new ArrayList<>();
        try {
            list = CRUD.list(Error.class,"where cod_cataerro = '" + idError+"'");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list.isEmpty()?null:list.get(0);
    }
    
}
