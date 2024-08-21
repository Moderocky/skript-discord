package mx.kenzie.skript_discord.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.effects.Delay;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.Trigger;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.skript.timings.SkriptTimings;
import ch.njol.skript.util.Timespan;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import mx.kenzie.clockwork.io.DataTask;
import mx.kenzie.eris.Bot;
import mx.kenzie.eris.api.Lazy;
import mx.kenzie.skript_discord.SkriptDiscord;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

@Name("Log in to Bot")
@Description("""
    Sends a bot log-in request to Discord.
    Using `wait for %bot%` will pause until the log-in is complete.
    
    Multiple log-in requests can be made for the same bot from different (or the same) machines.
    
    Each login will receive its own events and data.
    Therefore, it is advised to keep track of whether a bot has been logged in already to prevent receiving duplicates.
    """)
@Examples({
    """
        set {bot} to a new bot with token {token}
        log in to {bot}
        wait for {bot}
        # bot is online!"""
})
@Since("1.0.0")
public class EffLogin extends Effect {

    static {
        Skript.registerEffect(Delay.class,
            "log[ ]in to %bot%"
        );
    }

    protected Expression<Bot> bot;

    @Override
    public boolean init(Expression<?>[] expressions, int pattern, Kleenean delayed, ParseResult result) {
        //noinspection unchecked
        this.bot = (Expression<Bot>) expressions[0];
        return true;
    }

    @Override
    protected void execute(Event event) {
        final Bot bot = this.bot.getSingle(event);
        if (bot == null) return;
        SkriptDiscord.registerBot(bot);
        bot.start();
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "log in to " + bot.toString(event, debug);
    }

}
