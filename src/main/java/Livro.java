
public class Livro {
	private String autor;
	private String titulo;
	private int id;
	private static int idCounter = 1;
	private String status;
	private Usuario emprestador;
	
	public Livro(String titulo, String autor){
		this.autor = autor;
		this.titulo = titulo;
		this.id = idCounter;
		idCounter++;
		status = "disponivel";
		emprestador = null;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getTitulo(){
		return titulo;
	}
	
	public String getAutor(){
		return autor;
	}
	public Usuario getEmprestador(){
		return this.emprestador;
	}
	
	public boolean isDisponivel(){
		if(status.equals("disponivel"))
			return true;
		return false;
	}
	
	public void emprestar(Usuario user){
		status = "retirado";
		emprestador = user;
	}
	
	public void devolver(){
		status = "disponivel";
		emprestador = null;
	}
}
