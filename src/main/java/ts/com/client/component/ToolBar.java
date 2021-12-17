package ts.com.client.component;

import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class ToolBar extends HorizontalLayout {

    //accessible
    public Button btnChoose = new Button("", VaadinIcon.CHECK, "Elegir")
            .setStyle("width", "50px")
            .setStyle("fontSize", "16px");
    public Button btnExcel = new Button("", VaadinIcon.FILE_TABLE, "Exportar a Excel")
            .setStyle("width", "50px")
            .setStyle("fontSize", "16px")
            .setStyle("color","lime");
    public Button btnPDF = new Button("", VaadinIcon.FILE_O , "Exportar a PDF")
            .setStyle("width", "50px")
            .setStyle("fontSize", "16px")
            .setStyle("color","fuchsia");
    public Button btnPrint = new Button("", VaadinIcon.PRINT, "Imprimir")
            .setStyle("width", "50px")
            .setStyle("fontSize", "16px");
    public Button btnSave = new Button("Grabar", null , "Grabar")
            .setStyle("fontSize", "16px");
    public Button btnEdit = new Button("", VaadinIcon.SEARCH, "Ver mas o Editar")
            .setStyle("width", "50px")
            .setStyle("fontSize", "16px");
    public Button btnAdd = new Button("", VaadinIcon.PLUS, "Agregar")
            .setStyle("width", "50px")
            .setStyle("color", "lime")
            .setStyle("fontSize", "16px");
    public Button btnDelete = new Button("", VaadinIcon.MINUS, "Eliminar")
            .setStyle("width", "50px")
            .setStyle("color", "red")
            .setStyle("fontSize", "16px");
    public Button btnAnnul = new Button("", VaadinIcon.BAN, "Anular")
            .setStyle("width", "50px")
            .setStyle("fontSize", "16px")
            .setStyle("color","red");
    public Button btnRefresh = new Button("", VaadinIcon.REFRESH, "Refrescar")
            .setStyle("width", "50px")
            .setStyle("fontSize", "16px");
    public Button btnBack = new Button("", VaadinIcon.BACKWARDS, "Atras")
            .setStyle("width", "50px")
            .setStyle("fontSize", "16px");
    public Button btnClose = new Button("Cerrar", null , "Cerrar")
            .setStyle("fontSize", "16px");

    public ToolBar() {
        //this.setWidthFull();
        this.setSpacing(true);
        btnSave.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        btnClose.addThemeVariants(ButtonVariant.LUMO_ERROR);
        this.setAlignItems(Alignment.END);
        btnSave.setMinWidth("120px");
        btnClose.setMinWidth("100px");
        
        
    }

    public Button getBtnChoose() {
        return btnChoose;
    }

    public Button getBtnExcel() {
        return btnExcel;
    }

    public Button getBtnPDF() {
        return btnPDF;
    }

    public Button getBtnPrint() {
        return btnPrint;
    }

    public Button getBtnSave() {
        return btnSave;
    }

    public Button getBtnEdit() {
        return btnEdit;
    }

    public Button getBtnAdd() {
        return btnAdd;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }

    public Button getBtnAnnul() {
        return btnAnnul;
    }

    public Button getBtnRefresh() {
        return btnRefresh;
    }

    public Button getBtnBack() {
        return btnBack;
    }

    public Button getBtnClose() {
        return btnClose;
    }
    
}
