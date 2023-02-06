package models;

import java.util.ArrayList;
import java.util.List;

public class Post {
	
	private String slika;
	private String tekst;
	private List<Comment> komentari;
	private Boolean deleted;
	
	
	public Post(Post np) {
		this.slika = np.getSlika();
		this.tekst = np.getTekst();
		this.komentari = new ArrayList<Comment>();
		this.deleted = np.getDeleted();
	}
	
	public Post(String slika, String tekst, List<Comment> komentari, Boolean deleted) {
		super();
		this.slika = slika;
		this.tekst = tekst;
		this.komentari = komentari;
		this.deleted = deleted;
	}

	public Post(String slika, String tekst) {
		super();
		this.slika = slika;
		this.tekst = tekst;
	}

	public Post() {
		super();
	}

	public Post(String slika, String tekst, Boolean deleted) {
		super();
		this.slika = slika;
		this.tekst = tekst;
		this.deleted = deleted;
	}
	

	public Post(String tekst, Boolean deleted) {
		super();
		this.tekst = tekst;
		this.deleted = deleted;
	}

	public String getSlika()
	{
		return slika;
	}
	
	public List<Comment> getKomentari() {
		return komentari;
	}
	public void setKomentari(List<Comment> komentari) {
		this.komentari = komentari;
	}
	public void setSlika(String slika)
	{
		this.slika = slika;
	}
	public String getTekst()
	{
		return tekst;
	}
	public void setTekst(String tekst)
	{
		this.tekst = tekst;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "Post [slika=" + slika + ", tekst=" + tekst + ", komentari=" + komentari + "]";
	}

	
}
