package es.gtorresdev.traderstudy.models;

public class SymbolTable {
    private final String filePath;
    private String symbol;
    private long totalRecords;

    public SymbolTable(String filePath, String symbol, long totalRecords) {
        this.filePath = filePath;
        this.symbol = symbol;
        this.totalRecords = totalRecords;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public String getFile() {
        return filePath;
    }
}
