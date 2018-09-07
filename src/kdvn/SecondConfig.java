package kdvn;

import java.io.IOException;

public class SecondConfig
{
  private static final String fileName = "data.yml";
  
  public SecondConfig() {}
  
  private static java.io.File file = null;
  private static org.bukkit.configuration.file.FileConfiguration secondConfig = null;
  
  public static void setUpConfig() {
    file = new java.io.File(Main.plugin.getDataFolder(), "data.yml");
    if (!file.exists()) {
      try {
        file.createNewFile();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
    secondConfig = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(file);
  }
  
  public static void reloadConfig() {
    secondConfig = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(file);
  }
  
  public static void saveConfig() {
    try {
      secondConfig.save(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public static org.bukkit.configuration.file.FileConfiguration getConfig() {
    if (secondConfig == null) {
      setUpConfig();
    }
    return secondConfig;
  }
}
