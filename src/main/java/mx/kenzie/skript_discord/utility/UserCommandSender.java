package mx.kenzie.skript_discord.utility;

import mx.kenzie.argo.Json;
import mx.kenzie.eris.api.entity.Message;
import mx.kenzie.eris.api.entity.User;
import org.jetbrains.annotations.NotNull;

public record UserCommandSender(User user) implements DummyCommandSender {

    @Override
    public void sendMessage(@NotNull String... messages) {
        for (String message : messages) {
            if (message.startsWith("{") && message.endsWith("}")) {
                final Message wrapper = new Message();
                Json.fromJson(message, wrapper);
                this.user.sendMessage(wrapper);
            } else user.sendMessage(new Message(message));
        }
    }

    @Override
    public @NotNull String getName() {
        return user.displayName();
    }

}
