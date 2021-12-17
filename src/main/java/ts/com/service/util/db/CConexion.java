/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.com.service.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author etorres
 */

/**
 *
 * @author etorres
 */
public class CConexion { 
    
    private static int CONNECTIONS_COUNT = 0;

    public static String strUrl = null;
    public static String strDriver=null;
    public static String strUsr = null;
    public static String strPwd = null;

    Connection cnConexion = null;
    private static CConexion INSTANCE = null;
    private static boolean isTransaction = false;
    private static int intentos = 0;

    public CConexion() throws SQLException, ClassNotFoundException, NullPointerException, Exception {
        String url = "jdbc:postgresql://localhost/test";
        Properties props = new Properties();
        props.setProperty("user", strUsr);
        props.setProperty("password", strPwd);
        props.setProperty("connectTimeout", "10");
        
        
        System.out.println(this.strUrl);
            Class.forName(strDriver);
            intentos++;
            CONNECTIONS_COUNT++;
            cnConexion = DriverManager.getConnection(strUrl,props);
    }

    public CConexion(String url) throws Exception {
        this.strUrl = url;
        try {
            Class.forName(strDriver);
            cnConexion = DriverManager.getConnection(strUrl, strUsr, strPwd);
        } catch (ClassNotFoundException e) {
            cnConexion = null;
            e.printStackTrace();
            throw new Exception("Fallo la conexion al servidor");
        }
    }

    public Connection getConnection() {
        return cnConexion;
    }

    public void closeConnection() {
        if(INSTANCE != null) {
            if (INSTANCE.cnConexion != null) {
                try {
                    INSTANCE.cnConexion.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void killConnection() {
        if(INSTANCE != null) {
            if (INSTANCE.cnConexion != null) {
                try {
                    //INSTANCE.rollbackTransaction();
                    INSTANCE.cnConexion.close();
                    INSTANCE = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void execute(String sSql) throws Exception {
        if (cnConexion == null) {
            throw new SQLException("Se perdio la conexion.");
        }
        Statement stmt = null;
        stmt = cnConexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE);
        stmt.execute(sSql);
    }

    public ResultSet select(String sSql) throws Exception {
        if (cnConexion == null) {
            throw new SQLException("No hay conexion con el servidor.");
        }
        Statement stmt = null;
        ResultSet rs = null;
        stmt = cnConexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE);
        rs = stmt.executeQuery(sSql);
        return rs;
    }

    public int update(String sSql) throws Exception {
        if (cnConexion == null)
            throw new SQLException("Se perdio la conexion.");
        int nro = 0;
        Statement stmt = null;
        stmt = cnConexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE);
        nro = stmt.executeUpdate(sSql);stmt.close();
        return nro;
    }

    public void startTransaction() throws Exception {        
        execute("BEGIN WORK;");
        isTransaction = true;
    }

    public void rollbackTransaction() throws Exception {        
        execute("ROLLBACK WORK;");
        isTransaction = false;
    }

    public void commitTransaction() throws Exception {
        execute("COMMIT WORK;");
        isTransaction = false;
    }
    
    @Override
    public String toString() {
        return this.strUrl;
    }
/*
    private static synchronized void createInstance() throws SQLException,Exception{
        if(INSTANCE == null){            
            INSTANCE = new CConexion();
        }
    }*/

    public static CConexion getInstance() throws SQLException,Exception {
        
        try{
            if(INSTANCE ==null){
                INSTANCE = new CConexion();
                System.out.println("Creando nueva conexion : " + CONNECTIONS_COUNT);
            }
            if(INSTANCE.getConnection() ==null){
                INSTANCE = new CConexion();
                System.out.println("Creando nueva conexion : " + CONNECTIONS_COUNT);
            }
            
            if(INSTANCE.getConnection().isClosed()){
                INSTANCE = new CConexion();
                System.out.println("Creando nueva conexion por cerrarse la anterior :" + CONNECTIONS_COUNT);
            }
            return INSTANCE;
            
            
        }catch(Exception ex){
            ex.printStackTrace();
            isTransaction = false;
            throw new Exception("Operacion fallida. vuelva a intentarlo") ;
        }

    }

	public static void setINSTANCE(CConexion conexion) {
		INSTANCE = conexion;
	}    
}
