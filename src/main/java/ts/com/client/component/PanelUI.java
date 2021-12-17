package ts.com.client.component;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import ts.com.client.factory.Alerts;
import ts.com.client.handler.ChooseHandler;
import ts.com.client.handler.CloseHandler;
import ts.com.client.handler.SaveHandler;

public abstract class PanelUI extends VerticalLayout {

    private String text = "Opciones";
    private Icon icon = null;
    public Label label = new Label(text);
    private DialogView dialog;
    private HorizontalLayout header = new HorizontalLayout();
    public ListBox<Integer> lbxAnio = new ListBox<>();
    private VerticalLayout body = new VerticalLayout();
    private ToolBar toolBar = new ToolBar();
    private boolean askBeforeClose = false;

    private ChooseHandler chooseHandler= () -> {
    };
    public CloseHandler closeHandler = () -> {
    };
    private SaveHandler saveHandler = () -> {
        // TODO Auto-generated method stub
    };
    
    public PanelUI(VaadinIcon icon, String text){
        this(icon==null?null:icon.create(),text);
    }

    public PanelUI(Icon icon, String text) {
        if(icon!=null){
            this.icon = icon; 
        }
        if(text!=null){
            this.text = text;
            this.label = new Label(text);
            //label.getStyle().set("color", "indigo");
        }
        if(icon!=null){
            header.add(this.icon, label);
        }else{
            header.add(label);
        }
        
        this.getHeader().add(this.getToolBar());
        initStylesPanel();
        add(header,body);
        initHandlersUI();
    }

    private void initStylesPanel() {
        getBody().setPadding(false);
        this.setPadding(false);
        header.setWidthFull();
        label.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.START);
        header.setAlignItems(Alignment.CENTER);

        this.setSizeFull();
        this.getBody().setSizeFull();
        this.getToolBar().setWidthFull();
        this.getHeader().setWidthFull();
        this.getToolBar().setJustifyContentMode(JustifyContentMode.END);
    }

    private void initHandlersUI() {

        toolBar.getBtnClose().addClickListener((e) -> {
            if (askBeforeClose) {
//                Alerts.confirm("Desea cerrar la ventana de trabajo?", () -> {
//                    removeFromParent();
//                });
            } else {
                removeFromParent();
            }
        });
        toolBar.getBtnChoose().addClickListener(e -> {
            if (chooseHandler != null) {
                chooseHandler.onChoose();
            }
            removeFromParent();
        });
        toolBar.getBtnAnnul().addClickListener(e -> {
            onAnnul();
        });
        toolBar.getBtnDelete().addClickListener(e -> {
            onDelete();
        });

        toolBar.getBtnAdd().addClickListener(e -> {
            onAdd();
        });
        toolBar.getBtnEdit().addClickListener(e -> {
            onEdit();
        });
        toolBar.getBtnSave().addClickListener(e -> {
            Alerts.confirmSave(() -> {
                onSave();
            });
        });
        toolBar.getBtnRefresh().addClickListener(e -> {
            onRefresh();
        });
        toolBar.getBtnExcel().addClickListener(e -> {
            onExportToExcel();
        });
        toolBar.getBtnPDF().addClickListener(e -> {
            onExportToPdf();
        });
        toolBar.getBtnPrint().addClickListener(e -> onPrint());
    }

    public String getText() {
        return text;
    }

    public ToolBar getToolBar() {
        return toolBar;
    }

    public VerticalLayout getBody() {
        return body;
    }

    public HorizontalLayout getHeader() {
        return header;
    }
    
    

    public void showDialog() {
        dialog = new DialogView(this) {
        };
        dialog.open();
    }

    /**
     * triggers saveHandler before closing.
     */
    public final void saveAndClose() {
        saveHandler.onSave();
        close();
    }

    public final void close() {
        removeFromParent();
        
    }

    public final void removeFromParent() {
        if (closeHandler != null) {
            closeHandler.onClose();
        }
        if (dialog != null) {
            dialog.getElement().removeFromParent();
        }
        this.getElement().removeFromParent();
    }

    public void setText(String txt) {
        this.text = txt;

    }

    public void addChooseHandler(ChooseHandler chooseHandler) {
        this.chooseHandler = chooseHandler;
    }

    public void addCloseHandler(CloseHandler closeHandler) {
        this.closeHandler = closeHandler;
    }

    public void addSaveHandler(SaveHandler saveHandler) {
        this.saveHandler = saveHandler;
    }

//    public void init(String text) {
//        init(text, null);
//    }


//    public void init(String text, Icon icon) {
//        this.text = text;
//        this.icon = icon;
//        Label label = new Label(text);
//        label.getStyle().set("color", "indigo");
//        header.add(this.icon, label);
//        initUI();
//
//    }

    //public abstract void initUI();

    public void onAnnul() {

    }

    public void onAdd() {

    }

    public void onDelete() {

    }

    public void onEdit() {

    }

    public void onSave() {

    }

    public void onRefresh() {

    }

    public void onPrint() {

    }

    public void onExportToExcel() {

    }

    public void onExportToPdf() {

    }

    public Button getBtnRefresh() {
        return getToolBar().getBtnRefresh();
    }

    public Button getBtnSave() {
        return getToolBar().getBtnSave();
    }

    public Button getBtnBack() {
        return getToolBar().getBtnBack();
    }

    public Button getBtnChoose() {
        return getToolBar().getBtnChoose();
    }

    public Button getBtnClose() {
        return getToolBar().getBtnClose();
    }

    public Button getBtnEdit() {
        return getToolBar().getBtnEdit();
    }

    public Button getBtnAdd() {
        return getToolBar().getBtnAdd();
    }

    public Button getBtnDelete() {
        return getToolBar().getBtnDelete();
    }

    public Button getBtnAnnul() {
        return getToolBar().getBtnAnnul();
    }

    public Button getBtnPDF() {
        return getToolBar().getBtnPDF();
    }

    public Button getBtnPrint() {
        return getToolBar().getBtnPrint();
    }

    public Button getBtnExcel() {
        return getToolBar().getBtnExcel();
    }

}
