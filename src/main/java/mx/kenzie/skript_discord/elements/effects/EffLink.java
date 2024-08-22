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
import mx.kenzie.eris.DiscordAPI;
import mx.kenzie.eris.api.entity.Entity;
import org.bukkit.event.Event;

@Name("Link Discord Entity")
@Description("""
    Links a Discord 'entity' (message, channel, server, etc.) to a living Discord API connection.
        
    This is useful for reviving saved variables (e.g. a channel) after a server restart.
    Once an entity has been linked, it can be used as a Discord API connection itself.
    Newly-obtained data is already linked.""")
@Examples({
    """
        on load:
            set {bot} to a new bot with token {token}
            log in to {bot}
            wait for {bot}
            
            if {my cool channel} exists:
                link {channel} to {bot}
                # revive the old copy from before the server restarted
            else:
                set {my cool channel} to channel with id 12345 using {bot}
                # make a new copy
        """
})
@Since("1.0.0")
public class EffLink extends Effect {

    static {
        Skript.registerEffect(EffLink.class,
            "link %discordentities% (to|with) %discordapi%"
        );
    }

    protected Expression<Entity> entityExpression;
    protected Expression<DiscordAPI> apiExpression;

    @Override
    public boolean init(Expression<?>[] expressions, int pattern, Kleenean delayed, ParseResult result) {
        //noinspection unchecked
        this.entityExpression = (Expression<Entity>) expressions[0];
        //noinspection unchecked
        this.apiExpression = (Expression<DiscordAPI>) expressions[1];
        return true;
    }

    @Override
    protected void execute(Event event) {
        final DiscordAPI api = apiExpression.getSingle(event);
        if (api == null) return;
        for (Entity entity : entityExpression.getArray(event)) entity.api = api;
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "link " + entityExpression.toString(event, debug) + " to " + apiExpression.toString(event, debug);
    }

}
