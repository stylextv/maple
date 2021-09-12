package de.stylextv.lynx.util.chat;

import java.util.ArrayList;
import java.util.List;

import de.stylextv.lynx.util.TextUtil;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;

public class Message {
	
	private static final String EVENT_INDICATOR = "§x";
	
	private String text;
	
	public Message(String s) {
		this.text = s;
	}
	
	public Message append(Message m) {
		return append(m.getText());
	}
	
	public Message append(String s) {
		return new Message(text + s);
	}
	
	public MutableText asText() {
		List<MutableText> texts = new ArrayList<>();
		
		List<String> list = new ArrayList<>();
		
		String[] split = text.split(EVENT_INDICATOR);
		
		for(int i = 0; i < split.length; i++) {
			
			String s = split[i];
			
			if(i != 0) s = EVENT_INDICATOR + s;
			
			list.add(s);
		}
		
		while(!list.isEmpty()) {
			
			String s = list.remove(0);
			
			if(!s.startsWith(EVENT_INDICATOR)) {
				texts.add(new LiteralText(s));
				
				continue;
			}
			
			s = s.replaceFirst(EVENT_INDICATOR, "");
			
			String visible = TextUtil.findBracket(s, '[', ']', true);
			String hover = TextUtil.findBracket(s, '(', ')', true);
			String command = TextUtil.findBracket(s, '{', '}', true);
			
			int l = 0;
			
			if(visible != null) l += visible.length() + 2;
			if(hover != null) l += hover.length() + 2;
			if(command != null) l += command.length() + 2;
			
			s = s.substring(l);
			
			if(!s.isEmpty()) list.add(0, s);
			
			MutableText text = new LiteralText(visible);
			
			if(hover != null) {
				
				LiteralText t = new LiteralText(hover);
				
				text.styled((style) -> {
					return style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, t));
				});
			}
			
			if(command != null) {
				
				text.styled((style) -> {
					return style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
				});
			}
			
			texts.add(text);
		}
		
		MutableText text = new LiteralText("");
		
		for(MutableText t : texts) {
			text = text.append(t);
		}
		
		return text;
	}
	
	public String getText() {
		return text;
	}
	
}
