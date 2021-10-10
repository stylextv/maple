package de.stylextv.maple.util.chat;

import java.util.List;

import de.stylextv.maple.Constants;
import de.stylextv.maple.context.PlayerContext;
import de.stylextv.maple.util.MathUtil;
import net.minecraft.text.MutableText;

public class ChatUtil {
	
	private static final Message PREFIX = new Message(Constants.COLORED_NAME + " §8| §7");
	
	public static void sendList(List<String> list, int rows, int page, String command) {
		page--;
		
		int l = list.size();
		
		int pages = (int) Math.ceil((double) l / rows);
		
		if(pages == 0) pages = 1;
		
		int maxPage = pages - 1;
		
		page = MathUtil.clamp(page, 0, maxPage);
		
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
		
		String s = page == 0 ? "§8<< " : String.format("§x[§b<<]{#%s %s} ", command, page);
		
		s += "§7§ ";
		
		s += page == maxPage ? "§8>> " : String.format("§x[§b>>]{#%s %s} ", command, page + 2);
		
		s += String.format("§7%s/%s", page + 1, pages);
		
		send(s);
	}
	
	public static void send(String... lines) {
		for(String s : lines) {
			send(s);
		}
	}
	
	public static void send(String line) {
		Message m = new Message(line);
		
		send(m);
	}
	
	public static void send(Message m) {
		m = PREFIX.append(m);
		
		MutableText text = m.asText();
		
		PlayerContext.player().sendMessage(text, false);
	}
	
}
