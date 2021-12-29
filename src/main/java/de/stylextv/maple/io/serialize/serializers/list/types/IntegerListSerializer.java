package de.stylextv.maple.io.serialize.serializers.list.types;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import de.stylextv.maple.io.resource.StreamedResource;
import de.stylextv.maple.io.serialize.Serializer;
import de.stylextv.maple.io.serialize.serializers.list.ListSerializer;

public class IntegerListSerializer extends ListSerializer<Integer, Serializer<Integer>> {
	
	public IntegerListSerializer() {
		super(Serializer.INTEGER);
	}
	
	public void writeTo(StreamedResource r, int[] array) {
		IntStream stream = Arrays.stream(array);
		
		Stream<Integer> boxed = stream.boxed();
		
		List<Integer> list = boxed.toList();
		
		writeTo(r, list);
	}
	
}
