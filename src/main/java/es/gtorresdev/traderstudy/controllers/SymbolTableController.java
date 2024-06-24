package es.gtorresdev.traderstudy.controllers;

import es.gtorresdev.traderstudy.exceptions.SymbolTableExceptionHandler;
import es.gtorresdev.traderstudy.models.SymbolTable;
import es.gtorresdev.traderstudy.services.SymbolTableService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;


/**
 * Controlador para gestionar la vista de SymbolTable.
 * Este controlador maneja la lógica de inicialización e interacción para la tabla de símbolos.
 * Utiliza {@link SymbolTableService} para cargar datos y {@link SymbolTableExceptionHandler} para manejar excepciones.
 *
 * @author GTorresDev.es
 */
public class SymbolTableController {
    private final SymbolTableService symbolTableService;
    private final SymbolTableExceptionHandler exceptionHandler;

    @FXML
    private TableView<SymbolTable> symbolTable;

    @FXML
    private TableColumn<SymbolTable, String> symbolColumn;

    @FXML
    private TableColumn<SymbolTable, Integer> totalRecordsColumn;


    /**
     * Constructor para inyectar dependencias SymbolTableService y SymbolTableExceptionHandler.
     *
     * @param symbolTableService la dependencia SymbolTableService
     * @param exceptionHandler la dependencia SymbolTableExceptionHandler
     */
    public SymbolTableController(SymbolTableService symbolTableService, SymbolTableExceptionHandler exceptionHandler) {
        this.symbolTableService = symbolTableService;
        this.exceptionHandler = exceptionHandler;
    }


    /**
     * Inicializa el SymbolTableController.
     * Este método se llama después de que los campos FXML han sido inyectados.
     * Configura las columnas de la tabla, establece el ajuste automático de las columnas y carga los datos de la tabla.
     */
    @FXML
    private void initialize() {
        configureTableColumns();
        setupColumnAutoResize();
        loadTableData();
    }


    /**
     * Configura las columnas de la tabla.
     * Establece las fábricas de valores de celda para las columnas de símbolo y de registros totales.
     */
    private void configureTableColumns() {
        symbolColumn.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        totalRecordsColumn.setCellValueFactory(new PropertyValueFactory<>("totalRecords"));
    }


    /**
     * Configura el ajuste automático de las columnas de la tabla.
     * Distribuye el ancho de la tabla uniformemente entre todas las columnas.
     */
    private void setupColumnAutoResize() {
        symbolTable.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            double columnWidth = newWidth.doubleValue() / symbolTable.getColumns().size();
            for (TableColumn<SymbolTable, ?> column : symbolTable.getColumns()) {
                column.setPrefWidth(columnWidth);
            }
        });
    }


    /**
     * Carga los datos de la tabla de símbolos.
     * Este método recupera los datos del servicio de tabla de símbolos y los establece en la vista de tabla.
     * Si ocurre una excepción, es manejada por el manejador de excepciones.
     */
    private void loadTableData() {
        try {
            List<SymbolTable> items = symbolTableService.loadItemsSymbols();
            ObservableList<SymbolTable> itemList = FXCollections.observableArrayList(items);
            symbolTable.setItems(itemList);
        } catch (Exception e) {
            exceptionHandler.handleLoadExceptions(e);
        }
    }
}
