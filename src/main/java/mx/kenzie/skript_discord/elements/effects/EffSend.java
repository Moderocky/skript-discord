package mx.kenzie.skript_discord.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.StringMode;
import ch.njol.util.Kleenean;
import mx.kenzie.eris.DiscordAPI;
import mx.kenzie.eris.api.entity.Channel;
import mx.kenzie.eris.api.entity.Entity;
import mx.kenzie.eris.api.entity.Message;
import mx.kenzie.eris.api.entity.User;
import mx.kenzie.skript_discord.elements.ContextualElement;
import org.bukkit.event.Event;

@Name("Send Discord Message")
@Description("""
    An alternative to the `send %message% to %commandsender%` for Discord messages.
    This allows the sending of a Discord message to a channel or to a user.
        
    Unlike the regular send effect, this also marks the message as waiting for completion, allowing the `wait for` effect to be used with it.
    """)
@Examples({
    """
        set {_message} to a new message
        set the content of message to "hello!"
        send {_message} to {_channel} using {welcome bot}
        wait 5 seconds for {_message}""",
    """
        send discord message "hello" to {channel}""",
    """
        send "hello" to 988998882409189428 using {my bot}"""
})
@Since("1.0.0")
public class EffSend extends Effect implements ContextualElement {

    static {
        Skript.registerEffect(EffSend.class,
            "(send|dispatch) [[discord] message] %message/string% to %channel/user% [context:using %-discord%]"
        );
    }

    protected Expression<?> messageExpression;
    protected Expression<Entity> targetExpression;
    protected Expression<DiscordAPI> contextExpression;

    @Override
    public boolean init(Expression<?>[] expressions, int pattern, Kleenean delayed, ParseResult result) {
        this.messageExpression = expressions[0];
        //noinspection unchecked
        this.targetExpression = (Expression<Entity>) expressions[1];
        if (result.hasTag("context")) //noinspection unchecked
            this.contextExpression = (Expression<DiscordAPI>) expressions[2];
        return true;
    }

    @Override
    protected void execute(Event event) {
        final Object payload = messageExpression.getSingle(event);
        if (payload == null) return;
        final Entity target = targetExpression.getSingle(event);
        if (target == null) return;
        final Message message;
        if (payload instanceof Message thing) message = thing;
        else message = new Message(Classes.toString(payload, StringMode.MESSAGE));
        if (target.api == null) target.api = this.getApi(event, contextExpression, target, message);
        switch (target) {
            case User user -> user.sendMessage(message);
            case Channel channel -> channel.send(message);
            default -> {}
        }
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "send discord " + messageExpression.toString(event, debug)
            + " to " + targetExpression.toString(event, debug) + (contextExpression != null
            ? " using " + contextExpression.toString(event, debug) : "");
    }

}
