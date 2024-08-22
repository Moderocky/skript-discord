package mx.kenzie.skript_discord.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.StringMode;
import ch.njol.util.Kleenean;
import mx.kenzie.eris.DiscordAPI;
import mx.kenzie.eris.api.entity.Embed;
import mx.kenzie.eris.api.entity.Message;
import mx.kenzie.eris.api.entity.message.Attachment;
import mx.kenzie.skript_discord.elements.ContextualElement;
import mx.kenzie.skript_discord.elements.types.MessageFlag;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

@Name("New Message")
@Description("""
    Creates a new message. Messages can have (text) content, as well as embeds, attachments and interaction elements.
    The message object can be stored in a variable.
    """)
@Examples({
    """
        set {message} to a new discord message "hello"
        send {message} to {channel}"""
})
@Since("1.0.0")
public class ExprMessage extends SimpleExpression<Message> implements ContextualElement {

    static {
        Skript.registerExpression(ExprMessage.class, Message.class, ExpressionType.SIMPLE,
            "[a] new [discord] message [flags:with flags %-messageflags%]",
            "[a] new [discord] message %string% [flags:with flags %-messageflags%]",
            "[a] new [discord] message with content[s] %objects% [flags:(with|and) flags %-messageflags%]"
        );
    }

    protected Expression<?> contentsExpression;
    protected Expression<MessageFlag> flagsExpression;

    @Override
    public boolean init(Expression<?>[] expressions, int pattern, Kleenean delayed, SkriptParser.ParseResult result) {
        if (pattern > 0)
            this.contentsExpression = expressions[0];
        if (result.hasTag("flags"))
            this.flagsExpression = (Expression<MessageFlag>) expressions[expressions.length - 1];
        return true;
    }

    @Override
    protected Message[] get(Event event) {
        final DiscordAPI available = this.getApi(event, null);
        final Message message = new Message();
        final List<Embed> embeds = new ArrayList<>();
        for (Object object : contentsExpression.getArray(event)) {
            switch (object) {
                case Embed embed -> embeds.add(embed);
                case Attachment attachment -> throw new UnsupportedOperationException();
                default -> message.content = Classes.toString(object, StringMode.MESSAGE);
            }
        }
        if (!embeds.isEmpty()) message.embeds = embeds.toArray(new Embed[0]);
        if (available != null) message.api = available;
        if (flagsExpression != null) {
            final MessageFlag[] array = flagsExpression.getArray(event);
            message.flags = MessageFlag.combine(array);
        }
        return new Message[] {message};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Message> getReturnType() {
        return Message.class;
    }

    @Override
    public String toString(Event event, boolean debug) {
        // todo
        return null;
    }

}
