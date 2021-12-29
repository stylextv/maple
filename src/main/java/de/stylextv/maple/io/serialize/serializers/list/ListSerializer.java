package de.stylextv.maple.io.serialize.serializers.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.stylextv.maple.io.resource.StreamedResource;
import de.stylextv.maple.io.serialize.Serializer;

public class ListSerializer<T,R extends Serializer<T>> extends Serializer<List<T>> {
	
	private Serializer<T> serializer;
	
	public ListSerializer(Serializer<T> s) {
		this.serializer = s;
	}
	
	@Override
	public List<T> readFrom(StreamedResource r) {
		int l = Serializer.INTEGER.readFrom(r);
		
		List<T> list = new ArrayList<>();
		
		for(int i = 0; i < l; i++) {
			
			T t = serializer.readFrom(r);
			
			list.add(t);
		}
		
		return list;
	}
	
	public void writeTo(StreamedResource r, T[] array) {
		List<T> list = Arrays.asList(array);
		
		writeTo(r, list);
	}
	
	@Override
	public void writeTo(StreamedResource r, List<T> list) {
		int l = list.size();
		
		Serializer.INTEGER.writeTo(r, l);
		
		list.forEach((t) -> serializer.writeTo(r, t));
	}
	
	public Serializer<T> getSerializer() {
		return serializer;
	}
	
}
