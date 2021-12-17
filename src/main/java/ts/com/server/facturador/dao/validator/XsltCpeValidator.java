package ts.com.server.facturador.dao.validator;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import net.sf.saxon.event.Receiver;
import net.sf.saxon.jaxp.TransformerImpl;
import net.sf.saxon.serialize.MessageEmitter;
import net.sf.saxon.trans.XPathException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sistema.facturador.ConfigurationHolder;
import sistema.facturador.dao.ErrorDao;
import sistema.facturador.exception.XsltException;
import sistema.facturador.persistence.Error;
import sistema.facturador.service.ComunesService;
import sistema.facturador.util.FacturadorUtil;
import ts.com.server.facturador.dao.config.XsltCpePath;

public class XsltCpeValidator {
  private static final Logger log = LoggerFactory.getLogger(XsltCpeValidator.class);
  
  private ComunesService comunesService;
  
  private ErrorDao errorDao;
  
  public XsltCpeValidator(ComunesService comunesService, ErrorDao errorDao) {
    this.comunesService = comunesService;
    this.errorDao = errorDao;
  }
  
  public String validarXML(String tipoComprobante, String rutaEntrada, String nombreArchivo) throws XsltException {
    String XsltFullPath = "", retorno = "", outputHTML = "";
    String XmlFullPath = rutaEntrada + nombreArchivo + ".xml";
    ConfigurationHolder config = ConfigurationHolder.getInstance();
    config.set(new XsltCpePath());
    if ("RA".equals(tipoComprobante)) {
      XsltFullPath = this.comunesService.obtenerRutaTrabajo("VALI") + config.getXsltCpePath().getResumenAnulado();
    } else if ("03".equals(tipoComprobante)) {
      XsltFullPath = this.comunesService.obtenerRutaTrabajo("VALI") + config.getXsltCpePath().getBoleta();
    } else if ("01".equals(tipoComprobante)) {
      XsltFullPath = this.comunesService.obtenerRutaTrabajo("VALI") + config.getXsltCpePath().getFactura();
    } else if ("07".equals(tipoComprobante)) {
      XsltFullPath = this.comunesService.obtenerRutaTrabajo("VALI") + config.getXsltCpePath().getNotaCredito();
    } else if ("08".equals(tipoComprobante)) {
      XsltFullPath = this.comunesService.obtenerRutaTrabajo("VALI") + config.getXsltCpePath().getNotaDebito();
    } else if ("RC".equals(tipoComprobante)) {
      XsltFullPath = this.comunesService.obtenerRutaTrabajo("VALI") + config.getXsltCpePath().getResumenBoleta();
    } else if ("RR".equals(tipoComprobante)) {
      XsltFullPath = this.comunesService.obtenerRutaTrabajo("VALI") + config.getXsltCpePath().getResumenReversion();
    } else if ("20".equals(tipoComprobante)) {
      XsltFullPath = this.comunesService.obtenerRutaTrabajo("VALI") + config.getXsltCpePath().getRetencion();
    } else if ("40".equals(tipoComprobante)) {
      XsltFullPath = this.comunesService.obtenerRutaTrabajo("VALI") + config.getXsltCpePath().getPercepcion();
    } else if ("09".equals(tipoComprobante)) {
      XsltFullPath = this.comunesService.obtenerRutaTrabajo("VALI") + config.getXsltCpePath().getGuia();
    } 
    outputHTML = this.comunesService.obtenerRutaTrabajo("FIRM") + nombreArchivo + ".xml";
    try {
      transform(XmlFullPath, XsltFullPath, outputHTML);
      String rutaZipSalida = this.comunesService.obtenerRutaTrabajo("ENVI") + nombreArchivo + ".zip";
      OutputStream archivoZip = new FileOutputStream(rutaZipSalida);
      InputStream archivoXml = new FileInputStream(XmlFullPath);
      FacturadorUtil.comprimirArchivo(archivoZip, archivoXml, nombreArchivo + ".xml");
      archivoZip.close();
      archivoXml.close();
    } catch (Exception e) {
      e.printStackTrace();
      String mensaje = "";
      String mensajeError = "", nroObtenido = "";
      mensaje = e.getMessage();
      log.error("Error en Transform: " + mensaje);
      nroObtenido = FacturadorUtil.obtenerNumeroEnCadena(mensaje);
      log.error("Error en Transform Numero de Linea: " + nroObtenido);
      if ("".equals(nroObtenido)) {
        retorno = mensaje;
      } else {
        Error error = this.errorDao.consultarErrorById(new Integer(nroObtenido));
        if (error != null) {
          mensajeError = error.getNom_cataerro();
        } else {
          mensajeError = mensaje;
        } 
        log.debug("Error Obtenido Descripcion: " + mensajeError + mensaje);
        retorno = nroObtenido + " - " + mensajeError + " Detalle: " + mensaje;
      } 
    } 
    if (!"".equals(retorno)) {
      log.error(retorno);
      throw new XsltException("Error al validar XML: " + retorno);
    } 
    return retorno;
  }
  
  public List<Exception> transform(String XmlFullPath, String XsltFullPath, String outputHTML) throws TransformerException {
    OutputStream fos = null;
    final File archivoXML = new File(XmlFullPath);
    if (!archivoXML.exists())
      throw new RuntimeException("No existe la plantilla para el tipo documento a validar XML (Archivo XSL). nombre del archivo XSLT de validacion:" + XsltFullPath); 
    fos = new ByteArrayOutputStream();
    Writer out = null;
    try {
      out = new OutputStreamWriter(fos, "ISO8859_1");
    } catch (UnsupportedEncodingException e1) {
      throw new RuntimeException(e1);
    } 
    StreamSource xsltStreamSource = getXsltStreamSource(XsltFullPath);
    StreamSource xmlStreamSource = getXmlStreamSource(XmlFullPath);
    TransformerFactory transformerFactory = TransformerFactory.newInstance("net.sf.saxon.TransformerFactoryImpl", null);
    log.debug("Ejecutandose transformerFactory.newTransformer. Antes");
    Transformer transformer = transformerFactory.newTransformer(xsltStreamSource);
    TransformerImpl transformer1 = (TransformerImpl)transformer;
    final List<Exception> lstWarning = new ArrayList<>();
    transformer1.getUnderlyingController().setRecoveryPolicy(2);
    transformer1.getUnderlyingController().setMessageEmitter((Receiver)new MessageEmitter() {
          boolean abort = false;
          
          public void startDocument(int properties) throws XPathException {
            setWriter(new StringWriter());
            this.abort = ((properties & 0x4000) != 0);
            super.startDocument(properties);
          }
          
          public void endDocument() throws XPathException {
            XPathException de = new XPathException(getWriter().toString());
            de.setErrorCode("XTMM9000");
            if (this.abort)
              throw de; 
            lstWarning.add(de);
            XsltCpeValidator.log.warn("Observaciones XML: " + archivoXML + " - " + de);
            super.endDocument();
          }
          
          public void close() {}
        });
    transformer.setErrorListener(new ErrorListener() {
          public void warning(TransformerException exception) throws TransformerException {}
          
          public void fatalError(TransformerException exception) throws TransformerException {}
          
          public void error(TransformerException exception) throws TransformerException {}
        });
    transformer.setParameter("nombreArchivoEnviado", archivoXML.getName());
    log.debug("Ejecutandose transformerFactory.newTransformer. Despues");
    log.debug("Ejecutandose transformer.transform");
    try {
      fos = new FileOutputStream(outputHTML);
      out = new OutputStreamWriter(fos, "ISO8859_1");
      transformer.setOutputProperty("encoding", "ISO-8859-1");
      transformer.transform(xmlStreamSource, new StreamResult(out));
      fos.close();
    } catch (IOException e) {
      log.warn("No se pudo cerrar la salida de la transformacion", e);
    } 
    return lstWarning;
  }
  
  private StreamSource getXmlStreamSource(String dataXML) {
    log.debug("String: (" + dataXML + ")");
    StreamSource xmlStreamSource = null;
    try {
      xmlStreamSource = new StreamSource(new InputStreamReader(new FileInputStream(dataXML), "ISO8859_1"));
    } catch (UnsupportedEncodingException e1) {
      throw new RuntimeException("Archivo XML no soporta codificacion de caracteres", e1);
    } catch (FileNotFoundException e1) {
      throw new RuntimeException("Archivo XML no encontrado: " + dataXML, e1);
    } 
    return xmlStreamSource;
  }
  
  private StreamSource getXsltStreamSource(String inputXSL) {
    StreamSource xlsStreamSource = null;
    try {
      xlsStreamSource = new StreamSource(new InputStreamReader(new FileInputStream(inputXSL), "ISO8859_1"));
    } catch (UnsupportedEncodingException e1) {
      throw new RuntimeException("Archivo XSLT no soporta codificacion de caracteres", e1);
    } catch (FileNotFoundException e1) {
      throw new RuntimeException("No existe la plantilla (Archivo XSL). nombre del archivo XSLT de validacion:" + inputXSL);
    } 
    return xlsStreamSource;
  }
}
