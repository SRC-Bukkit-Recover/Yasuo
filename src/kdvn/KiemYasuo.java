package kdvn;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class KiemYasuo implements org.bukkit.event.Listener
{
  private static final String CHEM_1 = ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("ChemLan1"));
  private static final String CHEM_2 = ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("ChemLan2"));
  private static final String CHEM_3 = ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("ChemLan3"));
  private static final String E_DAMAGE_LORE = ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("LuotLore"));
  private static final int E_DELAY = Main.plugin.getConfig().getInt("LuotDelay");

  private static final String loreConfig = Main.plugin.getConfig().getString("Lore");
  private static final Particle particle = Particle.valueOf(Main.plugin.getConfig().getString("Particle"));
  private static final String LORE = ChatColor.translateAlternateColorCodes('&', loreConfig);

  private static final String DAMAGE_LORE = ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("DamageLore"));

  private static HashMap<Player, List<Location>> playerLocation = new HashMap();
  private static HashMap<Player, Integer> triggeredPlayer = new HashMap();
  private static HashMap<Player, List<LivingEntity>> eYasuoList = new HashMap();
  private static HashMap<Player, Integer> playerCount = new HashMap();
  public static HashMap<Player, Integer> playerSche = new HashMap();

  private static List<Player> dangBanLocXoay = new ArrayList();
  public static int sche;

  public void fireEffect(Player player, final double damage) { addLocationToPlayer(player);
    final List<Location> loList = getListLocation(player);

    if (playerCount.containsKey(player)) {
      playerCount.put(player, Integer.valueOf(0));
    }
    playerCount.put(player, Integer.valueOf(0));
    sche = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
      public void run() {
        KiemYasuo.playerCount.put(player, Integer.valueOf(((Integer) KiemYasuo.playerCount.get(player)).intValue() + 1));
        if (((Integer) KiemYasuo.playerCount.get(player)).intValue() == 50) {
          KiemYasuo.removePlayerFromLocationList(player);
          KiemYasuo.removeDangBanLocXoay(player);
          KiemYasuo.playerCount.put(player, Integer.valueOf(0));
          int temp = ((Integer) KiemYasuo.playerSche.get(player)).intValue();
          KiemYasuo.playerSche.remove(player);
          Bukkit.getScheduler().cancelTask(temp);
        }
        Iterator<LivingEntity> localIterator2;
        for (Iterator localIterator1 = loList.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
        {
          Location lo = (Location)localIterator1.next();
          lo.add(lo.getDirection().getX(), 0.0D, lo.getDirection().getZ());
          float offset = loList.indexOf(lo) * 0.2F;
          lo.getWorld().spawnParticle(particle, lo, 5, offset, offset, offset, 0.05F);
          localIterator2 = KiemYasuo.getEntityByLocation(lo, 2.0D).iterator();
          if (!localIterator2.hasNext()) continue;
          for (Iterator<LivingEntity> it = localIterator2; it.hasNext(); ) {
            LivingEntity e = it.next();
            if (!e.equals(player))
            {
              e.setVelocity(new Vector(0.0F, 1.1F, 0.0F));
              e.damage(damage, player);
            }
          }

        }

      }
    }, 0L, 1L);
    playerSche.put(player, Integer.valueOf(sche));
  }

  public static void eYasuo(final Player player, LivingEntity e, float damage)
  {
    if (isTargetYasuo(player, e)) {
      player.sendMessage(ChatColor.RED + "Bạn chưa thể lướt tới mục tiêu này tiếp");
      return;
    }
    triggerPlayerEYasuo(player, e);
    player.setVelocity(player.getLocation().getDirection().multiply(2.5F));
    e.damage(damage, player);
    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
      public void run() {
        KiemYasuo.removeEYasuo(e, player);
      }
    }, E_DELAY * 20);
  }

  public static void addBanLocXoay(Player player)
  {
    if (!dangBanLocXoay.contains(player)) {
      dangBanLocXoay.add(player);
    }
  }

  public static void removeDangBanLocXoay(Player player) {
    if (dangBanLocXoay.contains(player)) {
      dangBanLocXoay.remove(player);
    }
  }

  public static void removeEYasuo(LivingEntity e, Player player)
  {
    if (!isTargetYasuo(player, e)) {
      return;
    }
    List<LivingEntity> list = (List)eYasuoList.get(player);
    list.remove(e);
    eYasuoList.put(player, list);
  }

  public static void addLocationToPlayer(Player player) {
    if (playerLocation.containsKey(player)) {
      return;
    }
    List<Location> loList = new ArrayList();
    Location mainLocation = player.getLocation();
    Location location1 = player.getLocation().add(0.0D, 1.0D, 0.0D);
    Location location2 = player.getLocation().add(0.0D, 2.0D, 0.0D);
    Location location3 = player.getLocation().add(0.0D, 3.0D, 0.0D);
    Location location4 = player.getLocation().add(0.0D, 4.0D, 0.0D);
    Location location5 = player.getLocation().add(0.0D, 5.0D, 0.0D);
    Location location6 = player.getLocation().add(0.0D, -1.0D, 0.0D);
    Location location7 = player.getLocation().add(0.0D, -2.0D, 0.0D);

    loList.add(location6);
    loList.add(location7);
    loList.add(mainLocation);
    loList.add(location1);
    loList.add(location2);
    loList.add(location3);
    loList.add(location4);
    loList.add(location5);
    playerLocation.put(player, loList);
  }

  public static List<Location> getListLocation(Player player) {
    return (List)playerLocation.get(player);
  }

  public static void addEYasuoDamage(ItemStack item, int damage) {
    ItemMeta meta = item.getItemMeta();
    if (item.getItemMeta().hasLore()) {
      List<String> lore = item.getItemMeta().getLore();
      lore.add(E_DAMAGE_LORE + damage);
      meta.setLore(lore);
      item.setItemMeta(meta);
    }
    else {
      List<String> lore = new ArrayList();
      lore.add(E_DAMAGE_LORE + damage);
      meta.setLore(lore);
      item.setItemMeta(meta);
    }
  }

  public static float getEYasuoDamage(ItemStack item) {
    float damage = 0.0F;
    if (!item.getItemMeta().hasLore()) {
      return 0.0F;
    }
    ItemMeta meta = item.getItemMeta();
    List<String> lore = meta.getLore();
    for (int i = 0; i < lore.size(); i++) {
      if (((String)lore.get(i)).contains(E_DAMAGE_LORE)) {
        String damageLore = (String)lore.get(i);
        String satThuongChar = damageLore.substring(damageLore.lastIndexOf(" ") + 1, damageLore.length());
        try {
          int satThuong = Integer.parseInt(satThuongChar);
          damage = satThuong;
        }
        catch (Exception e) {
          return 0.0F;
        }
      }
    }


    return damage;
  }

  public static void removePlayerFromLocationList(Player player) {
    if (playerLocation.containsKey(player)) {
      playerLocation.remove(player);
    }
  }

  public static boolean isTargetYasuo(Player mainPlayer, LivingEntity e) {
    if (!eYasuoList.containsKey(mainPlayer)) {
      return false;
    }
    List<LivingEntity> list = (List)eYasuoList.get(mainPlayer);
    if (list.contains(e)) {
      return true;
    }
    return false;
  }

  public static void triggerPlayerEYasuo(Player player, LivingEntity e) {
    if (isTargetYasuo(player, e)) {
      return;
    }
    List<LivingEntity> list = new ArrayList();
    list.add(e);
    eYasuoList.put(player, list);
  }

  public static List<LivingEntity> getEntityByLocation(Location location, double radius) {
    List<LivingEntity> entityList = new ArrayList();
    for (Entity e : location.getWorld().getEntities()) {
      if ((location.distance(e.getLocation()) <= radius) &&
        ((e instanceof LivingEntity))) {
        entityList.add((LivingEntity)e);
      }
    }

    return entityList;
  }

  public static void addDamageLore(ItemStack item, int damage) {
    ItemMeta meta = item.getItemMeta();
    if (item.getItemMeta().hasLore()) {
      List<String> lore = item.getItemMeta().getLore();
      lore.add(DAMAGE_LORE + damage);
      meta.setLore(lore);
      item.setItemMeta(meta);
    }
    else {
      List<String> lore = new ArrayList();
      lore.add(DAMAGE_LORE + damage);
      meta.setLore(lore);
      item.setItemMeta(meta);
    }
  }

  public static void setItem(ItemStack item) {
    ItemMeta meta = item.getItemMeta();
    if (item.getItemMeta().hasLore()) {
      List<String> lore = item.getItemMeta().getLore();
      lore.add(LORE);
      meta.setLore(lore);
      item.setItemMeta(meta);
    }
    else {
      List<String> lore = new ArrayList();
      lore.add(LORE);
      meta.setLore(lore);
      item.setItemMeta(meta);
    }
  }

  public static float getDamageThroughItem(ItemStack item) {
    float damage = 0.0F;
    if (!item.getItemMeta().hasLore()) {
      return 0.0F;
    }
    ItemMeta meta = item.getItemMeta();
    List<String> lore = meta.getLore();
    for (int i = 0; i < lore.size(); i++) {
      if (((String)lore.get(i)).contains(DAMAGE_LORE)) {
        String damageLore = (String)lore.get(i);
        String satThuongChar = damageLore.substring(damageLore.lastIndexOf(" ") + 1, damageLore.length());
        try {
          int satThuong = Integer.parseInt(satThuongChar);
          damage = satThuong;
        }
        catch (Exception e) {
          return 0.0F;
        }
      }
    }


    return damage;
  }

  public static boolean isKiemYasuo(ItemStack i) {
    if (i == null) {
      return false;
    }
    if (!i.getItemMeta().hasLore()) {
      return false;
    }
    if (i.getItemMeta().getLore().contains(LORE)) {
      return true;
    }
    return false;
  }

  @EventHandler
  public void onInteract(PlayerInteractEvent e) {
    if (e.getHand() != EquipmentSlot.HAND) {
      return;
    }
    if ((e.getAction() != Action.LEFT_CLICK_AIR) && (e.getAction() != Action.LEFT_CLICK_BLOCK)) {
      return;
    }
    if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR) {
      return;
    }
    ItemStack item = e.getItem();
    if (!item.getItemMeta().hasLore()) {
      return;
    }
    if (!isKiemYasuo(item)) {
      return;
    }
    Player player = e.getPlayer();
    if (dangBanLocXoay.contains(player)) {
      return;
    }
    if (SafeZone.inLocation(player)) {
      return;
    }
    if (!triggeredPlayer.containsKey(player)) {
      triggeredPlayer.put(player, Integer.valueOf(1));
      player.sendMessage(CHEM_1);
    }
    else {
      int i = ((Integer)triggeredPlayer.get(player)).intValue();
      if (i == 1) {
        triggeredPlayer.put(player, Integer.valueOf(2));
        player.sendMessage(CHEM_2);
      }
      else if (i == 2) {
        addBanLocXoay(player);
        triggeredPlayer.remove(player);
        player.sendMessage(CHEM_3);
        fireEffect(player, getDamageThroughItem(item));
      }
    }
  }
  
  @EventHandler
  public void onInteractToEntity(PlayerInteractAtEntityEvent e) {
    if (e.getHand() != EquipmentSlot.HAND) {
      return;
    }
    if (SafeZone.inLocation(e.getPlayer())) {
      return;
    }
    Entity entity = e.getRightClicked();
    if (!(entity instanceof LivingEntity)) {
      return;
    }
    if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR) {
      return;
    }
    ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
    if (!isKiemYasuo(item)) {
      return;
    }
    if (isTargetYasuo(e.getPlayer(), (LivingEntity)entity)) {
      e.getPlayer().sendMessage(ChatColor.RED + "Bạn chưa thể lướt tới mục tiêu này tiếp");
      return;
    }
    eYasuo(e.getPlayer(), (LivingEntity)entity, getEYasuoDamage(item));
  }
}
