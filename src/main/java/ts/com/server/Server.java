package ts.com.server;


import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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
        if(Server.DBF==null){
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

        String ip     = "154.53.32.33";
        // String ip      = "localhost";
        String port     = "7077";
        String db_name  = "copacabanadb";
        Server.DB_PWD   = "evadb7007";
        //------------------------------------------------
        Server.DB_DRIVER = "org.postgresql.Driver";
        Server.DB_USR = "postgres";
        Server.DB_URL = "jdbc:postgresql://" + ip + ":" + port + "/" + db_name;

        CConexion.strDriver = Server.DB_DRIVER;
        CConexion.strPwd = Server.DB_PWD;
        CConexion.strUrl = Server.DB_URL;
        CConexion.strUsr = Server.DB_USR;
    }
    

}
