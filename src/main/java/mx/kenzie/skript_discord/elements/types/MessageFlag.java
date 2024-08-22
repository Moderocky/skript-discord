package mx.kenzie.skript_discord.elements.types;

import mx.kenzie.eris.api.magic.Intents;

public enum MessageFlag implements Intents {

    CROSSPOSTED(1 << 0), // this message has been published to subscribed channels (via Channel Following)
    IS_CROSSPOST(1 << 1), // this message originated from a message in another channel (via Channel Following)
    SUPPRESS_EMBEDS(1 << 2), // do not include any embeds when serializing this message
    SOURCE_MESSAGE_DELETED(1 << 3), // the source message for this crosspost has been deleted (via Channel Following)
    URGENT(1 << 4), // this message came from the urgent message system
    HAS_THREAD(1 << 5), // this message has an associated thread, with the same id as the message
    EPHEMERAL(1 << 6), // this message is only visible to the user who invoked the Interaction
    LOADING(1 << 7), // this message is an Interaction Response and the bot is "thinking"
    FAILED_TO_MENTION_SOME_ROLES_IN_THREAD(1 << 8), // this message failed to mention some roles and add their members to the thread
    SUPPRESS_NOTIFICATIONS(1 << 12), // this message will not trigger push and desktop notifications
    IS_VOICE_MESSAGE(1 << 13); // this message is a voice message

    private final int magic;

    MessageFlag(int magic) {
        this.magic = magic;
    }

    public int magic() {
        return magic;
    }

    public static int combine(MessageFlag... flags) {
        int total = 0;
        for (MessageFlag flag : flags) total |= flag.magic;
        return total;
    }

    public static void main(String[] args) {
        for (MessageFlag value : MessageFlag.values()) {
            System.out.println(value.name().toLowerCase() + ": " + value.name().toLowerCase().replace('_', ' '));
        }
    }

}
