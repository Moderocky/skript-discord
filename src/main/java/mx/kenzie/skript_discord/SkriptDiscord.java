package mx.kenzie.skript_discord;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.util.Version;
import mx.kenzie.eris.Bot;
import mx.kenzie.eris.DiscordAPI;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SkriptDiscord extends JavaPlugin {

    private static SkriptDiscord instance;
    private static ExecutorService service;
    private static List<Bot> bots;
    private SkriptAddon addon;

    @Override
    public void onEnable() {
        super.onEnable();
        SkriptDiscord.instance = this;
        SkriptDiscord.service = Executors.newVirtualThreadPerTaskExecutor();
        SkriptDiscord.bots = new ArrayList<>(4);

        final PluginManager manager = this.getServer().getPluginManager();
        final Plugin skript = manager.getPlugin("Skript");
        if (skript == null || !skript.isEnabled()) {
            this.getLogger().severe("""
                [skript-discord] Could not find Skript!
                Make sure you have it installed and that it properly loaded.
                Disabling...""");
            manager.disablePlugin(this);
            return;
        } else if (!Skript.getVersion()
            .isLargerThan(new Version(2, 8, 0))) { // Skript is not any version after 2.5.3 (aka 2.6)
            this.getLogger().severe("""
                [skript-discord] You are running an unsupported version of Skript.
                Please update to at least Skript 2.9.0.
                Disabling...""");
            manager.disablePlugin(this);
            return;
        }

        try {
            this.addon = Skript.registerAddon(this);
            this.addon.setLanguageFileDirectory("lang");
            this.addon.loadClasses("mx.kenzie.skript_discord.elements");
        } catch (IOException e) {
            this.getLogger().severe("An error occurred while trying to enable this addon.");
            error(e);
            manager.disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        for (Bot bot : SkriptDiscord.bots)
            if (bot.isRunning()) bot.close();
        if (service != null) service.close();
        SkriptDiscord.service = null;
        SkriptDiscord.instance = null;
        SkriptDiscord.bots = null;
    }

    public static SkriptDiscord instance() {
        return instance;
    }

    public static void registerBot(Bot bot) {
        SkriptDiscord.bots.add(bot);
    }

    public static void unregisterBot(Bot bot) {
        SkriptDiscord.bots.remove(bot);
        if (bot.isRunning()) bot.close();
    }

    public static void error(Throwable throwable) {
        if (throwable == null) return;
        throwable.printStackTrace();
    }

    public static DiscordAPI getUnaryApiInstance() {
        if (bots.size() == 1) return bots.getFirst().getAPI();
        return null;
    }

    public static ExecutorService executor() {
        return service;
    }

}
