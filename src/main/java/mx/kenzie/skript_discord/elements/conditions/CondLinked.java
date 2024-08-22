package mx.kenzie.skript_discord.elements.conditions;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import mx.kenzie.eris.api.entity.Entity;

@Name("Discord Entity is Linked")
@Description("""
    Checks whether a Discord entity (user, role, server, channel, etc.) is 'linked' with a live Discord API connection.
    The link (API connection) determines the bot account authority that is used for all functions.
        
    If no link is available then data may be outdated and functionality will be limited.
        
    An old record can be re-linked using the link effect.""")
@Examples({
    """
        if {my cool channel} is not linked:
            link {my cool channel} to {my bot}"""
})
@Since("1.0.0")
public class CondLinked extends PropertyCondition<Entity> {

    static {
        PropertyCondition.register(CondLinked.class, PropertyType.BE, "linked [(to|with) discord]", "entity");
    }

    @Override
    public boolean check(Entity value) {
        return value.api != null;
    }

    @Override
    protected String getPropertyName() {
        return "linked to discord";
    }

}
