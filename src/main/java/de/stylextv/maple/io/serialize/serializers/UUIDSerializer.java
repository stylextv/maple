package de.stylextv.maple.io.serialize.serializers;

import java.util.UUID;

import de.stylextv.maple.io.resource.StreamedResource;
import de.stylextv.maple.io.serialize.Serializer;

public class UUIDSerializer extends Serializer<UUID> {
	
	@Override
	public UUID readFrom(StreamedResource r) {
		long l1 = Serializer.LONG.readFrom(r);
		long l2 = Serializer.LONG.readFrom(r);
		
		return new UUID(l1, l2);
	}
	
	@Override
	public void writeTo(StreamedResource r, UUID id) {
		long l1 = id.getMostSignificantBits();
		long l2 = id.getLeastSignificantBits();
		
		Serializer.LONG.writeTo(r, l1);
		Serializer.LONG.writeTo(r, l2);
	}
	
}
