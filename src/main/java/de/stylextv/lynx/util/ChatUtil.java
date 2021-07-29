package de.stylextv.lynx.util;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import de.stylextv.lynx.Constants;
import de.stylextv.lynx.context.PlayerContext;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;

public class ChatUtil {
	
	private static final String PREFIX = Constants.COLORED_NAME + " §8| §7";
	
	private static final int MAX_LINE_LENGTH = 50;
	
	private static final char FORMATTING_SYMBOL = '§';
	
	public static void sendList(List<String> list, int rows, int page, String command) {
		page--;
		
		int l = list.size();
		
		int pages = (int) Math.ceil((double) l / rows);
		
		if(pages == 0) pages = 1;
		
		page = MathUtil.clamp(page, 0, pages - 1);
		
		int start = page * rows;
		
		int end = Math.min(start + rows, l) - 1;
		
		for(int i = start; i <= end; i++) {
			String s = list.get(i);
			
			send("- " + s);
		}
		
		int remaining = rows - (end - start + 1);
		
		for(int i = 0; i < remaining; i++) {
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
			PlayerContext.player().sendMessage(new TextComponent(PREFIX + s), null);
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
			
			l += ChatFormatting.stripFormatting(s).length();
			l += ChatFormatting.stripFormatting(word).length();
			
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
		List<ChatFormatting> list = getFormattings(s);
		
		list = Lists.reverse(list);
		
		for(ChatFormatting format : list) {
			
			if(format.equals(ChatFormatting.RESET)) return "";
			
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
	
	private static List<ChatFormatting> getFormattings(String s) {
		List<ChatFormatting> list = new ArrayList<>();
		
		char[] arr = s.toCharArray();
		
		boolean b = false;
		
		for(char ch : arr) {
			if(ch == FORMATTING_SYMBOL) {
				b = true;
				
				continue;
			}
			
			if(b) {
				ChatFormatting format = ChatFormatting.getByCode(ch);
				
				if(format != null) list.add(format);
			}
			
			b = false;
		}
		
		return list;
	}
	
}
