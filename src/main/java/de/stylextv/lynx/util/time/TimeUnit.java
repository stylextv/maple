package de.stylextv.lynx.util.time;

public class TimeUnit {
	
	private static final TimeUnit[] UNITS = new TimeUnit[8];
	
	public static final TimeUnit MILLI_SECONDS = new TimeUnit("ms", "ms", 1);
	
	public static final TimeUnit SECONDS = new TimeUnit("second", "seconds", 1000);
	
	public static final TimeUnit MINUTES = new TimeUnit("minute", "minutes", 60000);
	
	public static final TimeUnit HOURS = new TimeUnit("hour", "hours", 3600000);
	
	public static final TimeUnit DAYS = new TimeUnit("day", "days", 86400000);
	
	public static final TimeUnit WEEKS = new TimeUnit("week", "weeks", 604800000);
	
	public static final TimeUnit MONTHS = new TimeUnit("month", "months", 2628002880l);
	
	public static final TimeUnit YEARS = new TimeUnit("year", "years", 31556952000l);
	
	private static int pointer;
	
	private String singular;
	private String plural;
	
	private long duration;
	
	public TimeUnit(String singular, String plural, long duration) {
		this.singular = singular;
		this.plural = plural;
		
		this.duration = duration;
		
		registerUnit(this);
	}
	
	public long getAmount(long time) {
		return time / duration;
	}
	
	public String getName(long amount) {
		return amount == 1 ? singular : plural;
	}
	
	public String getSingular() {
		return singular;
	}
	
	public String getPlural() {
		return plural;
	}
	
	public long getDuration() {
		return duration;
	}
	
	private static void registerUnit(TimeUnit u) {
		UNITS[pointer] = u;
		
		pointer++;
	}
	
	public static TimeUnit getUnit(long time) {
		for(int i = UNITS.length - 1; i > 0; i--) {
			
			TimeUnit u = UNITS[i];
			
			if(time > u.getDuration()) return u;
		}
		
		return MILLI_SECONDS;
	}
	
	public static TimeUnit[] getUnits() {
		return UNITS;
	}
	
}
