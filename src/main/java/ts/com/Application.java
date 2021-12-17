package ts.com;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import ts.com.server.Server;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "pisoft", variant = Lumo.DARK)
@PWA(name = "PiSoft ERP", shortName = "PiSoft ERP", offlineResources = {"images/logo_pi2.png"}
,backgroundColor = "indigo",themeColor = "#00a7a9")
// ,backgroundColor = "indigo",themeColor = "#00a7a9")
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class Application extends SpringBootServletInitializer implements AppShellConfigurator {

    public static void main(String[] args) {
        Server.initDB();
        SpringApplication.run(Application.class, args);
    }

}
