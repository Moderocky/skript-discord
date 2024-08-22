package mx.kenzie.skript_discord.wrapper;

import mx.kenzie.eris.DiscordAPI;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class DiscordEvent extends Event {

    public DiscordAPI api;

    @Override
    public @NotNull HandlerList getHandlers() {
        return null;
    }

}
