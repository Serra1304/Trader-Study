package es.gtorresdev.traderstudy.controllers;

import es.gtorresdev.traderstudy.indicators.Indicator;
import es.gtorresdev.traderstudy.models.input.InputElement;
import es.gtorresdev.traderstudy.models.input.InputStatus;
import es.gtorresdev.traderstudy.services.IndicatorService;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


/**
 * Controlador para la configuración de indicadores en la aplicación. Este controlador maneja la configuración de los
 * indicadores, validación de entradas, y gestión del estado de la ventana de configuration.
 */
@Controller
public class IndicatorConfigController {
    private final IndicatorService indicatorService;

    private InputStatus inputStatus;
    private Indicator indicator;

    @FXML
    private Label lblIndicator;

    @FXML
    private VBox inputElements;


    /**
     * Constructor que inyecta el servicio de indicadores.
     *
     * @param indicatorService El servicio utilizado para manejar la lógica de los indicadores.
     */
    @Autowired
    public IndicatorConfigController(IndicatorService indicatorService) {
        this.indicatorService = indicatorService;
    }


    /**
     * Inicializa el estado inicial del controlador.
     * Este método es invocado automáticamente después de cargar la vista.
     */
    @FXML
    public void initialize() {
        inputStatus = InputStatus.WAITING;
    }


    /**
     * Maneja la acción de aceptación de los parámetros configurados. Válida las entradas
     * y cierra la ventana si las entradas son válidas.
     */
    @FXML
    public void handleAccept() {
        inputStatus = indicatorService.validateInputs(indicator, inputElements.getChildren().stream()
                .map(node -> (HBox) node)
                .toList());

        if (inputStatus.equals(InputStatus.VALIDATED)) {
            inputStatus = InputStatus.ACCEPTED;
            closeWindows();
        } else {
            showError("Por favor verifique los valores de entrada.");
        }
    }


    /**
     * Maneja la acción de cancelar la configuración. Establece el estado en "CANCELADO"
     * y cierra la ventana.
     */
    @FXML
    public void handleCancel() {
        inputStatus = InputStatus.CANCELED;
        closeWindows();
    }


    /**
     * Configura el controlador con el indicador específico y genera los elementos de entrada
     * necesarios para la configuración del mismo.
     *
     * @param indicator El indicador a configurar.
     */
    public void config(@NotNull Indicator indicator) {
        indicatorService.configureIndicator(indicator);
        this.indicator = indicator;

        String indicatorName = indicator.getClass().getSimpleName();
        Stage stage = (Stage) lblIndicator.getScene().getWindow();

        stage.setTitle(indicatorName);
        lblIndicator.setText(indicatorName);

        for (InputElement element : indicator.getInputElements().values()) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.BASELINE_LEFT);
            hBox.setSpacing(10);
            inputElements.getChildren().add(hBox);

            Label description = new Label(element.getDescription());
            description.setPrefWidth(200);
            description.setAlignment(Pos.CENTER_RIGHT);
            Node node = indicatorService.createInputNode(element);

            hBox.getChildren().add(description);
            hBox.getChildren().add(node);
        }
    }


    /**
     * Devuelve el estado actual del proceso de configuración.
     *
     * @return El estado de la configuración, como {@link InputStatus}.
     */
    public InputStatus getStatus() {
        return inputStatus;
    }


    /**
     * Muestra un cuadro de diálogo con un mensaje de error.
     *
     * @param message El mensaje de error a mostrar.
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Valor invalido");
        alert.setHeaderText("Error en los valores de entrada:");
        alert.setContentText(message);
        alert.showAndWait();
    }


    /**
     * Cierra la ventana actual del escenario.
     */
    private void closeWindows() {
        Stage stage = (Stage) lblIndicator.getScene().getWindow();
        stage.close();
    }
}
