
public class Usuario {
	private int id;
	private static int IdCounter=1;
	private boolean Blocked;
	private int prazo;
	private LivroDB livroDB;
	
	public Usuario(LivroDB livroDB){
		id = IdCounter;
		IdCounter++;
		Blocked = false;
		prazo = 0;
		this.livroDB = livroDB;
	}
	
	public int getId(){
		return id;
	}
	
	
	public void block(int prazo){
		Blocked = true;
		this.prazo = prazo;
	}
	
	public void unblock(){
		Blocked = false;
		this.prazo = 0;
	}
	
	public boolean isBlocked(){
		return Blocked;
	}
	
	public String checkBookStatus(String titulo, String autor){
		return livroDB.getLivroStatus(titulo, autor);
	}
	
}
