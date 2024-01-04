package com.insurgencedev.bossbaraddon.utils;

import com.insurgencedev.bossbaraddon.settings.MyConfig;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.Player;
import org.insurgencedev.insurgenceboosters.api.IBoosterAPI;
import org.insurgencedev.insurgenceboosters.libs.fo.model.Replacer;
import org.insurgencedev.insurgenceboosters.libs.fo.remain.CompBarStyle;
import org.insurgencedev.insurgenceboosters.libs.fo.remain.Remain;
import org.insurgencedev.insurgenceboosters.models.booster.GlobalBoosterManager;

import java.util.*;

@UtilityClass
public class BossBarUtil {

    private final List<Player> bossBarPlayers = new ArrayList<>();
    private final Map<UUID, String> bars = new HashMap<>();

    public void sendBossBar(Player player) {
        if (MyConfig.respectExternalBars && hasExternalBar(player)) {
            return;
        }

        if (hasBar(player)) {
            return;
        }

        List<GlobalBoosterManager.BoosterData.GlobalBooster> globalBoosters = IBoosterAPI.getGlobalBoosterManager().getBoosters();
        if (globalBoosters.isEmpty()) {
            return;
        }

        GlobalBoosterManager.BoosterData.GlobalBooster booster = IBoosterAPI.getGlobalBoosterManager().getBoosters().get(0);
        String message = Replacer.replaceArray(MyConfig.barMessage,
                "{multiplier}", booster.getMultiplier(),
                "{type}", booster.getType()
        );

        Remain.sendBossbarTimed(player, message, (int) booster.getTimeLeft(), MyConfig.barColor, CompBarStyle.SOLID);
        bossBarPlayers.add(player);
        bars.put(player.getUniqueId(), booster.getType());
    }

    public boolean isCurrentType(Player player, String type) {
        return bars.get(player.getUniqueId()).equals(type);
    }

    public void remove(Player player) {
        bossBarPlayers.remove(player);
    }

    public boolean hasBar(Player player) {
        return bossBarPlayers.contains(player);
    }

    private boolean hasExternalBar(Player player) {
        Iterator<KeyedBossBar> iterator = Bukkit.getServer().getBossBars();
        while (iterator.hasNext()) {
            BossBar bar = iterator.next();
            if (bar.getPlayers().contains(player)) {
                return true;
            }
        }

        return false;
    }
}
