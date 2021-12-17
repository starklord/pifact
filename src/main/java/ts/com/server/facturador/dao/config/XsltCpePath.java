package ts.com.server.facturador.dao.config;

public class XsltCpePath extends sistema.facturador.config.XsltCpePath{

    private String factura = "commons/xsl/validation/2.X/ValidaExprRegFactura-2.0.1.xsl";
    private String boleta = "commons/xsl/validation/2.X/ValidaExprRegBoleta-2.0.1.xsl";
    private String notaCredito = "commons/xsl/validation/2.X/ValidaExprRegNC-2.0.1.xsl";
    private String notaDebito = "commons/xsl/validation/2.X/ValidaExprRegND-2.0.1.xsl";
    private String resumenBoleta ="commons/xsl/validation/1.X/ValidaExprRegSummary-1.1.0.xsl";
    private String resumenAnulado="commons/xsl/validation/1.X/ValidaExprRegSummaryVoided-1.0.1.xsl";
    private String retencion;
    private String percepcion;
    private String guia;
    private String resumenReversion="commons/xsl/validation/1.X/ValidaExprRegOtrosVoided-1.0.1.xsl";
    private String servicioPublico;
    private String facturaXsd="commons/xsd/2.1/maindoc/UBL-Invoice-2.1.xsd";
    private String boletaXsd="commons/xsd/2.1/maindoc/UBL-Invoice-2.1.xsd";
    private String notaCreditoXsd="commons/xsd/2.1/maindoc/UBL-CreditNote-2.1.xsd";
    private String notaDebitoXsd="commons/xsd/2.1/maindoc/UBL-DebitNote-2.1.xsd";
    private String resumenBoletaXsd="commons/xsd/2.0/main/UBLPE-SummaryDocuments-1.0.xsd";
    private String resumenAnuladoXsd="commons/xsd/2.0/main/UBLPE-VoidedDocuments-1.0.xsd";
    private String retencionXsd;
    private String percepcionXsd;
    private String guiaXsd;
    private String resumenReversionXsd;

    public String getFactura() {
        return this.factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getBoleta() {
        return this.boleta;
    }

    public void setBoleta(String boleta) {
        this.boleta = boleta;
    }

    public String getNotaCredito() {
        return this.notaCredito;
    }

    public void setNotaCredito(String notaCredito) {
        this.notaCredito = notaCredito;
    }

    public String getNotaDebito() {
        return this.notaDebito;
    }

    public void setNotaDebito(String notaDebito) {
        this.notaDebito = notaDebito;
    }

    public String getResumenBoleta() {
        return this.resumenBoleta;
    }

    public void setResumenBoleta(String resumenBoleta) {
        this.resumenBoleta = resumenBoleta;
    }

    public String getResumenAnulado() {
        return this.resumenAnulado;
    }

    public void setResumenAnulado(String resumenAnulado) {
        this.resumenAnulado = resumenAnulado;
    }

    public String getRetencion() {
        return this.retencion;
    }

    public void setRetencion(String retencion) {
        this.retencion = retencion;
    }

    public String getPercepcion() {
        return this.percepcion;
    }

    public void setPercepcion(String percepcion) {
        this.percepcion = percepcion;
    }

    public String getGuia() {
        return this.guia;
    }

    public void setGuia(String guia) {
        this.guia = guia;
    }

    public String getResumenReversion() {
        return this.resumenReversion;
    }

    public void setResumenReversion(String resumenReversion) {
        this.resumenReversion = resumenReversion;
    }

    public String getServicioPublico() {
        return this.servicioPublico;
    }

    public void setServicioPublico(String servicioPublico) {
        this.servicioPublico = servicioPublico;
    }

    public String getFacturaXsd() {
        return this.facturaXsd;
    }

    public void setFacturaXsd(String facturaXsd) {
        this.facturaXsd = facturaXsd;
    }

    public String getBoletaXsd() {
        return this.boletaXsd;
    }

    public void setBoletaXsd(String boletaXsd) {
        this.boletaXsd = boletaXsd;
    }

    public String getNotaCreditoXsd() {
        return this.notaCreditoXsd;
    }

    public void setNotaCreditoXsd(String notaCreditoXsd) {
        this.notaCreditoXsd = notaCreditoXsd;
    }

    public String getNotaDebitoXsd() {
        return this.notaDebitoXsd;
    }

    public void setNotaDebitoXsd(String notaDebitoXsd) {
        this.notaDebitoXsd = notaDebitoXsd;
    }

    public String getResumenBoletaXsd() {
        return this.resumenBoletaXsd;
    }

    public void setResumenBoletaXsd(String resumenBoletaXsd) {
        this.resumenBoletaXsd = resumenBoletaXsd;
    }

    public String getResumenAnuladoXsd() {
        return this.resumenAnuladoXsd;
    }

    public void setResumenAnuladoXsd(String resumenAnuladoXsd) {
        this.resumenAnuladoXsd = resumenAnuladoXsd;
    }

    public String getRetencionXsd() {
        return this.retencionXsd;
    }

    public void setRetencionXsd(String retencionXsd) {
        this.retencionXsd = retencionXsd;
    }

    public String getPercepcionXsd() {
        return this.percepcionXsd;
    }

    public void setPercepcionXsd(String percepcionXsd) {
        this.percepcionXsd = percepcionXsd;
    }

    public String getGuiaXsd() {
        return this.guiaXsd;
    }

    public void setGuiaXsd(String guiaXsd) {
        this.guiaXsd = guiaXsd;
    }

    public String getResumenReversionXsd() {
        return this.resumenReversionXsd;
    }

    public void setResumenReversionXsd(String resumenReversionXsd) {
        this.resumenReversionXsd = resumenReversionXsd;
    }
}
