package es.gtorresdev.traderstudy.services;

import candleChart.charts.base.Chart;
import candleChart.charts.base.ChartBase;
import es.gtorresdev.traderstudy.indicators.Indicator;
import es.gtorresdev.traderstudy.models.ChartData;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.swing.*;


/**
 * Componente encargado de gestionar la interfaz gráfica del gráfico de velas.
 * Esta clase administra la integración del gráfico de velas basado en Swing dentro de un contenedor JavaFX.
 * Permite agregar gráficos y gestionar su visualización en la interfaz.
 */
@Component
public class ChartUIManager {
    private final ChartBase chartBase;
    private final SwingNode swingNode;

    private final AnchorPane anchorPane;


    /**
     * Constructor que inicializa los componentes gráficos necesarios para la visualización del gráfico de velas.
     * Este constructor crea un contenedor `AnchorPane` que alberga un `SwingNode`, el cual es responsable de contener
     * el componente Swing del gráfico (`ChartBase`).
     */
    public ChartUIManager() {
        chartBase = new ChartBase();
        anchorPane = new AnchorPane();

        swingNode = new SwingNode();
        SwingUtilities.invokeLater(() -> {
            swingNode.setContent(chartBase);
        });

        anchorPane.getChildren().add(swingNode);
        AnchorPane.setTopAnchor(swingNode, 0.0);
        AnchorPane.setBottomAnchor(swingNode, 0.0);
        AnchorPane.setLeftAnchor(swingNode, 0.0);
        AnchorPane.setRightAnchor(swingNode, 0.0);

        AnchorPane.setTopAnchor(anchorPane, 0.0);
        AnchorPane.setBottomAnchor(anchorPane, 0.0);
        AnchorPane.setLeftAnchor(anchorPane, 0.0);
        AnchorPane.setRightAnchor(anchorPane, 0.0);
    }


    /**
     * Inicializa el contenedor de la interfaz gráfica con el gráfico de velas.
     * Este método añade el `AnchorPane` que contiene el gráfico de velas al contenedor especificado.
     *
     * @param container el contenedor JavaFX donde se mostrará el gráfico de velas.
     */
    public void initialize(@NotNull Pane container) {
        container.getChildren().add(anchorPane);
    }


    /**
     * Añade un gráfico específico al gráfico base.
     * Este método se encarga de agregar un nuevo gráfico (`Chart`) al gráfico base (`ChartBase`)
     * y de actualizar la interfaz gráfica de usuario para reflejar los cambios.
     *
     * @param chart el gráfico a añadir.
     */
    public void add(Chart<?> chart) {
        chartBase.addChart(chart);
        SwingUtilities.updateComponentTreeUI(chartBase);
    }


    /**
     * Añade los gráficos del indicador al gráfico base actual.
     * Este método recorre los elementos gráficos del indicador y los añade al gráfico base (`ChartBase`).
     *
     * @param indicator indicador a añadir al gráfico base.
     */
    public void addIndicator(@NotNull Indicator indicator) {
        for (ChartData chart : indicator.getPaintingElements()) {
            chartBase.addChart(chart.getChartType());
        }
        SwingUtilities.updateComponentTreeUI(chartBase);
    }
}
