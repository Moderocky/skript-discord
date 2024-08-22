package mx.kenzie.skript_discord.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import mx.kenzie.eris.Bot;
import mx.kenzie.skript_discord.elements.types.GatewayIntent;
import org.bukkit.event.Event;

@Name("Create Bot")
@Description("""
    Creates a new bot with a token and intents for logging in.
    The bot object can be stored in a variable, but will need to be 'logged in' again after the server restarts.
    """)
@Examples({
    """
        set {bot} to a new bot with token {token}
        log in to {bot}
        wait for {bot} # wait until the login completes"""
})
@Since("1.0.0")
public class ExprBot extends SimpleExpression<Bot> {

    static {
        Skript.registerExpression(ExprBot.class, Bot.class, ExpressionType.SIMPLE,
            "[a] new [discord] bot with token %string%",
            "[a] new [discord] bot with token %string% and intents [for] %gatewayintents%");
    }

    protected Expression<String> string;
    protected Expression<GatewayIntent> intents;

    @SuppressWarnings({"unchecked", "null"})
    @Override
    public boolean init(Expression<?>[] expressions, int pattern, Kleenean delayed, SkriptParser.ParseResult result) {
        this.string = (Expression<String>) expressions[0];
        if (pattern > 0) intents = (Expression<GatewayIntent>) expressions[1];
        if (string instanceof Literal<String> literal
            && (literal.getSingle() == null || literal.getSingle().isBlank())) {
            Skript.error("You cannot log in to a bot with an empty token.");
            return false;
        }
        return true;
    }

    @Override
    protected Bot[] get(Event event) {
        final String token = string.getSingle(event);
        final int[] intents;
        if (this.intents != null) {
            final GatewayIntent[] array = this.intents.getArray(event);
            intents = new int[array.length];
            for (int i = 0; i < array.length; i++) intents[i] = array[i].magic();
        } else intents = new int[0];
        final Bot bot = new Bot(token, intents);
        return new Bot[] {bot};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Bot> getReturnType() {
        return Bot.class;
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "a new bot with token " + string.toString(event, debug);
    }

}
