package com.insurgencedev.bossbaraddon.listeners;

import com.insurgencedev.bossbaraddon.settings.MyConfig;
import com.insurgencedev.bossbaraddon.utils.BossBarUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.insurgencedev.insurgenceboosters.data.BoosterData;
import org.insurgencedev.insurgenceboosters.events.IBoosterEndEvent;
import org.insurgencedev.insurgenceboosters.events.IBoosterStartEvent;
import org.insurgencedev.insurgenceboosters.libs.fo.Common;
import org.insurgencedev.insurgenceboosters.libs.fo.remain.Remain;

public final class BoosterEventListener implements Listener {

    @EventHandler
    private void onStart(IBoosterStartEvent event) {
        BoosterData data = event.getBoosterData();
        if (data.getScope().equalsIgnoreCase(MyConfig.scope)) {
            Common.runLater(1, () -> BossBarUtil.sendBossBar(event.getPlayer()));
        }
    }

    @EventHandler
    private void onEnd(IBoosterEndEvent event) {
        BoosterData data = event.getBoosterData();

        if (data.getScope().equalsIgnoreCase(MyConfig.scope)) {
            Player player = event.getPlayer();
            Common.runLater(1, () -> {
                if (BossBarUtil.isCurrentType(player, data.getType())) {
                    if (data.getTimeLeft() > 0) {
                        BossBarUtil.removeBossBar(player);
                    }
                    BossBarUtil.remove(player);
                }

                BossBarUtil.sendBossBar(player);
            });
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

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onDisable(PluginDisableEvent event) {
        if (event.getPlugin().equals(Bukkit.getPluginManager().getPlugin("IBoosters"))) {
            Remain.getOnlinePlayers().forEach(BossBarUtil::remove);
        }
    }
}