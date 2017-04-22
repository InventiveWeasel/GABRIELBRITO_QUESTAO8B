import java.util.ArrayList;

public class Usuario {
	public final int LIBERADO = 1;
	public final int ATRASO = 2;
	public final int COBRANCA = 3;
	
	private int id;
	private static int IdCounter=1;
	private boolean Blocked;
	private String situacao;
	private int prazo;
	private LivroDB livroDB;
	private ArrayList<Emprestimo> emprestimosAtuais;
	
	public Usuario(LivroDB livroDB){
		id = IdCounter;
		IdCounter++;
		Blocked = false;
		prazo = 0;
		situacao = "liberado";
		this.livroDB = livroDB;
		emprestimosAtuais = new ArrayList<Emprestimo>();
	}
	
	public int getId(){
		return id;
	}
	
	
	public void block(int prazo, int situacao){
		switch(situacao){
			case ATRASO:
				this.situacao = "atraso";
				break;
			case COBRANCA:
				this.situacao = "cobranca";
				break;
			default:
				this.situacao = "liberado";
		}
		Blocked = true;
		this.prazo = prazo;
	}
	
	public void unblock(){
		Blocked = false;
		situacao = "liberado";
		this.prazo = 0;
	}
	
	public boolean isBlocked(){
		return Blocked;
	}
	
	public String checkBookStatus(String titulo, String autor){
		return livroDB.getLivroStatus(titulo, autor);
	}
	
	public boolean addEmprestimo(int id, String titulo,String autor, String situacao){
		emprestimosAtuais.add(new Emprestimo(id, titulo, autor, situacao));
		return true;
	}
	
	public boolean removerEmprestimo(int id){
		Emprestimo aux;
		for(int i = 0; i < emprestimosAtuais.size(); i++){
			aux = emprestimosAtuais.get(i);
			if(aux.getId() == id){
				emprestimosAtuais.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Emprestimo> getEmprestimoList(){
		return emprestimosAtuais;
	}
	
	public String checkSituation(){
		return situacao;
	}
	
}
