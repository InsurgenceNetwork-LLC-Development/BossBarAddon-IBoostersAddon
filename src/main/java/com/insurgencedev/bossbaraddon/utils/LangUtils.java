package com.insurgencedev.bossbaraddon.utils;

import com.insurgencedev.bossbaraddon.settings.MyConfig;
import org.insurgencedev.insurgenceboosters.api.IBoosterAPI;
import org.insurgencedev.insurgenceboosters.libs.luaj.vm2.Globals;

import java.util.function.Consumer;

public class LangUtils {

    public static String langOf(String key, Consumer<Globals> setupGlobals) {
        String message = MyConfig.getInstance().getMessage(key);
        if (message.startsWith("<script>")) {
            Globals globals = IBoosterAPI.INSTANCE.getLuaGlobals();
            setupGlobals.accept(globals);
            String script = message.substring(8);
            return globals.load(script).call().tojstring();
        } else {
            return message;
        }
    }
}