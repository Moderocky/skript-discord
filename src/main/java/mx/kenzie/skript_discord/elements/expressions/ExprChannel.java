package mx.kenzie.skript_discord.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.ExpressionType;
import mx.kenzie.eris.DiscordAPI;
import mx.kenzie.eris.api.entity.Channel;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Discord Channel")
@Description("""
    Fetches a discord channel (from a server) with the given ID.
    The bot may not be able to find the channel if it is not in the server it belongs to.
    Although the channel can be used immediately (for the purposes of sending messages), its data (name, icon, etc.) may not be available until `wait for {channel}` completes.
        
    An old copy of the data might be available if the channel has been asked for previously.""")
@Examples({
    """
        set {bot} to a new bot with token {token}
        log in to {bot}
        wait for {bot} # wait until the login completes
        set {channel} to the channel with id "988998882409189428" using {bot}
        """
})
@Since("1.0.0")
public class ExprChannel extends AbstractExprEntity<Channel> {

    static {
        Skript.registerExpression(ExprChannel.class, Channel.class, ExpressionType.SIMPLE,
            "[the] [discord] channel with id %number/string% [context:using %-discord%]");
    }

    @Override
    protected Channel get(Event event, @NotNull Object id, DiscordAPI api) {
        return api.getChannel(String.valueOf(id));
    }

    @Override
    public Class<? extends Channel> getReturnType() {
        return Channel.class;
    }

    @Override
    public String toString(Event event, boolean debug) {
        if (apiExpression == null)
            return "the discord channel with id " + idExpression.toString(event, debug)
                + " using " + apiExpression.toString(event, debug);
        return "the discord channel with id " + idExpression.toString(event, debug);
    }

}
