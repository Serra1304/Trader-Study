package es.gtorresdev.traderstudy.services;

import es.gtorresdev.traderstudy.ConfigLoader;
import es.gtorresdev.traderstudy.exceptions.DirectoryNotFoundException;
import es.gtorresdev.traderstudy.exceptions.SymbolTableExceptionHandler;
import es.gtorresdev.traderstudy.models.SymbolTable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SymbolTableService {

    public List<SymbolTable> loadItemsSymbols() {
        List<SymbolTable> items = new ArrayList<>();

        try {
            String folderPath = ConfigLoader.getDataBasePath();

            File folder = new File(folderPath);
            if (!folder.isDirectory()) {
                throw new DirectoryNotFoundException("La ruta especificada no es una carpeta.");
            }

            File[] listOfFiles = folder.listFiles();
            if (listOfFiles == null) {
                throw new DirectoryNotFoundException("La carpeta '" + folder.getName() + "' está vacía o no se puede leer.");
            }

            for (File file : listOfFiles) {
                if (file.isFile()) {
                    items.add(new SymbolTable(getFileName(file), getTotalRecords(file)));
                }
            }
        } catch (Exception e) {
            new SymbolTableExceptionHandler().handleLoadExceptions(e);
        }
        return items;
    }

    private String getFileName(File file) {
        return file.getName().substring(0, file.getName().lastIndexOf('.'));
    }

    private long getTotalRecords(File file) {
        int samplingLines = 10;
        int countLines = 0;
        int totalBytes = 0;
        String fileLine;

        try {
            // Se obtiene el número de bytes para hacer el promedio del tamaño de línea.
            BufferedReader reader = new BufferedReader(new FileReader(file));

            while ((fileLine = reader.readLine()) != null && countLines < samplingLines) {
                totalBytes += fileLine.getBytes().length;
                countLines++;
            }
            reader.close();

            // Si el fichero se encuentra vació retorna 0.
            if (countLines == 0) {
                return 0;
            }

            // Estimación del número de líneas del fichero.
            double byteAverage = (double) totalBytes / countLines;
            return (long) (file.length() / byteAverage);

        } catch (Exception e) {
            new SymbolTableExceptionHandler().handleLoadExceptions(e);
        }
        return 0;
    }
}
