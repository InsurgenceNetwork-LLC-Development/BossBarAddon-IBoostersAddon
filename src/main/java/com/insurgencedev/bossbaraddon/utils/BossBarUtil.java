package com.insurgencedev.bossbaraddon.utils;

import com.insurgencedev.bossbaraddon.settings.MyConfig;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.Player;
import org.insurgencedev.insurgenceboosters.api.IBoosterAPI;
import org.insurgencedev.insurgenceboosters.data.BoosterData;
import org.insurgencedev.insurgenceboosters.data.GlobalBooster;
import org.insurgencedev.insurgenceboosters.libs.fo.remain.CompBarColor;
import org.insurgencedev.insurgenceboosters.libs.fo.remain.CompBarStyle;
import org.insurgencedev.insurgenceboosters.libs.fo.remain.Remain;
import org.insurgencedev.insurgenceboosters.libs.luaj.vm2.LuaValue;
import org.insurgencedev.insurgenceboosters.libs.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.*;

@UtilityClass
public class BossBarUtil {

    private final List<Player> bossBarPlayers = new ArrayList<>();
    private final Map<Player, String> barTypeTracker = new HashMap<>();

    public void sendBossBar(Player player) {
        if (MyConfig.respectExternalBars && hasExternalBar(player)) {
            return;
        }

        if (hasBar(player)) {
            return;
        }

        switch (MyConfig.scope.toLowerCase()) {
            case "global" -> {
                List<GlobalBooster> globalBoosters = IBoosterAPI.INSTANCE.getGlobalBoosterManager().getGlobalBoosters();
                if (globalBoosters.isEmpty()) {
                    return;
                }
                GlobalBooster booster = globalBoosters.get(0);
                int timeLeft = (int) booster.getTimeLeft();
                handleBooster(player, timeLeft, CoerceJavaToLua.coerce(booster), booster.getMultiplier(), booster.getType());
            }
            case "personal" -> {
                List<BoosterData> personalBoosters = IBoosterAPI.INSTANCE.getCache(player).getBoosterDataManager().findAllActiveBoosters();
                if (personalBoosters.isEmpty()) {
                    return;
                }
                BoosterData booster = personalBoosters.get(0);
                int timeLeft = (int) booster.getTimeLeft();
                handleBooster(player, timeLeft, CoerceJavaToLua.coerce(booster), booster.getMultiplier(), booster.getType());
            }
        }
    }

    private void handleBooster(Player player, int timeLeft, LuaValue coercedBooster, double multiplier, String type) {
        String message = LangUtils.langOf("Boss_Bar.Message", globals -> {
            globals.set("booster", coercedBooster);
            globals.set("multiplier", (int) multiplier);
            globals.set("type", type);
        });
        CompBarColor color = CompBarColor.fromKey(LangUtils.langOf("Boss_Bar.Color", globals -> {
            globals.set("booster", coercedBooster);
            globals.set("multiplier", (int) multiplier);
            globals.set("type", type);
        }));

        String finalMessage = message.replace("{multiplier}", multiplier + "").replace("{type}", type);
        Remain.sendBossbarTimed(player, finalMessage, timeLeft, color, CompBarStyle.SOLID);
        bossBarPlayers.add(player);
        barTypeTracker.put(player, type);
    }

    public void removeBossBar(Player player) {
        Remain.removeBossbar(player);
    }

    public boolean isCurrentType(Player player, String type) {
        return barTypeTracker.get(player).equals(type);
    }

    public void remove(Player player) {
        bossBarPlayers.remove(player);
        barTypeTracker.remove(player);
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
