package de.stylextv.lynx.util.chat;

import java.util.List;

import de.stylextv.lynx.Constants;
import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.util.MathUtil;
import net.minecraft.network.chat.TextComponent;

public class ChatUtil {
	
	private static final String PREFIX = Constants.COLORED_NAME + " §8| §7";
	
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
		PlayerContext.player().sendMessage(new TextComponent(PREFIX + line), null);
	}
	
}
