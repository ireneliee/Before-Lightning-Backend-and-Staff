/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ProductTypeEntity;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author irene
 */
@Local
public interface ProductTypeSessionBeanLocal {

    public ProductTypeEntity createNewProductTypeEntity(ProductTypeEntity newProductTypeEntity) throws InputDataValidationException, UnknownPersistenceException;
    
}
