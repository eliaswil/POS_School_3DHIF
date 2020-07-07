/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 *
 * @author SF <htlkaindorf.at>
 */
public class DB_Properties {

  private static final Properties PROPS = new Properties();

  static {
    FileInputStream fis = null;
    try {
      Path propertyFile = Paths.get(System.getProperty("user.dir"), "src", "res", "dbConnect.properties");
      fis = new FileInputStream(propertyFile.toFile());
      PROPS.load(fis);
    } catch (IOException ex) {
      ex.printStackTrace();
    } finally {
      try {
        fis.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }
  
  public static String getPropertyValue(String key) {
    return PROPS.getProperty(key);
  }

}
