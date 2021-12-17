/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.com.client.factory;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import ts.com.client.component.AlertView;
import ts.com.client.component.PanelUI;
import ts.com.client.component.PasswordTextBox;
import ts.com.client.component.TextBox;
import ts.com.client.handler.CloseHandler;

/**
 *
 * @author rednave
 */
public class Alerts {

    public static int TYPE_PROMPT = 3;
    public static int TYPE_CONFIRM = 2;
    public static int TYPE_MESSAGE = 1;
    
    public static PanelUI warning(String message) {
        Icon icon = VaadinIcon.CIRCLE.create();
        icon.getStyle().set("color", "green");
        return showDialog(icon,"Advertencia", message, TYPE_MESSAGE, null);

    }

    public static PanelUI warning(String message, CloseHandler handler) {
        Icon icon = VaadinIcon.CIRCLE.create();
        icon.getStyle().set("color", "green");
        return showDialog(icon, "Advertencia", message, TYPE_MESSAGE, handler);

    }

    public static PanelUI info(String message) {
        Icon icon = VaadinIcon.CIRCLE.create();
        icon.getStyle().set("color", "green");
        return showDialog(icon, "Mensaje", message, TYPE_MESSAGE, null);

    }

    public static PanelUI info(String message, CloseHandler handler) {
        Icon icon = VaadinIcon.CIRCLE.create();
        icon.getStyle().set("color", "green");
        return showDialog(icon, "Mensaje", message, TYPE_MESSAGE, handler);

    }

    public static PanelUI infoSaved() {
        Icon icon = VaadinIcon.CIRCLE.create();
        icon.getStyle().set("color", "green");
        return showDialog(icon, "Mensaje", "Datos grabados exitosamente", TYPE_MESSAGE, null);
    }

    public static PanelUI infoSaved(CloseHandler handler) {
        Icon icon = VaadinIcon.CIRCLE.create();
        icon.getStyle().set("color", "red");
        return showDialog(icon, "Mensaje", "Datos grabados exitosamente", TYPE_MESSAGE, handler);

    }

    public static PanelUI error(String message) {
        Icon icon = VaadinIcon.CIRCLE.create();
        icon.getStyle().set("color", "red");
        return showDialog(icon, "Error", message, TYPE_MESSAGE, null);

    }

    public static PanelUI error(String message, CloseHandler handler) {
        Icon icon = VaadinIcon.CIRCLE.create();
        icon.getStyle().set("color", "red");
        return showDialog(icon, "Error", message, TYPE_MESSAGE, handler);
    }

    /**
     * shows a confirm dialog
     *
     * @param message
     * @param clickHandler the event when Yes button is clicked
     */
    public static PanelUI confirm(String message, CloseHandler handler) {
        Icon icon = VaadinIcon.CIRCLE.create();
        icon.getStyle().set("color", "orange");
        return showDialog(icon, "Confirmacion", message, TYPE_CONFIRM, null, null, handler);
    }

    public static PanelUI confirmSave(CloseHandler handler) {
        Icon icon = VaadinIcon.CIRCLE.create();
        icon.getStyle().set("color", "orange");
        return showDialog(icon, "Confirmacion", "Desea Grabar los Datos Ingresados?", TYPE_CONFIRM, null, null, handler);
    }

    public static PanelUI prompt(String message, TextBox txtPrompt, CloseHandler handler) {
        Icon icon = VaadinIcon.CIRCLE.create();
        icon.getStyle().set("color", "orange");
        txtPrompt.focus();
        return showDialog(icon, "Ingresar", message, TYPE_CONFIRM, txtPrompt, null, handler);
    }

    public static PanelUI prompt(String message, PasswordTextBox txtPass, CloseHandler handler) {
        Icon icon = VaadinIcon.CIRCLE.create();
        icon.getStyle().set("color", "orange");
        return showDialog(icon, "Ingresar", message, TYPE_CONFIRM, null, txtPass, handler);
    }

    private static PanelUI showDialog(Icon icon, String header, String message, int type,
            CloseHandler handler) {
        return showDialog(icon, header, message, type, null, null, handler);
    }

    private static PanelUI showDialog(Icon icon, String header, String message, int type, TextBox txt, PasswordTextBox txtp,
            CloseHandler handler) {
        AlertView view = new AlertView(icon, header, message, type, txt, txtp, handler);
        view.showDialog();

        return view;
    }

}
