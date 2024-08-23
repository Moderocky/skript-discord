package mx.kenzie.skript_discord.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import mx.kenzie.eris.api.entity.User;
import mx.kenzie.skript_discord.elements.ContextualElement;

@Name("Discord Avatar/Banner Link")
@Description("""
    The web link for a Discord user's avatar or banner image, as a PNG file.
        
    A user's avatar/banner might not exist if the user data has not been resolved from Discord yet.
    When requesting a user, make sure to `wait for {user}` so that all data is available.""")
@Examples({
    """
        set {url} to {user}'s avatar link""",
})
@Since("1.0.0")
public class ExprUserURL extends SimplePropertyExpression<User, String> implements ContextualElement {

    private static final int BANNER = 1, AVATAR = 2;

    static {
        register(ExprUserURL.class, String.class, "(:banner|:avatar) (link|url)", "user");
    }

    protected int mode;

    @Override
    public boolean init(Expression<?>[] expressions, int pattern, Kleenean delayed, SkriptParser.ParseResult result) {
        if (result.hasTag("banner")) mode = BANNER;
        else if (result.hasTag("avatar")) mode = AVATAR;
        return super.init(expressions, pattern, delayed, result);
    }

    @Override
    public String convert(User from) {
        if (mode == AVATAR) {
            if (from.avatar != null)
                return from.getAvatarURL();
        } else {
            if (from.banner != null)
                return from.getBannerURL();
        }
        return null;
    }

    @Override
    protected String getPropertyName() {
        return switch (mode) {
            case AVATAR -> "avatar link";
            default -> "banner link";
        };
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

}
