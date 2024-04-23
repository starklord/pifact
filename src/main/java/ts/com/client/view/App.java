package ts.com.client.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinSession;

import jakarta.servlet.http.HttpServletRequest;
import ts.com.client.component.Label;
import ts.com.server.Server;
import ts.com.service.factory.Services;

@Route("")
public class App extends VerticalLayout {

    public static final String COLOR1 = "#202f72";
    public static final String COLOR2 = "#16baff";
    public Label lblHeader = new Label("PiSoft");
    public Label lblPersona = new Label("PiSoft");
    // public final Image image = new Image(Server.IMAGE_WALL,
    // "logo_app").setStyles(
    // "width", "100%",
    // "height", "auto",
    // "max-width", "1000px"
    // );
    public AppLayout appLayout = new AppLayout();
    public VerticalLayout content = new VerticalLayout();
    public MenuView menuView = new MenuView(appLayout);

    public App() {
        Server.initDB();
        DrawerToggle dtgl = new DrawerToggle();
        dtgl.getStyle().set("color", "fuchsia");
        dtgl.getStyle().set("font-size", "20px");
        lblHeader.setStyles(
                "color", "#00a7a9",
                "font-size", "14px");
        lblPersona.setStyles(
                "color", "#00a7a9",
                "font-size", "14px");
        HorizontalLayout header = new HorizontalLayout(dtgl, lblHeader, lblPersona);
        header.setWidthFull();
        header.setAlignItems(Alignment.CENTER);
        header.getStyle()
                // .set("background", "#202f72")
                .set("height", "40px");
        appLayout.addToNavbar(header);
        appLayout.addToDrawer(menuView);

        this.setAlignItems(FlexComponent.Alignment.CENTER);
        this.setSizeFull();
        appLayout.getElement().getStyle().set("width", "100%");
        appLayout.setContent(content);
        content.setSizeFull();
        content.setPadding(false);
        content.setMargin(false);
        this.setPadding(true);
        this.setSpacing(true);
        initServices();
        // content.add(image);
        content.setAlignItems(Alignment.CENTER);
        LoginView loginView = new LoginView(this);
        this.add(loginView);

    }

    private void initServices() {
        // Services.setServiceUrl("http://localhost:7007/eva/");
        // Services.setServiceUrl("http://144.91.125.121:7007/ts_com_server/eva/");
    }

    public static String getRequestUrl() {
        VaadinRequest vaadinRequest = VaadinService.getCurrentRequest();
        HttpServletRequest httpServletRequest = ((VaadinServletRequest) vaadinRequest).getHttpServletRequest();

        return httpServletRequest.getRequestURL().toString();
    }

    public static void closeSession() throws Exception {
        VaadinSession session = UI.getCurrent().getSession();
        session.close();
    }

    public void setContent(Component view) {
        this.content.removeAll();
        this.content.add(view);
    }
}
