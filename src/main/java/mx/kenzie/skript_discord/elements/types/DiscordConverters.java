package mx.kenzie.skript_discord.elements.types;

import mx.kenzie.eris.api.entity.Channel;
import mx.kenzie.eris.api.entity.Member;
import mx.kenzie.eris.api.entity.User;
import mx.kenzie.skript_discord.utility.ChannelCommandSender;
import mx.kenzie.skript_discord.utility.UserCommandSender;
import org.bukkit.command.CommandSender;
import org.skriptlang.skript.lang.converter.Converters;

public class DiscordConverters {

    static {
        Converters.registerConverter(Channel.class, CommandSender.class, ChannelCommandSender::new);
        Converters.registerConverter(User.class, CommandSender.class, UserCommandSender::new);
        Converters.registerConverter(Member.class, User.class, member -> member.user);
    }

}
