package de.stylextv.lynx.util;

import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;

public class NBTUtil {
	
	public static ListNBT toTextList(String... arr) {
		ListNBT list = new ListNBT();
		
		for(String s : arr) {
			list.add(StringNBT.valueOf(toText(s)));
		}
		
		return list;
	}
	
	public static String toText(String s) {
		return "{\"text\":\"" + s + "\"}";
	}
	
}
