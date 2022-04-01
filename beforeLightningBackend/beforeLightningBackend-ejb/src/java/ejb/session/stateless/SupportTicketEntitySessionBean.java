/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.SupportTicketEntity;
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
import util.exception.CreateNewSupportTicketEntityException;
import util.exception.DeleteSupportTicketEntityException;
import util.exception.InputDataValidationException;
import util.exception.SupportTicketEntityNotFoundException;
import util.exception.UpdateSupportTicketEntityException;

/**
 *
 * @author srinivas
 */
@Stateless
public class SupportTicketEntitySessionBean implements SupportTicketEntitySessionBeanLocal {

    @PersistenceContext(unitName = "beforeLightningBackend-ejbPU")
    private EntityManager em;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public SupportTicketEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public SupportTicketEntity createNewSupportTicketEntity(SupportTicketEntity newSupportTicketEntity) throws InputDataValidationException, CreateNewSupportTicketEntityException {
        Set<ConstraintViolation<SupportTicketEntity>> constraintViolations = validator.validate(newSupportTicketEntity);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newSupportTicketEntity);
                em.flush();

                return newSupportTicketEntity;
            } catch (PersistenceException ex) {
                if (ex.getCause() != null
                        && ex.getCause().getCause() != null
                        && ex.getCause().getCause().getClass().getSimpleName().equals("SQLIntegrityConstraintViolationException")) {
                    throw new CreateNewSupportTicketEntityException("SupportTicket with same name already exist");
                } else {
                    throw new CreateNewSupportTicketEntityException("An unexpected error has occurred: " + ex.getMessage());
                }
            } catch (Exception ex) {
                throw new CreateNewSupportTicketEntityException("An unexpected error has occurred: " + ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    public void updateSupportTicketEntity(SupportTicketEntity updatedSupportTicket) throws UpdateSupportTicketEntityException {
        SupportTicketEntity s = em.find(SupportTicketEntity.class, updatedSupportTicket.getSupportTicketId());
        if (s == null) {
            throw new UpdateSupportTicketEntityException("Support ticket does not exist. ");
        }
        s.setIssue(updatedSupportTicket.getIssue());
        s.setSupportTicketStatus(updatedSupportTicket.getSupportTicketStatus());
        em.merge(s);

    }

    @Override
    public List<SupportTicketEntity> retrieveAllSupportTickets() {
        Query query = em.createQuery("SELECT t FROM SupportTicketEntity t");
        List<SupportTicketEntity> supportTicketEntities = query.getResultList();

        return supportTicketEntities;
    }

    @Override
    public List<SupportTicketEntity> retrieveAllSupportTicketsByEmail(String email) {
        Query query = em.createQuery("SELECT t FROM SupportTicketEntity t WHERE t.email =:inEmail");
        query.setParameter("inEmail", email);
        List<SupportTicketEntity> supportTicketEntities = query.getResultList();

        return supportTicketEntities;
    }

    @Override
    public SupportTicketEntity retrieveSupportTicketBySupportTicketId(Long supportTicketId) throws SupportTicketEntityNotFoundException {
        SupportTicketEntity supportTicketEntity = em.find(SupportTicketEntity.class, supportTicketId);

        if (supportTicketEntity != null) {
            return supportTicketEntity;
        } else {
            throw new SupportTicketEntityNotFoundException("SupportTicket ID " + supportTicketId + " does not exist!");
        }
    }

    @Override
    public void deleteSupportTicket(Long supportTicketId) throws SupportTicketEntityNotFoundException, DeleteSupportTicketEntityException {
        SupportTicketEntity supportTicketEntityToRemove = retrieveSupportTicketBySupportTicketId(supportTicketId);

    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<SupportTicketEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
