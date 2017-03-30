package fr.afcepf.atod.customer.test;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.afcepf.atod.customer.data.api.IDaoCustomer;
import fr.afcepf.atod.vin.data.exception.WineErrorCode;
import fr.afcepf.atod.vin.data.exception.WineException;
import fr.afcepf.atod.wine.entity.Adress;
import fr.afcepf.atod.wine.entity.Civility;
import fr.afcepf.atod.wine.entity.Customer;

/**
 * Test class for the database access of a {@link Customer}
 * @author ronan
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/springDataTest.xml")
public class TestDaoCustomer {
	private static Logger logger = Logger.getLogger(TestDaoCustomer.class);
	@Autowired
	private IDaoCustomer daoCustomer;
	private Customer nominal;
	private Customer mailEmpty;
	private Customer passwordEmpty;
	private WineException expectedException;

	/**
	 * should be done with a test database
	 * NOT PROD
	 * you can duplicate a database with the command below:
	 * mysqldump -uroot -p@bandito22 wine_bdd | mysql -uroot -p@bandito22 wine_bdd_test
	 * if the wine_bdd_test is not existing yet, you have to create it beforehand 
	 * Note: vu que ce mot de passe est connu de tous, je vais le changer ahaha
	 */
	@Before
	public void init() {
		//    	String path = Thread.currentThread().getContextClassLoader()
		//		.getResource("./createBdd.sh").getPath();
		//try {
		//	Process proc = Runtime.getRuntime().exec(path);
		//	proc.waitFor();
		//} catch (Exception e) {
		//	logger.error(e.getMessage());
		//}
	}

	/**
	 * Constructor && initialization
	 */
	public TestDaoCustomer() {
		expectedException = new WineException(WineErrorCode.LOGIN_MDP_INVALIDE,
				"mail  or password invalid");
		Adress adress = new Adress(1, "rue de rivoli", "18", false, null);
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-09-20 10:01:51");	
			nominal = new Customer(2,
					"customer",
					"customer", 
					date, 
					"fenwang@hotmail.com", 
					"fenwang@hotmail.com", 
					"test1234", 
					"0606080808", 
					date, 
					date, 
					Civility.MISS, 
					adress, 
					true);
			mailEmpty = new Customer(2,
					"customer",
					"customer", 
					date, 
					"", 
					"", 
					"test1234", 
					"0606080808", 
					date, 
					date, 
					Civility.MISS, 
					adress, 
					true);
			passwordEmpty = new Customer(2,
					"customer",
					"customer", 
					date, 
					"fenwang@hotmail.com", 
					"fenwang@hotmail.com", 
					"", 
					"0606080808", 
					date, 
					date, 
					Civility.MISS, 
					adress, 
					true);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}


	@Test
	public void testNominalCase() throws WineException {

		Customer retour = (Customer) daoCustomer.connect(nominal.getMail(), nominal.getPassword());
		Assert.assertNotNull(retour);
		Assert.assertEquals(retour.getFirstname(), nominal.getFirstname());
		Assert.assertEquals(retour.getLastname(), nominal.getLastname());
		Assert.assertEquals(retour.getLogin(), nominal.getLogin());
		Assert.assertEquals(retour.getMail(), nominal.getMail());
		Assert.assertEquals(retour.getPhonenumber(), nominal.getPhonenumber());
		Assert.assertEquals(retour.getPassword(), nominal.getPassword());
		Assert.assertEquals(retour.getCreatedAt().getTime(), nominal.getCreatedAt().getTime());
		Assert.assertEquals(retour.getUpdatedAt().getTime(), nominal.getUpdatedAt().getTime());
		
	}
	
	@Test(expected=WineException.class)
	public void testErrorCaseMailEmpty() throws WineException {
		daoCustomer.connect(mailEmpty.getMail(), mailEmpty.getPassword());
	}
	
	@Test
	public void testErrorCasePasswordEmpty() {
		try {
			daoCustomer.connect(passwordEmpty.getMail(), passwordEmpty.getPassword());
			Assert.fail("should not attempt this way");
		} catch (WineException e) {
			Assert.assertEquals(e.getErreurVin(), expectedException.getErreurVin());
		}
	}
}
