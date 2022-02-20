/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ProductEntity;
import javax.ejb.Local;
import util.exception.CreateNewProductEntityException;
import util.exception.InputDataValidationException;
import util.exception.ProductSkuCodeExistException;
import util.exception.ProductSkuNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author irene
 */
@Local
public interface ProductEntitySessionBeanLocal {

    public ProductEntity retrieveProductEntityBySkuCode(String skuCode) throws ProductSkuNotFoundException;

    public ProductEntity createNewProduct(ProductEntity newProductEntity) throws CreateNewProductEntityException, InputDataValidationException, UnknownPersistenceException, ProductSkuCodeExistException;
    
}
