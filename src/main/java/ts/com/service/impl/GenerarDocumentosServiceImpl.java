package ts.com.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.annotation.ManagedBean;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import document.signer.xml.DOMUtils;
import facturador.model.Contribuyente;
import facturador.model.Direccion;
import facturador.parser.JsonParserFactory;
import facturador.parser.Parser;
import facturador.parser.PipeParserFactory;
import pe.gob.sunat.facturaelectronica.model.UsuarioSol;
import pe.gob.sunat.facturaelectronica.service.wrapper.Response;
import pe.gob.sunat.facturaelectronica.service.wrapper.SunatGEMServiceWrapper;
import pe.gob.sunat.facturaelectronica.ws.client.WebServiceConsultaWrapper;
import sistema.facturador.ConfigurationHolder;
import sistema.facturador.persistence.Documento;
import sistema.facturador.service.ComunesService;
import sistema.facturador.service.GenerarDocumentosService;
import sistema.facturador.soap.gencdp.ExceptionDetail;
import sistema.facturador.soap.gencdp.TransferirArchivoException;
import sistema.facturador.util.Constantes;
import ts.com.server.Server;
import ts.com.service.factory.Services;
import ts.com.service.model.efact.Error;
import ts.com.service.model.efact.Parametro;

@ManagedBean
public class GenerarDocumentosServiceImpl implements GenerarDocumentosService {

    private static final Logger log = LoggerFactory.getLogger(GenerarDocumentosServiceImpl.class);

    @Inject
    private ComunesService comunesService;

    ConfigurationHolder config = ConfigurationHolder.getInstance();

    public GenerarDocumentosServiceImpl() {
        this.comunesService = new ComunesServiceImpl();
    }

    public void formatoPlantillaXml(Documento documento) throws TransferirArchivoException {

        String tipoComprobante = documento.getTip_docu();
        String nombreArchivo = documento.getNom_arch();
        String[] archivos = new String[13];
        StringBuilder archivoCabecera = new StringBuilder(), archivoDetalle = new StringBuilder();
        StringBuilder archivoAdiTributos = new StringBuilder();
        StringBuilder archivoRelacionado = new StringBuilder(), archivoAdiCabecera = new StringBuilder();
        StringBuilder archivoAdiDetalle = new StringBuilder(), archivoLeyenda = new StringBuilder();
        StringBuilder archivoVariableGlobal = new StringBuilder(), archivoDetalleTributos = new StringBuilder();
        StringBuilder archivoServTranspCarga = new StringBuilder(), archivoTramos = new StringBuilder(), archivoVehiculos = new StringBuilder();
        StringBuilder archivoPago = new StringBuilder(), archivoCuotas = new StringBuilder();
        if ("RA".equals(tipoComprobante)) {
            log.debug("BandejaDocumentosServiceImpl.generarComprobantePagoSunat...Formato de Bajas.");
            archivoCabecera.setLength(0);
            archivoCabecera.append(this.comunesService.obtenerRutaTrabajo("DATA")).append(documento.getNom_arch())
                    .append(".CBA");
            archivos[0] = archivoCabecera.toString();
        }
        if ("RC".equals(tipoComprobante)) {
            log.debug("BandejaDocumentosServiceImpl.generarComprobantePagoSunat...Formato de Resumen.");
            archivoCabecera.setLength(0);
            archivoCabecera.append(this.comunesService.obtenerRutaTrabajo("DATA")).append(documento.getNom_arch())
                    .append(".RDI");
            archivoDetalleTributos.setLength(0);
            archivoDetalleTributos.append(this.comunesService.obtenerRutaTrabajo("DATA")).append(documento.getNom_arch())
                    .append(".TRD");
            archivos[0] = archivoCabecera.toString();
            archivos[1] = archivoDetalleTributos.toString();
        }
        if ("RR".equals(tipoComprobante)) {
            log.debug("BandejaDocumentosServiceImpl.generarComprobantePagoSunat...Formato de Reversion.");
            archivoCabecera.setLength(0);
            archivoCabecera.append(this.comunesService.obtenerRutaTrabajo("DATA")).append(documento.getNom_arch())
                    .append(".RDR");
            archivos[0] = archivoCabecera.toString();
        }
        if ("01".equals(tipoComprobante) || "03"
                .equals(tipoComprobante)) {
            log.debug("BandejaDocumentosServiceImpl.generarComprobantePagoSunat...Formato de Facturas.");
            archivoCabecera.setLength(0);
            archivoCabecera.append(this.comunesService.obtenerRutaTrabajo("DATA")).append(documento.getNom_arch())
                    .append(".CAB");
            archivoDetalle.setLength(0);
            archivoDetalle.append(this.comunesService.obtenerRutaTrabajo("DATA")).append(documento.getNom_arch())
                    .append(".DET");
            archivoRelacionado.setLength(0);
            archivoRelacionado.append(this.comunesService.obtenerRutaTrabajo("DATA")).append(documento.getNom_arch())
                    .append(".REL");
            archivoAdiCabecera.setLength(0);
            archivoAdiCabecera.append(this.comunesService.obtenerRutaTrabajo("DATA")).append(documento.getNom_arch())
                    .append(".ACA");
            archivoAdiTributos.setLength(0);
            archivoAdiTributos.append(this.comunesService.obtenerRutaTrabajo("DATA")).append(documento.getNom_arch())
                    .append(".TRI");
            archivoAdiDetalle.setLength(0);
            archivoAdiDetalle.append(this.comunesService.obtenerRutaTrabajo("DATA")).append(documento.getNom_arch())
                    .append(".ADE");
            archivoLeyenda.setLength(0);
            archivoLeyenda.append(this.comunesService.obtenerRutaTrabajo("DATA")).append(documento.getNom_arch())
                    .append(".LEY");
            archivoVariableGlobal.setLength(0);
            archivoVariableGlobal.append(this.comunesService.obtenerRutaTrabajo("DATA"))
                    .append(documento.getNom_arch()).append(".ACV");
            archivoServTranspCarga.setLength(0);
            archivoServTranspCarga.append(this.comunesService.obtenerRutaTrabajo("DATA"))
                    .append(documento.getNom_arch()).append(".STC");
            archivoTramos.setLength(0);
            archivoTramos.append(this.comunesService.obtenerRutaTrabajo("DATA"))
                    .append(documento.getNom_arch()).append(".TRA");
            archivoVehiculos.setLength(0);
            archivoVehiculos.append(this.comunesService.obtenerRutaTrabajo("DATA"))
                    .append(documento.getNom_arch()).append(".VEH");
            archivoPago.setLength(0);
            archivoPago.append(this.comunesService.obtenerRutaTrabajo("DATA"))
                    .append(documento.getNom_arch()).append(".PAG");
            archivoCuotas.setLength(0);
            archivoCuotas.append(this.comunesService.obtenerRutaTrabajo("DATA"))
                    .append(documento.getNom_arch()).append(".DPA");
            archivos[0] = archivoCabecera.toString();
            archivos[1] = archivoDetalle.toString();
            archivos[2] = archivoRelacionado.toString();
            archivos[3] = archivoAdiCabecera.toString();
            archivos[4] = archivoAdiDetalle.toString();
            archivos[5] = archivoLeyenda.toString();
            archivos[6] = archivoAdiTributos.toString();
            archivos[7] = archivoVariableGlobal.toString();
            archivos[8] = archivoServTranspCarga.toString();
            archivos[9] = archivoTramos.toString();
            archivos[10] = archivoVehiculos.toString();
            archivos[11] = archivoPago.toString();
            archivos[12] = archivoCuotas.toString();
            for (int i = 0; i < archivos.length; i++) {
                log.debug("Archivo " + i + ": " + archivos[i]);
            }
        }
        if ("07".equals(tipoComprobante) || "08"
                .equals(tipoComprobante)) {
            archivoCabecera.setLength(0);
            archivoCabecera.append(this.comunesService.obtenerRutaTrabajo("DATA")).append(documento.getNom_arch())
                    .append(".NOT");
            archivoDetalle.setLength(0);
            archivoDetalle.append(this.comunesService.obtenerRutaTrabajo("DATA")).append(documento.getNom_arch())
                    .append(".DET");
            archivoRelacionado.setLength(0);
            archivoRelacionado.append(this.comunesService.obtenerRutaTrabajo("DATA")).append(documento.getNom_arch())
                    .append(".REL");
            archivoAdiCabecera.setLength(0);
            archivoAdiCabecera.append(this.comunesService.obtenerRutaTrabajo("DATA")).append(documento.getNom_arch())
                    .append(".ACA");
            archivoAdiTributos.setLength(0);
            archivoAdiTributos.append(this.comunesService.obtenerRutaTrabajo("DATA")).append(documento.getNom_arch())
                    .append(".TRI");
            archivoAdiDetalle.setLength(0);
            archivoAdiDetalle.append(this.comunesService.obtenerRutaTrabajo("DATA")).append(documento.getNom_arch())
                    .append(".ADE");
            archivoLeyenda.setLength(0);
            archivoLeyenda.append(this.comunesService.obtenerRutaTrabajo("DATA")).append(documento.getNom_arch())
                    .append(".LEY");
            archivoVariableGlobal.setLength(0);
            archivoVariableGlobal.append(this.comunesService.obtenerRutaTrabajo("DATA"))
                    .append(documento.getNom_arch()).append(".ACV");
            archivoServTranspCarga.setLength(0);
            archivoServTranspCarga.append(this.comunesService.obtenerRutaTrabajo("DATA"))
                    .append(documento.getNom_arch()).append(".STC");
            archivoTramos.setLength(0);
            archivoTramos.append(this.comunesService.obtenerRutaTrabajo("DATA"))
                    .append(documento.getNom_arch()).append(".TRA");
            archivoVehiculos.setLength(0);
            archivoVehiculos.append(this.comunesService.obtenerRutaTrabajo("DATA"))
                    .append(documento.getNom_arch()).append(".VEH");
            archivoPago.setLength(0);
            archivoPago.append(this.comunesService.obtenerRutaTrabajo("DATA"))
                    .append(documento.getNom_arch()).append(".PAG");
            archivoCuotas.setLength(0);
            archivoCuotas.append(this.comunesService.obtenerRutaTrabajo("DATA"))
                    .append(documento.getNom_arch()).append(".DPA");
            archivos[0] = archivoCabecera.toString();
            archivos[1] = archivoDetalle.toString();
            archivos[2] = archivoRelacionado.toString();
            archivos[3] = archivoAdiCabecera.toString();
            archivos[4] = archivoAdiDetalle.toString();
            archivos[5] = archivoLeyenda.toString();
            archivos[6] = archivoAdiTributos.toString();
            archivos[7] = archivoVariableGlobal.toString();
            archivos[8] = archivoServTranspCarga.toString();
            archivos[9] = archivoTramos.toString();
            archivos[10] = archivoVehiculos.toString();
            archivos[11] = archivoPago.toString();
            archivos[12] = archivoCuotas.toString();
            for (int i = 0; i < archivos.length; i++) {
                log.debug("Archivo " + i + ": " + archivos[i]);
            }
        }
        if ("20".equals(tipoComprobante)) {
            log.debug("BandejaDocumentosServiceImpl.generarComprobantePagoSunat...Formato de Retenciones.");
            archivoCabecera.setLength(0);
            archivoCabecera.append(this.comunesService.obtenerRutaTrabajo("DATA")).append(documento.getNom_arch())
                    .append(".RET");
            archivoDetalle.setLength(0);
            archivoDetalle.append(this.comunesService.obtenerRutaTrabajo("DATA")).append(documento.getNom_arch())
                    .append(".DRE");
            archivos[0] = archivoCabecera.toString();
            archivos[1] = archivoDetalle.toString();
        }
        if ("40".equals(tipoComprobante)) {
            log.debug("BandejaDocumentosServiceImpl.generarComprobantePagoSunat...Formato de Percepciones.");
            archivoCabecera.setLength(0);
            archivoCabecera.append(this.comunesService.obtenerRutaTrabajo("DATA")).append(documento.getNom_arch())
                    .append(".PER");
            archivoDetalle.setLength(0);
            archivoDetalle.append(this.comunesService.obtenerRutaTrabajo("DATA")).append(documento.getNom_arch())
                    .append(".DPE");
            archivos[0] = archivoCabecera.toString();
            archivos[1] = archivoDetalle.toString();
        }
        log.debug("GenerarDocumentosServiceImpl.formatoPlantillaXml...Inicio Procesamiento");
        log.debug("GenerarDocumentosServiceImpl.formatoPlantillaXml...tipoDocumento: " + tipoComprobante);
        try {
            PipeParserFactory parserFactory = new PipeParserFactory();
            Parser xmlParser = parserFactory.createParser(getContri(), tipoComprobante, archivos, nombreArchivo);
            StringBuilder rutaSalida = new StringBuilder();
            rutaSalida.setLength(0);
            rutaSalida.append(this.comunesService.obtenerRutaTrabajo("TEMP")).append(nombreArchivo).append(".xml");
            String templatesPath = this.comunesService.obtenerRutaTrabajo("VALI");
            byte[] myByteArray = xmlParser.parse(templatesPath);
            try (FileOutputStream fos = new FileOutputStream(rutaSalida.toString())) {
                fos.write(myByteArray);
            }
            log.debug("SoftwareFacturadorController.formatoPlantillaXml...Final Procesamiento");
        } catch (Exception e) {
            log.info("formatoPlantillaXml: " + e.getMessage());
            ExceptionDetail exceptionDetail = new ExceptionDetail();
            exceptionDetail.setMessage(e.getMessage());
            throw new TransferirArchivoException("Error en archivo TxT: " + e.getMessage(), exceptionDetail);
        }
    }

    private Contribuyente getContri() {
        String nombreComercial = "", ubigeo = "", direccion = "", param = "", numRuc = "", razonSocial = "", depar = "";
        String provin = "", distr = "", urbaniza = "";
        String id_para = "PARASIST";
        List<Parametro> listaParametros = Services.getParametro().consultarParametro(id_para);
        if (listaParametros.size() > 0) {
            for (Parametro parametro : listaParametros) {
                param = parametro.val_para;
                if ("NUMRUC".equals(parametro.cod_para)) {
                    numRuc = param;
                }
                if ("RAZON".equals(parametro.cod_para)) {
                    razonSocial = param;
                }
                if ("NOMCOM".equals(parametro.cod_para)) {
                    nombreComercial = param;
                }
                if ("UBIGEO".equals(parametro.cod_para)) {
                    ubigeo = param;
                }
                if ("DIRECC".equals(parametro.cod_para)) {
                    direccion = param;
                }
                if ("DEPAR".equals(parametro.cod_para)) {
                    depar = param;
                }
                if ("PROVIN".equals(parametro.cod_para)) {
                    provin = param;
                }
                if ("DISTR".equals(parametro.cod_para)) {
                    distr = param;
                }
                if ("URBANIZA".equals(parametro.cod_para)) {
                    urbaniza = param;
                }
            }
        }
        Direccion direccion1 = new Direccion(direccion, urbaniza, ubigeo, depar, provin, distr);
        Contribuyente contri = new Contribuyente(numRuc, nombreComercial, razonSocial, direccion1);
        return contri;
    }

    public String firmarComprimirXml(String nombreArchivo) {
        String rutaNombreEntrada = this.comunesService.obtenerRutaTrabajo("TEMP") + nombreArchivo + ".xml";
        String rutaNombreSalida = this.comunesService.obtenerRutaTrabajo("PARS") + nombreArchivo + ".xml";
        try {
            FileInputStream inDocument = new FileInputStream(rutaNombreEntrada);
            FileOutputStream fout = new FileOutputStream(rutaNombreSalida);
            Map<String, Object> firma = firmarDocumento(inDocument);
            ByteArrayOutputStream outDocument = (ByteArrayOutputStream) firma.get("signatureFile");
            String digestValue = (String) firma.get("digestValue");
            outDocument.writeTo(fout);
            fout.close();
            return digestValue;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error al firma archivo XML", e);
        } catch (IOException e) {
            throw new RuntimeException("Error al firma archivo XML", e);
        } catch (Exception e) {
            throw new RuntimeException("Error al firma archivo XML", e);
        }
    }

    public String Desencriptar(String textoEncriptado) {
        String secretKey = "qualityinfosolutions";
        String base64EncryptedString = "";
        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(2, key);
            byte[] plainText = decipher.doFinal(message);
            base64EncryptedString = new String(plainText, "UTF-8");
            return base64EncryptedString;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error al des encriptar", e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al des encriptar", e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException("Error al des encriptar", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Error al des encriptar", e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException("Error al des encriptar", e);
        } catch (BadPaddingException e) {
            throw new RuntimeException("Error al des encriptar", e);
        }
    }

    public String Encriptar(String texto) {
        try {
            String secretKey = "qualityinfosolutions";
            String base64EncryptedString = "";
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(1, key);
            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);
            return base64EncryptedString;
        } catch (Exception e) {
            throw new RuntimeException("Error al des encriptar", e);
        }
    }

    public Map<String, String> obtenerEstadoTicket(String rutaArchivo, String wsUrl, String nroTicket) {
        Parametro paramtroBean = new Parametro();
        String param = "", numeroRUC = "", usuarioSOL = "", password = "";
        String id_para = "PARASIST";
        List<Parametro> listaParametros = Services.getParametro().consultarParametro(id_para);
        if (listaParametros.size() > 0) {
            for (Parametro parametro : listaParametros) {
                param = parametro.val_para;
                if ("NUMRUC".equals(parametro.cod_para)) {
                    numeroRUC = param;
                }
                if ("USUSOL".equals(parametro.cod_para)) {
                    usuarioSOL = param;
                }
                if ("CLASOL".equals(parametro.cod_para)) {
                    password = Desencriptar(param);
                }
            }
        }
        UsuarioSol usuarioSol = new UsuarioSol(numeroRUC, usuarioSOL, password);
        SunatGEMServiceWrapper client = new SunatGEMServiceWrapper(usuarioSol, wsUrl);
        Response respon = null;
        respon = client.getStatus(rutaArchivo, nroTicket);
        Map<String, String> resultado = new HashMap<>();
        if (!respon.isError()) {
            Integer codError = Integer.valueOf(respon.getCodigo());
            log.debug("obtenerEstadoTicket Codigo de Error : " + codError);
            String msgError = respon.getMensaje();
            Integer pos = Integer.valueOf(msgError.indexOf("Detalle:"));
            if (pos.intValue() > 0) {
                msgError = msgError.substring(0, pos.intValue() - 1);
            }
            log.debug("obtenerEstadoTicket mensaje : " + msgError);
            if (codError.intValue() == 0) {
                resultado.put("situacion", "03");
                resultado.put("mensaje", "-");
            } else {
                resultado.put("situacion", "05");
                resultado.put("mensaje", msgError);
            }
        } else {
            Integer codError = Integer.valueOf(respon.getCodigo());
            log.debug("obtenerEstadoTicket Codigo de Error : " + codError);
            String msgError = respon.getMensaje();
            Integer pos = Integer.valueOf(msgError.indexOf("Detalle:"));
            if (pos.intValue() > 0) {
                msgError = msgError.substring(0, pos.intValue() - 1);
            }
            log.debug("obtenerEstadoTicket mensaje : " + msgError);
            if (codError.intValue() == 98) {
                resultado.put("situacion", "08");
            }
            if (codError.intValue() == 99) {
                resultado.put("situacion", "05");
            }
            resultado.put("mensaje", msgError);
        }
        return resultado;
    }

    public HashMap<String, String> enviarArchivoSunat(String wsUrl, String filename, String tipoComprobante) {

        HashMap<String, String> resultado = new HashMap<>();
        String param = "", numeroRUC = "", usuarioSOL = "", password = "", mensaje = "";
        log.info("url del servicio : " + wsUrl);
        synchronized (this) {
            String id_para = "PARASIST";
            List<Parametro> listaParametros = Services.getParametro().consultarParametro(id_para);
            if (listaParametros.size() > 0) {
                for (Parametro parametro : listaParametros) {
                    param = parametro.val_para;
                    if ("NUMRUC".equals(parametro.cod_para)) {
                        numeroRUC = param;
                    }
                    if ("USUSOL".equals(parametro.cod_para)) {
                        usuarioSOL = param;
                    }
                    if ("CLASOL".equals(parametro.cod_para)) {
                        password = Desencriptar(param);
                    }
                }
            }
            String zipFile = filename + ".zip";
            log.debug("Enviando archivo a SUNAT, filename:  " + filename);
            log.debug("Enviando archivo a SUNAT, zipFile:  " + zipFile);
            log.debug("Enviando archivo a SUNAT, numeroRUC:  " + numeroRUC);
            log.debug("Enviando archivo a SUNAT, usuarioSOL:  " + usuarioSOL);
            log.debug("Enviando archivo a SUNAT, password:  " + password);
            UsuarioSol usuarioSol = new UsuarioSol(numeroRUC, usuarioSOL, password);
            SunatGEMServiceWrapper client = new SunatGEMServiceWrapper(usuarioSol, wsUrl);
            Response respon = null;
            log.debug("Enviando archivo a SUNAT, tipoComprobante:  " + tipoComprobante);
            if ("RA".equals(tipoComprobante) || "RC"
                    .equals(tipoComprobante) || "RR"
                    .equals(tipoComprobante)) {
                respon = client.sendSummary(zipFile, this.comunesService.obtenerRutaTrabajo("ENVI") + zipFile);
            } else {
                respon = client.sendBill(this.comunesService.obtenerRutaTrabajo("RPTA"), zipFile, this.comunesService
                        .obtenerRutaTrabajo("ENVI") + zipFile);
            }
            if (!respon.isError()) {
                Integer errorCode = Integer.valueOf(respon.getCodigo());
                log.debug("isError es Falso, Codigo de Error : " + errorCode);
                String msgError = respon.getMensaje();
                log.debug("isError es Falso, Mensaje Error Obtenido: " + respon.getMensaje());
                List<String> listaWarnings = (respon.getWarnings() != null) ? respon.getWarnings() : new ArrayList<>();
                log.debug("isError es Falso, Cantidad de Warnings: " + listaWarnings.size());
                if (!"RA".equals(tipoComprobante)
                        && !"RC".equals(tipoComprobante)
                        && !"RR".equals(tipoComprobante)) {
                    if (errorCode.intValue() == 0 && listaWarnings.size() == 0) {
                        resultado.put("situacion", "03");
                    }
                    if (errorCode.intValue() == 0 && listaWarnings.size() > 0) {
                        resultado.put("situacion", "04");
                    }
                    if (errorCode.intValue() > 0) {
                        Error txxxzBean = Services.getError().consultarErrorById(errorCode);
                        if (txxxzBean != null) {
                            mensaje = txxxzBean.cod_cataerro + " - " + txxxzBean.nom_cataerro;
                        } else {
                            mensaje = msgError;
                        }
                        resultado.put("situacion", "10");
                        resultado.put("mensaje", mensaje);
                    }
                    if (errorCode.intValue() < 0) {
                        mensaje = "Error al invocar el servicio de SUNAT.";
                        resultado.put("situacion", "06");
                        resultado.put("mensaje", mensaje);
                    }
                } else {
                    mensaje = "Nro. Ticket: " + msgError;
                    resultado.put("situacion", "08");
                    resultado.put("mensaje", mensaje);
                }
            } else {
                Integer errorCode = Integer.valueOf(respon.getCodigo());
                log.debug("isError es True, Codigo de Error : " + errorCode);
                String msgError = respon.getMensaje();
                log.debug("isError es True, Mensaje Error Obtenido: " + msgError);
                log.debug("isError es True, tipoComprobante: " + tipoComprobante);
                if (Constantes.CONSTANTE_CODIGO_ENVIO_PREVIO.compareTo(errorCode) == 0
                        && !"RA".equals(tipoComprobante)
                        && !"RC".equals(tipoComprobante)
                        && !"RR".equals(tipoComprobante)) {
                    String nombreArchivoProp = "constantes.properties";
                    Properties prop = this.comunesService.getProperties(nombreArchivoProp);
                    String urlWebServiceCdr = (prop.getProperty("RUTA_SERV_CDR") != null) ? prop.getProperty("RUTA_SERV_CDR") : "XX";
                    WebServiceConsultaWrapper consultaCdr = new WebServiceConsultaWrapper(urlWebServiceCdr);
                    String usuario = numeroRUC + usuarioSOL;
                    log.debug("isError es True, usuario: " + usuario);
                    String nombreArchivo = this.comunesService.obtenerRutaTrabajo("RPTA") + "R" + filename + ".zip";
                    String[] datoArchivo = filename.split("\\-");
                    String rucCdp = datoArchivo[0];
                    log.debug("isError es True, rucCdp: " + rucCdp);
                    String tipoCdp = datoArchivo[1];
                    log.debug("isError es True, tipoCdp: " + tipoCdp);
                    String serieCdp = datoArchivo[2];
                    log.debug("isError es True, serieCdp: " + serieCdp);
                    String nroCdp = datoArchivo[3];
                    log.debug("isError es True, nroCdp: " + nroCdp);
                    log.debug("isError es True, Inicio llamada al WS");
                    Integer estado = consultaCdr.obtenerEstadoCdr(usuario, password, nombreArchivo, rucCdp, tipoCdp, serieCdp, new Integer(nroCdp), "0004");
                    log.debug("isError es True, Fin llamada al WS: " + estado);
                    if (estado.intValue() == 0) {
                        mensaje = "Este Comprobante ya fue enviado anteriormente, sse descargel CDR nuevamente.";
                        resultado.put("situacion", "11");
                        resultado.put("mensaje", mensaje);
                    } else if (estado.intValue() == 1) {
                        mensaje = "Este Comprobante ya fue enviado anteriormente, sse descargel CDR nuevamente.";
                        resultado.put("situacion", "12");
                        resultado.put("mensaje", mensaje);
                    } else if (estado.intValue() == -1) {
                        mensaje = "Error invocando al webservice para obtener CDR.";
                        resultado.put("situacion", "06");
                        resultado.put("mensaje", mensaje);
                    } else {
                        mensaje = "El webservice para obtener CDR, no se encontrel archivo CDR en SUNAT.";
                        resultado.put("situacion", "06");
                        resultado.put("mensaje", mensaje);
                    }
                } else {
                    Error txxxzBean = Services.getError().consultarErrorById(errorCode);
                    if (txxxzBean != null) {
                        mensaje = txxxzBean.cod_cataerro + " - " + txxxzBean.nom_cataerro;
                    } else {
                        mensaje = msgError;
                    }
                    resultado.put("situacion", "05");
                    resultado.put("mensaje", mensaje);
                }
            }
        }
        return resultado;
    }

    public void formatoJsonPlantilla(Documento documento) throws TransferirArchivoException {
        try {
            String archivoJson = this.comunesService.obtenerRutaTrabajo("DATA") + documento.getNom_arch() + ".JSON";
            String fileJson = leerArchivoJson(archivoJson);
            HashMap<String, Object> objectoJson = (HashMap<String, Object>) (new ObjectMapper()).readValue(fileJson, HashMap.class);
            String nombreArchivo = documento.getNom_arch();
            String[] idArchivo = nombreArchivo.split("\\-");
            JsonParserFactory parserFactory = new JsonParserFactory();
            Parser jsonParser = parserFactory.createParser(getContri(), idArchivo[1], objectoJson, nombreArchivo);
            StringBuilder rutaSalida = new StringBuilder();
            rutaSalida.setLength(0);
            rutaSalida.append(this.comunesService.obtenerRutaTrabajo("TEMP")).append(nombreArchivo).append(".xml");
            String templatesPath = this.comunesService.obtenerRutaTrabajo("VALI");
            byte[] myByteArray = jsonParser.parse(templatesPath);
            try (FileOutputStream fos = new FileOutputStream(rutaSalida.toString())) {
                fos.write(myByteArray);
            }
            log.debug("SoftwareFacturadorController.formatoPlantillaJson...Final Procesamiento");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al procesar formatoPlantillaXml: " + e.getMessage(), e);
        }
    }

    private String leerArchivoJson(String nombreArchivo) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
        StringBuilder sb = new StringBuilder();
        String linea = br.readLine();
        while (linea != null) {
            sb.append(linea);
            linea = br.readLine();
        }
        br.close();
        return sb.toString();
    }

    public String obtenerArchivoXml(String nombreArchivo) {
        try {
            log.debug("GenerarDocumentosServiceImpl.obtenerArchivoXml...Inicio Procesamiento");
            String contenido = "", linea = "";
            String archivoXml = nombreArchivo + ".xml";
            String archivoZip = "R" + nombreArchivo + ".zip";
            log.debug("GenerarDocumentosServiceImpl.obtenerArchivoXml...Ruta: " + this.comunesService
                    .obtenerRutaTrabajo("RPTA"));
            log.debug("GenerarDocumentosServiceImpl.obtenerArchivoXml...Archivo: " + archivoXml);
            File archivoRespuesta = new File(this.comunesService.obtenerRutaTrabajo("RPTA"), archivoZip);
            if (archivoRespuesta.exists()) {
                File archivoContenido = new File(this.comunesService.obtenerRutaTrabajo("ENVI"), archivoXml);
                if (archivoContenido.exists()) {
                    FileReader fileReader = new FileReader(archivoContenido);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    StringBuffer cadenaLinea = new StringBuffer();
                    cadenaLinea.setLength(0);
                    while ((linea = bufferedReader.readLine()) != null) {
                        cadenaLinea.append(linea);
                        cadenaLinea.append("\n");
                    }
                    fileReader.close();
                    contenido = cadenaLinea.toString();
                }
            }
            log.debug("GenerarDocumentosServiceImpl.obtenerArchivoXml...Final Procesamiento");
            return contenido;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener archivo xml", e);
        }
    }

    public void adicionarInformacionFacturador(String nombreArchivoXml) {
        try {
            String id_para = "PARASIST";
            String cod_para = "NUMRUC";
            List<Parametro> listaParametros = Services.getParametro().consultarParametroById(id_para, cod_para);
            String documentoOrigen = this.comunesService.obtenerRutaTrabajo("DATA") + nombreArchivoXml + ".xml";
            String documentoSalida = this.comunesService.obtenerRutaTrabajo("TEMP") + nombreArchivoXml + ".xml";
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setIgnoringComments(true);
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document document = builder.parse(new File(documentoOrigen));
            log.debug("Buscando Nodo");
            NodeList nodes = document.getElementsByTagName("cac:SignatoryParty");
            log.debug("Configurando cbc:ID");
            Text ruc = document.createTextNode(((Parametro) listaParametros.get(0)).val_para);
            Element elementRuc = document.createElement("cbc:ID");
            elementRuc.appendChild(ruc);
            nodes.item(0).getParentNode().insertBefore(elementRuc, nodes.item(0));
            log.debug("Configurando cbc:Note");
            Text texto = document.createTextNode("Elaborado por Sistema de Emision Electronica Facturador SUNAT (SEE-SFS) 1.4");
            Element elementTexto = document.createElement("cbc:Note");
            elementTexto.appendChild(texto);
            nodes.item(0).getParentNode().insertBefore(elementTexto, nodes.item(0));
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            File outputFile = new File(documentoSalida);
            StreamResult result = new StreamResult(outputFile);
            transformer.setOutputProperty("encoding", "ISO8859_1");
            transformer.transform(source, result);
        } catch (Exception e) {
            throw new RuntimeException("Error al adicionar Informacion Facturador", e);
        }
    }

    public void validarPlazo(String nombreArchivoXml) {
        try {
            String serie = nombreArchivoXml.substring(15, 16);
            if (serie.equals("B")) {
                String documentoOrigen = this.comunesService.obtenerRutaTrabajo("PARS") + nombreArchivoXml + ".xml";
                DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                domFactory.setIgnoringComments(true);
                DocumentBuilder builder = domFactory.newDocumentBuilder();
                Document document = builder.parse(new File(documentoOrigen));
                XPath xpath = XPathFactory.newInstance().newXPath();
                XPathExpression expr = xpath.compile(document.getDocumentElement().getNodeName() + "/IssueDate");
                String fecEmision = (String) expr.evaluate(document, XPathConstants.STRING);
                Integer fecEmisionInt = Integer.valueOf(Integer.parseInt(fecEmision.replaceAll("-", "")));
                log.debug("fecEmision: " + fecEmision + ", " + fecEmisionInt);
                if (fecEmisionInt.intValue() >= 20200107) {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date fecEmisionDate = null;
                    fecEmisionDate = df.parse(fecEmision);
                    Date today = new Date();
                    long diff = today.getTime() - fecEmisionDate.getTime();
                    long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                    Integer nroDias = Server.PLAZO_BOLETA;
                    if (days > nroDias.intValue()) {
                        log.debug("El XML fue generado, pero el Comprobante tiene mas de " + nroDias + " dEmisi" + fecEmision + ". Use el resumen diario para generar y enviar.");
                        throw new Exception("El XML fue generado, pero el Comprobante tiene mas de " + nroDias + " dEmisi" + fecEmision + ". Use el resumen diario para generar y enviar.");
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Map<String, Object> firmarDocumento(InputStream inDocument) {
        try {
            log.debug("GenerarDocumentosServiceImpl.firmarDocumento...Inicio de Firma");
            Map<String, Object> retorno = new HashMap<>();
            String param = "", clavePrivateKey = "";
            String id_para = "PARASIST";
            List<Parametro> listaParametros = null;
            listaParametros = Services.getParametro().consultarParametro(id_para);
            if (listaParametros.size() > 0) {
                for (Parametro parametro : listaParametros) {
                    param = parametro.val_para;
                    if ("PRKCRT".equals(parametro.cod_para)) {
                        clavePrivateKey = Desencriptar(param);
                    }
                }
            }
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(this.comunesService.obtenerRutaTrabajo("ALMC") + "FacturadorKey.jks"), "SuN@TF4CT"
                    .toCharArray());
            PrivateKey privateKey = (PrivateKey) ks.getKey("certContribuyente", clavePrivateKey.toCharArray());
            X509Certificate cert = (X509Certificate) ks.getCertificate("certContribuyente");
            ByteArrayOutputStream signatureFile = new ByteArrayOutputStream();
            log.debug("GenerarDocumentosServiceImpl.firmarDocumento...Crear Document");
            Document doc = buildDocument(inDocument);
            log.debug("GenerarDocumentosServiceImpl.firmarDocumento...Agregar Extension");
            Node parentNode = addExtensionContent(doc);
            log.debug("GenerarDocumentosServiceImpl.firmarDocumento...Colocando ID de Firma");
            String idReference = "SignSUNAT";
            XMLSignatureFactory fac = XMLSignatureFactory.getInstance();
            log.debug("GenerarDocumentosServiceImpl.firmarDocumento...Reference");
            Reference ref = fac.newReference("", fac.newDigestMethod("http://www.w3.org/2000/09/xmldsig#sha1", null),
                    Collections.singletonList(fac.newTransform("http://www.w3.org/2000/09/xmldsig#enveloped-signature", (TransformParameterSpec) null)), null, null);
            SignedInfo si = fac.newSignedInfo(fac
                    .newCanonicalizationMethod("http://www.w3.org/TR/2001/REC-xml-c14n-20010315", (C14NMethodParameterSpec) null), fac
                    .newSignatureMethod("http://www.w3.org/2000/09/xmldsig#rsa-sha1", null), Collections.singletonList(ref));
            KeyInfoFactory kif = fac.getKeyInfoFactory();
            List<X509Certificate> x509Content = new ArrayList<>();
            x509Content.add(cert);
            X509Data xd = kif.newX509Data(x509Content);
            KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));
            DOMSignContext dsc = new DOMSignContext(privateKey, doc.getDocumentElement());
            XMLSignature signature = fac.newXMLSignature(si, ki);
            if (parentNode != null) {
                dsc.setParent(parentNode);
            }
            dsc.setDefaultNamespacePrefix("ds");
            signature.sign(dsc);
            String digestValue = "-";
            Element elementParent = (Element) dsc.getParent();
            if (idReference != null && elementParent.getElementsByTagName("ds:Signature") != null) {
                Element elementSignature = (Element) elementParent.getElementsByTagName("ds:Signature").item(0);
                elementSignature.setAttribute("Id", idReference);
                NodeList nodeList = elementParent.getElementsByTagName("ds:DigestValue");
                for (int i = 0; i < nodeList.getLength(); i++) {
                    digestValue = obtenerNodo(nodeList.item(i));
                }
            }
            DOMUtils.outputDocToOutputStream(doc, signatureFile);
            signatureFile.close();
            retorno.put("signatureFile", signatureFile);
            retorno.put("digestValue", digestValue);
            log.debug("GenerarDocumentosServiceImpl.firmarDocumento...Final de Firma");
            return retorno;
        } catch (Exception e) {
            throw new RuntimeException("Error al firmar documento: ", e);
        }
    }

    private String obtenerNodo(Node node) throws Exception {
        StringBuffer valorClave = new StringBuffer();
        valorClave.setLength(0);
        Integer tamano = Integer.valueOf(node.getChildNodes().getLength());
        for (int i = 0; i < tamano.intValue(); i++) {
            Node c = node.getChildNodes().item(i);
            if (c.getNodeType() == 3) {
                valorClave.append(c.getNodeValue());
            }
        }
        String nodo = valorClave.toString().trim();
        return nodo;
    }

    private Document buildDocument(InputStream inDocument) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setAttribute("http://xml.org/sax/features/namespaces", Boolean.TRUE);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Reader reader = new InputStreamReader(inDocument, "ISO8859_1");
        Document doc = db.parse(new InputSource(reader));
        return doc;
    }

    private Node addExtensionContent(Document doc) {
        NodeList nodeList = doc.getDocumentElement().getElementsByTagNameNS("urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2", "UBLExtensions");
        Node extensions = nodeList.item(0);
        extensions.appendChild(doc.createTextNode("\n\t\t"));
        Node extension = doc.createElement("ext:UBLExtension");
        extension.appendChild(doc.createTextNode("\n\t\t\t"));
        Node content = doc.createElement("ext:ExtensionContent");
        extension.appendChild(content);
        extension.appendChild(doc.createTextNode("\n\t\t"));
        extensions.appendChild(extension);
        extensions.appendChild(doc.createTextNode("\n\t"));
        return content;
    }
}
