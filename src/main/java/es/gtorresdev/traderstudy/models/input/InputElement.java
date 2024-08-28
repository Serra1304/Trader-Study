package es.gtorresdev.traderstudy.models.input;

import org.jetbrains.annotations.NotNull;


/**
 * La clase {@code InputElement} representa un elemento de entrada genérico que puede contener diferentes tipos
 * de valores. Es útil en contextos donde se necesita manejar y almacenar datos de diferentes tipos de manera
 * flexible y dinámica. El contexto principal es establecer los tipos de datos de entrada que se requieren en un
 * indicador por parte del usuario.
 */
public class InputElement {
    private final InputType inputType;
    private final String description;
    private Object value;


    /**
     * Crea una nueva instancia de {@code InputElement} con el tipo de entrada,
     * descripción y valor por defecto especificados.
     *
     * @param inputType   el tipo de entrada que define el tipo de dato que este
     *                    elemento manejará. Este valor no puede ser nulo.
     * @param description una descripción del elemento de entrada, que será mostrado en una interfaz de usuario.
     *                    Este valor no puede ser nulo.
     * @param defaultValue el valor por defecto que este elemento de entrada tendrá. Debe ser del
     *                    tipo correspondiente al {@code inputType}.
     * @throws IllegalArgumentException si {@code defaultValue} no es una instancia
     *                                  del tipo definido por {@code inputType}.
     */
    public InputElement(@NotNull InputType inputType, @NotNull String description, Object defaultValue) {
        this.inputType = inputType;
        this.description = description;
        validateType(defaultValue);
        this.value = defaultValue;
    }


    /**
     * Devuelve el tipo de entrada que define el tipo de dato que este elemento maneja.
     *
     * @return el tipo de entrada asociado a este elemento.
     */
    public InputType getInputType() {
        return inputType;
    }


    /**
     * Devuelve la descripción de este elemento de entrada.
     *
     * @return la descripción del elemento de entrada.
     */
    public String getDescription() {
        return description;
    }


    /**
     * Establece el valor de este elemento de entrada.
     *
     * @param value el nuevo valor de entrada. Debe ser una instancia del tipo definido por {@code inputType}.
     * @throws IllegalArgumentException si {@code value} no es una instancia del tipo
     *                                  definido por {@code inputType}.
     */
    public void setValue(Object value) {
        validateType(value);
        this.value = value;
    }


    /**
     * Devuelve el valor de este elemento de entrada.
     *
     * @return el valor de este elemento.
     */
    public Object getDefaultValue() {
        return value;
    }


    /**
     * Válida que el valor proporcionado sea una instancia del tipo definido por {@code inputType}.
     *
     * @param value el valor a validar.
     * @throws IllegalArgumentException si el valor no es del tipo esperado.
     */
    private void validateType(Object value) {
        if (!inputType.isInstance(value)) {
            throw new IllegalArgumentException("Se esperaba un objeto de tipo: " + inputType.getType().getSimpleName());
        }
    }
}
