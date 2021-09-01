package de.stylextv.lynx.util.time;

public class TimeFormat {
	
	public static String format(long time) {
		String s = null;
		
		while(time != 0 || s == null) {
			
			TimeUnit u = TimeUnit.getUnit(time);
			
			long amount = u.getAmount(time);
			
			time -= amount * u.getDuration();
			
			String s2 = amount + " " + u.getName(amount);
			
			if(s == null) {
				s = s2;
				
				continue;
			}
			
			s += " and " + s2;
			
			break;
		}
		
		return s;
	}
	
}
