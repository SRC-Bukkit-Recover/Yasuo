package kdvn;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SafeZone
{
  public SafeZone() {}
  
  private static FileConfiguration dataConfig = SecondConfig.getConfig();
  private static HashMap<String, Location> locationList = new HashMap();
  
  public static void setLocation(Player player, double radius, String name, String world) {
    Location location = player.getLocation();
    double x = location.getX();
    double y = location.getY();
    double z = location.getZ();
    dataConfig.set("Location." + name + ".x", Double.valueOf(x));
    dataConfig.set("Location." + name + ".y", Double.valueOf(y));
    dataConfig.set("Location." + name + ".z", Double.valueOf(z));
    dataConfig.set("Location." + name + ".radius", Double.valueOf(radius));
    dataConfig.set("Location." + name + ".world", world);
    
    List<String> list = dataConfig.getStringList("List");
    list.add(name);
    dataConfig.set("List", list);
    
    SecondConfig.saveConfig();
    player.sendMessage(ChatColor.GREEN + "DONE!");
  }
  

  public static HashMap<String, Location> getLocationList(Player player)
  {
    List<String> list = dataConfig.getStringList("List");
    locationList = new HashMap();
    for (String s : list) {
      Location location = new Location(null, 0.0D, 0.0D, 0.0D);
      location.setX(dataConfig.getDouble("Location." + s + ".x"));
      location.setY(dataConfig.getDouble("Location." + s + ".y"));
      location.setZ(dataConfig.getDouble("Location." + s + ".z"));
      location.setX(dataConfig.getDouble("Location." + s + ".x"));
      locationList.put(s, location);
    }
    
    return locationList;
  }
  
  public static boolean inLocation(Player player) {
    List<String> listLocation = dataConfig.getStringList("List");
    Iterator localIterator = listLocation.iterator(); if (localIterator.hasNext()) { String s = (String)localIterator.next();
      Location location = (Location)getLocationList(player).get(s);
      org.bukkit.World world = org.bukkit.Bukkit.getWorld(dataConfig.getString("Location." + s + ".world"));
      location.setWorld(world);
      double radius = dataConfig.getDouble("Location." + s + ".radius");
      if (player.getLocation().distance(location) > radius)
        return false;
      return true;
    }
    return false;
  }
}
