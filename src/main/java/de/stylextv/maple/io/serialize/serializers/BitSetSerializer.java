package de.stylextv.maple.io.serialize.serializers;

import java.util.BitSet;

import de.stylextv.maple.io.resource.StreamedResource;
import de.stylextv.maple.io.serialize.Serializer;

public class BitSetSerializer extends Serializer<BitSet> {
	
	@Override
	public BitSet readFrom(StreamedResource r) {
		int l = Serializer.INTEGER.readFrom(r);
		
		byte[] data = r.read(l);
		
		return BitSet.valueOf(data);
	}
	
	@Override
	public void writeTo(StreamedResource r, BitSet bitSet) {
		byte[] data = bitSet.toByteArray();
		
		int l = data.length;
		
		Serializer.INTEGER.writeTo(r, l);
		
		r.write(data);
	}
	
}
