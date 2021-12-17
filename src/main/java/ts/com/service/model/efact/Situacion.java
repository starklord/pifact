/*
 * Copyright (C) 2019 Markov Huahuacondori
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ts.com.service.model.efact;

import java.io.Serializable;
import java.util.Objects;
import ts.com.service.util.db.client.TableDB;

/**
 *
 * @author Markov Huahuacondori
 */

@TableDB(name = "data.txxx_situacion")
public class Situacion implements Serializable {

    public Integer id;
    public String creador;
    public Boolean activo;
    public String cod_situacion;
    public String nom_situacion;

    @Override
    public String toString() {
        return nom_situacion;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final Situacion other = (Situacion) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    

}
