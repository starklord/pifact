package ts.com;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;

import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Theme(value = "pisoft", variant = Lumo.DARK)
// @PWA(name = "Pi Technologies", shortName = "Pi", backgroundColor = "black",
// themeColor = "green")
// @Theme(value = "pisoft", variant = Lumo.LIGHT)
@PWA(name = "PiSoft ERP", shortName = "Pi")
public class AppVaadin implements AppShellConfigurator {

}
