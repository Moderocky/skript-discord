package mx.kenzie.skript_discord.elements.types;

import ch.njol.skript.classes.Serializer;
import ch.njol.yggdrasil.Fields;
import mx.kenzie.argo.Json;
import mx.kenzie.eris.api.entity.Snowflake;
import mx.kenzie.eris.data.Payload;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;

public class PayloadSerializer<Type extends Payload> extends Serializer<Type> {

    @Override
    public Fields serialize(Type payload) throws NotSerializableException {
        final Fields fields = new Fields();
        fields.putObject("__type", payload.getClass());
        fields.putObject("__data", payload.toJson(null));
        return fields;
    }

    @Override
    protected Type deserialize(Fields fields)
        throws StreamCorruptedException, NotSerializableException {
        final Class<?> type = fields.getObject("__type", Class.class);
        final String json = fields.getObject("__data", String.class);
        if (type == null || json == null) return super.deserialize(fields);
        return (Type) Json.fromJson(json, type);
    }

    @Override
    public void deserialize(Type payload, Fields fields)
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

}

class SnowflakeSerializer<Type extends Snowflake> extends PayloadSerializer<Type> {
}
