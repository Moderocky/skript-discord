package mx.kenzie.skript_discord.elements;

import ch.njol.skript.lang.Expression;
import mx.kenzie.eris.DiscordAPI;
import mx.kenzie.eris.api.entity.Entity;
import mx.kenzie.skript_discord.SkriptDiscord;
import mx.kenzie.skript_discord.wrapper.DiscordEvent;
import org.bukkit.event.Event;

public interface ContextualElement {

    default DiscordAPI getApi(Event event, Expression<DiscordAPI> expression, Entity... available) {
        if (expression != null) {
            final DiscordAPI api = expression.getSingle(event);
            if (api != null) return api;
        }
        for (Entity entity : available)
            if (entity.api != null) return entity.api;
        if (event instanceof DiscordEvent discord && discord.api != null)
            return discord.api;
        DiscordAPI api = SkriptDiscord.getUnaryApiInstance();
        if (api != null) return api;
        if (expression != null)
            throw new IllegalStateException("No Discord API available, please specify it in syntax.");
        return null;
    }

}
