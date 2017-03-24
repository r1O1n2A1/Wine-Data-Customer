/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.afcepf.atod.customer.data.impl;

import fr.afcepf.atod.customer.data.api.IDaoCustomer;
import fr.afcepf.atod.vin.data.exception.WineErrorCode;
import fr.afcepf.atod.vin.data.exception.WineException;
import fr.afcepf.atod.wine.data.impl.DaoGeneric;
import fr.afcepf.atod.wine.entity.Customer;
import fr.afcepf.atod.wine.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ronan
 */
@Service
@Transactional
public class DaoCustomer extends DaoGeneric<User, Integer> implements IDaoCustomer {

    /**
     * ***************************************************. Requetes HQL
     ***************************************************
     */

    private static final String REQCONNEXION = "SELECT u FROM User u "
            + "WHERE  u.mail     = :paramMail"
            + " AND   u.password = :paramPassword";

    /**
     * **************************************************. Fin Requetes HQL
     ***************************************************
     */
    /**
     *
     * @param mail
     * @param password
     * @return
     * @throws WineException
     */
    @Override
    public User connect(String mail, String password) throws WineException {
        User user = null;
        if (!mail.equalsIgnoreCase("")
                && !password.equalsIgnoreCase("")) {
            user = (User) (getSf().getCurrentSession()
                    .createQuery(REQCONNEXION)
                    .setParameter("paramMail", mail)
                    .setParameter("paramPassword", password)
                    .uniqueResult());

            if (!user.getLastname().equalsIgnoreCase("")) {
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
