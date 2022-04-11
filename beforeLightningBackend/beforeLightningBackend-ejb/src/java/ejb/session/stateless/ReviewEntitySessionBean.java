/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AccessoryItemEntity;
import entity.ProductEntity;
import entity.ReviewEntity;
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
import util.exception.InputDataValidationException;
import util.exception.ReviewEntityNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Koh Wen Jie
 */
@Stateless
public class ReviewEntitySessionBean implements ReviewEntitySessionBeanLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    @PersistenceContext(unitName = "beforeLightningBackend-ejbPU")
    private EntityManager entityManager;

    public ReviewEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewReviewEntity(ReviewEntity newReviewEntity) throws InputDataValidationException, UnknownPersistenceException {

        Set<ConstraintViolation<ReviewEntity>> constraintViolations = validator.validate(newReviewEntity);

        if (constraintViolations.isEmpty()) {
            try {
                entityManager.persist(newReviewEntity);
                entityManager.flush();
                return newReviewEntity.getReviewEntityId();
            } catch (PersistenceException ex) {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public Long createNewReviewEntityForAcc(ReviewEntity newReviewEntity, Long accId) throws UnknownPersistenceException, InputDataValidationException {
        AccessoryItemEntity acc = entityManager.find(AccessoryItemEntity.class, accId);
        
        Set<ConstraintViolation<ReviewEntity>> constraintViolations = validator.validate(newReviewEntity);

        if (constraintViolations.isEmpty()) {
            try {
                entityManager.persist(newReviewEntity);
                entityManager.flush();
                acc.getReviewEntities().add(newReviewEntity);
        
                return newReviewEntity.getReviewEntityId();
            } catch (PersistenceException ex) {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
        
        
    }
    
    @Override
    public Long createNewReviewEntityForProduct(ReviewEntity newReviewEntity, Long prodId) throws UnknownPersistenceException, InputDataValidationException {
        ProductEntity prod = entityManager.find(ProductEntity.class, prodId);
        
        Set<ConstraintViolation<ReviewEntity>> constraintViolations = validator.validate(newReviewEntity);

        if (constraintViolations.isEmpty()) {
            try {
                entityManager.persist(newReviewEntity);
                entityManager.flush();
                prod.getReviewEntities().add(newReviewEntity);
        
                return newReviewEntity.getReviewEntityId();
            } catch (PersistenceException ex) {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
        
        
    }

    @Override
    public List<ReviewEntity> retrieveAllReviewEntities() {
        Query query = entityManager.createQuery("SELECT r FROM ReviewEntity r");
        List<ReviewEntity> list = query.getResultList();
        return list;
    }

    
    @Override
    public ReviewEntity retrieveReviewEntityByReviewEntityId(Long partEntityId) throws ReviewEntityNotFoundException {
        ReviewEntity pe = entityManager.find(ReviewEntity.class, partEntityId);
        if (pe == null) {
            throw new ReviewEntityNotFoundException();
        } else {
            return pe;
        }
    }

    @Override
    public List<ReviewEntity> retrieveReviewEntitiesByUsername(String username) throws ReviewEntityNotFoundException {
        Query query = entityManager.createQuery("SELECT r FROM ReviewEntity r WHERE r.customerUsername = :inName");
        query.setParameter("inName", username);
        List<ReviewEntity> list = query.getResultList();
        return list;
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<ReviewEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
