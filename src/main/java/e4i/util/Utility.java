package e4i.util;

public class Utility {
	
	/**
	 * Staticki metod koji proverava da li uneti string sadrzi cirilicne znakove
	 * @param text
	 * @return
	 */
	public static boolean containCyrillic(String text) {
		
		if(text == null) 
			return false;
		
		for(int i = 0; i < text.length(); i++) {
		    if(Character.UnicodeBlock.of(text.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
		        // contains Cyrillic
		    	return true;
		    }
		}
		return false;
	}

}
