import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

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
		bib.blockUser(user, 7, user.ATRASO);
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
		bib.blockUser(user, 7, user.ATRASO);
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
	
	//Usuario consegue ter acesso a sua lista de emprestimos
	@Test
	public void whenUserChecksOwnEmprestimoList(){
		bib.emprestarLivro(user, livro);
		bib.emprestarLivro(user, livro2);
		ArrayList<Emprestimo> emprestimos = user.getEmprestimoList();
		assertTrue(emprestimos.get(0).getId() == livro.getId());
		assertTrue(emprestimos.get(1).getId() == livro2.getId());
	}
	
	@Test
	public void whenUserChecksOwnEmprestimoListSituation(){
		bib.emprestarLivro(user, livro);
		bib.emprestarLivro(user, livro2);
		ArrayList<Emprestimo> emprestimos = user.getEmprestimoList();
		assertTrue(emprestimos.get(0).getSituacao().equals("No prazo"));
		assertTrue(emprestimos.get(1).getSituacao().equals("No prazo"));
	}
	
	@Test
	public void whenUserChecksSituationAndIsFreeReturnSituation(){
		assertTrue(user.checkSituation().equals("liberado"));
	}
	
	@Test
	public void whenUserIsBlockedAndCheckSituation(){
		bib.blockUser(user, 7, user.ATRASO);
		assertTrue(user.checkSituation().equals("atraso"));
		bib.unBlockUser(user);
		assertTrue(user.checkSituation().equals("liberado"));
		bib.blockUser(user, 7, user.COBRANCA);
		assertTrue(user.checkSituation().equals("cobranca"));
	}
	
}
