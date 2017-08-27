package com.jpm.openart.core;

import java.util.HashMap;
import java.util.Map;

public class GenreMapping {
private static  final Map<String,String> map = new HashMap<String, String>();
	
	static{
		map.put("Reggaeton", "Red");
		map.put("Rock", "crimson");
		map.put("Heavy metal", "");
		map.put("Country", "brown");
		map.put("Tango", "maroon");
		map.put("Gospel", "sienna");
		map.put("free", "chocolate");
		map.put("Exotica", "salmon");
		map.put("Mariachi", "darksalmon");
		map.put("Soul", "orange");
		map.put("Ska", "");
		map.put("Folk music", "goldenrod");
		map.put("Disco", "gold");
		map.put("Merengue", "yellow");
		map.put("Salsa", "palegoldenrod");
		map.put("Calypso", "darkkhaki");
		map.put("Samba", "olive");
		map.put("Vallenato", "");
		map.put("Cumbia", "aquamarine");
		map.put("Klezmer", "springgreen");
		map.put("Zydeco", "chartreuse");
		map.put("Contemporary Christian", "lightgreen");
		map.put("Rumba", "seagreen");
		map.put("Reggae", "green");
		map.put("Flamenco", "darkgreen");
		map.put("Son montuno", "darkslategray");
		map.put("Electronic", "silver");
		map.put("New Age and Space music", "");
		map.put("Polka", "antiquewhite");
		map.put("Jazz", "pink");
		map.put("Raï", "mediumvioletred");
		map.put("Mambo", "darkorchid");
		map.put("Hip hop", "darkblue");
		map.put("Brazilian rock", "navy");
		map.put("Blues", "");
		map.put("Afropop", "deepskyblue");
		map.put("Pop music", "");
		map.put("Filmi", "lavender");
		map.put("A cappella", "");
		
		
	}

	public static String getColorForGenre(String genre){
		return map.get(genre);
	}
	
}
