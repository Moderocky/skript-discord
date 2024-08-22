package mx.kenzie.skript_discord.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import mx.kenzie.eris.api.Lazy;
import mx.kenzie.skript_discord.SkriptDiscord;
import org.bukkit.event.Event;

@Name("Report Error from Request")
@Description("""
    Reports an error from a 'lazy' request.
    """)
@Examples({
    """
        set {bot} to a new bot with token {token}
        log in to {bot}
        wait 5 seconds for {bot}
        if {bot} wasn't successful:
            report the error from {bot}
        """
})
@Since("1.0.0")
public class EffReport extends Effect {

    static {
        Skript.registerEffect(EffReport.class,
            "report [the] error (from|of) %lazy%"
        );
    }

    protected Expression<Lazy> lazy;

    @Override
    public boolean init(Expression<?>[] expressions, int pattern, Kleenean delayed, ParseResult result) {
        //noinspection unchecked
        this.lazy = (Expression<Lazy>) expressions[0];
        return true;
    }

    @Override
    protected void execute(Event event) {
        final Lazy lazy = this.lazy.getSingle(event);
        if (lazy == null) return;
        System.out.println(lazy.toJson("\t")); // todo
        System.out.println(lazy.error()); // todo
        SkriptDiscord.error(lazy.error());
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "report the error from " + lazy.toString(event, debug);
    }

}
