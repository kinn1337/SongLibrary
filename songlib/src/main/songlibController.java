//Kenneth Lee KL877
//Abriel Hernandez AH1394
package main;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import helpers.songlibItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class songlibController {

    @FXML private Label curr_songname;
    @FXML private Label curr_songartist;
    @FXML private Label curr_songalbum;
    @FXML private Label curr_songyear;
    
    @FXML private TextField songNameIn;
    @FXML private TextField songArtistIn;
    @FXML private TextField songAlbumIn;
    @FXML private TextField songYearIn;
    
    @FXML private ListView<String> mainList;
    
    private List<songlibItem> songlibItems = new ArrayList<songlibItem>();
    private ObservableList<String> mainListOBS = FXCollections.observableArrayList();
    private Stage stageRef; 


    public void start(Stage mainStage) {

    	stageRef = mainStage;
    	

    	load_songData(songlibItems); 
    	
    	
    	//make list visible
    	for(int i = 0; i < songlibItems.size(); i++) {
    		mainListOBS.add(songlibItems.get(i).getName() + " by " + songlibItems.get(i).getArtist());
    	}
    	mainList.setItems(mainListOBS);
        mainList.getSelectionModel().select(0);
        showItem();
        
        mainList.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> showItem());
    }
    
    private void load_songData(List<songlibItem> library) {
    	try {
    		
    		File songlibFile = new File("songlib.txt"); 
    		try {
    			songlibFile.createNewFile();
    		} catch (IOException e) {
    			
    			e.printStackTrace();
    		}
    		
    		Scanner songReader = new Scanner(songlibFile);
    		library.clear(); 
    		while(songReader.hasNextLine()) {
    			String song = songReader.nextLine();
    			library.add(new songlibItem(song)); 
    		}
    		songReader.close(); 
    	}
    	catch (FileNotFoundException e) {
    		System.out.println("Failed to read file\n");
    		System.exit(1);
    	}
    }
    
    private void write_songData(List<songlibItem> library) {
    	try {
    		File songlibFile = new File("songlib.txt");
    		songlibFile.delete(); 
    		songlibFile.createNewFile(); 
    		FileWriter songWriter = new FileWriter(songlibFile);
    		for(int i = 0; i < library.size(); i++) {
    			String name = library.get(i).getName();
    			String artist = library.get(i).getArtist();
    			String album = library.get(i).getAlbum();
    			String year = library.get(i).getYear();
    			if(i == library.size()-1) {
    				songWriter.write(name + "|" + artist + "|" + album + "|" + year);
    			}
    			else {
    				songWriter.write(name + "|" + artist + "|" + album + "|" + year + "\n");
    			}
    		}
    		songWriter.close();
    		
    	}
    	catch (FileNotFoundException e) {
    		System.out.println("Failed to find file to write\n");
    		System.exit(1);
    	}
    	catch (IOException e) {
    		System.out.println("Failed to save songlib.txt\n");
    		System.exit(1);
    	}
    }
    
    private void lexicographicOrder(List<songlibItem> library, ObservableList<String> obsList) {
    	for(int i = 0; i < library.size(); i++) {
    		for(int j = i+1; j < library.size(); j++) {
    			String steadyName = library.get(i).getName();
    			String movingName = library.get(j).getName();
    			
    			if(movingName.compareToIgnoreCase(steadyName) < 0) {
    				songlibItem temp = library.get(i);
    				library.set(i, library.get(j));
    				library.set(j, temp);
    				
    				String tempString = obsList.get(i);
    				obsList.set(i, obsList.get(j));
    				obsList.set(j, tempString);
    			}
    			else if (movingName.compareToIgnoreCase(steadyName) == 0) { 
    				String steadyArtist = library.get(i).getArtist();
    				String movingArtist = library.get(j).getArtist();
    				
    				if(movingArtist.compareToIgnoreCase(steadyArtist) < 0) { 
    					songlibItem temp = library.get(i);
    					library.set(i, library.get(j));
    					library.set(j, temp);
    					
    					String tempString = obsList.get(i);
        				obsList.set(i, obsList.get(j));
        				obsList.set(j, tempString);
    				}
    			}
    		}
    	}
    }
    
    private void showItem() {
    	if(mainList.getSelectionModel().isEmpty()) {
    		curr_songname.setText("");
	    	curr_songartist.setText("");
	    	curr_songalbum.setText("");
	    	curr_songyear.setText("");
    	}
    	else {
	    	songlibItem temp = songlibItems.get(mainList.getSelectionModel().getSelectedIndex());
	    	curr_songname.setText(temp.getName());
	    	curr_songartist.setText(temp.getArtist());
	    	
	    	String album = temp.getAlbum();
	    	String year = temp.getYear();
	    	if(album.equals("-")) {
	    		curr_songalbum.setText("");
	    	}
	    	else {
	    		curr_songalbum.setText(temp.getAlbum());
	    	}
	    	if(year.equals("-")) {
	    		curr_songyear.setText("");
	    	}
	    	else {
	    		curr_songyear.setText(temp.getYear());
	    	}
    	}
    }
        
    private boolean confirmAction(Stage mainStage, String header, String message) {

    	Alert popup = new Alert(AlertType.CONFIRMATION);
    	popup.initOwner(mainStage);
    	popup.setTitle("Confirm Action");
    	popup.setHeaderText(header);
    	popup.setContentText(message);
    	popup.showAndWait();
    	if(popup.getResult().getButtonData().toString().equals("OK_DONE")) {
    		return true;
    	}
    	return false;
    }
    private void showError(Stage mainStage, String header, String message) {

    	Alert popup = new Alert(AlertType.ERROR);
    	popup.initOwner(mainStage);
    	popup.setTitle("Error");
    	popup.setHeaderText(header);
    	popup.setContentText(message);
    	popup.showAndWait();
    }

    
    @FXML
    protected void addItem() { 
    	String[] inputFields = {songNameIn.getText(), songArtistIn.getText(), songAlbumIn.getText(), songYearIn.getText()};
    	if(inputFields[0].isBlank() || inputFields[1].isBlank() || inputFields[0].equals("|") || inputFields[1].equals("|")) {
    		System.out.println("Invalid input, missing name/artist");
    		showError(stageRef, "Invalid input!", "Song name and artist is required!");

    		
    	}
    	else {
	    	String newSong = inputFields[0] + "|" + inputFields[1] + "|"; //name and artist are set
	    	
	    	if(inputFields[2].isBlank()) {
	    		newSong += "-|"; 
	    	}
	    	else {
	    		newSong += inputFields[2] + "|"; 
	    	}
	    	try {
		    	if(inputFields[3].isBlank()) {
		    		newSong += "-"; 
		    	}
		    	else {
		    		
		    		if(Integer.parseInt(inputFields[3]) < 0) {
		    			showError(stageRef, "Invalid input!", "Song year can not be negative!");
		    			return;
		    		}
		    		newSong += String.valueOf(Integer.parseInt(inputFields[3])); //has year, set year
		    	}
		    	
		    	songlibItem temp = new songlibItem(newSong);
		   
		        for(int i = 0; i < songlibItems.size(); i++) {
		        	if(temp.checkExists(songlibItems.get(i))) {
		        		System.out.println("Song already exists in library!");
		        		showError(stageRef, "Duplicate song!",
		        				"The song you are trying to add is already in the library!");
		        		return;
		        	}
		        }
		        
		        boolean confirmation = confirmAction(stageRef, "Confirm add?", "Are you sure you want to add this song?");
		        if(!confirmation) {
		        	return;
		        }
		        
	        	songlibItems.add(temp);
	        	mainListOBS.add(temp.getName() + " by " + temp.getArtist());
	        	lexicographicOrder(songlibItems, mainListOBS); //sorts both list lexicographically
	        	mainList.getSelectionModel().select(songlibItems.indexOf(temp)); //select the added song
	        	showItem();
	        	write_songData(songlibItems);
	        	songNameIn.clear();
	        	songArtistIn.clear();
	        	songAlbumIn.clear();
	        	songYearIn.clear();
	        	
	    	}
	    	catch (NumberFormatException e) {
	    		System.out.println("Year input is invalid!");
	    		showError(stageRef, "Invalid input!",
	    				"The song year was not recognized as a valid input.\nPlease provide a positive integer for the song year.");
	    		newSong += "-";
	    		
	    	}
    	}
    }

    @FXML
    protected void deleteItem() { 
    	try {
	    	int toBeDeleted = mainList.getSelectionModel().getSelectedIndex(); 
	    	
	    	//add confirmation
	    	if(!songlibItems.isEmpty()) {
	    		boolean confirmation = confirmAction(stageRef, "Confirm delete?", "Are you sure you want delete this song?");
	    		if(!confirmation) {
	    			return;
	    		}
	    	}
	    	
	    	songlibItems.remove(toBeDeleted); 
	    	mainListOBS.remove(toBeDeleted); 
	    	write_songData(songlibItems); 
	    	if(songlibItems.isEmpty()) { 
	    		showItem();
	    	}
	    	else if (toBeDeleted == songlibItems.size()) {
	    		mainList.getSelectionModel().select(toBeDeleted-1);
	    		showItem();
	    	}
	    	else { 
	    		mainList.getSelectionModel().select(toBeDeleted);
	    		showItem();
	    	}
    	}
    	catch (IndexOutOfBoundsException e){
    		System.out.println("No song to delete!");
    		showError(stageRef, "Empty song library!", "There are no more songs to delete!");
    	}
    }
    
    @FXML
    protected void editItem() { 
    	
    	songlibItem temp = songlibItems.get(mainList.getSelectionModel().getSelectedIndex());
    	int index = mainList.getSelectionModel().getSelectedIndex();
    	
    	
    	String[] inputFields = {songNameIn.getText(), songArtistIn.getText(), songAlbumIn.getText(), songYearIn.getText()};
    	String newSong;
    	
    	if(inputFields[0].isBlank()) {
    		newSong =  temp.getName() + "|";
    	}
    	else {
    		newSong = inputFields[0] + "|"; 
    	}
    	
    	if(inputFields[1].isBlank()) {
    		newSong +=  temp.getArtist() +"|";
    	}
    	else {
    		newSong += inputFields[1] + "|"; 
    	}
    	if(inputFields[2].isBlank()) {
    		newSong +=  temp.getAlbum() +"|";
    	}
    	else {
    		newSong += inputFields[2] + "|"; 
    	}
    	try {
	    	if(inputFields[3].isBlank()) {
	    		newSong += temp.getYear(); 
	    	}
	    	else {
	    		if(Integer.parseInt(inputFields[3]) < 0) {
	    			showError(stageRef, "Invalid input!", "Song year can not be negative!");
	    			return;
	    		}
	    		newSong += String.valueOf(Integer.parseInt(inputFields[3])); 
	    	}
	    	if(!songlibItems.isEmpty()) {
	    		boolean confirmation = confirmAction(stageRef, "Confirm edit?", "Are you sure you want to edit"+temp.getName()+ "by "+temp.getArtist()+"?");
	    		if(!confirmation) {
	    			return;
	    		}
	    	}
	    	
	    	songlibItem newEdit = new songlibItem(newSong);
    		
	        for(int i = 0; i < songlibItems.size(); i++) {
	        	if(newEdit.checkExists(songlibItems.get(i))) { 
	        		if(!temp.checkExists(songlibItems.get(i))) {
	        			System.out.println("Song already exists in library");
		        		showError(stageRef, "Duplicate song",
		        				"The song you are trying to add is already in the library");
		        		return;
	        		}
	        	}
	        }
	        
	        
	        songlibItems.remove(index);
	        mainListOBS.remove(index);
	        songlibItems.add(newEdit); 
        	mainListOBS.add(newEdit.getName() + " by " + newEdit.getArtist());
        	lexicographicOrder(songlibItems, mainListOBS); 
        	mainList.getSelectionModel().select(songlibItems.indexOf(newEdit)); 
        	showItem();
        	write_songData(songlibItems); 
        	songNameIn.clear();
        	songArtistIn.clear();
        	songAlbumIn.clear();
        	songYearIn.clear();
	        
    		
    	}catch(NumberFormatException e){
    		System.out.println("Year input is invalid");
    		showError(stageRef, "Invalid input",
    				"The song year was not recognized as a valid input.");
    		newSong += "-";
    		
    	}
    	
    	
    	
    	
    }
}