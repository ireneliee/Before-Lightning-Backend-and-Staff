/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PartChoiceEntity;
import entity.PartEntity;
import entity.ProductEntity;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CreateNewProductEntityException;
import util.exception.InputDataValidationException;
import util.exception.ProductSkuCodeExistException;
import util.exception.ProductSkuNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author irene
 */
@Stateless
public class ProductEntitySessionBean implements ProductEntitySessionBeanLocal {

    @PersistenceContext(unitName = "beforeLightningBackend-ejbPU")
    private EntityManager entityManager;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public ProductEntitySessionBean() {

        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

    }

    // assuming parts and part choices will only be created tgt
    public ProductEntity createNewProduct(ProductEntity newProductEntity) throws CreateNewProductEntityException, InputDataValidationException,
            UnknownPersistenceException, ProductSkuCodeExistException {

        Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validate(newProductEntity);

        if (constraintViolations.isEmpty()) {

            try {

                List<PartEntity> listOfPartEntities = newProductEntity.getParts();

                for (PartEntity p : listOfPartEntities) {

                    List<PartChoiceEntity> listOfPartChoices = p.getPartChoices();

                    for (PartChoiceEntity pc : listOfPartChoices) {

                        entityManager.persist(pc);

                    }

                    entityManager.persist(p);

                }

                entityManager.persist(newProductEntity);

                entityManager.flush();

                return newProductEntity;

            } catch (PersistenceException ex) {

                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {

                        throw new ProductSkuCodeExistException();

                    } else {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                } else {

                    throw new UnknownPersistenceException(ex.getMessage());

                }
            }

        } else {

            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));

        }

    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<ProductEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
    
    @Override
    public ProductEntity retrieveProductEntityBySkuCode(String skuCode) throws ProductSkuNotFoundException{
        String queryInString = "SELECT p FROM ProductEntity p WHERE p.skuCode = iSkuCode";
        Query retrievalQuery = entityManager.createQuery(queryInString);
        retrievalQuery.setParameter("iSkuCode", skuCode);
        ProductEntity result =  (ProductEntity) retrievalQuery.getSingleResult();
        if(result == null) {
            throw new ProductSkuNotFoundException("Product with SKU Code of " + skuCode + " does not exist.");
        }
        
        return result;
        
    }

}
