package mx.kenzie.skript_discord;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.effects.EffMessage;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Statement;
import ch.njol.skript.lang.SyntaxElement;
import ch.njol.skript.lang.SyntaxElementInfo;
import ch.njol.skript.util.Version;
import mx.kenzie.clockwork.io.IOQueue;
import mx.kenzie.eris.Bot;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.*;

public class SkriptDiscord extends JavaPlugin {

    private static SkriptDiscord instance;
    private static IOQueue queue;
    private static List<Bot> bots;
    private SkriptAddon addon;

    @Override
    public void onEnable() {
        super.onEnable();
        SkriptDiscord.instance = this;
        SkriptDiscord.queue = new IOQueue(1000);
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

        Collection<SyntaxElementInfo<?>> infos = this.removeEffects(EffMessage.class);
        try {
            this.addon = Skript.registerAddon(this);
            this.addon.setLanguageFileDirectory("lang");
            this.addon.loadClasses("org.skriptlang.skript_io.elements");
        } catch (IOException e) {
            this.getLogger().severe("An error occurred while trying to enable this addon.");
            e.printStackTrace();
            manager.disablePlugin(this);
        } finally {
            this.addEffects(infos);
        }
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

    public static SkriptDiscord instance() {
        return instance;
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

    private Collection<SyntaxElementInfo<?>> removeEffects(Class<? extends SyntaxElement>... types) {
        //<editor-fold desc="Remove Skript's copy of syntax" defaultstate="collapsed">
        Collection<SyntaxElementInfo<? extends Effect>> effects = Skript.getEffects();
        Collection<SyntaxElementInfo<? extends Statement>> statements = Skript.getStatements();
        Set<SyntaxElementInfo<?>> set = new HashSet<>();
        final Iterator<SyntaxElementInfo<? extends Effect>> iterator = effects.iterator();
        loop:
        while (iterator.hasNext()) {
            final SyntaxElementInfo<?> next = iterator.next();
            for (Class<? extends SyntaxElement> type : types) {
                if (next.elementClass != type) continue;
                set.add(next);
                iterator.remove();
                statements.remove(next);
                continue loop;
            }
        }
        return set;
        //</editor-fold>
    }

    @SuppressWarnings("unchecked")
    private void addEffects(Collection<SyntaxElementInfo<?>> effects) {
        //<editor-fold desc="Re-add Skript syntax" defaultstate="collapsed">
        Skript.getEffects().addAll((Collection<? extends SyntaxElementInfo<? extends Effect>>) effects);
        Skript.getStatements().addAll((Collection<? extends SyntaxElementInfo<? extends Statement>>) effects);
        //</editor-fold>
    }

}
