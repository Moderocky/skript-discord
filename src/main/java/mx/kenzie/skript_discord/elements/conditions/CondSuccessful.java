package mx.kenzie.skript_discord.elements.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import mx.kenzie.eris.api.Lazy;

@Name("Discord Request was Successful")
@Description("""
    Checks whether a requested Discord entity (user, role, server, channel, etc.) was 'successful'.
    In other words, whether its previous task completed successfully.
    
    To be successful:
        - A task must have been dispatched (e.g. data was requested)
        - The task must have finished
        - An error status was not returned
    
    This can be used in conjunction with the `ready` property to know when a task failed.""")
@Examples({
    """
        set {user} to user with id {@my id} using {bot}
        wait 5 seconds for {user}
        if {user} was successful:
            send "hello " + username of {user} to {user}"""
})
@Since("1.0.0")
public class CondSuccessful extends PropertyCondition<Lazy> {

    static {
        Skript.registerCondition(CondSuccessful.class,
            "%lazy% (i|wa)s successful",
            "%lazy% (i|wa)s(n't| not) successful"
        );
    }

    @Override
    public boolean check(Lazy value) {
        return value.ready() && value.successful();
    }

    @Override
    protected String getPropertyName() {
        return "successful";
    }

}
