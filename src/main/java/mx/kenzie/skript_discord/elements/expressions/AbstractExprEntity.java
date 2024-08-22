package mx.kenzie.skript_discord.elements.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import mx.kenzie.eris.DiscordAPI;
import mx.kenzie.eris.api.entity.Entity;
import mx.kenzie.skript_discord.elements.ContextualElement;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public abstract class AbstractExprEntity<Type extends Entity> extends SimpleExpression<Type>
    implements ContextualElement {

    protected Expression<?> idExpression;
    protected Expression<DiscordAPI> apiExpression;

    @Override
    public boolean init(Expression<?>[] expressions, int pattern, Kleenean delayed, SkriptParser.ParseResult result) {
        this.idExpression = expressions[0];
        if (result.hasTag("context")) //noinspection unchecked
            this.apiExpression = (Expression<DiscordAPI>) expressions[1];
        return true;
    }

    @Override
    protected Type[] get(Event event) {
        final Object id = idExpression.getSingle(event);
        if (id == null) return this.array(0);
        final DiscordAPI api = this.getApi(event, apiExpression);
        return array(1, this.get(event, id, api));
    }

    protected abstract Type get(Event event, @NotNull Object id, DiscordAPI api);

    @SafeVarargs
    protected final Type[] array(int length, Type... types) {
        return Arrays.copyOf(types, length);
    }

    @Override
    public boolean isSingle() {
        return true;
    }

}
