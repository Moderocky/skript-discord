package mx.kenzie.skript_discord.elements.types;

import mx.kenzie.eris.Bot;
import mx.kenzie.eris.DiscordAPI;
import mx.kenzie.eris.api.entity.*;
import mx.kenzie.skript_discord.utility.ChannelCommandSender;
import mx.kenzie.skript_discord.utility.UserCommandSender;
import org.bukkit.command.CommandSender;
import org.skriptlang.skript.lang.converter.Converter;
import org.skriptlang.skript.lang.converter.Converters;

public class DiscordConverters {

    static {
        Converters.registerConverter(Channel.class, CommandSender.class, ChannelCommandSender::new,
            Converter.NO_RIGHT_CHAINING);
        Converters.registerConverter(User.class, CommandSender.class, UserCommandSender::new,
            Converter.NO_RIGHT_CHAINING);
        Converters.registerConverter(Member.class, User.class, member -> member.user, Converter.NO_RIGHT_CHAINING);
        Converters.registerConverter(Bot.class, DiscordAPI.class, Bot::getAPI, Converter.NO_RIGHT_CHAINING);
        Converters.registerConverter(Entity.class, DiscordAPI.class, entity -> entity.api, Converter.NO_RIGHT_CHAINING);

        // ID converters
        Converters.registerConverter(String.class, User.class, DiscordConverters::makeUser, Converter.NO_CHAINING);
        Converters.registerConverter(Number.class, User.class, DiscordConverters::makeUser, Converter.NO_CHAINING);
        Converters.registerConverter(String.class, Channel.class, DiscordConverters::makeChannel, Converter.NO_CHAINING);
        Converters.registerConverter(Number.class, Channel.class, DiscordConverters::makeChannel, Converter.NO_CHAINING);
        Converters.registerConverter(String.class, Guild.class, DiscordConverters::makeGuild, Converter.NO_CHAINING);
        Converters.registerConverter(Number.class, Guild.class, DiscordConverters::makeGuild, Converter.NO_CHAINING);
    }

    private static User makeUser(String id) {
        var user = new User();
        user.id = id;
        return user;
    }

    private static User makeUser(Number id) {
        return makeUser(Long.toString(id.longValue()));
    }

    private static Channel makeChannel(String id) {
        var thing = new Channel();
        thing.id = id;
        return thing;
    }

    private static Channel makeChannel(Number id) {
        return makeChannel(Long.toString(id.longValue()));
    }

    private static Guild makeGuild(String id) {
        var thing = new Guild();
        thing.id = id;
        return thing;
    }

    private static Guild makeGuild(Number id) {
        return makeGuild(Long.toString(id.longValue()));
    }

}
