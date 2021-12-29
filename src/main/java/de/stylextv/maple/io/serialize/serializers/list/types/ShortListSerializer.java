package de.stylextv.maple.io.serialize.serializers.list.types;

import de.stylextv.maple.io.serialize.Serializer;
import de.stylextv.maple.io.serialize.serializers.list.ListSerializer;

public class ShortListSerializer extends ListSerializer<Short, Serializer<Short>> {
	
	public ShortListSerializer() {
		super(Serializer.SHORT);
	}
	
}
