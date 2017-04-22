
public interface LivroDB {
	public boolean InserirLivro(Livro livro);
	public boolean deleteLivro(int id);
	public boolean updateLivro(int id);
	public Livro getLivro(String titulo, String autor);
	public String getLivroStatus(String titulo, String autor);
}
