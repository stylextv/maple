package de.stylextv.maple.io.serialize.serializers;

import de.stylextv.maple.io.resource.StreamedResource;
import de.stylextv.maple.io.serialize.Serializer;

public class BooleanSerializer extends Serializer<Boolean> {
	
	@Override
	public Boolean readFrom(StreamedResource r) {
		byte b = r.read();
		
		return b != 0;
	}
	
	@Override
	public void writeTo(StreamedResource r, Boolean b) {
		r.write((byte) (b ? 1 : 0));
	}
	
}
