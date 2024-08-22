package mx.kenzie.skript_discord.elements.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import mx.kenzie.eris.Bot;
import mx.kenzie.skript_discord.elements.ContextualElement;
import org.bukkit.event.Event;

@Name("Token of Bot")
@Description("""
    The login token of a bot.
    Be careful about sharing or storing this, since it gives full access to a Discord bot.""")
@Examples({
    """
        set the token of {bot} to "a13894u2jvfwifn.12949859458.foobarfaketoken"
        log in to {bot}""",
})
@Since("1.0.0")
public class ExprToken extends SimplePropertyExpression<Bot, String> implements ContextualElement {

    static {
        register(ExprToken.class, String.class, "token", "bot");
    }

    @Override
    public String convert(Bot from) {
        return from.token();
    }

    @Override
    protected String getPropertyName() {
        return "token";
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return switch (mode) {
            case SET -> new Class[] {String.class};
            case RESET, DELETE -> new Class[0];
            default -> null;
        };
    }

    @Override
    public void change(Event event, Object[] delta, Changer.ChangeMode mode) {
        for (Bot bot : this.getExpr().getArray(event)) {
            switch (mode) {
                case SET -> bot.setToken(String.valueOf(delta[0]));
                case RESET, DELETE -> bot.setToken(null);
            }
        }
    }

}
