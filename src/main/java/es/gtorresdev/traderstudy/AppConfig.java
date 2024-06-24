package es.gtorresdev.traderstudy;

import es.gtorresdev.traderstudy.controllers.SymbolTableController;
import es.gtorresdev.traderstudy.exceptions.SymbolTableExceptionHandler;
import es.gtorresdev.traderstudy.services.SymbolTableService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods=false)
public class AppConfig {

    @Bean
    public SymbolTableService symbolTableService() {
        return new SymbolTableService();
    }

    @Bean
    public SymbolTableExceptionHandler exceptionHandler() {
        return new SymbolTableExceptionHandler();
    }

    @Bean
    public SymbolTableController symbolTableController() {
        return new SymbolTableController(symbolTableService(), exceptionHandler());
    }
}
