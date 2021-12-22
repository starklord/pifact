package ts.com.server;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.w3c.dom.Document;

import ts.com.service.util.db.CConexion;

public class Server {

    public static String DB_URL;
    public static String DB_DRIVER;
    public static String DB_USR;
    public static String DB_PWD;

    private static HttpClient HTTP_CLIENT;

    private static DocumentBuilderFactory DBF;

    public final static int PLAZO_BOLETA = 5;

    /**
     * converts the given width(pixels) into width of that table relative to the
     * given width
     *
     * @param width
     * @return
     */
    public static int getCellWidth(int width) {
        return width * (35);
    }

    public static Document getDocument(String fileName) throws Exception {
        if (Server.DBF == null) {
            Server.DBF = DocumentBuilderFactory.newInstance();
        }
        DocumentBuilder db = Server.DBF.newDocumentBuilder();
        return db.parse(new File(fileName));
    }

    public static HttpClient getHttpClient() {
        if (Server.HTTP_CLIENT == null) {
            Server.HTTP_CLIENT = HttpClientBuilder.create().build();
        }
        return Server.HTTP_CLIENT;
    }

    public static String quitaEspacios(String texto) {
        java.util.StringTokenizer tokens = new java.util.StringTokenizer(texto);
        StringBuilder buff = new StringBuilder();
        while (tokens.hasMoreTokens()) {
            buff.append(" ").append(tokens.nextToken());
        }
        return buff.toString().trim();
    }

    public static void writeFile(String filePath, String content) throws Exception {
        System.out.println("grabando en: " + filePath);
        System.out.println("contenido: " + content);
        FileWriter fw = new FileWriter(filePath);
        PrintWriter pw = new PrintWriter(fw);
        pw.print(content);
        fw.close();

    }

    public static void initDB() {
        System.out.println("inicializando datos de la base de datos...");

        String ip = "154.53.32.33";
        // String ip = "localhost";
        String port = "7077";
        String db_name = "pifactdb";
        Server.DB_PWD = "evadb7007";
        // ------------------------------------------------
        Server.DB_DRIVER = "org.postgresql.Driver";
        Server.DB_USR = "postgres";
        Server.DB_URL = "jdbc:postgresql://" + ip + ":" + port + "/" + db_name;

        CConexion.strDriver = Server.DB_DRIVER;
        CConexion.strPwd = Server.DB_PWD;
        CConexion.strUrl = Server.DB_URL;
        CConexion.strUsr = Server.DB_USR;
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

}
