package pl.minezone.enchantments.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import pl.minezone.enchantments.MyEnchantmentsMain;

import java.util.HashMap;

public class EventUtils {

    private Plugin plugin = MyEnchantmentsMain.getPlugin(MyEnchantmentsMain.class);

    public Player getLastDamager(HumanEntity humanEntity){
        return lastDamager.get(humanEntity);
    }

    public static HashMap<HumanEntity, Player> lastDamager;

    public void setLastDamager(HumanEntity entity, Player damager){
        if(lastDamager.containsKey(entity) && !lastDamager.get(entity).equals(damager)) {
            lastDamager.remove(entity);
        }else if(lastDamager.get(entity).equals(damager)){
            return;
        }
        lastDamager.put(entity, damager);

        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                lastDamager.remove(entity);
            }}, 20*10);
    }

    public void setEntityMetadata(Entity entity, String metadataKey, MetadataValue metadataValue){
        entity.setMetadata(metadataKey, metadataValue);
    }

    public Object getMetadataValue(Entity entity, String metadataKey, int metadataValueIndex){
        return entity.getMetadata(metadataKey).get(metadataValueIndex).value();
    }

    public void createExplosion(Location location, Float explosionSize){
        World world = location.getWorld();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        world.createExplosion(
                x,
                y,
                z,
                (float) (explosionSize*0.4), //size
                false, // set fire
                false); //block break
    }

    public void teleportPlayerToArrow(Player player, Location arrowLocation){
        Location loc = arrowLocation;
        loc.setYaw(player.getLocation().getYaw());
        loc.setPitch(player.getLocation().getPitch());
        loc.setDirection(player.getLocation().getDirection());

        player.teleport(loc);
    }

}
