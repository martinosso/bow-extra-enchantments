package pl.minezone.enchantments.events;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import org.bukkit.plugin.Plugin;
import pl.minezone.enchantments.MyEnchantmentsMain;
import pl.minezone.enchantments.enchantments.EnchantmentUtils;

public class ArrowHit implements Listener {
    private Plugin plugin = MyEnchantmentsMain.getPlugin(MyEnchantmentsMain.class);
    EnchantmentUtils enchantmentUtils = new EnchantmentUtils();

    private EventUtils eventUtils = new EventUtils();

    @EventHandler
    public void onShoot(ProjectileLaunchEvent e){
        if( e.getEntityType() != EntityType.ARROW && !(e.getEntity().getShooter() instanceof Player) ) {
            return;
        }

        Player shooter = (Player) e.getEntity().getShooter();
        ItemStack itemInHand = shooter.getInventory().getItemInMainHand();

        if(!itemInHand.getType().equals(Material.BOW)){
            return;
        }


        if(itemInHand.getItemMeta().hasEnchant(enchantmentUtils.EXPLOSIVE_ARROW)){
            eventUtils.setEntityMetadata(e.getEntity(), "explosive_arrow",  new FixedMetadataValue(plugin, itemInHand));
        }
        if(itemInHand.getItemMeta().hasEnchant(enchantmentUtils.TELEPORTATION_ARROW)){
            eventUtils.setEntityMetadata(e.getEntity(), "teleportation_arrow", new FixedMetadataValue(plugin, true));
        }

    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e){
        if( !(e.getEntity() instanceof Arrow) &&
                !(e.getEntity().getShooter() instanceof Player) ){
            return;
        }

        Arrow arrow = (Arrow) e.getEntity();
        Player shooter = (Player) arrow.getShooter();

        String metadataKey;

        metadataKey = "explosive_arrow";

        if(arrow.hasMetadata(metadataKey)){
            Location loc = arrow.getLocation();
            float explosionSize = ((ItemStack) eventUtils.getMetadataValue(arrow, metadataKey, 0))
                    .getItemMeta().getEnchantLevel(enchantmentUtils.EXPLOSIVE_ARROW);

            arrow.removeMetadata(metadataKey, plugin);

            eventUtils.createExplosion(loc, explosionSize);
        }

        metadataKey = "teleportation_arrow";

        if(arrow.hasMetadata(metadataKey)){
            if(!shooter.isOnline()) {
                return;
            }

            arrow.removeMetadata(metadataKey, plugin);

            eventUtils.teleportPlayerToArrow(shooter, arrow.getLocation());
        }
    }
}
