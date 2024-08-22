package mx.kenzie.skript_discord.utility;

import mx.kenzie.argo.Json;
import mx.kenzie.eris.api.entity.Channel;
import mx.kenzie.eris.api.entity.Message;
import org.jetbrains.annotations.NotNull;

public record ChannelCommandSender(Channel channel) implements DummyCommandSender {

    @Override
    public void sendMessage(@NotNull String... messages) {
        for (String message : messages) {
            if (message.startsWith("{") && message.endsWith("}")) {
                final Message wrapper = new Message();
                Json.fromJson(message, wrapper);
                this.channel.send(wrapper);
            } else channel.send(new Message(message));
        }
    }

    @Override
    public @NotNull String getName() {
        return channel.name;
    }

}
