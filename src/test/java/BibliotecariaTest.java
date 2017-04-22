import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class BibliotecariaTest {
	UsuarioDB userDB;
	LivroDB livroDB;
	EmprestimoDB emprestimoDB;
	Bibliotecaria bib;
	Usuario user, user2;
	Livro livro, livro2;

	@Before
	public void setup(){
		userDB = mock(UsuarioDB.class);
		livroDB = mock(LivroDB.class);
		emprestimoDB = mock(EmprestimoDB.class);
		bib = new Bibliotecaria(userDB, livroDB, emprestimoDB);
		user = new Usuario(livroDB);
		user2 = new Usuario(livroDB);
		livro = new Livro("Naruto", "Masashi Kishimoto");
		livro2 = new Livro("Microfisica", "Foucault");
	}
	@Test
	public void testAddUser() {
		when(bib.inserirUsuario(user)).thenReturn(true);
		assertTrue(bib.inserirUsuario(user));
	}
	
	@Test
	public void testAddBook(){
		when(bib.inserirLivro(livro)).thenReturn(true);
		assertTrue(bib.inserirLivro(livro));
	}
	
	@Test
	public void deleteValidUser(){
		bib.inserirUsuario(user);
		when(bib.deleteUsuario(user)).thenReturn(true);
		assertTrue(bib.deleteUsuario(user));
	}
	
	@Test
	public void whenUserNotInDBthenReturnFalse(){
		when(bib.deleteUsuario(user2)).thenReturn(false);
		assertFalse(bib.deleteUsuario(user));
	}
	
	@Test
	public void whenBookIsAvailableAndBorrowThenReturnTrue(){
		bib.inserirLivro(livro);
		assertTrue(bib.emprestarLivro(user, livro));
	}
	
	@Test
	public void whenBookIsNotAvailableAndBorrowThenReturnFalse(){
		bib.inserirLivro(livro);
		bib.emprestarLivro(user, livro);
		assertFalse(bib.emprestarLivro(user2, livro));
	}
	
	@Test
	public void whenUserIsBlockedAndBorrowThenReturnFalse(){
		bib.blockUser(user, 7);
		assertFalse(bib.emprestarLivro(user, livro));
	}
	
	@Test
	public void whenUserRetrieveBookTheReturnTrue(){
		bib.inserirLivro(livro);
		bib.emprestarLivro(user, livro);
		assertTrue(bib.devolverLivro(user, livro));
	}
	
	@Test
	public void whenUserRetrieveBookThatHadNotBorrowedTheReturnFalse(){
		bib.inserirLivro(livro);
		bib.emprestarLivro(user, livro);
		assertFalse(bib.devolverLivro(user2, livro));
	}
	
	@Test
	public void whenUserIsUnblockedAndTriesToBorrowThenReturnTrue(){
		bib.emprestarLivro(user, livro);
		bib.blockUser(user, 7);
		bib.devolverLivro(user, livro);
		assertTrue(bib.emprestarLivro(user, livro2));
	}
	
	@Test
	public void whenUserChecksBookStatus(){
		String status;
		String titulo = "Naruto";
		String autor = "Masashi Kishimoto";
		bib.inserirLivro(livro);
		when(user.checkBookStatus(titulo, autor)).thenReturn("disponivel");
		status = user.checkBookStatus(titulo, autor);
		assertTrue(status.equals("disponivel"));
		when(user.checkBookStatus(titulo, autor)).thenReturn("retirado");
		status = user.checkBookStatus(titulo, autor);
		assertTrue(status.equals("retirado"));
		
	}
}
