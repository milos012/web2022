package services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import enums.RequestStatus;
import models.FriendRequest;
import models.Post;
import models.User;

public class FriendRequestService {
	private ArrayList<FriendRequest> friendRequests; // only ones that are PENDING
	private String path;
	private ArrayList<FriendRequest> allFriendRequests; //all 3 of them - PENDING,DECLINED and ACCEPTED
	
	
	
	public FriendRequestService() {
		super();
	}
	
	public FriendRequestService(ArrayList<FriendRequest> friendRequests, String path,
			ArrayList<FriendRequest> allFriendRequests) {
		super();
		this.friendRequests = friendRequests;
		this.path = path;
		this.allFriendRequests = allFriendRequests;
	}
	

	public FriendRequestService(String path) {
		friendRequests = new ArrayList<FriendRequest>();
		allFriendRequests = new ArrayList<FriendRequest>();
		loadFR(path);
	}


	private void loadFR(String filePath) {
		ObjectMapper mapper = new ObjectMapper();
        
        String data = filePath + File.separator;

        path = data;
        try {
            List<FriendRequest> frList = Arrays.asList(mapper.readValue(Paths.get(data + "frequests.json").toFile(), FriendRequest[].class));
            System.out.println("Ucitavanje zahteva za prijateljstvo uspesno===");

            for (FriendRequest t : frList) {
            	
            	if (t.getStatus().equals(RequestStatus.PENDING)) {
            		friendRequests.add(t);
    			}
            	allFriendRequests.add(t);
            }

        } catch (IOException e) {
            System.out.println("Error while loading posts!");
        }
		
	}
	
	public List<FriendRequest> getPoslatiZahteviNaCekanju(User posiljalac) {
		ArrayList<FriendRequest> zahtevi = new ArrayList<FriendRequest>();
		for (FriendRequest fr : friendRequests) {
			if (fr.getPosiljalac().getEmail().equals(posiljalac.getEmail())) {
				zahtevi.add(fr);
			}
		}
		return zahtevi;
	}
	
	public List<FriendRequest> getPrimljeniZahteviNaCekanju(User primalac) {
		ArrayList<FriendRequest> zahtevi = new ArrayList<FriendRequest>();
		for (FriendRequest fr : friendRequests) {
			if (fr.getPrimalac().getEmail().equals(primalac.getEmail())) {
				zahtevi.add(fr);
			}
		}
		return zahtevi;
	}
	
	private FriendRequest getFriendRequest(User posiljalac, User primalac) {
		for (FriendRequest fr : friendRequests) {
			if (fr.getPrimalac().getEmail().equals(primalac.getEmail()) &&  fr.getPosiljalac().getEmail().equals(posiljalac.getEmail())) {
				return fr;
			}
		}
		return null;
	}
	
	public FriendRequest addFR(FriendRequest fr) {
		if (getFriendRequest(fr.getPosiljalac(), fr.getPrimalac()) != null) {
			System.out.println("FR already exists");
			return null;
		}
		
		FriendRequest novi = new FriendRequest(fr);
		friendRequests.add(novi);
		allFriendRequests.add(novi);
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			mapper.writeValue(Paths.get(path + "frequests.json").toFile(), getAllFriendRequests());
			System.out.println("FR je uspesno kreiran");
			System.out.println("na putanji: + " + path);
		} catch (IOException e) {
			System.out.println("Error while writing friend request!");
			return null;
		}
		return novi;
		
	}
	
	public void acceptFR(FriendRequest fr) {
		if (getFriendRequest(fr.getPosiljalac(), fr.getPrimalac()) == null) {
			System.out.println("ERROR! FR doesn't exist");
			return;
		}
		for (FriendRequest x : friendRequests) {
			if (x.getPrimalac().getEmail().equals(fr.getPrimalac().getEmail()) &&  x.getPosiljalac().getEmail().equals(x.getPosiljalac().getEmail())) {
				allFriendRequests.remove(x);
				x.setStatus(RequestStatus.ACCEPTED);
				allFriendRequests.add(x);
				friendRequests.remove(x);
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(Paths.get(path + "frequests.json").toFile(), getAllFriendRequests());
		} catch (IOException e) {
			System.out.println("Error! Writing to file was unsuccessful.");
		}
		
	}
	
	public void declineFR(FriendRequest fr) {
		if (getFriendRequest(fr.getPosiljalac(), fr.getPrimalac()) == null) {
			System.out.println("ERROR! FR doesn't exist");
			return;
		}
		for (FriendRequest x : friendRequests) {
			if (x.getPrimalac().getEmail().equals(fr.getPrimalac().getEmail()) &&  x.getPosiljalac().getEmail().equals(x.getPosiljalac().getEmail())) {
				allFriendRequests.remove(x);
				x.setStatus(RequestStatus.DECLINED);
				allFriendRequests.add(x);
				friendRequests.remove(x);
			}
		}
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(Paths.get(path + "frequests.json").toFile(), getAllFriendRequests());
		} catch (IOException e) {
			System.out.println("Error! Writing to file was unsuccessful.");
		}
		
	}
	
	
	public ArrayList<FriendRequest> getFriendRequests() {
		return friendRequests;
	}

	public void setFriendRequests(ArrayList<FriendRequest> friendRequests) {
		this.friendRequests = friendRequests;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ArrayList<FriendRequest> getAllFriendRequests() {
		return allFriendRequests;
	}

	public void setAllFriendRequests(ArrayList<FriendRequest> allFriendRequests) {
		this.allFriendRequests = allFriendRequests;
	}
	
	

	

}
