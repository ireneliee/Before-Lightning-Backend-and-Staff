/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ProductTypeEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.DeleteProductTypeException;
import util.exception.InputDataValidationException;
import util.exception.ProductTypeEntityNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author irene
 */
@Local
public interface ProductTypeSessionBeanLocal {

    public ProductTypeEntity createNewProductTypeEntity(ProductTypeEntity newProductTypeEntity) throws InputDataValidationException, UnknownPersistenceException;

    public void deleteProductType(Long productTypeId) throws DeleteProductTypeException, ProductTypeEntityNotFoundException;

    public void updateProductType(ProductTypeEntity productTypeEntity) throws ProductTypeEntityNotFoundException;

    public ProductTypeEntity retrieveProductTypeByProductTypeId(Long productTypeEntityId) throws ProductTypeEntityNotFoundException;

    public List<ProductTypeEntity> retrieveAllProductTypeEntities();
    
}
