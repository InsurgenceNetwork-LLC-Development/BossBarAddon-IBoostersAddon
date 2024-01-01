package com.insurgencedev.bossbaraddon.utils;

import com.insurgencedev.bossbaraddon.settings.MyConfig;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import org.insurgencedev.insurgenceboosters.api.IBoosterAPI;
import org.insurgencedev.insurgenceboosters.libs.fo.model.Replacer;
import org.insurgencedev.insurgenceboosters.libs.fo.remain.CompBarStyle;
import org.insurgencedev.insurgenceboosters.libs.fo.remain.Remain;
import org.insurgencedev.insurgenceboosters.models.booster.GlobalBoosterManager;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class BossBarUtil {

    public final List<Player> bossBarPlayers = new ArrayList<>();

    public void sendBossBar(Player player) {
        List<GlobalBoosterManager.BoosterData.GlobalBooster> globalBoosters = IBoosterAPI.getGlobalBoosterManager().getBoosters();
        if (globalBoosters.isEmpty()) {
            return;
        }

        if (hasBar(player)) {
            return;
        }

        GlobalBoosterManager.BoosterData.GlobalBooster booster = IBoosterAPI.getGlobalBoosterManager().getBoosters().get(0);
        String message = Replacer.replaceArray(MyConfig.barMessage,
                "{multiplier}", booster.getMultiplier(),
                "{type}", booster.getType()
        );

        Remain.sendBossbarTimed(player, message, (int) booster.getTimeLeft(), MyConfig.barColor, CompBarStyle.SOLID);
        bossBarPlayers.add(player);
    }

    public boolean hasBar(Player player) {
        return bossBarPlayers.contains(player);
    }

    public void remove(Player player) {
        if (hasBar(player)) {
            bossBarPlayers.remove(player);
        }
    }
}
