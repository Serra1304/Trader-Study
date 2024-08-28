package es.gtorresdev.traderstudy.services;

import es.gtorresdev.traderstudy.indicators.Indicator;
import es.gtorresdev.traderstudy.models.input.InputElement;
import es.gtorresdev.traderstudy.models.input.InputStatus;
import es.gtorresdev.traderstudy.models.input.InputType;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;


/**
 * Servicio encargado de la gestión y configuración de indicadores, así como de la validación
 * de los elementos de entrada en la aplicación.
 */
@Service
public class IndicatorService {


    /**
     * Configura un indicador llamando a su método de configuración.
     *
     * @param indicator El indicador a configurar. No debe ser nulo.
     */
    public void configureIndicator(@NotNull Indicator indicator) {
        indicator.configuration();
    }


    /**
     * Valída los elementos de entrada asociados a un indicador.
     *
     * @param indicator     El indicador cuyos elementos de entrada se validarán. No debe ser nulo.
     * @param inputElements Una lista de contenedores HBox que contienen los elementos de entrada.
     *                      No debe ser nula.
     * @return El estado de la validación, que puede ser {@link InputStatus#VALIDATED} o
     *         {@link InputStatus#INVALIDATED}.
     */
    public InputStatus validateInputs(@NotNull Indicator indicator, @NotNull List<HBox> inputElements) {
        List<InputElement> elementList = new ArrayList<>(indicator.getInputElements().values());
        boolean allValid = true;

        for (int i = 0; i < inputElements.size(); i++) {
            HBox hBox = inputElements.get(i);
            InputElement element = elementList.get(i);
            allValid &= updateElementValue(hBox, element);
        }

        return allValid ? InputStatus.VALIDATED : InputStatus.INVALIDATED;
    }


    /**
     * Actualiza el valor de un elemento de entrada basado en el contenido de su contenedor HBox.
     *
     * @param container El contenedor HBox que contiene el elemento de entrada.
     * @param element   El elemento de entrada a actualizar. No debe ser nulo.
     * @return {@code true} si el valor se actualizó correctamente, {@code false} si ocurrió un error
     *         en la conversión de tipos.
     */
    private boolean updateElementValue(HBox container, @NotNull InputElement element) {
        try {
            switch (element.getInputType()) {
                case InputType.INTEGER -> {
                    TextField textField = (TextField) container.getChildren().get(1);
                    element.setValue(Integer.parseInt(textField.getText()));
                }
                case InputType.DOUBLE -> {
                    TextField textField = (TextField) container.getChildren().get(1);
                    element.setValue(Double.parseDouble(textField.getText()));
                }
                case InputType.COLOR -> {
                    ColorPicker color = (ColorPicker) container.getChildren().get(1);
                    element.setValue(color.getValue());
                }
                default -> {
                    throw new IllegalArgumentException("Unsupported input type");
                }
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /**
     * Crea un nodo de entrada adecuado basado en el tipo de elemento de entrada.
     *
     * @param element El elemento de entrada para el cual se debe crear un nodo. No debe ser nulo.
     * @return El nodo de entrada correspondiente al tipo del elemento.
     * @throws IllegalArgumentException si el tipo de entrada no está soportado.
     */
    public Node createInputNode(@NotNull InputElement element) {
        switch (element.getInputType()) {
            case InputType.INTEGER, InputType.DOUBLE -> {
                return new TextField(element.getDefaultValue().toString());
            }

            case InputType.COLOR -> {
                Color color = (Color) element.getDefaultValue();
                return new ColorPicker(color);
            }

            default -> throw new IllegalArgumentException("Unsupported input type: " + element.getInputType());
        }
    }
}

