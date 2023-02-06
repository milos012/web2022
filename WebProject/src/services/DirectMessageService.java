package services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import models.DirectMessage;
import models.Post;
import models.User;

public class DirectMessageService {
	private ArrayList<DirectMessage> messages;
	private String path;
	private Comparator<DirectMessage> sorterDate;
	
	public DirectMessageService(ArrayList<DirectMessage> messages, String path, Comparator<DirectMessage> sorterDate) {
		super();
		this.messages = messages;
		this.path = path;
		this.sorterDate = sorterDate;
	}

	public DirectMessageService() {
		super();
	}

	public DirectMessageService(String path) {
		this.path = path;
		messages = new ArrayList<DirectMessage>();
		//initSorters();
		loadMessages(path);
	}

	private void loadMessages(String filePath) {
		ObjectMapper mapper = new ObjectMapper();
		path = filePath + File.separator;
		
		 try {
	            List<DirectMessage> msgList = Arrays.asList(mapper.readValue(Paths.get(path + "messages.json").toFile(), DirectMessage[].class));
	            System.out.println("Ucitavanje poruka uspesno===");

	            for (DirectMessage t : msgList) {
	            	
	            	messages.add(t);
	            }

	        } catch (IOException e) {
	            System.out.println("Error while loading direct messages!");
	        }
	}
	
	public DirectMessage createDM(DirectMessage dm) {
		
		DirectMessage nova = new DirectMessage(dm);
		messages.add(nova);
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			mapper.writeValue(Paths.get(path + "messages.json").toFile(), getMessages());
			System.out.println("Poruka je uspesno kreirana");
			System.out.println("na putanji: + " + path);
		} catch (IOException e) {
			System.out.println("Error while writing direct message!");
			return null;
		}
		return nova;
	}
	
	public List<DirectMessage> getPrimljenePorukeOdKorisnika(User ulogovani, User u2){
		ArrayList<DirectMessage> listaPoruka = new ArrayList<DirectMessage>();
		for (DirectMessage dm : messages) {
			if (ulogovani.getUsername().equals(dm.getPrimalac().getUsername()) && u2.getUsername().equals(dm.getPosiljalac().getUsername())) {
				listaPoruka.add(dm);
			}
		}
		//TODO: sortiraj po datumu prijema
		return listaPoruka;
	}
	
	public List<DirectMessage> getAllSentMsgs(User u){
		ArrayList<DirectMessage> listaPoruka = new ArrayList<DirectMessage>();
		for (DirectMessage dm : messages) {
			if (u.getUsername().equals(dm.getPosiljalac().getUsername())) {
				listaPoruka.add(dm);
			}
		}
		return listaPoruka;
	}
	
	public List<DirectMessage> getAllReceivedMsgs(User u){
		ArrayList<DirectMessage> listaPoruka = new ArrayList<DirectMessage>();
		for (DirectMessage dm : messages) {
			if (u.getUsername().equals(dm.getPrimalac().getUsername())) {
				listaPoruka.add(dm);
			}
		}
		return listaPoruka;
	}

	public ArrayList<DirectMessage> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<DirectMessage> messages) {
		this.messages = messages;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Comparator<DirectMessage> getSorterDate() {
		return sorterDate;
	}

	public void setSorterDate(Comparator<DirectMessage> sorterDate) {
		this.sorterDate = sorterDate;
	}
	
	
	
}
