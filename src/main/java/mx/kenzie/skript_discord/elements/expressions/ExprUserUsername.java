package mx.kenzie.skript_discord.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import mx.kenzie.eris.api.entity.User;
import mx.kenzie.skript_discord.elements.ContextualElement;

@Name("Discord Username")
@Description("""
    The username of a Discord user.
    This is the login name unique to a user, not their global display name.
        
    A user's username might not exist if the user data has not been resolved from Discord yet.
    When requesting a user, make sure to `wait for {user}` so that all data is available.""")
@Examples({
    """
        send "hello, %{user}'s username%" to {user}""",
})
@Since("1.0.0")
public class ExprUserUsername extends SimplePropertyExpression<User, String> implements ContextualElement {

    static {
        register(ExprUserUsername.class, String.class, "user[ ]name", "user");
    }

    @Override
    public String convert(User from) {
        return from.username;
    }

    @Override
    protected String getPropertyName() {
        return "username";
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

}
