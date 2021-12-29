package de.stylextv.maple.io.serialize.serializers.list.types;

import java.util.UUID;

import de.stylextv.maple.io.serialize.Serializer;
import de.stylextv.maple.io.serialize.serializers.list.ListSerializer;

public class UUIDListSerializer extends ListSerializer<UUID, Serializer<UUID>> {
	
	public UUIDListSerializer() {
		super(Serializer.UUID);
	}
	
}
