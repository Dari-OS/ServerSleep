package me.dari_os.serversleep;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    protected BossBar bossbar;
    protected static Main main;
    @Override
    public void onEnable() {
        main = this;
        Bukkit.getPluginManager().registerEvents(new BedEventHandler(), this);
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic
        BedEventHandler.removeBossBar();
    }
}
