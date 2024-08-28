package es.gtorresdev.traderstudy.models.input;

import javafx.scene.paint.Color;


/**
 * Enumeración que define los tipos de entrada posibles en el sistema.
 * Cada valor de esta enumeración está asociado con una clase Java que representa el tipo de entrada.
 */
public enum InputType {
    INTEGER(Integer.class),
    DOUBLE(Double.class),
    STRING(String.class),
    BOOLEAN(Boolean.class),
    COLOR(Color.class);

    private final Class<?> type;


    /**
     * Constructor para inicializar el tipo de entrada con la clase correspondiente.
     *
     * @param type La clase que representa el tipo de entrada.
     */
    InputType(Class<?> type) {
        this.type = type;
    }


    /**
     * Obtiene la clase asociada al tipo de entrada.
     *
     * @return La clase que representa el tipo de entrada.
     */
    public Class<?> getType() {
        return type;
    }


    /**
     * Verifica si un valor es compatible con el tipo de entrada.
     *
     * @param value El valor a verificar.
     * @return {@code true} si el valor es una instancia del tipo asociado, {@code false} en caso contrario.
     */
    public boolean isInstance(Object value) {
        return type.isInstance(value);
    }
}
