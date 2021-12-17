package ts.com.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sistema.facturador.dao.DocumentoDao;
import sistema.facturador.dao.ErrorDao;
import sistema.facturador.dao.ParametroDao;
import sistema.facturador.exception.XsltException;
import sistema.facturador.job.GestionarProcesosBatchJob;
import sistema.facturador.persistence.Documento;
import sistema.facturador.persistence.Parametro;
import sistema.facturador.service.BandejaDocumentosService;
import sistema.facturador.service.CertificadoFacturador;
import sistema.facturador.service.ComunesService;
import sistema.facturador.service.GenerarDocumentosService;
import sistema.facturador.service.ReporteDocumentosService;
import sistema.facturador.soap.gencdp.ExceptionDetail;
import sistema.facturador.soap.gencdp.TransferirArchivoException;
import sistema.facturador.util.FacturadorUtil;
import ts.com.server.facturador.dao.validator.XsdCpeValidator;
import ts.com.server.facturador.dao.validator.XsltCpeValidator;

public class BandejaDocumentosServiceImpl implements BandejaDocumentosService {

    private static final Logger log = LoggerFactory.getLogger(BandejaDocumentosServiceImpl.class);

    @Inject
    private DocumentoDao documentoDao;

    @Inject
    private ParametroDao parametroDao;

    @Inject
    private ErrorDao errorDao;

    @Inject
    private GenerarDocumentosService generarDocumentosService;

    @Inject
    private ReporteDocumentosService reporteDocumentosService;

    @Inject
    private ComunesService comunesService;

    @Inject
    GestionarProcesosBatchJob gestionarProcesosBatchJob;

    public BandejaDocumentosServiceImpl() {
        this.generarDocumentosService = new GenerarDocumentosServiceImpl();
        this.comunesService = new ComunesServiceImpl();
    }

    public void eliminarBandeja(Documento documento) throws Exception {
        this.documentoDao.eliminarBandeja(documento);
    }

    public List<Documento> consultarBandejaComprobantesPorId(Documento documento) {
        List<Documento> listaBandeja = this.documentoDao.consultarBandejaPorId(documento);
        return listaBandeja;
    }

    public void actualizarEstadoBandejaCdp(Documento documento) {
        this.documentoDao.actualizarBandeja(documento);
    }

    public List<Documento> consultarBandejaComprobantes() throws Exception {
        Documento documento = new Documento();
        List<Documento> listaBandeja = this.documentoDao.consultarBandeja(documento);
        return listaBandeja;
    }

    public void cargarArchivosContribuyente() {
        Documento documentoBusq = null;
        File listaArchivos = new File(this.comunesService.obtenerRutaTrabajo("DATA"));
        String[] totalArchivos = listaArchivos.list();
        if (totalArchivos == null) {
            String error = "Debe crear el directorio: " + this.comunesService.obtenerRutaTrabajo("DATA");
            log.error(error);
            throw new RuntimeException(error);
        }
        for (int x = 0; x < totalArchivos.length; x++) {
            String archivo = totalArchivos[x].toUpperCase();
            if (validarNombreArchivo(archivo).booleanValue()) {
                if (archivo.contains(".CBA")) {
                    String nombreArchivo = archivo;
                    documentoBusq = new Documento();
                    documentoBusq.setNom_arch(nombreArchivo);
                    documentoBusq.setInd_situ("01");
                    documentoBusq.setTip_arch("TXT");
                    insertarRegistroBandejaDocumentos(documentoBusq);
                }
                if (archivo.contains(".RDR")) {
                    String nombreArchivo = archivo;
                    documentoBusq = new Documento();
                    documentoBusq.setNom_arch(nombreArchivo);
                    documentoBusq.setInd_situ("01");
                    documentoBusq.setTip_arch("TXT");
                    insertarRegistroBandejaDocumentos(documentoBusq);
                }
                if (archivo.contains(".RDI")) {
                    String nombreArchivo = archivo;
                    documentoBusq = new Documento();
                    documentoBusq.setNom_arch(nombreArchivo);
                    documentoBusq.setInd_situ("01");
                    documentoBusq.setTip_arch("TXT");
                    insertarRegistroBandejaDocumentos(documentoBusq);
                }
                if (archivo.contains(".CAB")) {
                    String nombreArchivo = archivo;
                    documentoBusq = new Documento();
                    documentoBusq.setNom_arch(nombreArchivo);
                    documentoBusq.setInd_situ("01");
                    documentoBusq.setTip_arch("TXT");
                    insertarRegistroBandejaDocumentos(documentoBusq);
                }
                if (archivo.contains(".RET")) {
                    String nombreArchivo = archivo;
                    documentoBusq = new Documento();
                    documentoBusq.setNom_arch(nombreArchivo);
                    documentoBusq.setInd_situ("01");
                    documentoBusq.setTip_arch("TXT");
                    insertarRegistroBandejaDocumentos(documentoBusq);
                }
                if (archivo.contains(".PER")) {
                    String nombreArchivo = archivo;
                    documentoBusq = new Documento();
                    documentoBusq.setNom_arch(nombreArchivo);
                    documentoBusq.setInd_situ("01");
                    documentoBusq.setTip_arch("TXT");
                    insertarRegistroBandejaDocumentos(documentoBusq);
                }
                if (archivo.contains(".NOT")) {
                    String nombreArchivo = archivo;
                    documentoBusq = new Documento();
                    documentoBusq.setNom_arch(nombreArchivo);
                    documentoBusq.setInd_situ("01");
                    documentoBusq.setTip_arch("TXT");
                    insertarRegistroBandejaDocumentos(documentoBusq);
                }
                if (archivo.contains(".JSON")) {
                    String nombreArchivo = archivo.replace(".JSON", "");
                    documentoBusq = new Documento();
                    documentoBusq.setNom_arch(nombreArchivo);
                    documentoBusq.setInd_situ("01");
                    documentoBusq.setTip_arch("JSON");
                    insertarRegistroBandejaDocumentos(documentoBusq);
                }
                if (archivo.contains(".XML")) {
                    String nombreArchivo = archivo.replace(".XML", "");
                    documentoBusq = new Documento();
                    documentoBusq.setNom_arch(nombreArchivo);
                    documentoBusq.setInd_situ("07");
                    documentoBusq.setTip_arch("XML");
                    insertarRegistroBandejaDocumentos(documentoBusq);
                }
            }
        }
    }

    public void cargarArchivoContribuyente() throws Exception {
        Documento documentoBusq = null;
        File listaArchivos = new File(this.comunesService.obtenerRutaTrabajo("DATA"));
        String[] totalArchivos = listaArchivos.list();
        if (totalArchivos == null) {
            String error = "Debe crear el directorio: " + this.comunesService.obtenerRutaTrabajo("DATA");
            log.error(error);
            throw new Exception(error);
        }
        Boolean inserto = Boolean.valueOf(false);
        for (int x = 0; x < totalArchivos.length; x++) {
            String archivo = totalArchivos[x].toUpperCase();
            if (validarNombreArchivo(archivo).booleanValue()) {
                if (archivo.contains(".CBA")) {
                    String nombreArchivo = archivo;
                    documentoBusq = new Documento();
                    documentoBusq.setNom_arch(nombreArchivo);
                    documentoBusq.setInd_situ("01");
                    documentoBusq.setTip_arch("TXT");
                    inserto = insertarRegistroBandejaDocumentos(documentoBusq);
                }
                if (archivo.contains(".RDI")) {
                    String nombreArchivo = archivo;
                    documentoBusq = new Documento();
                    documentoBusq.setNom_arch(nombreArchivo);
                    documentoBusq.setInd_situ("01");
                    documentoBusq.setTip_arch("TXT");
                    inserto = insertarRegistroBandejaDocumentos(documentoBusq);
                }
                if (archivo.contains(".RDR")) {
                    String nombreArchivo = archivo;
                    documentoBusq = new Documento();
                    documentoBusq.setNom_arch(nombreArchivo);
                    documentoBusq.setInd_situ("01");
                    documentoBusq.setTip_arch("TXT");
                    inserto = insertarRegistroBandejaDocumentos(documentoBusq);
                }
                if (archivo.contains(".CAB")) {
                    String nombreArchivo = archivo;
                    documentoBusq = new Documento();
                    documentoBusq.setNom_arch(nombreArchivo);
                    documentoBusq.setInd_situ("01");
                    documentoBusq.setTip_arch("TXT");
                    inserto = insertarRegistroBandejaDocumentos(documentoBusq);
                }
                if (archivo.contains(".NOT")) {
                    String nombreArchivo = archivo;
                    documentoBusq = new Documento();
                    documentoBusq.setNom_arch(nombreArchivo);
                    documentoBusq.setInd_situ("01");
                    documentoBusq.setTip_arch("TXT");
                    inserto = insertarRegistroBandejaDocumentos(documentoBusq);
                }
                if (archivo.contains(".RET")) {
                    String nombreArchivo = archivo;
                    documentoBusq = new Documento();
                    documentoBusq.setNom_arch(nombreArchivo);
                    documentoBusq.setInd_situ("01");
                    documentoBusq.setTip_arch("TXT");
                    inserto = insertarRegistroBandejaDocumentos(documentoBusq);
                }
                if (archivo.contains(".PER")) {
                    String nombreArchivo = archivo;
                    documentoBusq = new Documento();
                    documentoBusq.setNom_arch(nombreArchivo);
                    documentoBusq.setInd_situ("01");
                    documentoBusq.setTip_arch("TXT");
                    inserto = insertarRegistroBandejaDocumentos(documentoBusq);
                }
                if (archivo.contains(".JSON")) {
                    String nombreArchivo = archivo.replace(".JSON", "");
                    documentoBusq = new Documento();
                    documentoBusq.setNom_arch(nombreArchivo);
                    documentoBusq.setInd_situ("01");
                    documentoBusq.setTip_arch("JSON");
                    inserto = insertarRegistroBandejaDocumentos(documentoBusq);
                }
                if (archivo.contains(".XML")) {
                    String nombreArchivo = archivo.replace(".XML", "");
                    documentoBusq = new Documento();
                    documentoBusq.setNom_arch(nombreArchivo);
                    documentoBusq.setInd_situ("07");
                    documentoBusq.setTip_arch("XML");
                    inserto = insertarRegistroBandejaDocumentos(documentoBusq);
                }
                if (inserto.booleanValue()) {
                    break;
                }
            }
        }
    }

    public void cargarArchivoContribuyente(String directorio, String archivoProcesar) {
        Documento documentoBusq = null;
        File listaArchivos = new File(directorio);
        String[] totalArchivos = listaArchivos.list();
        String archivoEnDirectorio = "";
        for (int x = 0; x < totalArchivos.length; x++) {
            archivoEnDirectorio = totalArchivos[x].toUpperCase();
            String[] archivoTrabajo = archivoEnDirectorio.split("\\.");
            System.out.println("Validar buscando el archivo a procesar: " + archivoTrabajo[0]);
            System.out.println("Validar enviado como parametro: " + archivoProcesar);
            if (archivoProcesar.equals(archivoTrabajo[0])) {
                if (archivoTrabajo.length > 2) {
                    throw new RuntimeException("El nombre del archivo no es el correcto o esta mal formado.");
                }
                System.out.println("Archivo para procesar: " + archivoEnDirectorio);
                if (validarNombreArchivo(archivoEnDirectorio).booleanValue()) {
                    if (archivoEnDirectorio.contains(".CBA")) {
                        documentoBusq = new Documento();
                        documentoBusq.setNom_arch(archivoProcesar);
                        documentoBusq.setInd_situ("01");
                        documentoBusq.setTip_arch("TXT");
                        insertarRegistroBandejaDocumentos(documentoBusq);
                        break;
                    }
                    if (archivoEnDirectorio.contains(".RDI")) {
                        documentoBusq = new Documento();
                        documentoBusq.setNom_arch(archivoProcesar);
                        documentoBusq.setInd_situ("01");
                        documentoBusq.setTip_arch("TXT");
                        insertarRegistroBandejaDocumentos(documentoBusq);
                        break;
                    }
                    if (archivoEnDirectorio.contains(".RDR")) {
                        documentoBusq = new Documento();
                        documentoBusq.setNom_arch(archivoProcesar);
                        documentoBusq.setInd_situ("01");
                        documentoBusq.setTip_arch("TXT");
                        insertarRegistroBandejaDocumentos(documentoBusq);
                        break;
                    }
                    if (archivoEnDirectorio.contains(".CAB")) {
                        documentoBusq = new Documento();
                        documentoBusq.setNom_arch(archivoProcesar);
                        documentoBusq.setInd_situ("01");
                        documentoBusq.setTip_arch("TXT");
                        insertarRegistroBandejaDocumentos(documentoBusq);
                        break;
                    }
                    if (archivoEnDirectorio.contains(".NOT")) {
                        documentoBusq = new Documento();
                        documentoBusq.setNom_arch(archivoProcesar);
                        documentoBusq.setInd_situ("01");
                        documentoBusq.setTip_arch("TXT");
                        insertarRegistroBandejaDocumentos(documentoBusq);
                        break;
                    }
                    if (archivoEnDirectorio.contains(".RET")) {
                        documentoBusq = new Documento();
                        documentoBusq.setNom_arch(archivoProcesar);
                        documentoBusq.setInd_situ("01");
                        documentoBusq.setTip_arch("TXT");
                        insertarRegistroBandejaDocumentos(documentoBusq);
                        break;
                    }
                    if (archivoEnDirectorio.contains(".PER")) {
                        documentoBusq = new Documento();
                        documentoBusq.setNom_arch(archivoProcesar);
                        documentoBusq.setInd_situ("01");
                        documentoBusq.setTip_arch("TXT");
                        insertarRegistroBandejaDocumentos(documentoBusq);
                        break;
                    }
                    if (archivoEnDirectorio.contains(".JSON")) {
                        documentoBusq = new Documento();
                        documentoBusq.setNom_arch(archivoProcesar);
                        documentoBusq.setInd_situ("01");
                        documentoBusq.setTip_arch("JSON");
                        insertarRegistroBandejaDocumentos(documentoBusq);
                        break;
                    }
                    if (archivoEnDirectorio.contains(".XML")) {
                        documentoBusq = new Documento();
                        documentoBusq.setNom_arch(archivoProcesar);
                        documentoBusq.setInd_situ("07");
                        documentoBusq.setTip_arch("XML");
                        insertarRegistroBandejaDocumentos(documentoBusq);
                        break;
                    }
                }
            }
        }
    }

    private Boolean insertarRegistroBandejaDocumentos(Documento documento) {
        System.out.println("BandejaDocumentosServiceImpl.insertarRegistroBandejaDocumentos...Inicio Procesamiento");
        String nroDocumento = "", numRuc = "", tipoDocumento = "", nombreArchivoCarga = "";
        String nombreArchivoOrigen = documento.getNom_arch();
        String tipoArchivo = documento.getTip_arch();
        Boolean archivoResumenBaja = Boolean.valueOf(false), archivoResumenBoletas = Boolean.valueOf(false), archivoReversion = Boolean.valueOf(false);
        Boolean archivoFacturas = Boolean.valueOf(false), archivoRetenciones = Boolean.valueOf(false), archivoPercepciones = Boolean.valueOf(false);
        Boolean nuevoRegistro = Boolean.valueOf(false);
        if ("TXT".equals(tipoArchivo)) {
            archivoResumenBaja = Boolean.valueOf(nombreArchivoOrigen.contains(".CBA"));
            archivoResumenBoletas = Boolean.valueOf(nombreArchivoOrigen.contains(".RDI"));
            archivoReversion = Boolean.valueOf(nombreArchivoOrigen.contains(".RDR"));
            archivoRetenciones = Boolean.valueOf(nombreArchivoOrigen.contains(".RET"));
            archivoPercepciones = Boolean.valueOf(nombreArchivoOrigen.contains(".PER"));
            archivoFacturas = Boolean.valueOf(nombreArchivoOrigen.contains(".CAB"));
            if (archivoReversion.booleanValue()) {
                nombreArchivoCarga = nombreArchivoOrigen.replace(".RDR", "");
            } else if (archivoResumenBoletas.booleanValue()) {
                nombreArchivoCarga = nombreArchivoOrigen.replace(".RDI", "");
            } else if (archivoResumenBaja.booleanValue()) {
                nombreArchivoCarga = nombreArchivoOrigen.replace(".CBA", "");
            } else if (archivoRetenciones.booleanValue()) {
                nombreArchivoCarga = nombreArchivoOrigen.replace(".RET", "");
            } else if (archivoPercepciones.booleanValue()) {
                nombreArchivoCarga = nombreArchivoOrigen.replace(".PER", "");
            } else if (archivoFacturas.booleanValue()) {
                nombreArchivoCarga = nombreArchivoOrigen.replace(".CAB", "");
            } else {
                nombreArchivoCarga = nombreArchivoOrigen.replace(".NOT", "");
            }
        }
        if ("JSON".equals(tipoArchivo)) {
            nombreArchivoCarga = nombreArchivoOrigen.replace(".JSON", "");
        }
        if ("XML".equals(tipoArchivo)) {
            nombreArchivoCarga = nombreArchivoOrigen.replace(".XML", "");
        }
        documento.setNom_arch(nombreArchivoCarga);
        Integer retorno = this.documentoDao.contarBandejaPorNomArch(documento);
        String[] nombreArchivo = nombreArchivoCarga.split("\\-");
        if (retorno.intValue() == 0) {
            if (!"RA".equals(nombreArchivo[1])
                    && !"RC".equals(nombreArchivo[1])
                    && !"RR".equals(nombreArchivo[1])) {
                numRuc = nombreArchivo[0];
                tipoDocumento = nombreArchivo[1];
                nroDocumento = nombreArchivo[2] + "-" + nombreArchivo[3];
            } else {
                numRuc = nombreArchivo[0];
                tipoDocumento = nombreArchivo[1];
                nroDocumento = nombreArchivo[1] + "-" + nombreArchivo[2] + "-" + nombreArchivo[3];
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String fechaActual = format.format(new Date());
            Documento DocumentoDaoInsert = new Documento();
            DocumentoDaoInsert.setNum_ruc(numRuc);
            DocumentoDaoInsert.setTip_docu(tipoDocumento);
            DocumentoDaoInsert.setNum_docu(nroDocumento);
            DocumentoDaoInsert.setFec_carg(fechaActual);
            DocumentoDaoInsert.setFec_envi("-");
            DocumentoDaoInsert.setFec_gene("-");
            DocumentoDaoInsert.setNom_arch(nombreArchivoCarga);
            DocumentoDaoInsert.setDes_obse("-");
            DocumentoDaoInsert.setInd_situ(documento.getInd_situ());
            DocumentoDaoInsert.setTip_arch(documento.getTip_arch());
            this.documentoDao.insertarBandeja(DocumentoDaoInsert);
            nuevoRegistro = Boolean.valueOf(true);
        }
        System.out.println("BandejaDocumentosServiceImpl.insertarRegistroBandejaDocumentos...Finalizado Procesamiento");
        return nuevoRegistro;
    }

    public HashMap<String, Object> enviarComprobantePagoSunat(Documento documento) {
        System.out.println("BandejaDocumentosServiceImpl.enviarComprobantePagoSunat...enviarComprobantePagoSunat Inicio");
        HashMap<String, Object> retorno = null;
        HashMap<String, String> resultadoWebService = null;
        String nombreArchivo = "constantes.properties";
        if ("02".equals(documento.getInd_situ()) || "10"
                .equals(documento.getInd_situ())) {
            retorno = new HashMap<>();
            Properties prop = this.comunesService.getProperties(nombreArchivo);
            String urlWebService = (prop.getProperty("RUTA_SERV_CDP") != null) ? prop.getProperty("RUTA_SERV_CDP") : "XX";
            String tipoComprobante = documento.getTip_docu();
            String filename = documento.getNom_arch();
            System.out.println("BandejaDocumentosServiceImpl.enviarComprobantePagoSunat...Validando Conexia Internet");
            String[] rutaUrl = urlWebService.split("\\/");
            System.out.println("BandejaDocumentosServiceImpl.enviarComprobantePagoSunat...tokens: " + rutaUrl[2]);
            this.comunesService.validarConexion(rutaUrl[2], Integer.valueOf(443));
            System.out.println("BandejaDocumentosServiceImpl.enviarComprobantePagoSunat...Enviando Documento");
            System.out.println("BandejaDocumentosServiceImpl.enviarComprobantePagoSunat...urlWebService: " + urlWebService);
            System.out.println("BandejaDocumentosServiceImpl.enviarComprobantePagoSunat...filename: " + filename);
            System.out.println("BandejaDocumentosServiceImpl.enviarComprobantePagoSunat...tipoComprobante: " + tipoComprobante);
            resultadoWebService = this.generarDocumentosService.enviarArchivoSunat(urlWebService, filename, tipoComprobante);
            retorno.put("resultadoWebService", resultadoWebService);
        }
        System.out.println("BandejaDocumentosServiceImpl.enviarComprobantePagoSunat...enviarComprobantePagoSunat Final");
        return retorno;
    }

    public String generarComprobantePagoSunat(Documento documento) throws TransferirArchivoException {
        XsltCpeValidator xsltCpeValidator = new XsltCpeValidator(this.comunesService, this.errorDao);
        XsdCpeValidator xsdCpeValidator = new XsdCpeValidator(this.comunesService, this.errorDao);
        try {
            String retorno = "01";
            String tipoComprobante = null;
            if ("01".equals(documento.getInd_situ()) || "06"
                    .equals(documento.getInd_situ()) || "07"
                    .equals(documento.getInd_situ()) || "10"
                    .equals(documento.getInd_situ()) || "05"
                    .equals(documento.getInd_situ())) {
                retorno = "";
                tipoComprobante = documento.getTip_docu();
                System.out.println("BandejaDocumentosServiceImpl.generarComprobantePagoSunat...tipoComprobante: " + tipoComprobante);
                if ("TXT".equalsIgnoreCase(documento.getTip_arch())) {
                    this.generarDocumentosService.formatoPlantillaXml(documento);
                    retorno = this.generarDocumentosService.firmarComprimirXml(documento.getNom_arch());
                    this.generarDocumentosService.validarPlazo(documento.getNom_arch());
                    xsdCpeValidator.validarSchemaXML(tipoComprobante, this.comunesService
                            .obtenerRutaTrabajo("PARS") + documento.getNom_arch() + ".xml");
                    xsltCpeValidator.validarXML(tipoComprobante, this.comunesService.obtenerRutaTrabajo("PARS"), documento
                            .getNom_arch());
                }
                System.out.println("BandejaDocumentosServiceImpl.generarComprobantePagoSunat...Tipo de Archivo Json: " + documento
                        .getTip_arch());
                if ("JSON".equalsIgnoreCase(documento.getTip_arch())) {
                    this.generarDocumentosService.formatoJsonPlantilla(documento);
                    retorno = this.generarDocumentosService.firmarComprimirXml(documento.getNom_arch());
                    this.generarDocumentosService.validarPlazo(documento.getNom_arch());
                    xsdCpeValidator.validarSchemaXML(tipoComprobante, this.comunesService.obtenerRutaTrabajo("PARS") + documento.getNom_arch() + ".xml");
                    xsltCpeValidator.validarXML(tipoComprobante, this.comunesService.obtenerRutaTrabajo("PARS"), documento.getNom_arch());
                }
                if ("XML".equalsIgnoreCase(documento.getTip_arch())) {
                    this.generarDocumentosService.adicionarInformacionFacturador(documento.getNom_arch());
                    retorno = this.generarDocumentosService.firmarComprimirXml(documento.getNom_arch());
                    this.generarDocumentosService.validarPlazo(documento.getNom_arch());
                    xsdCpeValidator.validarSchemaXML(tipoComprobante, this.comunesService
                            .obtenerRutaTrabajo("PARS") + documento.getNom_arch() + ".xml");
                    xsltCpeValidator.validarXML(tipoComprobante, this.comunesService.obtenerRutaTrabajo("PARS"), documento
                            .getNom_arch());
                }
            }
            return retorno;
        } catch (XsltException | sistema.facturador.exception.XsdException e) {
            System.out.println("------------");
            e.printStackTrace();
            log.info(e.getMessage());
            ExceptionDetail exceptionDetail = new ExceptionDetail();
            exceptionDetail.setMessage(e.getMessage());
            throw new TransferirArchivoException(e.getMessage(), exceptionDetail);
        } catch (Exception e) {
            log.info(e.getMessage());
            e.printStackTrace();
            ExceptionDetail exceptionDetail = new ExceptionDetail();
            exceptionDetail.setMessage(e.getMessage());
            throw new TransferirArchivoException(e.getMessage(), exceptionDetail);
        }
    }

    public HashMap<String, Object> listarCertificados() throws Exception {
        HashMap<String, Object> resultado = new HashMap<>();
        Map<String, String> archivoLista = null;
        List<Map<String, String>> listaArchivos = new ArrayList<>();
        Integer error = Integer.valueOf(0);
        File directorioCertificados = new File(this.comunesService.obtenerRutaTrabajo("CERT"));
        if (!directorioCertificados.exists()) {
            resultado.put("validacion", "No existe el directorio.");
            resultado.put("adjunto", null);
            error = Integer.valueOf(1);
        }
        if (!directorioCertificados.isDirectory() && error.intValue() == 0) {
            resultado.put("validacion", this.comunesService.obtenerRutaTrabajo("CERT") + ", no es directorio.");
            resultado.put("adjunto", null);
            error = Integer.valueOf(1);
        }
        if (error.intValue() == 0) {
            String[] archivos = directorioCertificados.list();
            if (archivos == null) {
                resultado.put("validacion", "No hay ficheros en el directorio especificado");
                resultado.put("adjunto", null);
                error = Integer.valueOf(1);
            } else {
                for (int x = 0; x < archivos.length; x++) {
                    archivoLista = new HashMap<>();
                    archivoLista.put("id", archivos[x]);
                    if (archivos[x] != null) {
                        if (archivos[x].length() > 29) {
                            archivoLista.put("nombre", archivos[x].substring(0, 28));
                        } else {
                            archivoLista.put("nombre", archivos[x]);
                        }
                    } else {
                        archivoLista.put("nombre", archivos[x]);
                    }
                    listaArchivos.add(archivoLista);
                }
                resultado.put("validacion", "");
                resultado.put("adjunto", listaArchivos);
                resultado.put("ruta", this.comunesService.obtenerRutaTrabajo("CERT"));
            }
        }
        return resultado;
    }

    public HashMap<String, String> importarCertificado(HashMap<String, Object> obj) throws Exception {
        String numRuc = "";
        String aliasPfx = "";
        Integer error = Integer.valueOf(0);
        HashMap<String, String> resultado = new HashMap<>();
        String nombreCertificado = (obj.get("nombreCertificado") != null) ? (String) obj.get("nombreCertificado") : "";
        String passPrivateKey = (obj.get("passPrivateKey") != null) ? (String) obj.get("passPrivateKey") : "";
        String rutaCertificado = this.comunesService.obtenerRutaTrabajo("CERT") + nombreCertificado;
        resultado.put("validacion", "EXITO");
        if ("".equals(rutaCertificado)) {
            resultado.put("validacion", "Debe ingresar la ruta del certificado");
            error = Integer.valueOf(1);
        }
        if (rutaCertificado.indexOf(".pfx") == -1 && rutaCertificado.indexOf(".p12") == -1 && error.intValue() == 0) {
            resultado.put("validacion", "Archivo cargado debe ser de tipo \"pfx o p12\" ");
            error = Integer.valueOf(1);
        }
        if ("".equals(passPrivateKey) && error.intValue() == 0) {
            resultado.put("validacion", "Debe ingresar su contrasede certificado");
            error = Integer.valueOf(1);
        }
        if (error.intValue() == 0) {
            List<Parametro> listado = new ArrayList<>();
            Parametro parametro = new Parametro();
            parametro.setId_para("PARASIST");
            parametro.setCod_para("NUMRUC");
            try {
                listado = this.parametroDao.consultarParametroById(parametro);
            } catch (Exception e) {
                log.error("Mensaje de Error: " + e.getMessage());
                resultado.put("validacion", "Mensaje de Error: " + e.getMessage());
                error = Integer.valueOf(1);
            }
            if (error.intValue() == 0) {
                if (listado.size() > 0) {
                    numRuc = ((Parametro) listado.get(0)).getVal_para();
                }
                if ("".equals(numRuc)) {
                    resultado.put("validacion", "No Existe valor parametrizado del RUC");
                    error = Integer.valueOf(1);
                }
            }
        }
        if (error.intValue() == 0)
      try {
            String output = (new CertificadoFacturador()).validaCertificado(rutaCertificado, numRuc, passPrivateKey);
            System.out.println("Metodo importarCertificado: Salida de generarDocumentosService.validaCertificado " + output);
            if (!output.contains("[ALIAS]")) {
                resultado.put("validacion", "Certificado, no esta configurado con el valor del RUC");
                error = Integer.valueOf(1);
            } else {
                Integer position = Integer.valueOf(output.indexOf(":") + 1);
                aliasPfx = output.substring(position.intValue());
            }
        } catch (Exception e) {
            System.out.println("Mensaje de Error: " + e.getMessage());
        }
        if (error.intValue() == 0) {
            String salida = FacturadorUtil.executeCommand("keytool -delete -alias certContribuyente -storepass SuN@TF4CT -keystore \"" + this.comunesService
                    .obtenerRutaTrabajo("ALMC") + "FacturadorKey.jks\"");
            System.out.println("Metodo importarCertificado: Salida de keytool -delete " + salida);
            salida = FacturadorUtil.executeCommand("keytool -importkeystore -srcalias \"" + aliasPfx + "\" -srckeystore \"" + rutaCertificado + "\" -srcstoretype pkcs12 -srcstorepass \"" + passPrivateKey + "\" -destkeystore \"" + this.comunesService
                    .obtenerRutaTrabajo("ALMC") + "FacturadorKey.jks\" -deststoretype JKS -destalias certContribuyente -deststorepass SuN@TF4CT");
            System.out.println("Metodo importarCertificado: Salida de keytool -importkeystore " + salida);
            if (!"".equals(salida)) {
                resultado.put("validacion", "Hubo un error, el certificado no fue creado");
                error = Integer.valueOf(1);
            }
        }
        if (error.intValue() == 0) {
            List<Parametro> listado = new ArrayList<>();
            Parametro parametro = new Parametro();
            parametro.setId_para("PARASIST");
            parametro.setCod_para("NOMCERT");
            try {
                listado = this.parametroDao.consultarParametroById(parametro);
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
            if (listado.size() > 0) {
                parametro.setVal_para(nombreCertificado);
                this.parametroDao.actualizarParametro(parametro);
            } else {
                parametro.setNom_para("Nombre del Certificado");
                parametro.setTip_para("CADENA");
                parametro.setVal_para(nombreCertificado);
                parametro.setInd_esta_para("1");
                this.parametroDao.insertarParametro(parametro);
            }
        }
        if (error.intValue() == 0) {
            List<Parametro> listado = new ArrayList<>();
            Parametro parametro = new Parametro();
            parametro.setId_para("PARASIST");
            parametro.setCod_para("PRKCRT");
            try {
                listado = this.parametroDao.consultarParametroById(parametro);
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
            if (listado.size() > 0) {
                parametro.setVal_para(this.generarDocumentosService.Encriptar(passPrivateKey));
                this.parametroDao.actualizarParametro(parametro);
            } else {
                parametro.setNom_para("Contrasedel Certificado Emisor");
                parametro.setTip_para("CADENA");
                parametro.setVal_para(this.generarDocumentosService.Encriptar(passPrivateKey));
                parametro.setInd_esta_para("1");
                this.parametroDao.insertarParametro(parametro);
            }
        }
        return resultado;
    }

    public HashMap<String, Object> obtenerParametros() throws Exception {
        HashMap<String, Object> resultado = new HashMap<>();
        Parametro paramtroBean = new Parametro();
        String numRuc = "", usuarioSol = "", claveSol = "", funcionamiento = "", certificado = "";
        String param = "", razonSocial = "", tiempoGenera = "", rutaSolucion = "", tiempoEnvia = "";
        paramtroBean = new Parametro();
        paramtroBean.setId_para("PARASIST");
        List<Parametro> listaParametros = null;
        try {
            listaParametros = this.parametroDao.consultarParametro(paramtroBean);
            if (listaParametros.size() > 0) {
                for (Parametro parametro : listaParametros) {
                    param = parametro.getVal_para();
                    if ("NUMRUC".equals(parametro.getCod_para())) {
                        numRuc = param;
                    }
                    if ("USUSOL".equals(parametro.getCod_para())) {
                        usuarioSol = param;
                    }
                    if ("CLASOL".equals(parametro.getCod_para())) {
                        claveSol = this.generarDocumentosService.Desencriptar(param);
                    }
                    if ("NOMCERT".equals(parametro.getCod_para())) {
                        certificado = param;
                    }
                    if ("RUTSOL".equals(parametro.getCod_para())) {
                        rutaSolucion = param;
                    }
                    if ("FUNCIO".equals(parametro.getCod_para())) {
                        funcionamiento = param;
                    }
                    if ("RAZON".equals(parametro.getCod_para())) {
                        razonSocial = param;
                    }
                    if ("TIMEGENERA".equals(parametro.getCod_para())) {
                        tiempoGenera = param;
                    }
                    if ("TIMEENVIA".equals(parametro.getCod_para())) {
                        tiempoEnvia = param;
                    }
                }
            }
            Map<String, String> parametros = new HashMap<>();
            parametros.put("numRuc", numRuc);
            parametros.put("usuarioSol", usuarioSol);
            parametros.put("claveSol", claveSol);
            parametros.put("certificado", certificado);
            parametros.put("funcionamiento", funcionamiento);
            parametros.put("razonSocial", razonSocial);
            parametros.put("tiempoGenera", tiempoGenera);
            parametros.put("tiempoEnviar", tiempoEnvia);
            parametros.put("rutaSolucion", rutaSolucion);
            resultado.put("validacion", "");
            resultado.put("adjunto", parametros);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }

    public Map<String, Object> obtenerOtrosParametros() throws Exception {
        Map<String, Object> resultado = new HashMap<>();
        String nombreComercial = "", ubigeo = "", direccion = "", departamento = "", provincia = "", distrito = "";
        String urbanizacion = "", param = "";
        Parametro paramtroBean = new Parametro();
        paramtroBean = new Parametro();
        paramtroBean.setId_para("PARASIST");
        List<Parametro> listaParametros = null;
        try {
            listaParametros = this.parametroDao.consultarParametro(paramtroBean);
            if (listaParametros.size() > 0) {
                for (Parametro parametro : listaParametros) {
                    param = parametro.getVal_para();
                    if ("NOMCOM".equals(parametro.getCod_para())) {
                        nombreComercial = param;
                    }
                    if ("UBIGEO".equals(parametro.getCod_para())) {
                        ubigeo = param;
                    }
                    if ("DIRECC".equals(parametro.getCod_para())) {
                        direccion = param;
                    }
                    if ("DEPAR".equals(parametro.getCod_para())) {
                        departamento = param;
                    }
                    if ("PROVIN".equals(parametro.getCod_para())) {
                        provincia = param;
                    }
                    if ("DISTR".equals(parametro.getCod_para())) {
                        distrito = param;
                    }
                    if ("URBANIZA".equals(parametro.getCod_para())) {
                        urbanizacion = param;
                    }
                }
            }
            Map<String, String> parametros = new HashMap<>();
            parametros.put("nombreComercial", nombreComercial);
            parametros.put("ubigeo", ubigeo);
            parametros.put("direccion", direccion);
            parametros.put("departamento", departamento);
            parametros.put("provincia", provincia);
            parametros.put("distrito", distrito);
            parametros.put("urbanizacion", urbanizacion);
            resultado.put("validacion", "");
            resultado.put("adjunto", parametros);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            resultado.put("validacion", e.getMessage());
        }
        return resultado;
    }

    public HashMap<String, String> grabarParametro(HashMap<String, Object> obj) throws Exception {
        Integer error = Integer.valueOf(0);
        HashMap<String, String> resultado = new HashMap<>();
        String numRuc = (obj.get("txtNumeroRuc") != null) ? (String) obj.get("txtNumeroRuc") : "";
        String usuaSol = (obj.get("txtUsuarioSol") != null) ? (String) obj.get("txtUsuarioSol") : "";
        String claveSol = (obj.get("txtClaveSol") != null) ? (String) obj.get("txtClaveSol") : "";
        String cmbFuncion = (obj.get("cmbFuncionamiento") != null) ? (String) obj.get("cmbFuncionamiento") : "";
        String txtRazonSocial = (obj.get("txtRazonSocial") != null) ? (String) obj.get("txtRazonSocial") : "";
        String txtTiempoGenera = (obj.get("cmbTiempoGenera") != null) ? (String) obj.get("cmbTiempoGenera") : "";
        String txtTiempoEnviar = (obj.get("cmbTiempoEnvia") != null) ? (String) obj.get("cmbTiempoEnvia") : "";
        String txtRutaSolucion = (obj.get("txtRutaSolucion") != null) ? (String) obj.get("txtRutaSolucion") : "";
        resultado.put("validacion", "EXITO");
        if ("".equals(numRuc)) {
            resultado.put("validacion", "Debe ingresar el nro de RUC");
            error = Integer.valueOf(1);
        }
        if (numRuc.length() != 11 && error.intValue() == 0) {
            resultado.put("validacion", "El RUC debe ser de 11 caracteres");
            error = Integer.valueOf(1);
        }
        if ("".equals(usuaSol) && error.intValue() == 0) {
            resultado.put("validacion", "Debe ingresar su usuario SOL");
            error = Integer.valueOf(1);
        }
        if ("".equals(claveSol) && error.intValue() == 0) {
            resultado.put("validacion", "Debe ingresar su clave SOL");
            error = Integer.valueOf(1);
        }
        if ("".equals(cmbFuncion) && error.intValue() == 0) {
            resultado.put("validacion", "Debe elegir si usar o no temporizador.");
            error = Integer.valueOf(1);
        }
        if ("01".equals(cmbFuncion) && error.intValue() == 0) {
            if ("".equals(txtTiempoGenera)) {
                resultado.put("validacion", "Debe seleccionar el tiempo del temporizador para generar comprobante.");
                error = Integer.valueOf(1);
            } else {
                try {
                    this.gestionarProcesosBatchJob.generarComprobanteJob(new Integer(txtTiempoGenera), "INICIAR");
                    if (!"".equals(txtTiempoEnviar)) {
                        this.gestionarProcesosBatchJob.enviarComprobanteJob(new Integer(txtTiempoEnviar), "INICIAR");
                    }
                } catch (Exception e) {
                    resultado.put("validacion", "Error: " + e.getMessage() + " - Causa: " + e.getCause());
                    error = Integer.valueOf(1);
                }
            }
        } else if (error.intValue() == 0) {
            try {
                this.gestionarProcesosBatchJob.generarComprobanteJob(new Integer(0), "APAGAR");
                if ("".equals(txtTiempoEnviar)) {
                    this.gestionarProcesosBatchJob.enviarComprobanteJob(new Integer(0), "APAGAR");
                }
            } catch (Exception e) {
                resultado.put("validacion", "Error: " + e.getMessage() + " - Causa: " + e.getCause());
                error = Integer.valueOf(1);
            }
        }
        if ("".equals(txtRazonSocial) && error.intValue() == 0) {
            resultado.put("validacion", "Debe ingresar su Razon Social o Apellidos y Nombres.");
            error = Integer.valueOf(1);
        }
        if (error.intValue() == 0) {
            Parametro parametro = new Parametro();
            parametro.setId_para("PARASIST");
            parametro.setCod_para("NUMRUC");
            List<Parametro> listado = null;
            try {
                listado = this.parametroDao.consultarParametroById(parametro);
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
            if (listado.size() > 0) {
                parametro.setVal_para(numRuc);
                this.parametroDao.actualizarParametro(parametro);
            } else {
                parametro.setNom_para("Ruc del Contribuyente Emisor");
                parametro.setTip_para("CADENA");
                parametro.setVal_para(numRuc);
                parametro.setInd_esta_para("1");
                this.parametroDao.insertarParametro(parametro);
            }
            parametro = new Parametro();
            parametro.setId_para("PARASIST");
            parametro.setCod_para("USUSOL");
            try {
                listado = this.parametroDao.consultarParametroById(parametro);
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
            if (listado.size() > 0) {
                parametro.setVal_para(usuaSol);
                this.parametroDao.actualizarParametro(parametro);
            } else {
                parametro.setNom_para("Usuario SOL del Contribuyente Emisor");
                parametro.setTip_para("CADENA");
                parametro.setVal_para(usuaSol);
                parametro.setInd_esta_para("1");
                this.parametroDao.insertarParametro(parametro);
            }
            parametro = new Parametro();
            parametro.setId_para("PARASIST");
            parametro.setCod_para("CLASOL");
            try {
                listado = this.parametroDao.consultarParametroById(parametro);
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
            if (listado.size() > 0) {
                parametro.setVal_para(this.generarDocumentosService.Encriptar(claveSol));
                this.parametroDao.actualizarParametro(parametro);
            } else {
                parametro.setNom_para("Clave SOL del Contribuyente Emisor");
                parametro.setTip_para("CADENA");
                parametro.setVal_para(this.generarDocumentosService.Encriptar(claveSol));
                parametro.setInd_esta_para("1");
                this.parametroDao.insertarParametro(parametro);
            }
            parametro = new Parametro();
            parametro.setId_para("PARASIST");
            parametro.setCod_para("FUNCIO");
            try {
                listado = this.parametroDao.consultarParametroById(parametro);
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
            if (listado.size() > 0) {
                parametro.setVal_para(cmbFuncion);
                this.parametroDao.actualizarParametro(parametro);
            } else {
                parametro.setNom_para("Tipo Funcionamiento del facturador");
                parametro.setTip_para("CADENA");
                parametro.setVal_para(cmbFuncion);
                parametro.setInd_esta_para("1");
                this.parametroDao.insertarParametro(parametro);
            }
            parametro = new Parametro();
            parametro.setId_para("PARASIST");
            parametro.setCod_para("TIMEGENERA");
            try {
                listado = this.parametroDao.consultarParametroById(parametro);
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
            if (listado.size() > 0) {
                parametro.setVal_para(txtTiempoGenera);
                this.parametroDao.actualizarParametro(parametro);
            } else {
                parametro.setNom_para("Valor Temporizador del facturador - Generar");
                parametro.setTip_para("CADENA");
                parametro.setVal_para(txtTiempoGenera);
                parametro.setInd_esta_para("1");
                this.parametroDao.insertarParametro(parametro);
            }
            parametro = new Parametro();
            parametro.setId_para("PARASIST");
            parametro.setCod_para("TIMEENVIA");
            try {
                listado = this.parametroDao.consultarParametroById(parametro);
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
            if (listado.size() > 0) {
                parametro.setVal_para(txtTiempoEnviar);
                this.parametroDao.actualizarParametro(parametro);
            } else {
                parametro.setNom_para("Valor Temporizador del facturador - Enviar");
                parametro.setTip_para("CADENA");
                parametro.setVal_para(txtTiempoEnviar);
                parametro.setInd_esta_para("1");
                this.parametroDao.insertarParametro(parametro);
            }
            parametro = new Parametro();
            parametro.setId_para("PARASIST");
            parametro.setCod_para("RAZON");
            try {
                listado = this.parametroDao.consultarParametroById(parametro);
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
            if (listado.size() > 0) {
                parametro.setVal_para(txtRazonSocial);
                this.parametroDao.actualizarParametro(parametro);
            } else {
                parametro.setNom_para("Razon Social o Nombres");
                parametro.setTip_para("CADENA");
                parametro.setVal_para(txtRazonSocial);
                parametro.setInd_esta_para("1");
                this.parametroDao.insertarParametro(parametro);
            }
            parametro = new Parametro();
            parametro.setId_para("PARASIST");
            parametro.setCod_para("RUTSOL");
            try {
                listado = this.parametroDao.consultarParametroById(parametro);
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
            if (listado.size() > 0) {
                parametro.setVal_para(txtRutaSolucion);
                this.parametroDao.actualizarParametro(parametro);
            } else {
                parametro.setNom_para("Ruta de la solucide software");
                parametro.setTip_para("CADENA");
                parametro.setVal_para(txtRutaSolucion);
                parametro.setInd_esta_para("1");
                this.parametroDao.insertarParametro(parametro);
            }
        }
        return resultado;
    }

    public HashMap<String, String> grabarOtrosParametros(HashMap<String, Object> obj) throws Exception {
        Integer error = Integer.valueOf(0);
        HashMap<String, String> resultado = new HashMap<>();
        String nombreComercial = (obj.get("txtNombreComercial") != null) ? (String) obj.get("txtNombreComercial") : "";
        String ubigeo = (obj.get("txtUbigeo") != null) ? (String) obj.get("txtUbigeo") : "";
        String direccion = (obj.get("txtDireccion") != null) ? (String) obj.get("txtDireccion") : "";
        String departamento = (obj.get("txtDepartamento") != null) ? (String) obj.get("txtDepartamento") : "";
        String provincia = (obj.get("txtProvincia") != null) ? (String) obj.get("txtProvincia") : "";
        String distrito = (obj.get("txtDistrito") != null) ? (String) obj.get("txtDistrito") : "";
        String urbanizacion = (obj.get("txtUrbanizacion") != null) ? (String) obj.get("txtUrbanizacion") : "";
        resultado.put("validacion", "EXITO");
        if ("".equals(nombreComercial)) {
            resultado.put("validacion", "Debe ingresar el nombre comercial");
            error = Integer.valueOf(1);
        }
        if ("".equals(ubigeo) && error.intValue() == 0) {
            resultado.put("validacion", "Debe ingresar el UBIGEO");
            error = Integer.valueOf(1);
        }
        if ("".equals(direccion) && error.intValue() == 0) {
            resultado.put("validacion", "Debe ingresar direccion");
            error = Integer.valueOf(1);
        }
        if ("".equals(departamento) && error.intValue() == 0) {
            resultado.put("validacion", "Debe ingresar departamento de UBIGEO.");
            error = Integer.valueOf(1);
        }
        if ("".equals(provincia) && error.intValue() == 0) {
            resultado.put("validacion", "Debe ingresar provincia de UBIGEO.");
            error = Integer.valueOf(1);
        }
        if ("".equals(distrito) && error.intValue() == 0) {
            resultado.put("validacion", "Debe ingresar distrito de UBIGEO.");
            error = Integer.valueOf(1);
        }
        if ("".equals(urbanizacion) && error.intValue() == 0) {
            resultado.put("validacion", "Debe ingresar urbanizacion de UBIGEO.");
            error = Integer.valueOf(1);
        }
        if (error.intValue() == 0) {
            Parametro parametro = new Parametro();
            parametro.setId_para("PARASIST");
            parametro.setCod_para("NOMCOM");
            List<Parametro> listado = null;
            try {
                listado = this.parametroDao.consultarParametroById(parametro);
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
            if (listado.size() > 0) {
                parametro.setVal_para(nombreComercial);
                this.parametroDao.actualizarParametro(parametro);
            } else {
                parametro.setNom_para("Nombre Comercial del Emisor");
                parametro.setTip_para("CADENA");
                parametro.setVal_para(nombreComercial);
                parametro.setInd_esta_para("1");
                this.parametroDao.insertarParametro(parametro);
            }
            parametro = new Parametro();
            parametro.setId_para("PARASIST");
            parametro.setCod_para("UBIGEO");
            try {
                listado = this.parametroDao.consultarParametroById(parametro);
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
            if (listado.size() > 0) {
                parametro.setVal_para(ubigeo);
                this.parametroDao.actualizarParametro(parametro);
            } else {
                parametro.setNom_para("Ubigeo Direccidel Emisor");
                parametro.setTip_para("CADENA");
                parametro.setVal_para(ubigeo);
                parametro.setInd_esta_para("1");
                this.parametroDao.insertarParametro(parametro);
            }
            parametro = new Parametro();
            parametro.setId_para("PARASIST");
            parametro.setCod_para("DIRECC");
            try {
                listado = this.parametroDao.consultarParametroById(parametro);
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
            if (listado.size() > 0) {
                parametro.setVal_para(direccion);
                this.parametroDao.actualizarParametro(parametro);
            } else {
                parametro.setNom_para("Direccidel Emisor");
                parametro.setTip_para("CADENA");
                parametro.setVal_para(direccion);
                parametro.setInd_esta_para("1");
                this.parametroDao.insertarParametro(parametro);
            }
            parametro = new Parametro();
            parametro.setId_para("PARASIST");
            parametro.setCod_para("DEPAR");
            try {
                listado = this.parametroDao.consultarParametroById(parametro);
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
            if (listado.size() > 0) {
                parametro.setVal_para(departamento);
                this.parametroDao.actualizarParametro(parametro);
            } else {
                parametro.setNom_para("Departamento direccion de Emisor");
                parametro.setTip_para("CADENA");
                parametro.setVal_para(departamento);
                parametro.setInd_esta_para("1");
                this.parametroDao.insertarParametro(parametro);
            }
            parametro = new Parametro();
            parametro.setId_para("PARASIST");
            parametro.setCod_para("PROVIN");
            try {
                listado = this.parametroDao.consultarParametroById(parametro);
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
            if (listado.size() > 0) {
                parametro.setVal_para(provincia);
                this.parametroDao.actualizarParametro(parametro);
            } else {
                parametro.setNom_para("Provincia direccion de Emisor");
                parametro.setTip_para("CADENA");
                parametro.setVal_para(provincia);
                parametro.setInd_esta_para("1");
                this.parametroDao.insertarParametro(parametro);
            }
            parametro = new Parametro();
            parametro.setId_para("PARASIST");
            parametro.setCod_para("DISTR");
            try {
                listado = this.parametroDao.consultarParametroById(parametro);
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
            if (listado.size() > 0) {
                parametro.setVal_para(distrito);
                this.parametroDao.actualizarParametro(parametro);
            } else {
                parametro.setNom_para("Distrito direccion de Emisor");
                parametro.setTip_para("CADENA");
                parametro.setVal_para(distrito);
                parametro.setInd_esta_para("1");
                this.parametroDao.insertarParametro(parametro);
            }
            parametro = new Parametro();
            parametro.setId_para("PARASIST");
            parametro.setCod_para("URBANIZA");
            try {
                listado = this.parametroDao.consultarParametroById(parametro);
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
            if (listado.size() > 0) {
                parametro.setVal_para(urbanizacion);
                this.parametroDao.actualizarParametro(parametro);
            } else {
                parametro.setNom_para("Urbanizacion de direccion de Emisor");
                parametro.setTip_para("CADENA");
                parametro.setVal_para(urbanizacion);
                parametro.setInd_esta_para("1");
                this.parametroDao.insertarParametro(parametro);
            }
        }
        return resultado;
    }

    public List<Documento> buscarBandejaPorSituacion(String situacionComprobante) throws Exception {
        Documento documentoEnviar = new Documento();
        documentoEnviar.setInd_situ(situacionComprobante);
        List<Documento> listaPendEnvio = this.documentoDao.consultarBandejaPorSituacion(documentoEnviar);
        return listaPendEnvio;
    }

    public String validarParametroRegistrado() throws Exception {
        System.out.println("Iniciando validarParametroRegistrado.BandejaDocumentosServiceImpl");
        String mensaje = "";
        List<String> listaParametroValidar = new ArrayList<>();
        listaParametroValidar.add("NUMRUC");
        listaParametroValidar.add("USUSOL");
        listaParametroValidar.add("CLASOL");
        listaParametroValidar.add("RUTSOL");
        listaParametroValidar.add("PRKCRT");
        listaParametroValidar.add("FUNCIO");
        listaParametroValidar.add("RAZON");
        listaParametroValidar.add("NOMCOM");
        listaParametroValidar.add("UBIGEO");
        listaParametroValidar.add("DIRECC");
        listaParametroValidar.add("DEPAR");
        listaParametroValidar.add("PROVIN");
        listaParametroValidar.add("DISTR");
        listaParametroValidar.add("URBANIZA");
        listaParametroValidar.add("TIEMPO");
        Parametro paramtroBean = new Parametro();
        paramtroBean = new Parametro();
        paramtroBean.setId_para("PARASIST");
        try {
            List<Parametro> listaParametros = this.parametroDao.consultarParametro(paramtroBean);
            List<String> listaParametrosExistentes = new ArrayList<>();
            for (Parametro parametro : listaParametros) {
                listaParametrosExistentes.add(parametro.getCod_para());
            }
            for (String codigo : listaParametroValidar) {
                System.out.println("BandejaDocumentosServiceImpl.validarParametroRegistrado...codigo: " + codigo);
                if (!listaParametrosExistentes.contains(codigo)) {
                    if ("NUMRUC".equals(codigo)) {
                        mensaje = "Debe ingresar el parRUC en la opciconfiguracidel facturador.";
                    }
                    if ("USUSOL".equals(codigo)) {
                        mensaje = "Debe ingresar el parusuario secundario en la opciconfiguracidel facturador.";
                    }
                    if ("CLASOL".equals(codigo)) {
                        mensaje = "Debe ingresar el parclave SOL en la opciconfiguracidel facturador.";
                    }
                    if ("RUTSOL".equals(codigo)) {
                        mensaje = "Debe importar su certificado digital.";
                    }
                    if ("PRKCRT".equals(codigo)) {
                        mensaje = "Debe importar su certificado digital.";
                    }
                    if ("FUNCIO".equals(codigo)) {
                        mensaje = "Debe ingresar el partipo de funcionamiento con temporizador o no, en la opciconfiguracidel facturador.";
                    }
                    if ("TIMEGENERA".equals(codigo)) {
                        mensaje = "Debe ingresar el parde tiempo de generacien la opciconfiguracidel facturador.";
                    }
                    if ("TIMEENVIA".equals(codigo)) {
                        mensaje = "Debe ingresar el parde tiempo de enven la opciconfiguracidel facturador.";
                    }
                    if ("RAZON".equals(codigo)) {
                        mensaje = "Debe ingresar el parde razsocial en la opciconfiguracidel facturador.";
                    }
                    if ("NOMCOM".equals(codigo)) {
                        mensaje = "Debe ingresar el parde nombre completo en la opciconfiguracidel facturador.";
                    }
                    if ("UBIGEO".equals(codigo)) {
                        mensaje = "Debe ingresar el parde ubigeo en la opciconfiguracidel facturador.";
                    }
                    if ("DIRECC".equals(codigo)) {
                        mensaje = "Debe ingresar el parde direccien la opciconfiguracidel facturador.";
                    }
                    if ("DEPAR".equals(codigo)) {
                        mensaje = "Debe ingresar el parde departamento en la opciconfiguracidel facturador.";
                    }
                    if ("PROVIN".equals(codigo)) {
                        mensaje = "Debe ingresar el parde provincia en la opciconfiguracidel facturador.";
                    }
                    if ("URBANIZA".equals(codigo)) {
                        mensaje = "Debe ingresar el parde urbanizacien la opciconfiguracidel facturador.";
                    }
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Finalizando validarParametroRegistrado.BandejaDocumentosServiceImpl mensaje: " + mensaje);
        System.out.println("Finalizando validarParametroRegistrado.BandejaDocumentosServiceImpl");
        return mensaje;
    }

    private Boolean validarNombreArchivo(String nombreArchivo) {
        Boolean resultado = Boolean.valueOf(true);
        System.out.println("Validar que no sea directorio: " + resultado);
        File directorio = new File(nombreArchivo);
        if (directorio.isDirectory()) {
            resultado = Boolean.valueOf(false);
        }
        System.out.println("Validar que no exista mas de un punto en el nombre del archivo: " + resultado);
        String[] validacionPunto = nombreArchivo.split("\\.");
        if (validacionPunto.length != 2) {
            resultado = Boolean.valueOf(false);
        }
        System.out.println("Validar que sea una extension valida: " + resultado);
        if (resultado.booleanValue()) {
            String tipoExtension = validacionPunto[1];
            System.out.println("Validar que sea una extension valida, Extension: " + tipoExtension);
            if (!"CBA".equals(tipoExtension)
                    && !"RDI".equals(tipoExtension)
                    && !"TRD".equals(tipoExtension)
                    && !"RDR".equals(tipoExtension)
                    && !"NOT".equals(tipoExtension)
                    && !"CAB".equals(tipoExtension)
                    && !"RET".equals(tipoExtension)
                    && !"PER".equals(tipoExtension)
                    && !"JSON".equals(tipoExtension)
                    && !"XML".equals(tipoExtension)
                    && !"DET".equals(tipoExtension)
                    && !"REL".equals(tipoExtension)
                    && !"ACA".equals(tipoExtension)
                    && !"ADE".equals(tipoExtension)
                    && !"LEY".equals(tipoExtension)
                    && !"DRE".equals(tipoExtension)
                    && !"DPE".equals(tipoExtension)) {
                resultado = Boolean.valueOf(false);
            }
        }
        System.out.println("Validar que el nombre esta bien conformado: " + resultado);
        String[] validacionNombre = nombreArchivo.split("\\-");
        if (resultado.booleanValue()) {
            if (validacionNombre.length != 4) {
                resultado = Boolean.valueOf(false);
            }
        }
        System.out.println("Validar RUC es numerico y 11 digitos: " + resultado);
        if (resultado.booleanValue()) {
            String expresion = "^[0-9]{11}$";
            Pattern patron = Pattern.compile(expresion);
            Matcher validador = patron.matcher(validacionNombre[0]);
            resultado = Boolean.valueOf(validador.matches());
        }
        System.out.println("Validar extension en el dominio valido: " + resultado);
        if (resultado.booleanValue()
                && !"08".equals(validacionNombre[1])
                && !"07".equals(validacionNombre[1])
                && !"01".equals(validacionNombre[1])
                && !"20".equals(validacionNombre[1])
                && !"40".equals(validacionNombre[1])
                && !"03".equals(validacionNombre[1])
                && !"RA".equals(validacionNombre[1])
                && !"RC".equals(validacionNombre[1])
                && !"RR".equals(validacionNombre[1])
                && !"09".equals(validacionNombre[1])) {
            resultado = Boolean.valueOf(false);
        }
        System.out.println("Validar serie sea 4 caracteres: " + resultado);
        if (resultado.booleanValue()) {
            String expresion = "";
            if (!"RA".equals(validacionNombre[1])
                    && !"RC".equals(validacionNombre[1])
                    && !"RR".equals(validacionNombre[1])) {
                expresion = "^[a-zA-Z0-9]{4}$";
            } else {
                expresion = "^[a-zA-Z0-9]{8}$";
            }
            Pattern patron = Pattern.compile(expresion);
            Matcher validador = patron.matcher(validacionNombre[2]);
            resultado = Boolean.valueOf(validador.matches());
        }
        System.out.println("Retorno desde Validador de Nombre: " + resultado);
        return resultado;
    }
}
