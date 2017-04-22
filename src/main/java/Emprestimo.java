
public class Emprestimo {
	public final int NO_PRAZO = 1;
	public final int VENCIDO = 2;
	private String titulo, autor, situacao;
	private int id;
	
	public Emprestimo(int id, String titulo, String autor, String situacao){
		this.titulo = titulo;
		this.autor = autor;
		this.situacao = situacao;
		this.id = id;
	}
	
	public String getAutor(){
		return autor;
	}
	
	public String titulo(){
		return titulo;
	}
	
	public String getSituacao(){
		return situacao;
	}
	
	public int getId(){
		return id;
	}
	
}
