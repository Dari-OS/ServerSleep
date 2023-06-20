package me.dari_os.serversleep;

import io.papermc.paper.event.player.PlayerDeepSleepEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.TimeSkipEvent;

public class BedEventHandler implements Listener {

    private static Util ut = new Util();

    @EventHandler
    public void onSleep(PlayerDeepSleepEvent e) {
        if (ut.getBossBar() == null) {
            ut.initBossbar(true);
        } else {
            ut.updateBossbar();
        }

        if (ut.isNightSkippable()) {
            System.out.println(2);
            if (!Bukkit.getWorld("world").isDayTime()) Bukkit.getWorld("world").setTime(0);
            if (Bukkit.getWorld("world").hasStorm()) Bukkit.getWorld("world").setStorm(false);
        }
    }

    @EventHandler
    public void onDay(TimeSkipEvent e) {
        if (ut.getBossBar() != null) ut.showBossbar(false);
    }

    @EventHandler
    public void onBedLeave(PlayerBedLeaveEvent e) {
        if (ut.getBossBar() != null) ut.showBossbar(false);
    }

    public static void showBossb(boolean b) {
        if (ut.getBossBar() != null) ut.showBossbar(b);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (ut.getBossBar() != null) ut.showBossbar(false);
    }

    public static void removeBossBar() {
        ut.destroyBossBar();
    }

}
