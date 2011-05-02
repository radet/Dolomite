import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;



public class LdapTest extends UnitTest {
	private static LdapUser flo;
	
	@Before
	public void connect(){
		// Create a new user and save it
		new LdapUser("flora.dupont@utt.fr", "test", "Flora", "Dupont", "flora.dupont").addUser();
		// Retrieve the user with login+passwd
		flo = LdapUser.connect("flora.dupont", "test");
	}

	@Test
    public void createAndRetrieveUser() 
	{	
	    
	    // Test
		flo.deleteUser();
		
		
	    
    }
	
	@Test
	public void tryConnectAsUser() {
		// Create a new user and save it
		new LdapUser("flora.dupont@utt.fr", "test", "Flora", "Dupont", "flora.dupont").addUser();
		LdapUser flo = LdapUser.connect("flora.dupont", "test");
		
		// Test 
	}
	
	@Test
	public void tryConnectWrongUser(){
		LdapUser stef = LdapUser.connect("stephane.batteux", "pas_le_bon");
		assertNull(stef);
	}
	
	@Test
	public void tryConnectWrongPwd(){
		LdapUser flo2 = LdapUser.connect("flora.dupont", "mauvais_mot_de_passe");
		assertNull(flo2);
	}
	
	@Test 
	public void tryUpdateWithWrongPwd(){
		LdapUser admin = LdapUser.connect("admin", "if052010");
		
		
		flo.updateUser("flora.dupont@utt.fr", "hehehe", "arolf", "tnopud");


		
		LdapUser floWithOldPwd = LdapUser.connect("flora.dupont", "test");
		
		
		
		assertNull(floWithOldPwd);

		
	}
	
	@Test
	public void tryUpdateWithRightPwd(){
		LdapUser admin = LdapUser.connect("admin", "if052010");
		
		flo.updateUser("flora.dupont@utt.fr", "hehehe", "arolf", "tnopud");
		
		LdapUser floModified = LdapUser.connect("flora.dupont", "hehehe");
		
		floModified.deleteUser();
		
		assertNotNull(floModified);
		assertEquals("flora.dupont@utt.fr", floModified.getEmail());
		assertEquals("arolf", floModified.getFirstname());
		assertEquals("tnopud", floModified.getLastname());
	}
	
	@Test
	public void tryDeleteUser(){
		
		flo.deleteUser();
		assertNotNull(flo);
		
		
		
		LdapUser flo2 = LdapUser.connect("flora.dupont", "test");
		
		assertNull(flo2);
	
	}
	
	@After
	public void endTest(){
		flo.deleteUser();
		assertNotNull(flo); 
		assertEquals("Flora", flo.getFirstname());
		assertEquals("Dupont", flo.getLastname());
		assertEquals("flora.dupont@utt.fr", flo.getEmail());
	}

}
