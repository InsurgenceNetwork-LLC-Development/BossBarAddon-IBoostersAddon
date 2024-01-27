package com.insurgencedev.bossbaraddon;

import com.insurgencedev.bossbaraddon.listeners.BoosterEventListener;
import com.insurgencedev.bossbaraddon.settings.MyConfig;
import com.insurgencedev.bossbaraddon.utils.BossBarUtil;
import org.insurgencedev.insurgenceboosters.api.addon.IBoostersAddon;
import org.insurgencedev.insurgenceboosters.api.addon.InsurgenceBoostersAddon;
import org.insurgencedev.insurgenceboosters.libs.fo.remain.Remain;

@IBoostersAddon(name = "BossBarAddon", version = "1.0.3", author = "InsurgenceDev", description = "Display bossbar when boosters are active")
public class BossBarAddon extends InsurgenceBoostersAddon {

    private static MyConfig config;

    @Override
    public void onAddonStart() {
        config = new MyConfig();
    }

    @Override
    public void onAddonReloadAblesStart() {
        config.reload();
        registerEvent(new BoosterEventListener());
    }

    @Override
    public void onAddonReload() {
        Remain.getOnlinePlayers().forEach(player -> {
            BossBarUtil.removeBossBar(player);
            BossBarUtil.remove(player);
        });
    }
}
