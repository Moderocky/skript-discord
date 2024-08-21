package mx.kenzie.skript_discord.elements.conditions;

import ch.njol.skript.conditions.base.PropertyCondition;
import mx.kenzie.eris.api.Lazy;

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
