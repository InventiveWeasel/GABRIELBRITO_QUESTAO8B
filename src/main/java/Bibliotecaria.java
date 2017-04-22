
public class Bibliotecaria {
	UsuarioDB userDB;
	LivroDB livroDB;
	EmprestimoDB emprestimoDB;
	
	public Bibliotecaria(UsuarioDB userDB, LivroDB livroDB, EmprestimoDB emprestimoDB){
		this.userDB = userDB;
		this.livroDB = livroDB;
		this.emprestimoDB = emprestimoDB;
	}
	
	public boolean inserirUsuario(Usuario user){
		return userDB.createUser(user);
	}
	
	public boolean deleteUsuario(Usuario user){
		return userDB.deleteUser(user.getId());
	}
	
	public boolean inserirLivro(Livro livro){
		return livroDB.InserirLivro(livro);
	}
	
	public boolean deleteLivro(Livro livro){
		return livroDB.deleteLivro(livro.getId());
	}
	
	public void blockUser(Usuario user, int prazo, int motivo){
		user.block(prazo, motivo);
		userDB.updateUser(user.getId());
	}
	
	public void unBlockUser(Usuario user){
		user.unblock();
		userDB.updateUser(user.getId());
	}
	
	public boolean emprestarLivro(Usuario user, Livro livro){
		if(user.isBlocked() || !livro.isDisponivel())
			return false;
		user.addEmprestimo(livro.getId(), livro.getTitulo(),livro.getAutor(),"No prazo");
		livro.emprestar(user);
		livroDB.updateLivro(livro.getId());
		emprestimoDB.registrarEmprestimo(livro);
		return true;
	}
	
	public boolean devolverLivro(Usuario user, Livro livro){
		Usuario aux = livro.getEmprestador();
		if(aux.getId() != user.getId())
			return false;
		if(aux.isBlocked()){
			unBlockUser(user);
		}
		user.removerEmprestimo(livro.getId());
		livro.devolver();
		livroDB.updateLivro(livro.getId());
		emprestimoDB.registrarDevolucao(livro);
		return true;
	}
}
