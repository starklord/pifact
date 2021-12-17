package ts.com.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import sistema.facturador.persistence.Documento;
import sistema.facturador.service.ComunesService;
import ts.com.server.facturador.dao.DocumentoDao;
import ts.com.service.factory.Services;
import ts.com.service.model.efact.Parametro;

@ManagedBean
public class ComunesServiceImpl implements ComunesService {


    @Inject
    private DocumentoDao documentoDao;

    public ComunesServiceImpl() {
        this.documentoDao = new DocumentoDao();
    }

    private static final Log log = LogFactory.getLog(ComunesServiceImpl.class);

    public String obtenerRutaTrabajo(String rutaTrabajoBusqueda) {
        log.debug("ComunesServiceImpl.obtenerRutaTrabajo...Inicio");
        StringBuilder rutaDirectorio = new StringBuilder();
        rutaDirectorio.setLength(0);
        String id_para = "PARASIST";
        String cod_para = "RUTSOL";
        List<Parametro> parametroRuta = Services.getParametro().consultarParametroById(id_para,cod_para);
        if (parametroRuta.size() > 0) {
            rutaDirectorio.append(((Parametro) parametroRuta.get(0)).val_para);
        } else {
            rutaDirectorio.append("");
        }
        log.debug("ComunesServiceImpl.obtenerRutaTrabajo...Ruta Base: " + rutaDirectorio.toString());
        if ("TEMP".equals(rutaTrabajoBusqueda)) {
            rutaDirectorio.append("/sunat_archivos/sfs/TEMP/");
        }
        if ("PARS".equals(rutaTrabajoBusqueda)) {
            rutaDirectorio.append("/sunat_archivos/sfs/PARSE/");
        }
        if ("DATA".equals(rutaTrabajoBusqueda)) {
            rutaDirectorio.append("/sunat_archivos/sfs/DATA/");
        }
        if ("CERT".equals(rutaTrabajoBusqueda)) {
            rutaDirectorio.append("/sunat_archivos/sfs/CERT/");
        }
        if ("ENVI".equals(rutaTrabajoBusqueda)) {
            rutaDirectorio.append("/sunat_archivos/sfs/ENVIO/");
        }
        if ("RPTA".equals(rutaTrabajoBusqueda)) {
            rutaDirectorio.append("/sunat_archivos/sfs/RPTA/");
        }
        if ("FIRM".equals(rutaTrabajoBusqueda)) {
            rutaDirectorio.append("/sunat_archivos/sfs/FIRMA/");
        }
        if ("REPO".equals(rutaTrabajoBusqueda)) {
            rutaDirectorio.append("/sunat_archivos/sfs/REPO/");
        }
        if ("VALI".equals(rutaTrabajoBusqueda)) {
            rutaDirectorio.append("/sunat_archivos/sfs/VALI/");
        }
        if ("ORID".equals(rutaTrabajoBusqueda)) {
            rutaDirectorio.append("/sunat_archivos/sfs/ORIDAT/");
        }
        if ("ALMC".equals(rutaTrabajoBusqueda)) {
            rutaDirectorio.append("/sunat_archivos/sfs/ALMCERT/");
        }
        log.debug("ComunesServiceImpl.obtenerRutaTrabajo...Ruta Completa: " + rutaDirectorio.toString());
        File farchivo = new File(rutaDirectorio.toString());
        if (!farchivo.exists() || !farchivo.isDirectory()) {
            farchivo.mkdirs();
            farchivo.mkdir();
        }
        log.debug("ComunesServiceImpl.obtenerRutaTrabajo...Final");
        return rutaDirectorio.toString();
    }

    public void validarConexion(String direccion, Integer puerto) {
        if (!tieneConexionInternet(direccion, puerto).booleanValue()) {
            throw new RuntimeException("No tienen Acceso a Internet.");
        }
    }

    public Boolean validarVersionFacturador(String versionFacturador) {
        log.debug("ComunesServiceImpl.validarVersionFacturador...Inicio");
        Boolean resultado = Boolean.valueOf(true);
        return resultado;
    }

    public Boolean actualizarVersionFacturador(String versionFacturador) {
        Boolean resultado = Boolean.valueOf(true);
        String nombreArchivo = "constantes.properties";
        resultado = validarVersionFacturador(versionFacturador);
        if (!resultado.booleanValue()) {
            Properties prop = new Properties();
            InputStream input = null;
            String rutaArchivoProperties = obtenerRutaTrabajo("VALI") + nombreArchivo;
            try {
                input = new FileInputStream(rutaArchivoProperties);
                prop.load(input);
                input.close();
            } catch (FileNotFoundException e1) {
                throw new RuntimeException("No se encontro el archivo Properties: " + rutaArchivoProperties, e1);
            } catch (IOException e) {
                throw new RuntimeException("No se encontro el archivo Properties: " + rutaArchivoProperties, e);
            }
            String origenHttp = (prop.getProperty("RUTA_HTTP_UPD") != null) ? prop.getProperty("RUTA_HTTP_UPD") : "XX";
            String rutaDestino = obtenerRutaTrabajo("VALI") + "sfsupdate.xml";
            String rutaBase = System.getenv("SUNAT_HOME");
            if (rutaBase == null || "".equals(rutaBase)) {
                throw new RuntimeException("No existe la varible de entorno SUNAT_HOME");
            }
            String rutaLibrerias = rutaBase + "/servers/sfs/lib/ext";
            String rutaFuentes = rutaBase + "/servers/sfs/webapps";
            String rutaComponentes = rutaBase + "/servers/sfs/webapps/a/js/swfacturador";
            try {
                log.debug("Copiando la URL a File");
                FileUtils.copyURLToFile(new URL(origenHttp), new File(rutaDestino));
                log.debug("Buscando Tag de Librerias");
                List<String> librerias = leerEtiquetaListaArchivoXml(rutaDestino, "filesLibraries");
                for (String ruta : librerias) {
                    String[] tokens = ruta.split("\\/");
                    Integer tamano = Integer.valueOf(tokens.length);
                    if (tamano.intValue() > 0) {
                        String fileName = tokens[tamano.intValue() - 1];
                        log.debug("Copiando Archivo Librerias: " + fileName);
                        FileUtils.copyURLToFile(new URL(ruta), new File(rutaLibrerias, fileName));
                    }
                }
                log.debug("Buscando Tag de Componentes Web");
                List<String> componentes = leerEtiquetaListaArchivoXml(rutaDestino, "filesWebComponents");
                for (String ruta : componentes) {
                    String[] tokens = ruta.split("\\/");
                    Integer tamano = Integer.valueOf(tokens.length);
                    if (tamano.intValue() > 0) {
                        String fileName = tokens[tamano.intValue() - 1];
                        log.debug("Copiando Archivo Componentes: " + fileName);
                        FileUtils.copyURLToFile(new URL(ruta), new File(rutaComponentes, fileName));
                    }
                }
                log.debug("Buscando Tag de Fuentes");
                List<String> fuentes = leerEtiquetaListaArchivoXml(rutaDestino, "filesSources");
                for (String ruta : fuentes) {
                    String[] tokens = ruta.split("\\/");
                    Integer tamano = Integer.valueOf(tokens.length);
                    if (tamano.intValue() > 0) {
                        String fileName = tokens[tamano.intValue() - 1];
                        log.debug("Copiando Archivo Fuentes: " + fileName);
                        FileUtils.copyURLToFile(new URL(ruta), new File(rutaFuentes, fileName));
                    }
                }
            } catch (Exception e) {
                log.error("No se pudo descargar la version actualizada desde internet: " + e.getMessage());
            }
        }
        return resultado;
    }

    public String leerEtiquetaArchivoXml(String rutaArchivo, String nombreEtiqueta) {
        File file = new File(rutaArchivo);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            Reader reader = new InputStreamReader(inputStream, "ISO8859_1");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(new InputSource(reader));
            document.getDocumentElement().normalize();
            log.debug("Elemento Ra:" + document.getDocumentElement().getNodeName());
            log.debug("Elemento :" + document.getElementsByTagName(nombreEtiqueta));
            log.debug("Valor :" + document.getElementsByTagName(nombreEtiqueta).item(0).getTextContent());
            String informacion = document.getElementsByTagName(nombreEtiqueta).item(0).getTextContent();
            return informacion;
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("No se pudo procesar el archivo: " + rutaArchivo, e);
        } catch (IOException e) {
            throw new IllegalArgumentException("No se pudo procesar el archivo: " + rutaArchivo, e);
        } catch (ParserConfigurationException e) {
            throw new IllegalArgumentException("No se pudo procesar el archivo: " + rutaArchivo, e);
        } catch (SAXException e) {
            throw new IllegalArgumentException("No se pudo procesar el archivo: " + rutaArchivo, e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException iOException) {
            }
        }
    }

    public List<String> leerEtiquetaListaArchivoXml(String rutaArchivo, String nombreEtiqueta) {
        File file = new File(rutaArchivo);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            Reader reader = new InputStreamReader(inputStream, "ISO8859_1");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(new InputSource(reader));
            String contenido = "", elementoSub = "";
            List<String> listaRutas = new ArrayList<>();
            document.getDocumentElement().normalize();
            log.debug("Elemento Ra:" + document.getDocumentElement().getNodeName());
            NodeList nList = document.getElementsByTagName(nombreEtiqueta);
            if ("filesSources".equals(nombreEtiqueta)) {
                elementoSub = "fileSource";
            }
            if ("filesWebComponents".equals(nombreEtiqueta)) {
                elementoSub = "fileWebComponent";
            }
            if ("filesLibraries".equals(nombreEtiqueta)) {
                elementoSub = "fileLibrary";
            }
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                log.debug("Elemento Actual :" + nNode.getNodeName());
                if (nNode.getNodeType() == 1) {
                    Element eElement = (Element) nNode;
                    contenido = eElement.getElementsByTagName(elementoSub).item(0).getTextContent();
                    listaRutas.add(contenido);
                }
            }
            return listaRutas;
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("NO se puede leer etiqueta: archivo: " + rutaArchivo + ", etiqueta: " + nombreEtiqueta, e);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("NO se puede leer etiqueta: archivo: " + rutaArchivo + ", etiqueta: " + nombreEtiqueta, e);
        } catch (ParserConfigurationException e) {
            throw new IllegalArgumentException("NO se puede leer etiqueta: archivo: " + rutaArchivo + ", etiqueta: " + nombreEtiqueta, e);
        } catch (SAXException e) {
            throw new IllegalArgumentException("NO se puede leer etiqueta: archivo: " + rutaArchivo + ", etiqueta: " + nombreEtiqueta, e);
        } catch (IOException e) {
            throw new IllegalArgumentException("NO se puede leer etiqueta: archivo: " + rutaArchivo + ", etiqueta: " + nombreEtiqueta, e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException iOException) {
            }
        }
    }

    public Properties getProperties(String nombreArchivo) {
        Properties prop = new Properties();
        InputStream input = null;
        String rutaArchivoProperties = obtenerRutaTrabajo("VALI") + nombreArchivo;
        try {
            input = new FileInputStream(rutaArchivoProperties);
            prop.load(input);
            input.close();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("No se pudo procesar el archivo de propiedades", e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    public Documento consultarBandejaPorNomArch(Documento documento) {
        Documento retorno = null;
        List<Documento> lista = this.documentoDao.consultarBandejaPorNomArch(documento);
        if (lista.size() > 0) {
            retorno = lista.get(0);
        }
        return retorno;
    }

    public Parametro obtenerParametro(Parametro parametro) {
        Parametro retorno = null;
        List<Parametro> lista = Services.getParametro().consultarParametroById(parametro.id_para,parametro.cod_para);
        if (lista.size() > 0) {
            retorno = lista.get(0);
        }
        return retorno;
    }

    private Boolean tieneConexionInternet(String direccion, Integer puerto) {
        Boolean retorno = Boolean.valueOf(false);
        try {
            Socket socket = new Socket(direccion, puerto.intValue());
            if (socket.isConnected()) {
                socket.close();
            }
            retorno = Boolean.valueOf(true);
        } catch (Exception e) {
            log.warn("Error en Conexion a Internet (verificar): " + direccion + ":" + puerto + "- Mensaje: " + e.getMessage());
            retorno = Boolean.valueOf(true);
        }
        return retorno;
    }

    @Override
    public sistema.facturador.persistence.Parametro obtenerParametro(sistema.facturador.persistence.Parametro prmtr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
