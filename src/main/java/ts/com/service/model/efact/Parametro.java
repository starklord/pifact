package ts.com.service.model.efact;
import java.io.Serializable;
import java.util.Objects;
import ts.com.service.util.db.client.TableDB;

/**
 *
 * @author Markov Huahuacondori
 */

@TableDB(name = "efact.txxxx_param")
public class Parametro implements Serializable {

    public Integer id;
    public String creador;
    public Boolean activo;
    public String id_para;
    public String cod_para;
    public String nom_para;
    public String tip_para;
    public String val_para;
    public String ind_esta_para;

    public String getIdPara() {
        return id_para;
    }

    public String getCodPara() {
        return cod_para;
    }

    public String getNomPara(){
        return nom_para;
    }

    public String getTipPara() {
        return tip_para;
    }

    public String getValPara() {
        return val_para;
    }

    public String getIndEstaPara() {
        return ind_esta_para;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Parametro other = (Parametro) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

 
}
