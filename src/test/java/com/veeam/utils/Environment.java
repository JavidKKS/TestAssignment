package com.veeam.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Environment {

    public static final String BASE_URL;

    static {

        /**
         * First checks if "env" value was passed from maven command line.
         * If yes, it will use that value.
         * If not, it will use value in local configuration.properties file
         */
        Properties properties = null;
        String environment = System.getProperty("env") != null ? System.getProperty("env") :
                ConfigurationReader.getProperty("env");


        try {
            String path = System.getProperty("user.dir") +
                    "/src/test/resources/env/" + environment + ".properties";

            FileInputStream input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);
            input.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        BASE_URL = properties.getProperty("base_url");
    }
}
