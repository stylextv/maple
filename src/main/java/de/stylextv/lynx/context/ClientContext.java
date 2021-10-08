package de.stylextv.lynx.context;

public class ClientContext {
	
	public static boolean isClientSide() {
//		System.out.println(Thread.currentThread());
		
		return true;
	}
	
}
