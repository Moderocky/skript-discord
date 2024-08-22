package mx.kenzie.skript_discord.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.ExpressionType;
import mx.kenzie.eris.DiscordAPI;
import mx.kenzie.eris.api.entity.Guild;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Discord Server (Guild)")
@Description("""
    Fetches a discord server (guild) with the given ID.
    Although the server object can be used immediately (for the purposes of sending messages & getting channels), its data (name, icon, etc.) may not be available until `wait for {guild}` completes.
        
    An old copy of the data might be available if the guild has been asked for previously.""")
@Examples({
    """
        set {bot} to a new bot with token {token}
        log in to {bot}
        wait for {bot} # wait until the login completes
        set {guild} to the guild with id "988998880794402856" using {bot}"""
})
@Since("1.0.0")
public class ExprGuild extends AbstractExprEntity<Guild> {

    static {
        Skript.registerExpression(ExprGuild.class, Guild.class, ExpressionType.SIMPLE,
            "[the] [discord] (guild|server) with id %number/string% [context:using %-discord%]");
    }

    @Override
    protected Guild get(Event event, @NotNull Object id, DiscordAPI api) {
        if (id instanceof Number number) return api.getGuild(number.longValue());
        return api.getGuild(api.getGuildId(id));
    }

    @Override
    public Class<? extends Guild> getReturnType() {
        return Guild.class;
    }

    @Override
    public String toString(Event event, boolean debug) {
        if (apiExpression == null)
            return "the discord server with id " + idExpression.toString(event, debug)
                + " using " + apiExpression.toString(event, debug);
        return "the discord server with id " + idExpression.toString(event, debug);
    }

}
