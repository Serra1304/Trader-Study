package es.gtorresdev.traderstudy.exceptions;

import java.io.IOException;

public class SymbolTableExceptionHandler {
    public void handleLoadExceptions(Exception e) {
        // TODO Controlar adecuadamente las excepciones.
        if (e instanceof IOException) {
            e.printStackTrace();
        } else if (e instanceof PropertyNotFoundException) {
            e.printStackTrace();
        } else if (e instanceof ConfigFileNotFoundException) {
            e.printStackTrace();
        } else {
            // Handle general exceptions
            e.printStackTrace();
        }
    }
}
