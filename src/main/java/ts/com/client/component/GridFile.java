/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ts.com.client.component;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import ts.com.client.factory.Alerts;
/** 
 * 
 * @author Enrique Gutierrez Arias
 * 
 */
public class GridFile extends VerticalLayout{

    //Carpeta
//    private String sCarpAct = System.getProperty("user.dir");
    private String sCarpAct = null;
    private File[] archivos;
    
    private PiTable<FileModel> Grid = new PiTable<>();
    private TextBox txtruta = new TextBox("Direccion", "Ingrese Direccion:");
    //private DirectoryInfo Directory;
    

    public GridFile() {
        super();
        System.out.println("Carpeta del usuario = " + sCarpAct);
        ReadFiles();
        Grid.addCol(FileModel::getName, "NOMBRE");
        Grid.addCol(FileModel::getTipoArchivo, "CARPETA");
        Grid.addCol(FileModel::getPeso, "TAMAÑO").setTextAlign(ColumnTextAlign.END);
        Grid.addCol(FileModel::getFecha, "FECHA MODIFICACION");
        
//        Grid.addComponentColumn(item-> {
//           return item.getTotalSpace();
//        }).setHeader("Nota Asistencia E.").setWidth("35px");
          
        this.add(txtruta,Grid);
        txtruta.addValueChangeListener(e-> {
        setPath(txtruta.getValue());
        });
        
    }

    private void ReadFiles(){
        if (sCarpAct == null) {
            System.out.println("Sin Direccion");
            Alerts.error("Direccion Vacia, Verifique la que ingreso");
        }else{
            File carpeta = new File(sCarpAct);
            this.archivos = carpeta.listFiles();
            List<FileModel> listaFile = new ArrayList<>();
            FileFilter filtro = new FileFilter() {
              @Override
              public boolean accept(File arch) {
                return arch.isFile();
              }
            };

            archivos = carpeta.listFiles(filtro);

            if (archivos == null || archivos.length == 0) {
              System.out.println("No hay elementos dentro de la carpeta actual");
              Alerts.error("Direccion Vacia, Verifique la que ingreso");
              return;
            }
            else {
              SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
              for (int i=0; i< archivos.length; i++) {
                File archivo = archivos[i];
                FileModel fm = new FileModel(archivo);
                listaFile.add(fm);
                System.out.println(String.format("%s (%s) - %d - %s", 
                                                  archivo.getName(), 
                                                  archivo.isDirectory() ? "Carpeta" : "Archivo",
                                                  archivo.length(),
                                                  sdf.format(archivo.lastModified())
                                                  ));
              }
            }
            this.Grid.setList(listaFile);
        }
    }
    
    public void setPath(String path){
        System.out.println("OLIII");
        this.Grid.ListClear();
        this.sCarpAct = path;
        ReadFiles();
    }

}
