package mx.kenzie.skript_discord.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import mx.kenzie.eris.api.entity.Guild;
import mx.kenzie.skript_discord.elements.ContextualElement;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Name("Discord Guild Properties")
@Description("""
    Various (text) properties of a Discord guild (server).
    These include:
        - Name
        - Description
        - Banner
        - Icon
        - Region
        
    A guild's properties might not exist if the guild data has not been resolved from Discord yet.
    When requesting a guild, make sure to `wait for {guild}` so that all data is available.""")
@Examples({
    """
        broadcast {guild}'s description""",
    """
        broadcast {guild}'s icon""",
})
@Since("1.0.0")
public class ExprGuildProperties extends SimplePropertyExpression<Guild, String> implements ContextualElement {

    private static final Map<String, Function<Guild, String>> providers = new HashMap<>();

    static {
        providers.put("description", guild -> guild.description);
        providers.put("banner", guild -> "https://cdn.discordapp.com/banners/" + guild.id + "/" + guild.banner + ".png");
        providers.put("name", guild -> guild.name);
        providers.put("icon", guild -> "https://cdn.discordapp.com/icons/" + guild.id + "/" + guild.icon + ".png");
        providers.put("icon hash", guild -> guild.icon_hash);
        providers.put("region", guild -> guild.region);
        providers.put("splash", guild -> guild.splash);
        providers.put("vanity code", guild -> guild.vanity_url_code);
        providers.put("preferred locale", guild -> guild.preferred_locale);

        final StringBuilder builder = new StringBuilder("(");
        for (String string : providers.keySet())
            builder.append(':').append(string).append("|");
        builder.deleteCharAt(builder.length() - 1);
        builder.append(')');
        register(ExprGuildProperties.class, String.class, builder.toString(), "guild");
    }

    protected String mode;
    protected Function<Guild, String> provider;

    @Override
    public boolean init(Expression<?>[] expressions, int pattern, Kleenean delayed, SkriptParser.ParseResult result) {
        for (Map.Entry<String, Function<Guild, String>> entry : providers.entrySet()) {
            if (!result.hasTag(entry.getKey())) continue;
            this.mode = entry.getKey();
            this.provider = entry.getValue();
            break;
        }
        return super.init(expressions, pattern, delayed, result);
    }

    @Override
    public String convert(Guild from) {
        return provider.apply(from);
    }

    @Override
    protected String getPropertyName() {
        return mode;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

}
