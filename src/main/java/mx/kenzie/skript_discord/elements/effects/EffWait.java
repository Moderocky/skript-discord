package mx.kenzie.skript_discord.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.effects.Delay;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.Trigger;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.skript.timings.SkriptTimings;
import ch.njol.skript.util.Timespan;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import mx.kenzie.eris.api.Lazy;
import mx.kenzie.skript_discord.SkriptDiscord;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

@Name("Wait for Result (Delay)")
@Description("""
    Waits for the result of a request, making sure it is completed.
    This is useful when asking Discord for something (e.g. a user record) and then waiting until all the data arrives.
        
    A timespan can (optionally) be provided, which is the *maximum* amount of time this will wait.
    The delay would end early if the data is received within that time. If the whole timespan elapses, the data may not all be present.
        
    A lazy object can be waited for indefinitely, in which case this will *only* continue when the data is marked as completed.
    As a request may hang forever (e.g. if the wifi is switched off) users may wish to provide a maximum wait time.
    """)
@Examples({
    """
        set {user} to user with id {@my id} using {bot}
        wait 5 seconds for {user}
        if {user} is ready:
            send "hello " + username of {user} to {user}"""
})
@Since("1.0.0")
public class EffWait extends Effect {

    static {
        Skript.registerEffect(EffWait.class,
            "(wait for|await|expect) %lazy%",
            "wait %timespan% for %lazy%"
        );
    }

    protected Expression<Lazy> lazy;
    protected Expression<Timespan> timespan;

    @SuppressWarnings({"unchecked", "null"})
    @Override
    public boolean init(Expression<?>[] expressions, int pattern, Kleenean delayed, ParseResult result) {
        this.getParser().setHasDelayBefore(Kleenean.TRUE);
        this.lazy = (Expression<Lazy>) expressions[pattern];
        if (pattern > 0) timespan = (Expression<Timespan>) expressions[0];
        return true;
    }

    @Override
    protected TriggerItem walk(Event event) {
        this.debug(event, true);
        final long start = Skript.debug() ? System.currentTimeMillis() : 0;
        final TriggerItem next = this.getNext();
        if (next == null || !Skript.getInstance().isEnabled()) return null;
        Delay.addDelayedEvent(event);

        final Lazy lazy = this.lazy.getSingle(event);
        if (lazy == null || lazy.ready()) return next;

        final Timespan duration;
        if (timespan != null) duration = timespan.getSingle(event);
        else duration = null;

        // Back up local variables
        final Object locals = Variables.removeLocals(event);

        if (Bukkit.isPrimaryThread()) SkriptDiscord.executor().submit(() -> {
            EffWait.this.await(lazy, duration, event, locals, next, start);
        });
        else this.await(lazy, duration, event, locals, next, start);
        return null;
    }

    private void await(Lazy lazy, Timespan duration, Event event, Object locals, TriggerItem next, long start) {
        if (duration != null) //noinspection UnstableApiUsage,deprecation
            lazy.await(duration.getMilliSeconds());
        else lazy.await();
        Bukkit.getScheduler().runTask(Skript.getInstance(), () -> {
            Skript.debug(getIndentation() + "... continuing after " + (System.currentTimeMillis() - start) + "ms");

            // Re-set local variables
            if (locals != null)
                Variables.setLocalVariables(event, locals);

            Object timing = null; // Timings reference must be kept so that it can be stopped after TriggerItem
            // execution
            if (SkriptTimings.enabled()) { // getTrigger call is not free, do it only if we must
                Trigger trigger = getTrigger();
                if (trigger != null)
                    timing = SkriptTimings.start(trigger.getDebugLabel());
            }

            TriggerItem.walk(next, event);
            Variables.removeLocals(event); // Clean up local vars, we may be exiting now

            SkriptTimings.stop(timing); // Stop timing if it was even started
        });
    }

    @Override
    protected void execute(Event event) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString(Event event, boolean debug) {
        if (timespan != null)
            return "wait " + timespan.toString(event, debug) + " for " + lazy.toString(event, debug);
        return "wait for " + lazy.toString(event, debug);
    }

}
