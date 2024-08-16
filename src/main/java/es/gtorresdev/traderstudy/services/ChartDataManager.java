package es.gtorresdev.traderstudy.services;

import candleChart.charts.base.Chart;
import candleChart.charts.candle.Candle;
import candleChart.charts.candle.CandleChart;
import es.gtorresdev.traderstudy.indicators.Indicator;
import es.gtorresdev.traderstudy.models.ChartData;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Servicio responsable de manejar los datos de gráficos y los indicadores asociados.
 * Esta clase gestiona la lista de velas históricas y los indicadores, así como el estado actual
 * de visualización en el gráfico. También se encarga de actualizar el gráfico de velas y
 * los indicadores cuando se cambian los datos o la configuración de visualización.
 */
@Service
public class ChartDataManager {
    private final List<Candle> candleList;
    private final List<Indicator> indicatorList;

    private int currentIndex;
    private int numberOfDisplayedElements;

    private CandleChart candleChart;


    /**
     * Constructor por defecto que inicializa el gestor de datos del gráfico.
     * Este constructor crea listas vacías para las velas y los indicadores, y establece los valores
     * iniciales para el índice de visualización y el número de elementos mostrados en el gráfico.
     */
    public ChartDataManager() {
        candleList = new ArrayList<>();
        indicatorList = new ArrayList<>();

        currentIndex = 0;
        numberOfDisplayedElements = 1;
    }

    /**
     * Obtiene la lista de velas históricas del administrador de datos del gráfico.
     *
     * @return la lista de velas históricas.
     */
    public List<Candle> getCandleList() {
        return candleList;
    }


    /**
     * Establece la lista de velas históricas en el administrador de datos del gráfico.
     * En caso de ya existir una lista, esta será reemplazada.
     *
     * @param candleList la nueva lista de velas históricas a establecer.
     */
    public void setCandleList(@NotNull List<Candle> candleList) {
        if (!candleList.isEmpty()) {
            this.candleList.clear();
            this.candleList.addAll(candleList);
            currentIndex = candleList.size();

            if (candleChart != null) {
                updateCandleChart();
            }
        }
    }


    /**
     * Obtiene el número de elementos visualizados en el gráfico.
     *
     * @return el número de elementos visualizados.
     */
    public int getNumberOfDisplayedElements() {
        return numberOfDisplayedElements;
    }


    /**
     * Establece el número de elementos visualizados en el gráfico. En el caso de que el número de elementos mostrados
     * sea mayor al índice actual de visualización, este se desplazara hasta el número de elementos visualizados.
     *
     * @param displayedElements el número de elementos visualizados.
     */
    public void setNumberOfDisplayedElements(int displayedElements) {
        numberOfDisplayedElements = displayedElements;
        currentIndex = Math.max(currentIndex, displayedElements);
        updateCandleChart();
        updateIndicators();
    }


    /**
     * Obtiene el índice actual del elemento representado en el gráfico.
     *
     * @return el índice actual.
     */
    public int getIndex() {
        return currentIndex;
    }


    /**
     * Establece el índice en la position indicada.
     * Si el índice especificado es mayor que el número de elementos mostrados, el método se asegura de que
     * el índice no exceda el tamaño de la lista. Si el índice especificado es menor o igual al número de
     * elementos mostrados, establece el índice en el valor de `numberOfDisplayedElements`.
     *
     * @param index
     */
    public void setIndex(int index) {
        currentIndex = index > numberOfDisplayedElements ? Math.min(index, candleList.size()) : numberOfDisplayedElements;
        updateCandleChart();
        updateIndicators();
    }


    /**
     * Añade un CandleChart al administrador. Si ya hay una lista de velas establecida, se actualiza el gráfico.
     *
     * @param candleChart el gráfico de velas a añadir.
     */
    public void add(CandleChart candleChart) {
        this.candleChart = candleChart;

        if (!candleList.isEmpty()) {
            updateCandleChart();
        }
    }


    /**
     * Añade un indicador al administrador y actualiza los indicadores.
     *
     * @param indicator el indicador a añadir.
     */
    public void add(Indicator indicator) {
        indicatorList.add(indicator);
        updateIndicators();
    }


    /**
     * Actualiza los datos del gráfico de velas, basándose en el índice y el número de elementos visualizados.
     */
    private void updateCandleChart() {
        int endCandle = Math.min(currentIndex, candleList.size());
        int firstCandle = Math.max(0, Math.min(endCandle - numberOfDisplayedElements, endCandle));

        candleChart.setBuffer(candleList.subList(firstCandle, endCandle));
    }


    /**
     * Actualiza los datos de los indicadores, basándose en el índice y el número de elementos visualizados.
     */
    private void updateIndicators() {
        for(Indicator indicator : indicatorList) {
            for (ChartData chartData : indicator.getPaintingElements()) {
                Chart<Double> chart = (Chart<Double>) chartData.getChartType();
                chart.setBuffer(chartData.getDataList().subList(currentIndex - numberOfDisplayedElements, currentIndex));
            }
        }
    }
}
