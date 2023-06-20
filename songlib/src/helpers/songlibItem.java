//Kenneth Lee KL877
//Abriel Hernandez AH1394
package helpers;

import java.util.List;
import java.util.ArrayList;

public class songlibItem {
	
	private List<String> metadata;
	
	public songlibItem(String song) {
		
		metadata = new ArrayList<String>(4);
		String[] songData = song.split("\\|"); 
		

		assert songData.length == 4 : "Song data error: " + songData;
		for(int i = 0; i < 4; i++) {
			this.metadata.add(i, songData[i]);
		}
	}
	
	public String getName() {
		return this.metadata.get(0);
	}
	
	public String getArtist() {
		return this.metadata.get(1);
		}
	
	public String getAlbum() {
		return this.metadata.get(2);
		}
	
	public String getYear() {
		return this.metadata.get(3);
	}
	
	public boolean checkExists(songlibItem otherSong) {
		if(this.getName().toLowerCase().equals(otherSong.getName().toLowerCase())
				&& this.getArtist().toLowerCase().equals(otherSong.getArtist().toLowerCase())) {
			return true;
		}
		return false;
	}
	
	public List<String> getMeta() {
		return this.metadata;
	}
}