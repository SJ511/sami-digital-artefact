import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;
import java.util.*;

public class AccountManagerTest {
    private AccountManager accountManager;

    @Before
    public void setUp() {
        accountManager = new AccountManager();
    }

    @Test
    public void testCreateAccount() {
        Scanner scanner = new Scanner("testUser\nTest@123\n");
        Account account = accountManager.createAccount(scanner);
        assertNotNull(account);
        assertEquals("testUser", account.getUsername());
    }

    @Test
    public void testValidatePasswordValid() {
        assertTrue(accountManager.validatePassword("Test@123"));
    }

    @Test
    public void testValidatePasswordInvalid() {
        assertFalse(accountManager.validatePassword("password"));
        assertFalse(accountManager.validatePassword("sami"));
    }

    @Test
    public void testLoginValid() {
        Scanner scanner = new Scanner("testUser\nTest@123\n");
        accountManager.createAccount(scanner);
        scanner = new Scanner("testUser\nTest@123\n");
        Account account = accountManager.login(scanner);
        assertNotNull(account);
        assertEquals("testUser", account.getUsername());
    }

    @Test
    public void testLoginInvalid() {
        Scanner scanner = new Scanner("testUser\nTest@123\n");
        accountManager.createAccount(scanner);
        scanner = new Scanner("testUser\nWrongPassword\n");
        Account account = accountManager.login(scanner);
        assertNull(account);
    }
}
