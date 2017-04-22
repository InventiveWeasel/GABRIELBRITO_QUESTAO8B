
public interface UsuarioDB {
	public boolean createUser(Usuario user);
	public boolean deleteUser(int id);
	public boolean updateUser(int id);
	public Usuario getUser(int Id);
	
}
