package es.gtorresdev.traderstudy.services;

import candleChart.charts.candle.Candle;
import candleChart.charts.candle.CandleChart;
import es.gtorresdev.traderstudy.indicators.Indicator;
import es.gtorresdev.traderstudy.utils.FileDataLoader;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Clase de servicio responsable de sincronizar los datos del gráfico y los componentes de la interfaz de usuario.
 * Esta clase maneja la inicialización, actualización y gestión de los elementos del gráfico,
 * incluyendo velas e indicadores.
 */
@Service
public class ChartSyncService {

    private final ChartUIManager chartUIManager;
    private final ChartDataManager chartDataManager;
    private final FileDataLoader fileDataLoader;


    /**
     * Construye un nuevo ChartSyncService con las dependencias especificadas.
     *
     * @param chartUIManager  el gestor responsable de manejar las operaciones relacionadas con la interfaz de usuario.
     * @param chartDataManager  el gestor responsable de manejar las operaciones de datos del gráfico.
     * @param fileDataLoader  utilidad para cargar datos de velas desde archivos.
     */
    @Autowired
    public ChartSyncService(ChartUIManager chartUIManager, ChartDataManager chartDataManager, FileDataLoader fileDataLoader) {
        this.chartUIManager = chartUIManager;
        this.chartDataManager = chartDataManager;
        this.fileDataLoader = fileDataLoader;
    }


    /**
     * Inicializa el gráfico dentro del contenedor proporcionado. Este método configura el gráfico de velas (CandleChart)
     * y lo registra con los gestores de interfaz de usuario y de datos.
     *
     * @param container  el Pane en el cual se mostrará el gráfico.
     */
    public void initialize(Pane container) {
        CandleChart candleChart = new CandleChart();
        
        chartUIManager.initialize(container);
        chartUIManager.add(candleChart);
        chartDataManager.add(candleChart);
    }


    /**
     * Establece el número de elementos que se mostrarán en el gráfico.
     *
     * @param numberOfDisplayedElements  el número de elementos a mostrar.
     */
    public void setNumberOfDisplayedElements(int numberOfDisplayedElements) {
        chartDataManager.setNumberOfDisplayedElements(numberOfDisplayedElements);
    }


    /**
     * Abre y carga un gráfico desde la ruta de archivo especificada. Los datos de velas cargados
     * se pasan al ChartDataManager.
     *
     * @param filePath  la ruta al archivo que contiene los datos de velas.
     */
    public void openChart(String filePath) {
        List<Candle> candleChartData = fileDataLoader.loadCandleData(filePath);
        chartDataManager.setCandleList(candleChartData);
    }


    /**
     * Cierra el gráfico actualmente abierto. Este método aún no está implementado.
     */
    public void closeChart() {
        // Método por implementar
    }


    /**
     * Agrega un indicador al gráfico. El indicador se inicializa con los datos de velas actuales
     * y se registra con los gestores de interfaz de usuario y de datos.
     *
     * @param indicator  el indicador a agregar al gráfico
     */
    public void add(@NotNull Indicator indicator) {
        indicator.initialize(chartDataManager.getCandleList());

        chartUIManager.addIndicator(indicator);
        chartDataManager.add(indicator);
    }


    /**
     * Elimina el indicador especificado del gráfico. Este método aún no está implementado.
     *
     * @param indicator  el indicador a eliminar del gráfico
     */
    public void remove(Indicator indicator) {
        // Método por implementar
    }


    /**
     * Elimina el indicador en el índice especificado del gráfico. Este método aún no está implementado.
     *
     * @param index  el índice del indicador a eliminar
     */
    public void remove(int index) {
        // Método por implementar
    }


    /**
     * Navega hacia atrás en los datos del gráfico por un número predefinido de elementos.
     * Este método desplaza el índice actual en el ChartDataManager hacia atrás.
     */
    public void backChartData() {
        chartDataManager.setIndex(chartDataManager.getIndex() - 3);
    }


    /**
     * Navega hacia adelante en los datos del gráfico por un número predefinido de elementos.
     * Este método desplaza el índice actual en el ChartDataManager hacia adelante.
     */
    public void nextChartData() {
        chartDataManager.setIndex(chartDataManager.getIndex() + 3);
    }
}
