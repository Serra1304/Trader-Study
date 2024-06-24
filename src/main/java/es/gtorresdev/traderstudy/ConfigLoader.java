package es.gtorresdev.traderstudy;

import es.gtorresdev.traderstudy.exceptions.ConfigFileNotFoundException;
import es.gtorresdev.traderstudy.exceptions.PropertyNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Clase responsable de cargar configuraciones desde un archivo de propiedades.
 */
public class ConfigLoader {
    private static final String CONFIG_FILE_PATH = "config/config.properties";
    private static final String DATA_BASE_KEY = "dataBasePath";


    /**
     * Obtiene la ruta de la base de datos desde el archivo de configuración.
     *
     * @return La ruta de la base de datos.
     * @throws ConfigFileNotFoundException Si no se encuentra el archivo de configuración.
     * @throws IOException Si ocurre un error al leer el archivo de configuración.
     * @throws PropertyNotFoundException Si no se encuentra la propiedad especificada.
     */
    public static String getDataBasePath() throws ConfigFileNotFoundException, IOException, PropertyNotFoundException {
        Properties properties = loadProperties();
        return getProperty(properties, DATA_BASE_KEY);
    }


    /**
     * Carga las propiedades desde el archivo de configuración.
     *
     * @return Las propiedades cargadas.
     * @throws ConfigFileNotFoundException Si no se encuentra el archivo de configuración.
     * @throws IOException Si ocurre un error al leer el archivo de configuración.
     */
    private static Properties loadProperties() throws ConfigFileNotFoundException, IOException {
        Properties properties = new Properties();
        InputStream fileConfig = ConfigLoader.class.getResourceAsStream(CONFIG_FILE_PATH);

        if (fileConfig == null) {
            throw new ConfigFileNotFoundException("No se encontró el archivo de configuración.");
        }

        properties.load(fileConfig);
        return properties;
    }

    /**
     * Obtiene una propiedad específica de las propiedades cargadas.
     *
     * @param properties Las propiedades cargadas.
     * @param key La clave de la propiedad.
     * @return El valor de la propiedad.
     * @throws PropertyNotFoundException Si no se encuentra la propiedad especificada.
     */
    private static String getProperty(Properties properties, String key) throws PropertyNotFoundException {
        String value = properties.getProperty(key);

        if (value == null) {
            throw new PropertyNotFoundException("No se encontró la propiedad: " + key);
        }
        return value;
    }
}
