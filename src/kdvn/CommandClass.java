package kdvn;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandClass implements org.bukkit.command.CommandExecutor
{
  public CommandClass() {}
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if ((cmd.getName().equalsIgnoreCase("yasuo")) && (sender.hasPermission("yasuo.*"))) {
      if (args.length == 0) {
        sender.sendMessage(ChatColor.YELLOW + "1. /yasuo setitem");
        sender.sendMessage(ChatColor.YELLOW + "2. /yasuo setlocxoaydamage <damage>");
        sender.sendMessage(ChatColor.YELLOW + "3. /yasuo setluotdamage <damage>");
        sender.sendMessage(ChatColor.YELLOW + "4. /yasuo safezone <ban_kinh> <ten_khu_vuc>");

      }
      else if (args[0].equalsIgnoreCase("setlocxoaydamage")) {
        try {
          int satThuong = Integer.parseInt(args[1]);
          Player player = (Player)sender;
          KiemYasuo.addDamageLore(player.getInventory().getItemInMainHand(), satThuong);
        }
        catch (Exception e) {
          sender.sendMessage(ChatColor.RED + "Xãy ra lỗi");
        }
      }
      else if (args[0].equalsIgnoreCase("setluotdamage")) {
        try {
          int satThuong = Integer.parseInt(args[1]);
          Player player = (Player)sender;
          KiemYasuo.addEYasuoDamage(player.getInventory().getItemInMainHand(), satThuong);
        }
        catch (Exception e) {
          sender.sendMessage(ChatColor.RED + "Xãy ra lỗi");
        }
      }
      else if (args[0].equalsIgnoreCase("setitem")) {
        Player player = (Player)sender;
        KiemYasuo.setItem(player.getInventory().getItemInMainHand());

      }
      else if (args[0].equalsIgnoreCase("safezone")) {
        int radius = Integer.parseInt(args[1]);
        Player player = (Player)sender;
        SafeZone.setLocation(player, radius, args[2], player.getWorld().getName());
      }
    }
    
    return false;
  }
}
