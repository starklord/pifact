/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.com.client.component;

import java.io.IOException;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.server.StreamResource;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import ts.com.Client;
import ts.com.client.factory.Alerts;
import ts.com.server.Server;

/**
 *
 * @author evanl
 */
public class DownloadLink extends HorizontalPanel {

    private static final long serialVersionUID = 1L;
    public final Label btnRemove = new Label("X").setStyles("color","red");
    public final Anchor anchor;
    public DownloadLink(File file) {
        anchor = new Anchor(getStreamResource(file.getName(), file), file.getName());
        anchor.getElement().setAttribute("download", true);
        anchor.setHref(getStreamResource(file.getName(), file));
        btnRemove.setVisible(false);
        add(anchor,btnRemove);
    }

    public StreamResource getStreamResource(String filename, File content) {
        return new StreamResource(filename, () -> {
            try {
                return new BufferedInputStream(new FileInputStream(content));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
}
