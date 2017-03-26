package fr.afcepf.atod.customer.data.api;

import fr.afcepf.atod.vin.data.exception.WineException;
import fr.afcepf.atod.wine.data.api.IDaoGeneric;
import fr.afcepf.atod.wine.entity.Customer;
import fr.afcepf.atod.wine.entity.User;
import java.io.Serializable;

/**
 * Database access for an object of type {@link Customer}
 * @author ronan 
 */
public interface IDaoCustomer extends IDaoGeneric<User, Integer>{
    /**
     * Method allowing a user to connect
     * to our app
     * @param mail {@link String}
     * @param password {@link String}
     * @return {@link User}
     * @throws WineException {@link WineException}
     */
    User connect(String mail, String password) throws WineException;
    
}
