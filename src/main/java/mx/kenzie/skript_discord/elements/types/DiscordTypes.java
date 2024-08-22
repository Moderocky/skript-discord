package mx.kenzie.skript_discord.elements.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.EnumClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.yggdrasil.Fields;
import mx.kenzie.argo.Json;
import mx.kenzie.eris.Bot;
import mx.kenzie.eris.DiscordAPI;
import mx.kenzie.eris.api.Lazy;
import mx.kenzie.eris.api.entity.*;
import mx.kenzie.eris.data.Payload;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;

public class DiscordTypes {

    static {
        Classes.registerClass(new ClassInfo<>(Bot.class, "bot")
            .user("bots?")
            .name("Bot")
            .description("A Discord bot.")
            .since("1.0.0")
            //<editor-fold desc="String stuff" defaultstate="collapsed">
            .parser(new Parser<>() {

                @Override
                public Bot parse(String s, ParseContext context) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public boolean canParse(ParseContext context) {
                    return false;
                }

                @Override
                public String toString(Bot bot, int flags) {
                    return bot.toString();
                }

                @Override
                public String toVariableNameString(Bot bot) {
                    return "bot:" + bot.toString();
                }
            })
            .serializer(new Serializer<>() {
                @Override
                public Fields serialize(Bot bot) throws NotSerializableException {
                    Fields fields = new Fields();
                    fields.putObject("token", bot.token());
                    fields.putObject("secret", bot.secret());
                    fields.putPrimitive("intents", bot.intents());
                    return fields;
                }

                @Override
                protected Bot deserialize(Fields fields) throws StreamCorruptedException, NotSerializableException {
                    final String token, secret;
                    final int intents;
                    token = fields.getObject("token", String.class);
                    secret = fields.getObject("secret", String.class);
                    intents = fields.getPrimitive("intents", int.class);
                    return new Bot(token, secret, intents);
                }

                @Override
                public void deserialize(Bot bot, Fields fields)
                    throws StreamCorruptedException {
                    final String token, secret;
                    final int intents;
                    token = fields.getObject("token", String.class);
                    secret = fields.getObject("secret", String.class);
                    intents = fields.getPrimitive("intents", int.class);
                    bot.setToken(token);
                    bot.setSecret(secret);
                    bot.setIntents(intents);
                }

                @Override
                public boolean mustSyncDeserialization() {
                    return false;
                }

                @Override
                protected boolean canBeInstantiated() {
                    return false;
                }
            })//</editor-fold>
        );
        Classes.registerClass(new ClassInfo<>(DiscordAPI.class, "discord")
            .user("discord apis?")
            .name("Discord API")
            .description("""
                A live connection to the Discord API, usually belonging to a bot.
                Discord API connections are unique to a bot, e.g. if you have two bots logged in at once, they will have their own connections.
                                
                This connection can be fetched from any new Discord data (e.g. a message, a channel) as well as a logged-in bot.
                Therefore, any (live) discord entity can be used as a Discord API connection in syntax.
                                
                Note: API connections are not preserved in variables after a restart.""")
            .examples("""
                set {server} to the discord server with id "988998880794402856" using {bot}""")
            .since("1.0.0")
        );
        Classes.registerClass(new ClassInfo<>(Payload.class, "payload")
                .user("payloads?")
                .name("Payload")
                .description("A data object that can be sent to/received from Discord.")
                .since("1.0.0")
                //<editor-fold desc="String stuff" defaultstate="collapsed">
                .serializer(new Serializer<>() {

                    @Override
                    public Fields serialize(Payload payload) throws NotSerializableException {
                        final Fields fields = new Fields();
                        fields.putObject("__type", payload.getClass());
                        fields.putObject("__data", payload.toJson(null));
                        return fields;
                    }

                    @Override
                    protected Payload deserialize(Fields fields)
                        throws StreamCorruptedException, NotSerializableException {
                        final Class<?> type = fields.getObject("__type", Class.class);
                        final String json = fields.getObject("__data", String.class);
                        if (type == null || json == null) return super.deserialize(fields);
                        return (Payload) Json.fromJson(json, type);
                    }

                    @Override
                    public void deserialize(Payload payload, Fields fields)
                        throws StreamCorruptedException, NotSerializableException {
                        final String json = fields.getObject("__data", String.class);
                        if (json == null) return;
                        Json.fromJson(json, payload);
                    }

                    @Override
                    public boolean mustSyncDeserialization() {
                        return false;
                    }

                    @Override
                    protected boolean canBeInstantiated() {
                        return false;
                    }
                })
            //</editor-fold>
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

        Classes.registerClass(new EnumClassInfo<>(MessageFlag.class, "messageflag", "message flags")
            .user("message flags?")
            .name("Message Flags")
            .description("""
                Flags representing special data about a message (e.g. whether it was crossposted).
                Some of these flags (e.g. `ephemeral`) can be used when creating a message.""")
            .since("1.0.0")
        );

    }

}
