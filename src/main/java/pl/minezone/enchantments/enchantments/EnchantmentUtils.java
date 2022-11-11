package pl.minezone.enchantments.enchantments;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class EnchantmentUtils {
    public static final ExplosiveArrow EXPLOSIVE_ARROW = new ExplosiveArrow(NamespacedKey.minecraft("explosive_arrow"));

    public static final TeleportationArrow TELEPORTATION_ARROW = new TeleportationArrow(NamespacedKey.minecraft("teleportation_arrow"));

    public static ArrayList<Enchantment> getEnchantments() {
        return enchantments;
    }

    private static ArrayList<Enchantment> enchantments = new ArrayList<Enchantment>();

    public void loadEnchantments() {
        enchantments.add(EXPLOSIVE_ARROW);
        enchantments.add(TELEPORTATION_ARROW);
        try{
            try{
                Field f = Enchantment.class.getDeclaredField("acceptingNew");
                f.setAccessible(true);
                f.set(null, true);
            } catch (Exception e) {
                e.printStackTrace();
            }

            for(Enchantment enchantment : enchantments) {
                try {
                    Enchantment.registerEnchantment(enchantment);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void unloadEnchantments() {
        try{
            Field byKeyField = Enchantment.class.getDeclaredField("byKey");
            Field byNameField = Enchantment.class.getDeclaredField("byName");
            byKeyField.setAccessible(true);
            byNameField.setAccessible(true);

            HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) byKeyField.get(null);
            HashMap<NamespacedKey, Enchantment> byName = (HashMap<NamespacedKey, Enchantment>) byNameField.get(null);

            for(Enchantment enchantment : enchantments) {
                if (byKey.containsKey(enchantment.getKey())) {
                    byKey.remove(enchantment.getKey());
                }

                if (byName.containsKey(enchantment.getName())) {
                    byName.remove(enchantment.getName());
                }
            }
        } catch (Exception e) {  }
    }
}
