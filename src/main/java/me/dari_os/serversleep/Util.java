package me.dari_os.serversleep;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class Util {

    private static double onlinePlayers = 0;
    private static double sleepingPlayers = 0;
    private final Main main = Main.main;
    public void initBossbar(Boolean b) {
        updatePlayers();
        int leftToSleep = 0;
        if(main.getConfig().getBoolean("RoundUp")) {
            leftToSleep = (int) (Math.ceil(onlinePlayers/2.0) - sleepingPlayers);
        } else {
            leftToSleep = (int) (Math.floor(onlinePlayers/2.0) - sleepingPlayers);
        }
        try {
        main.bossbar = Bukkit.createBossBar(
                new TextComponent(main.getConfig().getString("ColorOfNum") + leftToSleep).toLegacyText() + " "
                        + new TextComponent(main.getConfig().getString("BossBarSleepMessage")).toLegacyText(),
                BarColor.valueOf(main.getConfig().getString("BossBarColor")),
                BarStyle.SOLID
        ); } catch (IllegalArgumentException e) {
            Bukkit.getLogger().severe("[ServerSleep] \"" + main.getConfig().getString("BossBarColor") + "\" isn´t recognized as color!\n" +
                    "Please change the config.yml and refer to this colors:\nhttps://hub.spigotmc.org/javadocs/spigot/org/bukkit/boss/BarColor.html");

            main.bossbar = Bukkit.createBossBar( //sets default values in order to keep the plugin functioning
                    new TextComponent(main.getConfig().getString("ColorOfNum") + leftToSleep) + " "
                            + new TextComponent(main.getConfig().getString("BossBarSleepMessage")).toLegacyText(),
                    BarColor.BLUE,
                    BarStyle.SOLID
            );
        }
        if (b != null) {
            showBossbar(b);
        }
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player, Sound.BLOCK_AMETHYST_BLOCK_STEP, 1, 10);
                main.bossbar.addPlayer(player);
            }
        main.bossbar.setProgress(calcProgress());
    }

    public void updateBossbar() {
        updatePlayers();
        int leftToSleep = 0;
        if(main.getConfig().getBoolean("RoundUp")) {
            leftToSleep = (int) (Math.ceil(onlinePlayers/2.0) - sleepingPlayers);
        } else {
            leftToSleep = (int) (Math.floor(onlinePlayers/2.0) - sleepingPlayers);
        }
        main.bossbar.setTitle(new TextComponent(main.getConfig().getString("ColorOfNum") + leftToSleep).toLegacyText() + " "
                + new TextComponent(main.getConfig().getString("BossBarSleepMessage")).toLegacyText());
        main.bossbar.setProgress(calcProgress());
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player, Sound.BLOCK_AMETHYST_BLOCK_STEP, 1, 10);
            main.bossbar.addPlayer(player);
        }
        showBossbar(true);
    }

    public void showBossbar(boolean bol) {
        main.bossbar.setVisible(bol);
    }

    public boolean isNightSkippable() {
        updatePlayers();
        if(main.getConfig().getBoolean("RoundUp")) {
            if (Math.ceil(onlinePlayers/2.0) - sleepingPlayers <= 0) return true;
        } else {
            if (Math.floor(onlinePlayers/2.0)  - sleepingPlayers <= 0) return true;
        }

        return false;
    }
    public void updatePlayers() {
        onlinePlayers = 0;
        sleepingPlayers = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            onlinePlayers++;
            if (player.isSleeping()) sleepingPlayers++;
        }
    }

    public double calcProgress() {
        double retValue = 0;
        updatePlayers();
        if(main.getConfig().getBoolean("RoundUp")) {
            retValue = sleepingPlayers / Math.ceil(onlinePlayers / 2.0);
        } else {
            retValue = sleepingPlayers / Math.floor(onlinePlayers / 2.0);
        }
        if (retValue > 1 || retValue > 0) return 1;
        return retValue;
    }


    public BossBar getBossBar() {
        return main.bossbar;
    }

    public int getSleeping() {
        return (int) sleepingPlayers;
    }

    public void destroyBossBar () { //prevents exceptions from occurring if you do / reload
        if (main.bossbar != null) main.bossbar.removeAll();
    }
}
