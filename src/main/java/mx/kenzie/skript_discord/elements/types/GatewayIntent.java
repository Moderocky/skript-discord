package mx.kenzie.skript_discord.elements.types;

import mx.kenzie.eris.api.magic.Intents;

public enum GatewayIntent implements Intents {

    GUILDS(Intents.GUILDS),
    GUILD_MEMBERS(Intents.GUILD_MEMBERS),
    GUILD_BANS(Intents.GUILD_BANS),
    GUILD_EMOJIS_AND_STICKERS(Intents.GUILD_EMOJIS_AND_STICKERS),
    GUILD_INTEGRATIONS(Intents.GUILD_INTEGRATIONS),
    GUILD_WEBHOOKS(Intents.GUILD_WEBHOOKS),
    GUILD_INVITES(Intents.GUILD_INVITES),
    GUILD_VOICE_STATES(Intents.GUILD_VOICE_STATES),
    GUILD_PRESENCES(Intents.GUILD_PRESENCES),
    GUILD_MESSAGES(Intents.GUILD_MESSAGES),
    GUILD_MESSAGE_REACTIONS(Intents.GUILD_MESSAGE_REACTIONS),
    GUILD_MESSAGE_TYPING(Intents.GUILD_MESSAGE_TYPING),
    DIRECT_MESSAGES(Intents.DIRECT_MESSAGES),
    DIRECT_MESSAGE_REACTIONS(Intents.DIRECT_MESSAGE_REACTIONS),
    DIRECT_MESSAGE_TYPING(Intents.DIRECT_MESSAGE_TYPING),
    MESSAGE_CONTENT(Intents.MESSAGE_CONTENT),
    GUILD_SCHEDULED_EVENTS(Intents.GUILD_SCHEDULED_EVENTS),
    AUTO_MODERATION_CONFIGURATION(Intents.AUTO_MODERATION_CONFIGURATION),
    AUTO_MODERATION_EXECUTION(Intents.AUTO_MODERATION_EXECUTION);

    private final int magic;

    GatewayIntent(int magic) {
        this.magic = magic;
    }

    public int magic() {
        return magic;
    }

    public static void main(String[] args) {
        for (GatewayIntent value : GatewayIntent.values()) {
            System.out.println(value.name().toLowerCase() + ": " + value.name().toLowerCase().replace('_', ' '));
        }
    }

}
