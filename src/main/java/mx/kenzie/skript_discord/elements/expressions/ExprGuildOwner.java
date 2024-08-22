package mx.kenzie.skript_discord.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import mx.kenzie.eris.DiscordAPI;
import mx.kenzie.eris.api.entity.Guild;
import mx.kenzie.eris.api.entity.User;
import mx.kenzie.skript_discord.elements.ContextualElement;

@Name("Owner of Discord Server")
@Description("""
    The Discord user that owns a server (guild).
    The owner may not exist if the guild data is not yet available.
    
    If the guild object is not linked with a Discord API then the user will not be linked either.
    The user object will need to be waited for before all data is available.""")
@Examples({
    """
        set the token of {bot} to "a13894u2jvfwifn.12949859458.foobarfaketoken"
        log in to {bot}""",
})
@Since("1.0.0")
public class ExprGuildOwner extends SimplePropertyExpression<Guild, User> implements ContextualElement {

    static {
        register(ExprGuildOwner.class, User.class, "owner", "guild");
    }

    @Override
    public User convert(Guild from) {
        if (from.owner_id == null) return null;
        final DiscordAPI api = this.getApi(null, null, from);
        if (api != null) return api.getUser(from.owner_id);
        final User user = new User();
        user.id = from.owner_id;
        return user;
    }

    @Override
    protected String getPropertyName() {
        return "owner";
    }

    @Override
    public Class<? extends User> getReturnType() {
        return User.class;
    }

}
