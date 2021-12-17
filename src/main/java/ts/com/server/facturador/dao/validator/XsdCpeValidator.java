package ts.com.server.facturador.dao.validator;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import sistema.facturador.ConfigurationHolder;
import sistema.facturador.dao.ErrorDao;
import sistema.facturador.exception.XsdException;
import sistema.facturador.service.ComunesService;
import sistema.facturador.service.ResourceResolver;
import ts.com.server.facturador.dao.config.XsltCpePath;

public class XsdCpeValidator {
  private static final Logger log = LoggerFactory.getLogger(XsdCpeValidator.class);
  
  private ComunesService comunesService;
  
  public XsdCpeValidator(ComunesService comunesService, ErrorDao errorDao) {
    this.comunesService = comunesService;
  }
  
  public void validarSchemaXML(String tipoComprobante, String nombreArchivo) throws XsdException {
    log.debug("validarSchemaXML...Iniciando Validacion de Schema");
    ConfigurationHolder config = ConfigurationHolder.getInstance();
    config.set(new XsltCpePath());
    try {
      String schemaValidador = "";
      if ("01".equals(tipoComprobante) || "03"
        .equals(tipoComprobante))
        schemaValidador = this.comunesService.obtenerRutaTrabajo("VALI") + config.getXsltCpePath().getFacturaXsd(); 
      if ("07".equals(tipoComprobante))
        schemaValidador = this.comunesService.obtenerRutaTrabajo("VALI") + config.getXsltCpePath().getNotaCreditoXsd(); 
      if ("08".equals(tipoComprobante))
        schemaValidador = this.comunesService.obtenerRutaTrabajo("VALI") + config.getXsltCpePath().getNotaDebitoXsd(); 
      if ("RA".equals(tipoComprobante))
        schemaValidador = this.comunesService.obtenerRutaTrabajo("VALI") + config.getXsltCpePath().getResumenAnuladoXsd(); 
      if ("RC".equals(tipoComprobante))
        schemaValidador = this.comunesService.obtenerRutaTrabajo("VALI") + config.getXsltCpePath().getResumenBoletaXsd(); 
      if ("RR".equals(tipoComprobante))
        schemaValidador = this.comunesService.obtenerRutaTrabajo("VALI") + config.getXsltCpePath().getResumenReversionXsd(); 
      if ("20".equals(tipoComprobante))
        schemaValidador = this.comunesService.obtenerRutaTrabajo("VALI") + config.getXsltCpePath().getRetencionXsd(); 
      if ("40".equals(tipoComprobante))
        schemaValidador = this.comunesService.obtenerRutaTrabajo("VALI") + config.getXsltCpePath().getPercepcionXsd(); 
      if ("09".equals(tipoComprobante))
        schemaValidador = this.comunesService.obtenerRutaTrabajo("VALI") + config.getXsltCpePath().getGuiaXsd(); 
      log.debug("validarSchemaXML...Asignando schemaValidador (" + schemaValidador + ")");
      log.debug("validarSchemaXML...Aplicando builderFactory");
      DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
      builderFactory.setNamespaceAware(true);
      log.debug("validarSchemaXML...Parseando DocumentBuilder");
      DocumentBuilder parser = builderFactory.newDocumentBuilder();
      File file = new File(nombreArchivo);
      InputStream inputStream = new FileInputStream(file);
      Reader reader = new InputStreamReader(inputStream, "ISO-8859-1");
      log.debug("validarSchemaXML...Configurando reader (" + reader + ")");
      Document document = parser.parse(new InputSource(reader));
      log.debug("validarSchemaXML...Configurando ResourceResolver document (" + document + ")");
      SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
      factory.setResourceResolver((LSResourceResolver)new ResourceResolver());
      log.debug("validarSchemaXML...Configurando SchemaFile");
      StreamSource schemaFile = new StreamSource(new File(schemaValidador));
      Schema schema = factory.newSchema(schemaFile);
      Validator validator = schema.newValidator();
      validator.validate(new DOMSource(document));
      log.debug("validarSchemaXML...Configurando Validator");
    } catch (ParserConfigurationException e) {
      throw new RuntimeException("No se puede leer (parsear) el archivo XML: " + e
          .getMessage() + " - Causa: " + e.getCause(), e);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("No se puede leer (parsear) el archivo XML: " + e
          .getMessage() + " - Causa: " + e.getCause(), e);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("No se puede leer (parsear) el archivo XML: " + e
          .getMessage() + " - Causa: " + e.getCause(), e);
    } catch (SAXException e) {
      if (e.getMessage().contains("cvc-complex-type"))
        throw new XsdException("No se puede leer (parsear) el archivo XML: " + e
            .getMessage()); 
      throw new RuntimeException("No se puede leer (parsear) el archivo XML: " + e
          .getMessage() + " - Causa: " + e.getCause(), e);
    } catch (IOException e) {
      throw new RuntimeException("No se puede leer (parsear) el archivo XML: " + e
          .getMessage() + " - Causa: " + e.getCause(), e);
    } 
    log.debug("Finalizando Validacion de Schema");
  }
}
