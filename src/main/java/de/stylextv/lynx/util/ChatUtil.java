package de.stylextv.lynx.util;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import de.stylextv.lynx.Constants;
import de.stylextv.lynx.context.PlayerContext;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class ChatUtil {
	
	private static final String PREFIX = Constants.COLORED_NAME + " §8| §7";
	
	private static final int MAX_LINE_LENGTH = 45;
	
	private static final char FORMATTING_SYMBOL = '§';
	
	public static void sendList(List<String> list, int rows, int page, String command) {
		int l = list.size();
		
		int pages = (int) Math.ceil((double) l / page);
		
		page = MathUtil.clamp(page, 0, pages - 1);
		
		for(String s : list) {
			send("- " + s);
		}
		
		int n = rows - l;
		
		for(int i = 0; i < n; i++) {
			send("§8--");
		}
		
		String s = String.format("§8<< §7¦ §b>> §7%s/%s", page + 1, pages);
		
		send(s);
	}
	
	public static void send(String... lines) {
		for(String s : lines) {
			send(s);
		}
	}
	
	public static void send(String line) {
		List<String> list = splitLine(line);
		
		for(String s : list) {
			PlayerContext.player().sendMessage(new StringTextComponent(PREFIX + s), null);
		}
	}
	
	private static List<String> splitLine(String line) {
		List<String> list = new ArrayList<>();
		
		String[] words = line.split(" ");
		
		String s = null;
		
		for(String word : words) {
			
			if(s == null) {
				s = word;
				
				continue;
			}
			
			int l = 1;
			
			l += TextFormatting.stripFormatting(s).length();
			l += TextFormatting.stripFormatting(word).length();
			
			if(l <= MAX_LINE_LENGTH) {
				
				s += " " + word;
				
			} else {
				list.add(s);
				
				String formatting = findLastFormatting(s, true) + findLastFormatting(s, false);
				
				s = formatting + word;
			}
		}
		
		list.add(s);
		
		return list;
	}
	
	private static String findLastFormatting(String s, boolean color) {
		List<TextFormatting> list = getFormattings(s);
		
		list = Lists.reverse(list);
		
		for(TextFormatting format : list) {
			
			if(format.equals(TextFormatting.RESET)) return "";
			
			if(format.isColor()) {
				
				if(color) {
					return format.toString();
				} else {
					return "";
				}
				
			} else if(!color) {
				return format.toString();
			}
		}
		
		return "";
	}
	
	private static List<TextFormatting> getFormattings(String s) {
		List<TextFormatting> list = new ArrayList<>();
		
		char[] arr = s.toCharArray();
		
		boolean b = false;
		
		for(char ch : arr) {
			if(ch == FORMATTING_SYMBOL) {
				b = true;
				
				continue;
			}
			
			if(b) {
				TextFormatting format = TextFormatting.getByCode(ch);
				
				if(format != null) list.add(format);
			}
			
			b = false;
		}
		
		return list;
	}
	
}
