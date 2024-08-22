package mx.kenzie.skript_discord.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import mx.kenzie.eris.api.entity.Entity;
import mx.kenzie.eris.api.entity.Member;
import mx.kenzie.eris.api.entity.User;
import mx.kenzie.skript_discord.elements.ContextualElement;

@Name("Discord Nickname")
@Description("""
    The server nickname of a Discord member, or the global (displayed) name of a Discord user.
    This is their non-unique 'nickname' rather than their username.
        
    A user's nickname might not exist if the user data has not been resolved from Discord yet.
    When requesting a user, make sure to `wait for {user}` so that all data is available.
        
    If a (legacy) user has no global nickname, this will fall back to the user's username.""")
@Examples({
    """
        send "hello, %{user}'s nickname%" to {user}""",
})
@Since("1.0.0")
public class ExprUserGlobalName extends SimplePropertyExpression<Entity, String> implements ContextualElement {

    static {
        register(ExprUserGlobalName.class, String.class, "(nick[ ]name|global [display] name)", "member/user");
    }

    @Override
    public String convert(Entity from) {
        if (from instanceof Member member) {
            return member.nick != null ? member.nick : member.user != null ? member.user.global_name != null
                ? member.user.global_name
                : member.user.username : null;
        } else if (from instanceof User user) {
            return user.global_name != null ? user.global_name : user.username;
        } else return null;
    }

    @Override
    protected String getPropertyName() {
        return "nickname";
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

}
