package ts.com.server.facturador.dao;


import javax.annotation.ManagedBean;
import sistema.facturador.mybatis.mappers.ErrorMap;
import sistema.facturador.persistence.Error;
import sistema.facturador.util.FacturadorUtil;

@ManagedBean
public class ErrorDao {
  ErrorMap errorMapper;
  
  public Error consultarErrorById(Integer idError) {
    String codError = FacturadorUtil.completarCeros(idError.toString(), "I", Integer.valueOf(4));
    Error error = new Error();
    error.setCod_cataerro(codError);
    Error retorno = this.errorMapper.consultarErrorById(error);
    return retorno;
  }
}
