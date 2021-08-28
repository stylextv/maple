package de.stylextv.lynx.util.chat;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;

public class Message {
	
	private String text;
	
	public Message(String s) {
		this.text = s;
	}
	
	public MutableComponent asComponent() {
		return new TextComponent(text);
	}
	
	public static Message fromString(String s) {
		return new Message("§cHover and click [me](Text){command}!");
	}
	
}
