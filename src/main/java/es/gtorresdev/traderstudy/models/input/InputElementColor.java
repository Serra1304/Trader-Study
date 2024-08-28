package es.gtorresdev.traderstudy.models.input;

import candleChart.charts.bar.BarChart;
import candleChart.charts.base.Chart;
import candleChart.charts.curvedLine.CurvedLineChart;
import candleChart.charts.line.LineChart;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * Clase que representa un elemento de entrada específico para el manejo de colores en gráficos.
 * Extiende la clase {@link InputElement} y está diseñada para trabajar con colores en un {@link Chart}.
 */
public class InputElementColor extends InputElement{
    private final List<Chart<?>> charts;


    /**
     * Crea una nueva instancia de {@code InputElementColor}.
     *
     * @param inputType El tipo de entrada, que debe ser {@link InputType#COLOR}.
     * @param description La descripción del elemento de entrada. Este valor no puede ser nulo.
     * @param defaultValue El valor predeterminado de entrada, que debe ser una instancia de {@link Color}.
     * @param chart El gráfico asociado al elemento de entrada.
     * @throws IllegalArgumentException Si {@code inputType} no es {@link InputType#COLOR} o
     * si {@code defaultValue} no es una instancia de {@link Color}.
     */
    public InputElementColor(@NotNull InputType inputType, @NotNull String description, Object defaultValue, Chart<?> chart) {
        super(inputType, description, defaultValue);
        validateInputType(inputType, defaultValue);

        charts = new ArrayList<>();
        charts.add(chart);
    }


    /**
     * Crea una nueva instancia de {@code InputElementColor}.
     *
     * @param inputType El tipo de entrada, que debe ser {@link InputType#COLOR}.
     * @param description La descripción del elemento de entrada. Este valor no puede ser nulo.
     * @param defaultValue El valor predeterminado de entrada, que debe ser una instancia de {@link Color}.
     * @param charts Lista de gráficos asociados al elemento de entrada.
     * @throws IllegalArgumentException Si {@code inputType} no es {@link InputType#COLOR} o
     * si {@code defaultValue} no es una instancia de {@link Color}.
     */
    public InputElementColor(@NotNull InputType inputType, @NotNull String description, Object defaultValue, List<Chart<?>> charts) {
        super(inputType, description, defaultValue);
        validateInputType(inputType, defaultValue);

        this.charts = charts;
    }


    /**
     * Establece un nuevo valor de color para el elemento de entrada y los gráficos asociados.
     *
     * @param value El nuevo valor a establecer, que debe ser una instancia de {@link Color}.
     * @throws IllegalArgumentException Si {@code value} no es una instancia de {@link Color}.
     */
    @Override
    public void setValue(Object value) {
        if (!(value instanceof Color color)) {
            throw new IllegalArgumentException("Se esperaba un objeto de tipo Color.");
        }
        super.setValue(value);

        for (Chart<?> chart : charts) {
            if (chart instanceof LineChart lineChart) {
                lineChart.setLineColor(convertToAwtColor(color));
            } else if (chart instanceof CurvedLineChart curvedLineChart) {
                curvedLineChart.setLineColor(convertToAwtColor(color));
            } else if (chart instanceof BarChart barChart) {
                barChart.setBarColor(convertToAwtColor(color));
            }
        }
    }


    /**
     * Agrega un gráfico a la lista de gráficos asociados al elemento de entrada.
     *
     * @param chart gráfico a asociar al elemento de entrada.
     */
    public void addChart(Chart<?> chart) {
        charts.add(chart);
    }


    /**
     * Obtiene una lista de los gráficos asociados al elemento de entrada.
     *
     * @return El gráfico asociado.
     */
    public List<Chart<?>> getCharts() {
        return charts;
    }


    /**
     * Obtiene un gráfico específico de la lista según su índice.
     *
     * @param index El índice del gráfico a obtener.
     * @return El gráfico en el índice especificado.
     */
    public Chart<?> getChart(int index) {
        return charts.get(index);
    }


    /**
     * Válida que {@code inputType} y {@code defaultValue} sean de tipo color.
     *
     * @param inputType @param inputType El tipo de entrada, que debe ser {@link InputType#COLOR}.
     * @param defaultValue El valor predeterminado de entrada, que debe ser una instancia de {@link Color}.
     * @throws IllegalArgumentException Si {@code inputType} no es {@link InputType#COLOR} o
     * si {@code defaultValue} no es una instancia de {@link Color}.
     */
    private void validateInputType(InputType inputType, Object defaultValue) {
        if (inputType != InputType.COLOR) {
            throw new IllegalArgumentException("El inputType debe ser de tipo COLOR.");
        }
        if (!(defaultValue instanceof Color)) {
            throw new IllegalArgumentException("Se esperaba un 'defaultValue' de tipo: Color.");
        }
    }


    /**
     * Convierte un color de JavaFX a un color de AWT.
     *
     * @param color El color de JavaFX a convertir, no puede ser nulo.
     * @return Una nueva instancia de {@link java.awt.Color} que representa el color convertido, nunca es nulo.
     */
    @Contract("_ -> new")
    private java.awt.@NotNull Color convertToAwtColor(@NotNull Color color) {
         return new java.awt.Color(
                (float) color.getRed(),
                (float) color.getGreen(),
                (float) color.getBlue(),
                (float) color.getOpacity()
        );
    }
}
