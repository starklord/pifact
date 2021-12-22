package ts.com.service.model.efact;

import java.io.Serializable;

import ts.com.service.util.db.client.TableDB;

@TableDB(name="efact.empresa")
public class Empresa implements Serializable {

    public Integer id;
    public Boolean activo;
    public String creador;
    public String ruc;
    public String razon_Social;
    public String nombre_comercial;
    public String app_name;
    

    public String getRuc() {
        return ruc;
    }

    public String getAppName(){
        return app_name;
    }

    public String getRazonSocial(){
        return razon_Social;
    }

    public String getNombreComercial(){
        return nombre_comercial;
    }


    @Override
    public String toString() {
        return ruc+"-"+app_name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Empresa other = (Empresa) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    
    
}
