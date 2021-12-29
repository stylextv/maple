package de.stylextv.maple.io.serialize.serializers.list.types;

import de.stylextv.maple.io.serialize.Serializer;
import de.stylextv.maple.io.serialize.serializers.list.ListSerializer;

public class DoubleListSerializer extends ListSerializer<Double, Serializer<Double>> {
	
	public DoubleListSerializer() {
		super(Serializer.DOUBLE);
	}
	
}
