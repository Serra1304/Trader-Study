package es.gtorresdev.traderstudy.controllers;

import candleChart.charts.base.ElementDimension;
import es.gtorresdev.traderstudy.indicators.Indicator;
import es.gtorresdev.traderstudy.services.ChartSyncService;
import es.gtorresdev.traderstudy.utils.ClassFinder;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

@Controller
public class MainController {
    private static final String INDICATOR_PACKAGE_NAME = "es.gtorresdev.traderstudy.indicators";
    private static final String INDICATOR_PREFIX = "INDICATOR:";
    private static final String CHART_PREFIX = "CHART:";

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ListView<Class<?>> indicatorList;

    @Autowired
    private ChartSyncService chartSyncService;

    @FXML
    private void initialize() {
        chartSyncService.initialize(anchorPane);


        populateIndicatorList();
        setupListViewCellFactory();

        anchorPane.addEventFilter(MouseEvent.MOUSE_CLICKED, this::handleMouseClicked); // El anchorPane solicita el foco al hacerse click en el.

        anchorPane.widthProperty().addListener((observableValue, oldValue, newValue) -> {
            int candleDisplayCount = newValue.intValue() / ElementDimension.SMALL.getRelativePosition();
            chartSyncService.setNumberOfDisplayedElements(candleDisplayCount - 1);
        });
    }

    private void populateIndicatorList() {
        Set<Class<?>> classes = ClassFinder.findClassesImplementingInterface(INDICATOR_PACKAGE_NAME, Indicator.class);
        indicatorList.getItems().addAll(classes);
    }

    private void setupListViewCellFactory() {
        indicatorList.setCellFactory(lv -> createListCell());
    }

    @NotNull
    private ListCell<Class<?>> createListCell() {
        ListCell<Class<?>> cell = new ListCell<>() {
            @Override
            protected void updateItem(Class<?> item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getSimpleName());
                }
            }
        };

        cell.setOnDragDetected(this::handleDragDetected);
        cell.setOnDragDone(DragEvent::consume);

        return cell;
    }

    private void handleDragDetected(@NotNull MouseEvent event) {
        Object source = event.getSource();
        if (source instanceof ListCell<?> cell) {
            if (cell.getItem() == null) {
                return;
            }

            Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(INDICATOR_PREFIX + cell.getItem());
            db.setContent(content);

            event.consume();
        }
    }

    @FXML
    public void handleDragOver(@NotNull DragEvent event) {
        if (event.getGestureSource() != anchorPane && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    @FXML
    public void handleDragDropped(@NotNull DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            String content = db.getString();

            if (content.startsWith(CHART_PREFIX)) {
                String filePath = content.substring(6);
                chartSyncService.openChart(filePath);

            } else if (content.startsWith(INDICATOR_PREFIX)) {
                System.out.println("Es un indicador");

                String className = content.substring(16);

                try {
                    Class<?> clazz = Class.forName(className);
                    if (Indicator.class.isAssignableFrom(clazz)) {
                        Class<? extends Indicator> indicatorClass = clazz.asSubclass(Indicator.class);
                        Indicator indicator = indicatorClass.getDeclaredConstructor().newInstance();

                        System.out.println("Es un indicador: " + indicator);
                        chartSyncService.add(indicator);
                    } else {
                        System.out.println("La clase no es un tipo de indicador");
                    }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException |
                         InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            success = true;
        }
        anchorPane.requestFocus();
        event.setDropCompleted(success);
        event.consume();
    }


    @FXML
    private void handleMouseClicked(@NotNull MouseEvent event) {
        anchorPane.requestFocus();
        event.consume();
    }


    @FXML
    private void handleKeyPress(@NotNull KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                chartSyncService.backChartData();
                break;

            case RIGHT:
                chartSyncService.nextChartData();
                break;

            default:
        }
        event.consume();
    }
}
