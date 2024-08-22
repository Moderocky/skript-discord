package mx.kenzie.skript_discord.elements.types;

import mx.kenzie.eris.Bot;
import mx.kenzie.eris.DiscordAPI;
import mx.kenzie.eris.api.entity.Channel;
import mx.kenzie.eris.api.entity.Entity;
import mx.kenzie.eris.api.entity.Member;
import mx.kenzie.eris.api.entity.User;
import mx.kenzie.skript_discord.utility.ChannelCommandSender;
import mx.kenzie.skript_discord.utility.UserCommandSender;
import org.bukkit.command.CommandSender;
import org.skriptlang.skript.lang.converter.Converter;
import org.skriptlang.skript.lang.converter.Converters;

public class DiscordConverters {

    static {
        Converters.registerConverter(Channel.class, CommandSender.class, ChannelCommandSender::new, Converter.NO_RIGHT_CHAINING);
        Converters.registerConverter(User.class, CommandSender.class, UserCommandSender::new, Converter.NO_RIGHT_CHAINING);
        Converters.registerConverter(Member.class, User.class, member -> member.user, Converter.NO_RIGHT_CHAINING);
        Converters.registerConverter(Bot.class, DiscordAPI.class, Bot::getAPI, Converter.NO_RIGHT_CHAINING);
        Converters.registerConverter(Entity.class, DiscordAPI.class, entity -> entity.api, Converter.NO_RIGHT_CHAINING);
    }

}
