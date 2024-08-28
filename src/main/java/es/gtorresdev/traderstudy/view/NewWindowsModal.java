package es.gtorresdev.traderstudy.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;

import java.io.IOException;


/**
 * Clase genérica que facilita la creación y manejo de nuevas ventanas modales en una
 * aplicación JavaFX, utilizando la inyección de dependencias de Spring para los controladores.
 *
 * @param <T> El tipo del controlador asociado a la ventana.
 */
public class NewWindowsModal<T> {
    private final FXMLLoader loader;
    private final Stage stage;


    /**
     * Constructor que inicializa una nueva ventana modal utilizando un archivo FXML.
     *
     * @param context El contexto de la aplicación Spring, utilizado para la inyección de dependencias.
     * @param fxml    La ruta al archivo FXML que define la interfaz de la ventana.
     */
    public NewWindowsModal(@NotNull ApplicationContext context, String fxml) {
        loader = new FXMLLoader(getClass().getResource(fxml));
        loader.setControllerFactory(context::getBean); // Delega a Spring la creación del controlador

        stage = new Stage();

        try {
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Muestra la ventana modal y espera hasta que el usuario la cierre.
     * Este método bloquea la ejecución hasta que la ventana modal es cerrada.
     */
    public void showAndWait() {
        stage.showAndWait();
    }


    /**
     * Obtiene el controlador asociado a la ventana modal.
     *
     * @return El controlador de tipo {@code T} que fue cargado junto con el archivo FXML.
     */
    public T getController() {
        return loader.getController();
    }
}
