package cz.muni.fi.pa165.travelagency.dao;

import java.util.List;

import cz.muni.fi.pa165.travelagency.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.travelagency.entity.User;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author omular
 */
@ContextConfiguration(classes=PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserDaoTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private UserDao customerDao;
            
    private User c1;
    private User c2;
    
    @BeforeMethod
    public void setUp() {
        c1 = new User();
        c1.setEmail("test@mail.com");
        c1.setName("User Name");
        c1.setPhoneNumber("1234567890");
        c1.setPasswordHash("random_hash");
        
        c2 = new User();
        c2.setEmail("mail@test.net");
        c2.setName("Best User");
        c2.setPhoneNumber("42");
        c2.setPasswordHash("random_hash");
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testNullName() {
        c1.setName(null);
        customerDao.create(c1);
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testNullEmail() {
        c1.setEmail(null);
        customerDao.create(c1);
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testNullPhoneNumber() {
        c1.setPhoneNumber(null);
        customerDao.create(c1);
    }

    @Test
    public void testUpdate() {
        customerDao.create(c1);
        c1.setName("Another Name");
        customerDao.update(c1);
        User c = customerDao.findById(c1.getId());
        Assert.assertEquals("Another Name", c.getName());
        Assert.assertEquals("test@mail.com", c.getEmail());
        Assert.assertEquals("1234567890", c.getPhoneNumber());
        Assert.assertEquals("random_hash", c.getPasswordHash());
    }

    @Test
    public void testRemove() {
        customerDao.create(c1);
        Assert.assertTrue(customerDao.findAll().size() == 1);
        customerDao.remove(c1);
        Assert.assertTrue(customerDao.findAll().isEmpty());
    }

    @Test
    public void testFindAll() {
        customerDao.create(c1);
        customerDao.create(c2);
        List<User> cList = customerDao.findAll();
        Assert.assertTrue(cList.size() == 2);
    }

    @Test
    public void testFindById() {
        customerDao.create(c1);
        User c = customerDao.findById(c1.getId());
        Assert.assertEquals(c1.getId(), c.getId());
        Assert.assertEquals("test@mail.com", c.getEmail());
        Assert.assertEquals("User Name", c.getName());
        Assert.assertEquals("1234567890", c.getPhoneNumber());
        Assert.assertEquals("random_hash", c.getPasswordHash());
    }

    @Test
    public void testFindByName() {
        customerDao.create(c1);
        customerDao.create(c2);
        List<User> cList = customerDao.findByName("User");
        Assert.assertTrue(cList.size() == 2);
        cList = customerDao.findByName("Best User");
        Assert.assertTrue(cList.size() == 1);
        User c = cList.get(0);
        Assert.assertEquals("Best User", c.getName());
        Assert.assertEquals("42", c.getPhoneNumber());
        Assert.assertEquals("mail@test.net", c.getEmail());
        Assert.assertEquals("random_hash", c.getPasswordHash());
    }
    
    @Test(expectedExceptions = PersistenceException.class)
    public void testEmailUnique() {
        customerDao.create(c1);
        User c = new User();
        c.setEmail("test@mail.com");
        c.setName("Random Name");
        c.setPhoneNumber("654321");
        c.setPasswordHash("random_hash");
        customerDao.create(c);
    }

    @Test
    public void testFindByEmail() {
        customerDao.create(c1);
        User c = customerDao.findByEmail("test@mail.com");
        Assert.assertNotNull(c);
        Assert.assertEquals("test@mail.com", c.getEmail());
    }
    
    @Test
    public void testNotExistingEmail() {
        Assert.assertNull(customerDao.findByEmail("not@existing.mail"));
    }
    
    @Test
    public void testNotExistingId() {
        Assert.assertNull(customerDao.findById(Long.MIN_VALUE));
    }
}
