package ts.com;

import java.io.File;
import java.util.TimeZone;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.servlet.http.Cookie;
import ts.com.client.factory.Alerts;
import ts.com.server.Server;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;

@QuarkusMain
public class App {

    public static void main(String... args) {
        try {
            Server.initDB();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("no se pudo cargar los datos iniciales...");
            System.out.println(ex.getMessage());
        }
        // Quarkus.run(MyApp.class, args);
        Quarkus.run(args);
    }

    public static void closeSession() {
        VaadinSession session = UI.getCurrent().getSession();
        session.close();
    }

    public static Cookie getCookieByName(String name) {
        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }

    public static void destroyCookieByName(String name) {
        Cookie cookie = getCookieByName(name);
        if (cookie != null) {
            cookie.setValue(null);
            cookie.setMaxAge(0);
            cookie.setPath(VaadinService.getCurrentRequest().getContextPath());
            VaadinService.getCurrentResponse().addCookie(cookie);
            try {
                App.closeSession();
            } catch (Exception e) {
                Alerts.error("no se pudo cerrar la sesion");
                e.printStackTrace();
            }
        }
    }

}
