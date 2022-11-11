package pl.minezone.enchantments;

import org.bukkit.plugin.java.JavaPlugin;
import pl.minezone.enchantments.config.Config;
import pl.minezone.enchantments.enchantments.EnchantmentUtils;
import pl.minezone.enchantments.events.ArrowHit;

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
        this.getServer().getPluginManager().registerEvents(new ArrowHit(), this);

    }
}
