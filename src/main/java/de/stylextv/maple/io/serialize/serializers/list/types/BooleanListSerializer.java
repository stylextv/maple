package de.stylextv.maple.io.serialize.serializers.list.types;

import de.stylextv.maple.io.serialize.Serializer;
import de.stylextv.maple.io.serialize.serializers.list.ListSerializer;

public class BooleanListSerializer extends ListSerializer<Boolean, Serializer<Boolean>> {
	
	public BooleanListSerializer() {
		super(Serializer.BOOLEAN);
	}
	
}
