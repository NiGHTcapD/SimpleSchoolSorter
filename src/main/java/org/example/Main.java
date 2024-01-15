package org.example;

import org.example.services.LabberService;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.awt.*;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Main.class).headless(false).web(WebApplicationType.NONE).run(args);
    }

    public Main(final LabberService labberService) {
        EventQueue.invokeLater(() -> {
            Labber labber = new Labber(labberService);
            labber.getFrame().setVisible(true);
        });
    }
}