package mx.kenzie.skript_discord.elements.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.conditions.base.PropertyCondition;
import mx.kenzie.eris.api.Lazy;

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
