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
import ch.njol.util.Kleenean;
import mx.kenzie.eris.Bot;
import mx.kenzie.skript_discord.SkriptDiscord;
import org.bukkit.event.Event;

@Name("Log out of Bot")
@Description("""
    Closes a bot's connection to Discord.
    Using `wait for %bot%` will pause until the log-in is complete.
        
    Bot objects are not designed to be re-used (i.e. you should create a new bot rather than re-logging in to the same one).
    """)
@Examples({
    """
        set {bot} to a new bot with token {token}
        log in to {bot}
        wait for {bot}
        # bot is online :)
        log out of {bot}
        # bot is gone :(
        """
})
@Since("1.0.0")
public class EffLogOut extends Effect {

    static {
        Skript.registerEffect(Delay.class,
            "log[ ]out (of|from) %bot%"
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
        SkriptDiscord.unregisterBot(bot);
        bot.finish();
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "log out of " + bot.toString(event, debug);
    }

}
