package mx.kenzie.skript_discord.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import mx.kenzie.eris.api.entity.Snowflake;
import mx.kenzie.skript_discord.elements.ContextualElement;

@Name("Discord ID")
@Description("""
    The Discord ID of a Discord data object, such as a user, a role, a channel or a server.
        
    This ID is a 17-20 digit long number.
    Most syntax that accepts an ID will accept either a text ("12345") or a number (12345).""")
@Examples({
    """
        set {id} the discord id of {user}""",
})
@Since("1.0.0")
public class ExprEntityID extends SimplePropertyExpression<Snowflake, Long> implements ContextualElement {

    static {
        register(ExprEntityID.class, Long.class, "[discord] id", "snowflake");
    }

    @Override
    public Long convert(Snowflake from) {
        if (from == null || from.id == null) return null;
        return from.id();
    }

    @Override
    protected String getPropertyName() {
        return "discord id";
    }

    @Override
    public Class<? extends Long> getReturnType() {
        return Long.class;
    }

}
