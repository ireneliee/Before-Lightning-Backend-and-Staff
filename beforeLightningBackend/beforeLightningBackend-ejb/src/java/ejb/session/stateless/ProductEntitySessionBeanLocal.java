/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ProductEntity;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewProductEntityException;
import util.exception.DeleteProductEntityException;
import util.exception.InputDataValidationException;
import util.exception.PartEntityNotFoundException;
import util.exception.ProductEntityNotFoundException;
import util.exception.ProductNameNotFoundException;
import util.exception.ProductSkuCodeExistException;
import util.exception.ProductSkuNotFoundException;
import util.exception.UnableToAddPartToProductException;
import util.exception.UnableToRemovePartFromProductException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateProductEntityException;

/**
 *
 * @author irene
 */
@Local
public interface ProductEntitySessionBeanLocal {

    public Long createNewProductEntity(ProductEntity newProductEntity) throws CreateNewProductEntityException, InputDataValidationException, UnknownPersistenceException, ProductSkuCodeExistException;

    public List<ProductEntity> retrieveAllProductEntities();

    public ProductEntity retrieveProductEntityBySkuCode(String skuCode) throws ProductSkuNotFoundException;

    public ProductEntity retrieveProductEntityByProductName(String productName) throws ProductNameNotFoundException;

    public ProductEntity retrieveProductEntityByProductEntityId(Long productId) throws ProductEntityNotFoundException;

    public void updateProductEntity(ProductEntity productEntity) throws ProductEntityNotFoundException, UpdateProductEntityException, InputDataValidationException;

    public void deleteProductEntity(Long productEntityId) throws ProductEntityNotFoundException, DeleteProductEntityException;

    public void toggleDisableProductEntity(ProductEntity productEntity) throws ProductEntityNotFoundException, UpdateProductEntityException, InputDataValidationException;

    public void addPartToProduct(Long partEntityId, Long productEntityId) throws PartEntityNotFoundException, ProductEntityNotFoundException, UnableToAddPartToProductException;

    public void removePartFromProduct(Long partEntityId, Long productEntityId) throws PartEntityNotFoundException, ProductEntityNotFoundException, UnableToRemovePartFromProductException;

    public List<ProductEntity> retrieveAllProductEntitiesThatCanSell();

    public Long createBrandNewProductEntity(ProductEntity newProductEntity, Integer quantityOnHand, Integer reorderQuantity, String brand, BigDecimal price, String imageLink) throws CreateNewProductEntityException, InputDataValidationException, UnknownPersistenceException, ProductSkuCodeExistException;

    public Long createBrandNewProductEntity(ProductEntity newProductEntity, Integer quantityOnHand, Integer reorderQuantity, String brand, BigDecimal price) throws CreateNewProductEntityException, InputDataValidationException, UnknownPersistenceException, ProductSkuCodeExistException;


}
