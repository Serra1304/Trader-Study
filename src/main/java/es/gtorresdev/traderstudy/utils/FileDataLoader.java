package es.gtorresdev.traderstudy.utils;

import candleChart.charts.candle.Candle;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileDataLoader {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
    private static final String CHARSET = StandardCharsets.UTF_16.name();

    public FileDataLoader() {
    }

    public List<Candle> loadCandleData(String filePath) {
        List<Candle> candleList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), CHARSET))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                LocalDateTime dateTime = LocalDateTime.parse(parts[0], FORMATTER);
                double openPrice = Double.parseDouble(parts[1]);
                double highPrice = Double.parseDouble(parts[2]);
                double lowPrice = Double.parseDouble(parts[3]);
                double closePrice = Double.parseDouble(parts[4]);

                Candle candle = new Candle(dateTime, openPrice, highPrice, lowPrice, closePrice);
                candleList.add(candle);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar los datos desde el archivo: " + filePath, e);
        }
        return candleList;
    }
}
