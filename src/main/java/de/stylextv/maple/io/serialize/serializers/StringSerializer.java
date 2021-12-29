package de.stylextv.maple.io.serialize.serializers;

import de.stylextv.maple.io.resource.StreamedResource;
import de.stylextv.maple.io.serialize.Serializer;

public class StringSerializer extends Serializer<String> {
	
	@Override
	public String readFrom(StreamedResource r) {
		byte[] data = r.readAll();
		
		return new String(data);
	}
	
	@Override
	public void writeTo(StreamedResource r, String s) {
		byte[] data = s.getBytes();
		
		r.write(data);
	}
	
}
