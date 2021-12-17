package ts.com.client.component;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import ts.com.client.factory.Alerts;
import ts.com.client.handler.CloseHandler;

public final class AlertView extends PanelUI {

    public String message;
    public int type;
    public CloseHandler handler;
    public TextBox txt;
    public PasswordField txtp;

    public AlertView(Icon icon, String header, String message, int type, TextBox txt, PasswordField txtp, CloseHandler handler) {
        super(icon, header);
        this.txt = txt;
        this.txtp = txtp;
        this.message = message;
        this.type = type;
        this.handler = handler;
        initUI();
    }

    public void initUI() {
        Label lblMessage = new Label(message);

        Button btnSi = new Button("Ok")
                .setStyle("width", "100px")
                .setStyle("color", "green");
        if (handler != null) {
            btnSi.addClickListener(e -> {
                handler.onClose();
                this.removeFromParent();
            });
        } else {
            btnSi.addClickListener(e -> {
                this.removeFromParent();
            });
        }

        Button btnNo = new Button("Cancelar")
                .setStyle("width", "100px")
                .setStyle("color", "red");
        btnNo.addClickListener(e -> {
            this.removeFromParent();
        });
        getBody().setSpacing(true);
        getBody().add(lblMessage);
        HorizontalLayout pnl = new HorizontalLayout(btnSi, btnNo);
        pnl.setWidthFull();
        pnl.setJustifyContentMode(JustifyContentMode.CENTER);
        if (txtp != null) {
            getBody().add(txtp, pnl);
        }
        if (txt != null) {
            getBody().add(txt, pnl);
        }
        if (txt == null && txtp == null) {
            getBody().add(pnl);
        }
        btnNo.setVisible(type == Alerts.TYPE_CONFIRM);

        if (txtp != null) {
            txtp.focus();
        }
        if (txt != null) {
            txt.focus();
        }
        if (txt == null && txtp == null) {
            btnSi.focus();
        }

    }
}
