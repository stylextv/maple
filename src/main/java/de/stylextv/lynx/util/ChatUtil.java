package de.stylextv.lynx.util;

import de.stylextv.lynx.Constants;
import de.stylextv.lynx.input.PlayerContext;
import net.minecraft.util.text.StringTextComponent;

public class ChatUtil {
	
	private static final String PREFIX = "§d[" + Constants.NAME + "] §8> §7";
	
	public static void sendToUser(String s) {
		PlayerContext.player().sendMessage(new StringTextComponent(PREFIX + s), null);
	}
	
}
