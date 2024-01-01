package com.insurgencedev.bossbaraddon.listeners;

import com.insurgencedev.bossbaraddon.utils.BossBarUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.insurgencedev.insurgenceboosters.events.IBoosterEndEvent;
import org.insurgencedev.insurgenceboosters.events.IBoosterStartEvent;
import org.insurgencedev.insurgenceboosters.libs.fo.Common;

public final class BoosterEventListener implements Listener {

    @EventHandler
    private void onStart(IBoosterStartEvent event) {
        if (event.getBoosterData().getScope().equalsIgnoreCase("global")) {
            Common.runLater(1, () -> BossBarUtil.sendBossBar(event.getPlayer()));
        }
    }

    @EventHandler
    private void onEnd(IBoosterEndEvent event) {
        if (event.getBoosterData().getScope().equalsIgnoreCase("global")) {
            BossBarUtil.bossBarPlayers.clear();
            Common.runLater(1, () -> BossBarUtil.sendBossBar(event.getPlayer()));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onJoin(PlayerJoinEvent event) {
        Common.runLater(1, () -> BossBarUtil.sendBossBar(event.getPlayer()));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onLeave(PlayerQuitEvent event) {
        Common.runLater(1, () -> BossBarUtil.remove(event.getPlayer()));
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onKick(PlayerKickEvent event) {
        Common.runLater(1, () -> BossBarUtil.remove(event.getPlayer()));
    }
}
