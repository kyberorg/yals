package ee.yals.test.modeltests;

import ee.yals.exceptions.ElementAlreadyExistsException;
import ee.yals.models.Secret;
import ee.yals.models.User;
import ee.yals.models.dao.SecretDao;
import ee.yals.models.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:test-app.xml"})
@WebAppConfiguration
@TestPropertySource(locations = "classpath:test-app.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SecretsTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private SecretDao secretDao;

    @Test
    public void canCreateNewSecret() {
        User user = createUser();
        Secret secret = Secret.create("ABC321").forUser(user).please();
        secretDao.save(secret);

        List<Secret> allSecrets = secretDao.findAll();
        assertTrue(allSecrets.size() == 1);
    }

    @Test
    public void canUpdateSecret() {
        String initialSecret = "ABC321";
        String updatedSecretString = "CBA123";

        User user = createUser();
        Secret secret = Secret.create(initialSecret).forUser(user).please();
        secretDao.save(secret);

        secret.updateSecretWith(updatedSecretString);
        secretDao.save(secret);

        Optional<Secret> updatedSecret = secretDao.findSingleByUser(user);

        assertTrue(updatedSecret.isPresent());
        assertEquals(updatedSecretString, updatedSecret.get().getPassword());
    }

    @Test(expected = ElementAlreadyExistsException.class)
    public void cannotCreateMoreThanOneSecretForUser() {
        User user = createUser();
        Secret secret = Secret.create("ABC123").forUser(user).please();
        secretDao.save(secret);

        Secret secret2 = Secret.create("DEF321").forUser(user).please();
        secretDao.save(secret2);

        List<Secret> secrets = secretDao.findAll();
        assertTrue(secrets.size() == 1);
    }

    @Test(expected = IllegalStateException.class)
    public void cannotCreateSecretWithEmptyPassword() {
        User user = createUser();
        Secret.create("").forUser(user).please();
    }

    @Test(expected = IllegalStateException.class)
    public void cannotCreateSecretWithNullPassword() {
        User user = createUser();
        Secret.create(null).forUser(user).please();
    }

    private User createUser() {
        User u = User.create("uzer");
        return userDao.save(u);
    }
}
