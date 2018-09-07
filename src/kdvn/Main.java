package kdvn;

import org.bukkit.configuration.file.FileConfiguration;

public class Main extends org.bukkit.plugin.java.JavaPlugin
{
    public static Main plugin;
    private FileConfiguration config = getConfig();

    public Main() {}

    public void onEnable() { plugin = this;
        getCommand("yasuo").setExecutor(new CommandClass());
        SecondConfig.setUpConfig();
        SecondConfig.saveConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();

        org.bukkit.Bukkit.getPluginManager().registerEvents(new KiemYasuo(), this);
    }


    public void onDisable() {}


    public FileConfiguration getConfiguration()
    {
        return config;
    }
}
