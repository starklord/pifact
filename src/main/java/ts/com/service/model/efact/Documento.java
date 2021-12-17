package ts.com.service.model.efact;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import ts.com.service.util.db.client.TableDB;

@TableDB(name = "efact.txxxx_bandfact")
public class Documento implements Serializable {

    public Integer id;
    public String creador;
    public Boolean activo;
    public String num_ruc;
    public String tip_docu;
    public String num_docu;
    public Date fec_carg;
    public Date fec_gene;
    public Date fec_envi;
    public String des_obse;
    public String nom_arch;
    public String ind_situ;
    public String tip_arch;
    public String firm_digital;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.id);
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
        final Documento other = (Documento) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    


}