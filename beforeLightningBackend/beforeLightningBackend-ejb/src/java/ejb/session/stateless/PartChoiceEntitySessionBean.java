/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PartChoiceEntity;
import entity.PartEntity;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CreateNewPartChoiceEntityException;
import util.exception.InputDataValidationException;
import util.exception.PartEntityNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author irene
 */
@Stateless
public class PartChoiceEntitySessionBean implements PartChoiceEntitySessionBeanLocal {

    @EJB
    private PartEntitySessionBeanLocal partEntitySessionBeanLocal;
    @PersistenceContext(unitName = "beforeLightningBackend-ejbPU")
    private EntityManager entityManager;
    @Resource
    private EJBContext eJBContext;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public PartChoiceEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    // with existing related part choice entity
    @Override
    public PartChoiceEntity createPartChoiceEntityException(List<Long> relatedPartChoiceEntityIds, Long partEntityId, PartChoiceEntity newPartChoice) throws UnknownPersistenceException, InputDataValidationException, CreateNewPartChoiceEntityException, PartEntityNotFoundException {

        Set<ConstraintViolation<PartChoiceEntity>> constraintViolations = validator.validate(newPartChoice);

        if (constraintViolations.isEmpty()) {
            PartEntity partEntity = partEntitySessionBeanLocal.retrievePartEntityByPartEntityId(partEntityId);

            entityManager.persist(newPartChoice);
            entityManager.flush();

            partEntity.getPartChoices().add(newPartChoice);

            for (Long id : relatedPartChoiceEntityIds) {
                PartChoiceEntity relatedPartChoice = entityManager.find(PartChoiceEntity.class, id);
                if (relatedPartChoice == null) {
                    eJBContext.setRollbackOnly();
                    throw new CreateNewPartChoiceEntityException("An error has occured while creating the part choice entity: id of related part choice does not exist. ");
                } else {
                    relatedPartChoice.getLeftSuitablePartChoices().add(newPartChoice);
                    newPartChoice.getRightSuitablePartChoices().add(relatedPartChoice);
                }
                
            }
            
            return newPartChoice;

        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }

    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<PartChoiceEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

}
