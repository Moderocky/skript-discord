package mx.kenzie.skript_discord.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import mx.kenzie.eris.api.entity.Message;
import mx.kenzie.eris.api.entity.User;
import mx.kenzie.skript_discord.elements.ContextualElement;

@Name("Discord Message Author")
@Description("""
    The author of a Discord message.
    This is the user (or application) that composed a message.
        
    This will not be set for locally-created messages, or some system messages with no author.
        
    An author not exist if the message data has not been resolved from Discord yet.
    When requesting an existing message, make sure to `wait for {message}` so that all data is available.""")
@Examples({
})
@Since("1.0.0")
public class ExprAuthor extends SimplePropertyExpression<Message, User> implements ContextualElement {

    static {
        register(ExprAuthor.class, User.class, "author", "message");
    }

    @Override
    public User convert(Message from) {
        return from.author;
    }

    @Override
    protected String getPropertyName() {
        return "author";
    }

    @Override
    public Class<? extends User> getReturnType() {
        return User.class;
    }

}
