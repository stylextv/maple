package de.stylextv.maple.io.serialize.serializers;

import java.nio.ByteBuffer;

import de.stylextv.maple.io.resource.StreamedResource;
import de.stylextv.maple.io.serialize.Serializer;

public class DoubleSerializer extends Serializer<Double> {
	
	private static final int SIZE_IN_BYTES = 8;
	
	@Override
	public Double readFrom(StreamedResource r) {
		byte[] data = r.read(SIZE_IN_BYTES);
		
		ByteBuffer buffer = ByteBuffer.wrap(data);
		
		return buffer.getDouble();
	}
	
	@Override
	public void writeTo(StreamedResource r, Double d) {
		ByteBuffer buffer = ByteBuffer.allocate(SIZE_IN_BYTES);
		
		buffer.putDouble(d);
		
		byte[] data = buffer.array();
		
		r.write(data);
	}
	
}
