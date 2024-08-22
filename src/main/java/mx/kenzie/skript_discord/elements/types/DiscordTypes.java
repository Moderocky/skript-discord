package mx.kenzie.skript_discord.elements.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.EnumClassInfo;
import ch.njol.skript.registrations.Classes;
import mx.kenzie.eris.Bot;
import mx.kenzie.eris.DiscordAPI;
import mx.kenzie.eris.api.Lazy;
import mx.kenzie.eris.api.entity.*;
import mx.kenzie.eris.data.Payload;

public class DiscordTypes {

    static {
        Classes.registerClass(new ClassInfo<>(Bot.class, "bot")
            .user("bots?")
            .name("Bot")
            .description("A Discord bot.")
            .since("1.0.0")
        );
        Classes.registerClass(new ClassInfo<>(DiscordAPI.class, "discordapi")
            .user("discord apis?")
            .name("Discord API")
            .description("A live connection to the Discord API, belonging to a bot.")
            .since("1.0.0")
        );
        Classes.registerClass(new ClassInfo<>(Payload.class, "payload")
            .user("payloads?")
            .name("Payload")
            .description("A data object that can be sent to/received from Discord.")
            .since("1.0.0")
        );
        Classes.registerClass(new ClassInfo<>(Entity.class, "discordentity")
            .user("discord entit(y|ies)")
            .name("Entity")
            .description("A Discord entity (thing) e.g. a message, user, channel, role, server.")
            .since("1.0.0")
        );
        Classes.registerClass(new ClassInfo<>(Lazy.class, "lazy")
            .user("laz(y|ies)")
            .name("Lazy (Entity)")
            .description("A dispatched/received Discord element.")
            .since("1.0.0")
        );
        Classes.registerClass(new ClassInfo<>(Snowflake.class, "snowflake")
            .user("snowflakes?")
            .name("Snowflake")
            .description("A Discord entity with a numeric ID, e.g. a user, a channel, a message.")
            .since("1.0.0")
        );

        // basic things
        Classes.registerClass(new ClassInfo<>(User.class, "user")
            .user("users?")
            .name("User")
            .description("A Discord user.")
            .since("1.0.0")
        );
        Classes.registerClass(new ClassInfo<>(Member.class, "member")
            .user("members?")
            .name("Member")
            .description("""
                A Discord member in a server.
                Members are linked to a particular server.
                Member objects are not transferable between servers (e.g. user X's membership in server A is different from user X's membership in server B).""")
            .since("1.0.0")
        );
        Classes.registerClass(new ClassInfo<>(Self.class, "self")
            .user("sel(f|ves)")
            .name("Self")
            .description("""
                A bot's user account, containing some extra information.
                This may also represent a user account which has given access to the application.""")
            .since("1.0.0")
        );

        Classes.registerClass(new EnumClassInfo<>(GatewayIntent.class, "gatewayintent", "gateway intents")
            .user("(gateway|login) intents?")
            .name("Gateway Intents")
            .description("""
                The things your bot intends to interact with after logging in.
                If a gateway intent is not requested, some functionality may be unavailable.
                Some gateway intents are "privileged" (and require configuration in the Discord portal to access).""")
            .since("1.0.0")
        );

    }

}
