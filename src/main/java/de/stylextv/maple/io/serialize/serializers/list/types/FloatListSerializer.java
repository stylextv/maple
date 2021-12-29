package de.stylextv.maple.io.serialize.serializers.list.types;

import de.stylextv.maple.io.serialize.Serializer;
import de.stylextv.maple.io.serialize.serializers.list.ListSerializer;

public class FloatListSerializer extends ListSerializer<Float, Serializer<Float>> {
	
	public FloatListSerializer() {
		super(Serializer.FLOAT);
	}
	
}
