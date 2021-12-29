package de.stylextv.maple.io.serialize.serializers;

import java.nio.ByteBuffer;

import de.stylextv.maple.io.resource.StreamedResource;
import de.stylextv.maple.io.serialize.Serializer;

public class CharSerializer extends Serializer<Character> {
	
	private static final int SIZE_IN_BYTES = 2;
	
	@Override
	public Character readFrom(StreamedResource r) {
		byte[] data = r.read(SIZE_IN_BYTES);
		
		ByteBuffer buffer = ByteBuffer.wrap(data);
		
		return buffer.getChar();
	}
	
	@Override
	public void writeTo(StreamedResource r, Character c) {
		ByteBuffer buffer = ByteBuffer.allocate(SIZE_IN_BYTES);
		
		buffer.putChar(c);
		
		byte[] data = buffer.array();
		
		r.write(data);
	}
	
}
