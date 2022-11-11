package pl.minezone.enchantments.events;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage implements Listener {

    private EventUtils eventUtils = new EventUtils();

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e){
        if( !(e.getEntity() instanceof HumanEntity) ){
            return;
        }
        if( !e.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) ){
            return;
        }

        HumanEntity humanEntity = (HumanEntity) e.getEntity();
        double health = humanEntity.getHealth();

        if( !(health - e.getDamage()>0) ){

            if(eventUtils.getLastDamager(humanEntity)==null){
                return;
            }

            e.setCancelled(true);
            e.getEntity().setLastDamageCause((EntityDamageEvent) eventUtils.getLastDamager(humanEntity));
            humanEntity.setLastDamage(e.getDamage());
        }

    }

    @EventHandler
    public void playerHitEntity(EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof HumanEntity) && !(e.getDamager() instanceof Player)){
            return;
        }

        HumanEntity entity = (HumanEntity) e.getEntity();
        Player player = (Player) e.getDamager();
        eventUtils.setLastDamager(entity, player);
    }
}
