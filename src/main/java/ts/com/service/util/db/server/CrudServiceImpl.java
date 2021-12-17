/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.com.service.util.db.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ts.com.service.util.db.client.CrudService;

/**
 *
 * @author User
 */
public class CrudServiceImpl<T> implements CrudService<T> {
    
    @Override
    public List<T> list(Class clazz) {
        return list(clazz,null,null);
    }
    
    @Override
    public List<T> list(Class clazz, String where) {
        return list(clazz,null,where);
    }

    @Override
    public List<T> list(Class clazz, String[] require, String where) {

        try {
            if(where ==null){
                where = "";
            }
            return CRUD.list(clazz, require, where);
        } catch (Exception ex) {
            Logger.getLogger(CrudServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<T>();

        }
    }

    @Override
    public T save(T entity) {
        try {
            CRUD.save(entity);
            return entity;
        } catch (Exception ex) {
            Logger.getLogger(CrudServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public T update(T entity) {
        try {
            CRUD.update(entity);
            return entity;
        } catch (Exception ex) {
            Logger.getLogger(CrudServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public boolean delete(T entity) {
        try {
            CRUD.delete(entity);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(CrudServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
