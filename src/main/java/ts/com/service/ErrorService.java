/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ts.com.service;

import ts.com.service.model.efact.Error;
/**
 *
 * @author evanl
 */
public interface ErrorService {
    public Error consultarErrorById(int idError);
}
