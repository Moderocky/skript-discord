package mx.kenzie.skript_discord.elements.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.StringMode;
import mx.kenzie.eris.api.entity.Message;
import mx.kenzie.skript_discord.elements.ContextualElement;
import org.bukkit.event.Event;

@Name("Content of Message")
@Description("""
    The (text) content of a message.""")
@Examples({
    """
        set {_message} to a new message
        set the content of {_message} to "hello"
        send {_message} to {_channel}""",
    "reset the content of {_message}"
})
@Since("1.0.0")
public class ExprMessageContent extends SimplePropertyExpression<Message, String> implements ContextualElement {

    static {
        register(ExprMessageContent.class, String.class, "[message] content[s]", "message");
    }

    @Override
    public String convert(Message from) {
        return from.content;
    }

    @Override
    protected String getPropertyName() {
        return "contents";
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return switch (mode) {
            case SET, ADD -> new Class[] {String.class};
            case RESET, DELETE -> new Class[0];
            default -> super.acceptChange(mode);
        };
    }

    @Override
    public void change(Event event, Object[] delta, Changer.ChangeMode mode) {
        for (Message message : this.getExpr().getArray(event)) {
            switch (mode) {
                case SET -> {
                    if (delta == null || delta.length < 1 || delta[0] == null)
                        message.content = null;
                    else
                        message.content = Classes.toString(delta[0], StringMode.MESSAGE);

                }
                case ADD -> {
                    if (delta != null && delta.length > 0)
                        message.content += Classes.toString(delta[0], StringMode.MESSAGE);
                }
                case RESET, DELETE -> message.content = null;
                default -> super.change(event, delta, mode);
            }
        }
    }

}
