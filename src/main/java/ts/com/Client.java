/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.com;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author StarkLord
 */
public class Client {

    // codigos de productos para pensiones y matriculas
    public static final int LINEA_OTROS = 26;
    public static final int LINEA_SOLICITUDES = 25;
    public static final int LINEA_PENSIONES = 24;
    public static final int LINEA_MATRICULAS = 23;
    public static final String CODIGO_PENSION1 = "PENSION1";
    public static final String CODIGO_PENSION2 = "PENSION2";
    public static final String CODIGO_PENSION3 = "PENSION3";
    public static final String CODIGO_PENSION4 = "PENSION4";
    public static final String CODIGO_PENSION5 = "PENSION5";
    public static final String CODIGO_MATRICULA = "MATRICULA";

    public static final int ID_PENSION1 = 6511;
    public static final int ID_PENSION2 = 6512;
    public static final int ID_PENSION3 = 6513;
    public static final int ID_PENSION4 = 6514;
    public static final int ID_PENSION5 = 6515;
    public static final int ID_MATRICULA = 6509;

    public static String APP_NAME;
    public static String APP_TITLE;
    public static String APP_COLOR;
    public static String USUARIO = "root";
    public static BigDecimal factor_descuento = new BigDecimal(0.5);

    // para los tipos de persona
    public static final Character TIPO_PERSONA_ALUMNO = 'A';
    public static final Character TIPO_PERSONA_DOCENTE = 'D';
    public static final Character TIPO_PERSONA_ASISTENTE = 'T';
    public static final Character TIPO_PERSONA_SECRETARIA = 'S';
    public static final Character TIPO_PERSONA_ADMINISTRATIVO = 'M';
    public static final Character TIPO_PERSONA_PERSONA = 'P';
    public static final Character TIPO_PERSONA_HACK = 'H';

    // DOOM EVENTS
    public static String CLICK = "click";

    // para los documentos de identidad:
    public static int DOCUMENTO_IDENTIDAD_DNI = 1;
    public static int DOCUMENTO_IDENTIDAD_EXTRANGERIA = 4;
    public static int DOCUMENTO_IDENTIDAD_RUC = 6;
    public static int DOCUMENTO_IDENTIDAD_PASAPORTE = 7;

    // para los datos de la persona:
    public static char SEXO_MASCULINO_ID = 'M';
    public static char SEXO_FEMENINO_ID = 'F';

    // para los documentos de pago
    public static final int IMPUESTO_NO_AFECTO = 0;
    public static final int DOCUMENTO_TIPO_NOTA_PEDIDO = 0;
    public static final int DOCUMENTO_TIPO_FACTURA = 1;
    public static final int DOCUMENTO_TIPO_BOLETA = 3;
    public static final int DOCUMENTO_TIPO_GUIA = 9;
    public static final int DOCUMENTO_TIPO_RECIBO_TEMPORAL = 54;
    public static final int DOCUMENTO_TIPO_ORDEN_VENTA = 55;

    // para el igv 1: IGV, 2: EXO, 3: INA, 4: EXP
    public static final int TIPO_IMPUESTO_IGV = 1;
    public static final int TIPO_IMPUESTO_EXO = 2;
    public static final int TIPO_IMPUESTO_INA = 3;
    public static final int TIPO_IMPUESTO_EXP = 4;

    // para las VENTAS
    // formas de pago
    public static final String CREDITO = "Credito";
    public static final String CONTADO = "Contado";
    public static final int FP_EFECTIVO = 1;
    public static final int FP_TARJETA = 3;
    public static final int FP_CREDITO = 4;
    public static final int FP_MULTIPLE = 6;
    public static final int FP_CHEQUE = 7;
    public static final int FP_DEPOSITO = 8;
    public static final int FP_LETRAS = 9;

    // para los impuestos
    public static final int IMPUESTO_IGV_18 = 2;

    // para las monedas
    public static int MONEDA_SOLES_ID = 1;
    public static int MONEDA_DOLARES_ID = 2;

    // para los recibos
    public static final int DESTINO_COBRO_INTERNO = 1;
    public static final int DESTINO_COBRO_EXTERNO = 2;

    public static final char MOVIMIENTO_EGRESO = 'E';
    public static final char MOVIMIENTO_INGRESO = 'I';

    public static final char MOVIMIENTO_KARDEX_ENTRADA = 'E';
    public static final char MOVIMIENTO_KARDEX_SALIDA = 'S';

    public static final int RT_OTROS_INGRESOS = 0;
    public static final int RT_COBRO_VENTA = 2;
    public static final int RT_COBRO_DEPOSITO = 23;
    public static final int RT_COBRO_CHEQUE = 4;
    public static final int RT_COBRO_TARJETA = 3;
    public static final int RT_COBRO_EFECTIVO = 9;
    public static final int RT_OTROS_EGRESOS = 1;
    public static final int RT_PAGO_EFECTIVO = 5;
    public static final int RT_PAGO_DEPOSITO = 6;
    public static final int RT_PAGO_TARJETA = 7;
    public static final int RT_PAGO_CHEQUE = 8;

    // para las tarjetas
    public static final int TARJETA_VISA_SOLES = 1;
    public static final int TARJETA_MASTERCARD = 2;

    // para el porcentaje de utilidad por defecto
    public static final BigDecimal UTILIDAD = new BigDecimal("20");

    // unidades:
    public static String UNIDAD_KG = "KG";
    public static String UNIDAD_LT = "LT";
    public static String UNIDAD_M3 = "M3";
    public static String UNIDAD_MT = "MT";
    public static String UNIDAD_PZ = "PZ";
    public static String UNIDAD_UN = "UN";
    public static String UNIDAD_GA = "GA";// GALONES
    public static int UNIDAD_UN_ID = 0;

    // para las situaciones en el facturador sunat
    public static Map<String, String> MAP_EFACT_SITUACIONES = new HashMap<>();
    public static String COD_SITU_DESCARGAR_CDR_OBS = "12";
    public static String COD_SITU_DESCARGAR_CDR = "11";
    public static String ENVIADO_RECHAZADO = "10";
    public static String ENVIADO_PROCESANDO = "09";
    public static String ENVIADO_POR_PROCESAR = "08";
    public static String XML_VALIDAR = "07";
    public static String CON_ERRORES = "06";
    public static String ENVIADO_ANULADO = "05";
    public static String ENVIADO_ACEPTADO_OBSERVADO = "04";
    public static String ENVIADO_ACEPTADO = "03";
    public static String XML_GENERADO = "02";
    public static String POR_GENERAR_XML = "01";

    // para las personas
    public static final String TRATAMIENTO_SR = "Sr.";
    public static final String TRATAMIENTO_DR = "Dr.";

    public static final char CALIFICACION_LISTA_NEGRA = 'L';
    public static final char CALIFICACION_LEGAL = 'G';
    public static final char CALIFICACION_MOROSO = 'M';
    public static final char CALIFICACION_BUENA = 'B';

    public static final char ESTADO_MATRICULA_VIGENTE = 'V';
    public static final char ESTADO_MATRICULA_CONCLUIDA = 'C';
    public static final char ESTADO_MATRICULA_INCONCLUSA = 'I';

    public static final int TIPO_CLIENTE = 1;
    public static final int TIPO_PROVEEDOR = 2;
    public static final int TIPO_VENDEDOR = 3;
    public static final int TIPO_EMPLEADO = 4;
    public static final int TIPO_PLANILLA = 5;

    // para logistica
    public static final int MOTIVO_TRASLADO_VENTA = 1;

    // para las ventas
    public static BigDecimal CONVERTIR;

    // para los mantenimientos
    public static final char MANTENIMIENTO_CORRECTIVO = 'C';
    public static final char MANTENIMIENTO_PREVENTIVO = 'P';

    public static String EMPRESA = "";
    public static String SISTEMA = "EVA";
    public static String VERSION = "2.0.1";

    // para las ordenes de entrada salida
    public static char TIPO_ORDEN_COMPRA = 'C';
    public static char TIPO_ORDEN_VENTA = 'V';
    public static char TIPO_ORDEN_TRASLADO = 'T';
    public static char TIPO_ORDEN_REGULARIZACION = 'R';
    public static char TIPO_ORDEN_ENTRADA = 'E';
    public static char TIPO_ORDEN_SALIDA = 'S';

    //
    public static boolean IS_VENDEDOR;
    public static boolean IS_JEFE_VENTAS;
    public static boolean IS_ALMACENERO;
    public static boolean IS_JEFE_ALMACEN;
    public static boolean IS_ASISTENTE_LOGISTICA;
    public static boolean IS_JEFE_LOGISTICA;
    public static boolean IS_ASISTENTE_FINANZAS;
    public static boolean IS_JEFE_FINANZAS;
    public static boolean IS_ASISTENTE_ADMINISTRATIVO;
    public static boolean IS_JEFE_ADMINISTRACION;
    public static boolean IS_ASISTENTE_RRHH;
    public static boolean IS_JEFE_RRHH;
    public static boolean IS_MEDICO;
    public static boolean IS_ENFERMERO;

    // PARA LOS PERMISOS
    public static final int PER_ROOT = 0;
    public static final int PER_VENDEDOR = 1;
    public static final int PER_JEFE_VENTAS = 2;
    public static final int PER_ALMACENERO = 20;
    public static final int PER_JEFE_ALMACEN = 21;
    public static final int PER_ASISTENTE_LOGISTICA = 30;
    public static final int PER_JEFE_LOGISTICA = 31;
    public static final int PER_ASISTENTE_FINANZAS = 40;
    public static final int PER_JEFE_FINANZAS = 41;
    public static final int PER_ASISTENTE_ADMINISTRATIVO = 50;
    public static final int PER_JEFE_ADMINISTRACION = 51;
    public static final int PER_ASISTENTE_RRHH = 60;
    public static final int PER_JEFE_RRHH = 61;
    public static final int PER_MEDICO = 63;
    public static final int PER_ENFERMERO = 64;
    public static final int PER_ALUMNO = 70;
    public static final int PER_DOCENTE = 71;

    // objetos por defecto
    // utilitarios
    public static String OK = "OK";
    public static String SI = "SI";
    public static String NO = "NO";

    // ESTADOS DEL ALUMNO
    public static char ESTADO_RETIRO_ANULADO = 'R';
    public static char ESTADO_RETIRO_DEFINITIVO = 'D';
    public static char ESTADO_RETIRO_PERMISO = 'P';
    public static char ESTADO_RETIRO_TEMPORAL = 'T';
    public static char ESTADO_RETIRO_ = 'A';

    public static char ESTADO_SOLICITUD_APROBADO = 'A';
    public static char ESTADO_SOLICITUD_PENDIENTE = 'P';
    public static char ESTADO_SOLICITUD_APLICADO = 'L';

    public static char ESTADO_DECRETO_APROBADO = 'A';
    public static char ESTADO_DECRETO_PENDIENTE = 'P';
    public static char ESTADO_DECRETO_APLICADO = 'L';

    public static char TIPO_DECRETO_NORMAL = 'N';
    public static char TIPO_DECRETO_RESOLUCION = 'R';

    // ESTADOS DE LA MATRICULA
    public static char INGRESO_NORMAL = 'N';
    public static char INGRESO_REINGRESO = 'R';
    public static char INGRESO_DISTANCIA = 'D';
    public static char INGRESO_REPITENTE = 'E';

    // TIPO DE RETIRO
    public static String TIPO_RETIRO_RETIRADO = "RETIRADO";
    public static String TIPO_RETIRO_ACTIVADO = "ACTIVADO";

    // public static boolean tienePermiso(int permisoId) {
    // List<Permiso> permisos = (List<Permiso>)
    // UI.getCurrent().getSession().getAttribute("permisos");
    //
    // for(Permiso p:permisos){
    // if(p.id==permisoId){
    // return true;
    // }
    // }
    // return false;
    // }

    public static String getEstadoMatriculaSRT(Character estado) {
        if (estado == Client.ESTADO_MATRICULA_CONCLUIDA) {
            return "CONCLUIDO";
        }
        if (estado == Client.ESTADO_MATRICULA_INCONCLUSA) {
            return "INCONCLUSA";
        }
        return "VIGENTE";
    }

    public static String getTipoMatricula(Character tipoMatricula) {
        if (tipoMatricula == Client.INGRESO_REINGRESO) {
            return "REINGRESO";
        }
        if (tipoMatricula == Client.INGRESO_REPITENTE) {
            return "REPITENTE";
        }
        if (tipoMatricula == Client.INGRESO_DISTANCIA) {
            return "DISTANCIA";
        }
        return "NORMAL";
    }

    public static String getTipoDecretoSRT(Character tipoDecreto) {
        if (tipoDecreto == Client.TIPO_DECRETO_RESOLUCION) {
            return "RESOLUCION";
        }
        return "DECRETO";
    }

    public static Character setTipoDecreto(String tipoDecreto) {
        if (tipoDecreto == "RESOLUCION") {
            return Client.TIPO_DECRETO_RESOLUCION;
        }
        return Client.TIPO_DECRETO_NORMAL;
    }

    // CONVERTIR FECHAS
    // Enriquez
    public static LocalDate ConvertToLocalDate(Date date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localdate = LocalDate.parse(date.toString(), formatter);
        return localdate;
    }

    public static Date ConvertToDate(LocalDate localdate) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date date = Date.from(localdate.atStartOfDay(defaultZoneId).toInstant());
        return date;
    }

    // restar sumar meses
    public static Date sumarRestarMeses(Date fecha, int meses) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.MONTH, meses);
        return calendar.getTime();
    }

}
