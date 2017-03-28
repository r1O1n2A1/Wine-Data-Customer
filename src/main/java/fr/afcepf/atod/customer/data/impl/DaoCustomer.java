package fr.afcepf.atod.customer.data.impl;

import fr.afcepf.atod.customer.data.api.IDaoCustomer;
import fr.afcepf.atod.vin.data.exception.WineErrorCode;
import fr.afcepf.atod.vin.data.exception.WineException;
import fr.afcepf.atod.wine.data.impl.DaoGeneric;
import fr.afcepf.atod.wine.data.util.DaoUtil;
import fr.afcepf.atod.wine.entity.Customer;
import fr.afcepf.atod.wine.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ronan
 */
@Service
@Transactional
public class DaoCustomer extends DaoGeneric<User, Integer> 
	implements IDaoCustomer {
	
	/****************************************************
	 *  Requetes HQL
	 ***************************************************/

	/**
	 * Return the user thanks to his mail and password
	 */
	private static final String REQCONNEXION = "SELECT u FROM User u "
			+ "WHERE  u.mail     = :paramMail"
			+ " AND   u.password = :paramPassword";

	/***************************************************
	 * Fin Requetes HQL
	 ***************************************************/

	/* (non-Javadoc)
	 * @see fr.afcepf.atod.customer.data.api.IDaoCustomer#connect(java.lang.String, java.lang.String)
	 */
	@Override
	public User connect(String mail, String password) throws WineException {
		User user = null;
		if (!mail.equalsIgnoreCase(DaoUtil.EMPTY_STR)
				&& !password.equalsIgnoreCase(DaoUtil.EMPTY_STR)) {
			user = (User) (getSf().getCurrentSession()
					.createQuery(REQCONNEXION)
					.setParameter("paramMail", mail)
					.setParameter("paramPassword", password)
					.uniqueResult());

			if (!user.getLastname().equalsIgnoreCase(DaoUtil.EMPTY_STR)) {
				return user;
			} else {
				throw new WineException(WineErrorCode.LOGIN_MDP_INVALIDE,
						"user not in the db");
			}
		} else {
			throw new WineException(WineErrorCode.LOGIN_MDP_INVALIDE,
					"mail  or password invalid");
		}
	}
}
