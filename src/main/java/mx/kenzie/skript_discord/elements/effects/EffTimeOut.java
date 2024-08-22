package mx.kenzie.skript_discord.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import mx.kenzie.eris.api.entity.Guild;
import mx.kenzie.eris.api.entity.User;
import org.bukkit.event.Event;

import java.time.Duration;

@Name("Time Out User")
@Description("""
    Closes a bot's connection to Discord.
    Using `wait for %bot%` will pause until the log-in is complete.
        
    Bot objects are not designed to be re-used (i.e. you should create a new bot rather than re-logging in to the same one).
    """)
@Examples({
    """
        time out {user} in {guild} for 50 minutes for "being rude"
        """,
    """
        time out {user} in {guild} for 7 days"""
})
@Since("1.0.0")
public class EffTimeOut extends Effect {

    static {
        Skript.registerEffect(EffTimeOut.class,
            "time[ ]out %user% in %guild% for %timespan%",
            "time[ ]out %user% in %guild% for %timespan% (for|with reason) %string%"
        );
    }

    protected Expression<User> userExpression;
    protected Expression<Guild> guildExpression;
    protected Expression<Timespan> timespanExpression;
    protected Expression<String> stringExpression;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int pattern, Kleenean delayed, ParseResult result) {
        this.userExpression = (Expression<User>) expressions[0];
        this.guildExpression = (Expression<Guild>) expressions[1];
        this.timespanExpression = (Expression<Timespan>) expressions[2];
        if (pattern > 0)
            this.stringExpression = (Expression<String>) expressions[3];
        return true;
    }

    @Override
    protected void execute(Event event) {
        final User user = userExpression.getSingle(event);
        final Guild guild = guildExpression.getSingle(event);
        final Timespan timespan = timespanExpression.getSingle(event);
        if (user == null || guild == null || timespan == null) return;
        if (stringExpression != null)
            guild.timeOut(user, Duration.ofMillis(timespan.getAs(Timespan.TimePeriod.MILLISECOND)),
                this.stringExpression.getSingle(event));
        else
            guild.timeOut(user, Duration.ofMillis(timespan.getAs(Timespan.TimePeriod.MILLISECOND)));
    }

    @Override
    public String toString(Event event, boolean debug) {
        if (stringExpression != null)
            return "time out " + userExpression.toString(event, debug)
                + " in " + guildExpression.toString(event, debug)
                + " for " + timespanExpression.toString(event, debug)
                + " with reason " + stringExpression.toString(event, debug);
        else
            return "time out " + userExpression.toString(event, debug)
                + " in " + guildExpression.toString(event, debug)
                + " for " + timespanExpression.toString(event, debug);
    }

}
