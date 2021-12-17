/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ts.com.client.component;
import java.io.File;
import java.text.SimpleDateFormat;
/**
 * 
 * @author Enrique Gutierrez Arias
 */
public class FileModel {

    public File file;
    public String name;
    public String peso;
    public Boolean achivo;
    public String tipoArchivo;
    public String fecha;
    
    public FileModel(File file) {
        this.file = file;
        this.name = file.getName();
        this.peso = String.valueOf(file.length()) + " KB";
        this.tipoArchivo = file.isDirectory() ? "Carpeta" : "Archivo";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.fecha = sdf.format(file.lastModified());
    }

    public String getName() {
        return name;
    }

    public String getPeso() {
        return peso;
    }

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public String getFecha() {
        return fecha;
    }
    
    

}
