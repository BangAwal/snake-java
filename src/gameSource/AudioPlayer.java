package gameSource;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public class AudioPlayer {
	//Using external library for music, and creating Hashmap to store music
	public static Map<String, Music> musicMap = new HashMap<String, Music>();
	
	public static void load() {
		
		try {
			musicMap.put("music", new Music("res/sound/backsound.ogg"));			//get the music from directory and put the music to the hashmap 
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Music getMusic(String key) {
		return musicMap.get(key);
	}

}
