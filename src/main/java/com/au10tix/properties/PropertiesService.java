package com.au10tix.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesService {

    public Properties getProperty(String fileName){
        Properties properties = new Properties();
        try {
            FileInputStream input = new FileInputStream(fileName);
            properties.load(input);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
