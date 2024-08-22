package mx.kenzie.skript_discord.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.ExpressionType;
import mx.kenzie.eris.DiscordAPI;
import mx.kenzie.eris.api.entity.User;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Discord User")
@Description("""
    Fetches a discord user with the given ID.
    The bot should always be able to resolve the user if it exists, but may be unable to send messages to it or see some of its data if they do not share a server.
    Although the user can be used immediately (for the purposes of sending messages), its data (name, icon, etc.) may not be available until `wait for {user}` completes.
        
    An old copy of the data might be available if the user has been asked for previously.""")
@Examples({
    """
        set {bot} to a new bot with token {token}
        log in to {bot}
        wait for {bot} # wait until the login completes
                
        set {user} to the user with id 1223579043311245674 using {bot}
                
        send "hello, %{user}'s name%" to {user}"""
})
@Since("1.0.0")
public class ExprUser extends AbstractExprEntity<User> {

    static {
        Skript.registerExpression(ExprUser.class, User.class, ExpressionType.SIMPLE,
            "[the] [discord] user with id %number/string% [context:using %-discord%]");
    }

    @Override
    protected User get(Event event, @NotNull Object id, DiscordAPI api) {
        if (id instanceof Number number) return api.getUser(number.longValue());
        return api.getUser(String.valueOf(id));
    }

    @Override
    public Class<? extends User> getReturnType() {
        return User.class;
    }

    @Override
    public String toString(Event event, boolean debug) {
        if (apiExpression == null)
            return "the discord user with id " + idExpression.toString(event, debug)
                + " using " + apiExpression.toString(event, debug);
        return "the discord user with id " + idExpression.toString(event, debug);
    }

}
