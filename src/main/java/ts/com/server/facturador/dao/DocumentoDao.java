package ts.com.server.facturador.dao;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.inject.Inject;
import org.mybatis.cdi.Mapper;
import sistema.facturador.mybatis.mappers.DocumentoMap;
import sistema.facturador.persistence.Documento;

@ManagedBean
public class DocumentoDao {
  @Inject
  @Mapper
  DocumentoMap documentoMapper;
  
  public List<Documento> consultarBandejaPorId(Documento documento) {
    List<Documento> listado = this.documentoMapper.consultarBandejaPorId(documento);
    return listado;
  }
  
  public List<Documento> consultarBandeja(Documento documento) {
    List<Documento> listado = this.documentoMapper.consultarBandeja(documento);
    return listado;
  }
  
  public List<Documento> consultarBandejaPorSituacion(Documento documento) {
    List<Documento> listado = this.documentoMapper.consultarBandejaPorSituacion(documento);
    return listado;
  }
  
  public void insertarBandeja(Documento documento) {
    this.documentoMapper.insertarBandeja(documento);
  }
  
  public Integer contarBandejaPorNomArch(Documento documento) {
    Integer retorno = this.documentoMapper.contarBandejaPorNomArch(documento);
    return retorno;
  }
  
  public void actualizarBandeja(Documento documento) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String fechaActual = format.format(new Date());
    Documento param = new Documento();
    param.setNom_arch(documento.getNom_arch());
    param.setInd_situ(documento.getInd_situ());
    param.setDes_obse(documento.getDes_obse());
    if ("FECHA_GENERACION".equals(documento.getFec_gene())) {
      param.setFec_gene(fechaActual);
      this.documentoMapper.actualizarBandejaGenerar(param);
    } 
    if ("FECHA_ENVIO".equals(documento.getFec_envi())) {
      param.setFec_envi(fechaActual);
      this.documentoMapper.actualizarBandejaEnviar(param);
    } 
    if (!"FECHA_GENERACION".equals(documento.getFec_gene()) && !"FECHA_ENVIO".equals(documento.getFec_envi()))
      if ("CDR".equals(documento.getFec_envi())) {
        documento.setFec_gene("-");
        this.documentoMapper.actualizarBandejaArchivo(param);
      } else {
        param.setFirm_digital(documento.getFirm_digital());
        this.documentoMapper.actualizarBandejaArchivo(param);
      }  
  }
  
  public void eliminarBandeja(Documento documento) {
    this.documentoMapper.eliminarBandeja(documento);
  }
  
  public List<Documento> consultarBandejaPorNomArch(Documento documento) {
    List<Documento> listado = this.documentoMapper.consultarBandejaPorNomArch(documento);
    return listado;
  }
}
