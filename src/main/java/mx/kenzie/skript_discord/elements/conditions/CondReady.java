package mx.kenzie.skript_discord.elements.conditions;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import mx.kenzie.eris.api.Lazy;

@Name("Discord Request is Ready")
@Description("""
    Checks whether a requested Discord entity (user, role, server, channel, etc.) is 'ready'.
    In other words, whether its previous task has completed.
    
    When requesting data (e.g. a user/channel by ID), 'ready' means all of its data has been received.
    If something is not 'ready' then:
        - some or all of its data may be missing
        - its data may be outdated
        - its functionality may be limited.
    
    The `wait for %lazy%` effect may be used to wait until something is ready.
    WARNING: if no previous instruction was given, the effect will wait INDEFINITELY (since it will never be 'ready')!""")
@Examples({
    """
        set {user} to user with id {@my id} using {bot}
        wait 5 seconds for {user}
        if {user} is ready:
            send "hello " + username of {user} to {user}"""
})
@Since("1.0.0")
public class CondReady extends PropertyCondition<Lazy> {

    static {
        PropertyCondition.register(CondReady.class, PropertyType.BE, "ready", "lazy");
    }

    @Override
    public boolean check(Lazy value) {
        return value.ready();
    }

    @Override
    protected String getPropertyName() {
        return "ready";
    }

}
