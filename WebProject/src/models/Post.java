package models;

import java.util.List;

public class Post {
	
	private String slika;
	private String tekst;
	private List<Comment> komentari;
	
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

	@Override
	public String toString() {
		return "Post [slika=" + slika + ", tekst=" + tekst + ", komentari=" + komentari + "]";
	}

	
}
