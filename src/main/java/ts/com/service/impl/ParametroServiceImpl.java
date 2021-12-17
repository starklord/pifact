/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ts.com.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ts.com.service.ParametroService;
import ts.com.service.model.efact.Parametro;
import ts.com.service.util.db.server.CRUD;

/**
 *
 * @author evanl
 */
public class ParametroServiceImpl implements ParametroService {

    @Override
    public List<Parametro> consultarParametroById(String id_para, String cod_para) {
        List<Parametro> list = new ArrayList<>();
        try {
            list = CRUD.list(Parametro.class, "where id_para = '" + id_para + "'"
                    + " and cod_para = '" + cod_para + "'");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Parametro> consultarParametro(String id_para) {
        List<Parametro> list = new ArrayList<>();
        try {
            list = CRUD.list(Parametro.class, "where id_para = '" + id_para + "'");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public void insertarParametro(Parametro parametro) throws Exception{
        CRUD.save(parametro);
    }

    @Override
    public void actualizarParametro(Parametro parametro) throws Exception{
        CRUD.update(parametro);
    }

}
