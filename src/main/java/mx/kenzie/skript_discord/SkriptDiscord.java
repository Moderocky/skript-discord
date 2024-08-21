package mx.kenzie.skript_discord;

import mx.kenzie.clockwork.io.IOQueue;
import mx.kenzie.eris.Bot;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class SkriptDiscord extends JavaPlugin {

    private static SkriptDiscord instance;
    private static IOQueue queue;
    private static List<Bot> bots;

    @Override
    public void onEnable() {
        super.onEnable();
        SkriptDiscord.instance = this;
        SkriptDiscord.queue = new IOQueue(1000);
        SkriptDiscord.bots = new ArrayList<>(4);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        for (Bot bot : SkriptDiscord.bots)
            if (bot.isRunning()) bot.close();
        if (queue != null) SkriptDiscord.queue.shutdown(1);
        SkriptDiscord.queue = null;
        SkriptDiscord.instance = null;
        SkriptDiscord.bots = null;
    }

    public static IOQueue queue() {
        return queue;
    }

    public static void registerBot(Bot bot) {
        SkriptDiscord.bots.add(bot);
    }

    public static void unregisterBot(Bot bot) {
        SkriptDiscord.bots.remove(bot);
        if (bot.isRunning()) bot.close();
    }

}
