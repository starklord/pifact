package ts.com.service.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import org.apache.commons.io.FileUtils;

public class Util {

    public static int waitCursorCount = 0;
    // public static DialogBox dbxWait;

    public static final String WIDTH_FULL = "widthFull";

    public static String OK = "OK";
    public static String SI = "SI";
    public static String NO = "NO";
    public static String SIN_ELEGIR = "SIN ELEGIR";
    public static String PENDIENTE = "PENDIENTE";
    public static String NUESTRO = "Nuestro";
    public static String PARTICULAR = "Particular";

    public static final String SISTEMA_PRODUCCION = "Produccion";
    public static final String SISTEMA_VENTAS = "Ventas";
    public static final String SISTEMA_ADMINISTRACION = "Administracion";
    public static final String SISTEMA_LOGISTICA = "Logistica";

    public static String EMPRESA_GRUPO_UPGRADE = "GRUPO UPGRADE S.A.C.";
    public static String EMPRESA_GRUPO_NOTICIAS = "GRUPO NOTICIAS S.A.C.";
    public static String PRODUCTION = "SISTEMA DE PRODUCCION";
    public static String ADMIN = "SISTEMA ADMINISTRATIVO";
    public static String LOGISTIC = "SISTEMA LOGISTICA";
    public static String CORRECTIONS = "SISTEMA DE CORRECCIONES";
    public static String VERSION_CORRECTIONS = "v. 2.08.06";
    public static String VERSION_VENTAS_NOTICIAS = "v. 1.0.0.1";
    public static String VERSION_VENTAS_UPGRADE = "v. 1.0.0.1";
    public static String VERSION_LOGISTIC = "v. 5.03.23";
    public static String VERSION_PRODUCTION = "v. 6.06.13";

    // para el tratamiento de los errores:
    public static String NO_HA_INGRESADO = "NO HA INGRESADO UN DATO: ";
    public static String FORMATO_INCORRECTO = "FORMATO INCORRECTO DE DATO: ";
    public static String CAMPO_REQUERIDO = "Campo requerido";

    public static BigDecimal FACTOR_IGV = new BigDecimal("1.18");
    public static BigDecimal IGV = new BigDecimal("18");

    // para los datos de los pdfs a imprimir
    public static String PDF_TO_USE = "reportes_sistemas/";
    public static String PDF_CONSTANCIA = "reportes_sistemas/constancia_";
    public static String PDF_DOC_PAGO = "reportes_sistemas/docpago_";

    public final static int HALIGN_LEFT = 1;
    public final static int HORIZONTAL_ALIGN_TO_MIDDLE = 2;
    public final static int HALIGN_RIGHT = 3;

    public final static int VERTICAL_ALIGN_TO_TOP = 4;
    public final static int VALIGN_MIDDLE = 5;
    public final static int VERTICAL_ALIGN_TO_BOTTOM = 6;

    public static String PREDEFINED_BUTTON_SI = "Si";
    public static String PREDEFINED_BUTTON_NO = "No";

    // para las busquedas
    public static String BUSQUEDA_NOMBRES = "Nombres";
    public static String BUSQUEDA_RUCDNI = "RUC / DNI";

    public static int BUSCAR_PRODUCTO_CODIGO = 1;
    public static int BUSCAR_PRODUCTO_DESCRIPCION = 2;
    public static int BUSCAR_PRODUCTO_SERIE = 3;

    public static final SimpleDateFormat SDF_HOURS = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat SDF_DATE_HOURS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat SDF_DATE_HOURS_SQLTE = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static int PXH_DIF = 35;
    public static int PXW_DIF = 200;

    // para los datos de las personas
    // para los handlers
    // public static List<ResizeWindowHandler> RESIZERS = new ArrayList<>();
    public static float round2(float num) {
        float result = num * 100;
        result = Math.round(result);
        result = result / 100;
        return result;
    }

    public static float round4(float num) {
        float result = num * 10000;
        result = Math.round(result);
        result = result / 10000;
        return result;
    }

    /**
     *
     * @param date1 first date
     * @param date2 second date
     * @return 1,0,-1 if date1 is >, ==,or less than date2
     */
    public static int compareDates(Date date1, Date date2) {
        int year1 = date1.getYear() + 1900;
        int month1 = date1.getMonth();
        int day1 = date1.getDate();
        int year2 = date2.getYear() + 1900;
        int month2 = date2.getMonth();
        int day2 = date2.getDate();

        if (year2 < year1) {
            return 1;
        }
        if (year2 == year1 && month2 < month1) {
            return 1;
        }
        if (year2 == year1 && month2 == month1 && day2 < day1) {
            return 1;
        }
        if (year2 == year1 && month2 == month1 && day2 == day1) {
            return 0;
        }
        return -1;
    }

    /**
     * complete the given string with zeroes at init
     *
     * @param text
     * @param length
     * @return
     */
    public static String completeWithZeros(String text, int length) {
        int size = text.length();
        int dif = length - size;
        String zeroes = "";
        for (int i = 0; i < dif; i++) {
            zeroes += "0";
        }
        return zeroes + text;
    }

    public static float calcGlnToTn(float gln) {
        return (float) (gln * 4.32 / 1000);
    }

    public static float calcKgToGln(float kg) {
        return (float) (kg / 4.32);
    }

    public static boolean isPar(int n) {
        return n % 2 == 0;
    }

    // public static String formatDate(Date date) {
    // return DateTimeFormat.getFormat("dd-MM-yyyy").format(date);
    // }
    //
    public static String formatDateDMY(Date date) {
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static String formatDateToHourAndMinutes(Date date) {
        if (date == null) {
            return "--:--";
        }
        int hours = date.getHours();
        int minutes = date.getMinutes();
        String strHours = String.valueOf(hours);
        String strMinutes = String.valueOf(minutes);

        if (hours >= 0 && hours <= 9) {
            strHours = "0" + hours;
        }

        if (minutes >= 0 && minutes <= 9) {
            strMinutes = "0" + minutes;
        }

        return strHours + ":" + strMinutes;
    }

    public static int compareObjects(Object o1, Object o2) {
        if (o1 == null || o2 == null) {
            return 0;
        }
        if (o1.getClass().equals(Integer.class)) {
            return ((Integer) o1).compareTo((Integer) o2);
        }
        if (o1.getClass().equals(Date.class)) {
            return ((Date) o1).compareTo((Date) o2);
        }
        if (o1.getClass().equals(BigDecimal.class)) {
            return ((BigDecimal) o1).compareTo((BigDecimal) o2);
        }
        if (o1.getClass().equals(String.class)) {
            return ((String) o1).compareTo((String) o2);
        }
        if (o1.getClass().equals(Float.class)) {
            return ((Float) o1).compareTo((Float) o2);
        }
        if (o1.getClass().equals(Character.class)) {
            return ((Character) o1).compareTo((Character) o2);
        }
        if (o1.getClass().equals(Boolean.class)) {
            return ((Boolean) o1).compareTo((Boolean) o2);
        }
        return 0;
    }
    // // encrypts one string into a code

    public static String encrypt(String raw) {
        String result = "";
        int letra;
        int valor = 0;
        int newvalor = 0;
        char let;
        for (int i = 0; i < raw.length(); i++) {
            let = raw.charAt(i);
            letra = stringToAscii(let);
            valor = valor + letra;
        }

        valor = (valor % 31);
        if (valor == 0) {
            valor = 19;
        }
        for (int i = 0; i < raw.length(); i++) {
            let = raw.charAt(i);
            letra = stringToAscii(let);
            if (i == 0) {
                newvalor = letra + valor;
            } else if ((i % 2) == 0) {
                newvalor = letra + valor;
            } else if ((i % 2) != 0) {
                newvalor = letra - valor;
            }
            valor--;
            result += Integer.toString(newvalor, 16);
        }
        return result;
    }
    //
    // public static String convertNumberToLetters(double number) {
    // return NumberToLetterConverter.convertNumberToLetter(number);
    // }

    // convertir una letra en su valor asccii
    private static int stringToAscii(char string) {
        int ascii = string;
        return ascii;
    }

    public static String getNumero(String cadena) {
        char[] cadena_char = cadena.toCharArray();
        String cadena_numero = "";
        for (int i = 0; i < cadena_char.length; i++) {
            // if(cadena_char[i]>='0' && cadena_char[i]<='9')
            if (Character.isDigit(cadena_char[i])) {
                cadena_numero += cadena_char[i];
            }
        }
        return cadena_numero;
    }

    public static String getCadena(String cadena) {
        char[] cadena_char = cadena.toCharArray();
        String cadena_numero = "";
        for (int i = 0; i < cadena_char.length; i++) {
            if (!Character.isDigit(cadena_char[i])) {
                cadena_numero += cadena_char[i];
            }
        }
        return cadena_numero.trim();
    }

    public static Date addDays(Date date, int days) {
        final long MILLSECS_PER_DAYS = days * 24 * 60 * 60 * 1000;
        long dateTime = date.getTime() + MILLSECS_PER_DAYS;
        Date dateEnd = new Date();
        dateEnd.setTime(dateTime);
        return dateEnd;
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Time asTime(LocalTime localTime) {
        return Time.valueOf(localTime);
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalTime asLocalTime(Time time) {
        return time.toLocalTime();
    }

    public static LocalTime asLocalTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalTime();
    }
    //
    // public static Date subtractDays(Date date,int days) {
    // GregorianCalendar cal = new GregorianCalendar();
    // cal.setTime(date);
    // cal.add(Calendar.DATE, -days);
    // return cal.getTime();
    // }

    public static int daysBetweenDates(Date inicio, Date fin) {
        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
        long startTime = inicio.getTime();
        long endTime = fin.getTime();
        long diffTime = endTime - startTime;
        long diffDays = diffTime / MILLSECS_PER_DAY;

        return (int) diffDays;
    }

    public static void deleteFilesOnDir(String path, String coincidence) {
        File dir = new File(path);
        File[] files = dir.listFiles();
        if (coincidence != null) {
            files = dir.listFiles((FilenameFilter) (d, name) -> name.contains(coincidence));

        }
        for (File file : files) {
            file.delete();
        }

    }

    public static void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation) throws IOException {
        File sourceDirectory = new File(sourceDirectoryLocation);
        File destinationDirectory = new File(destinationDirectoryLocation);
        FileUtils.copyDirectory(sourceDirectory, destinationDirectory);
    }

}
