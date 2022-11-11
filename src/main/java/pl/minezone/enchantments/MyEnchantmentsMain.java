package pl.minezone.enchantments;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.minezone.enchantments.config.Config;
import pl.minezone.enchantments.enchantments.EnchantmentUtils;
import pl.minezone.enchantments.events.ArrowHit;
import pl.minezone.enchantments.events.EntityDamage;

public final class MyEnchantmentsMain extends JavaPlugin {

    @Override
    public void onEnable() {
        Config config = new Config();
        config.loadConfig();
        EnchantmentUtils enchantmentUtils = new EnchantmentUtils();
        enchantmentUtils.loadEnchantments();
        registerEvents();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onDisable() {
        EnchantmentUtils enchantmentUtils = new EnchantmentUtils();
        enchantmentUtils.unloadEnchantments();
    }

    private void registerEvents(){
        getServer().getPluginManager().registerEvents(new ArrowHit(), this);
        getServer().getPluginManager().registerEvents(new EntityDamage(), this);
    }
}
