/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.MemberEntity;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.DeleteMemberEntityException;
import util.exception.MemberEntityNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.MemberEntityUsernameExistException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateMemberEntityException;
import util.security.CryptographicHelper;

/**
 *
 * @author Koh Wen Jie
 */
@Stateless
public class MemberEntitySessionBean implements MemberEntitySessionBeanLocal {

    @PersistenceContext(unitName = "beforeLightningBackend-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public MemberEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public Long createNewMemberEntity(MemberEntity newMemberEntity) throws MemberEntityUsernameExistException, InputDataValidationException, UnknownPersistenceException {

        Set<ConstraintViolation<MemberEntity>> constraintViolations = validator.validate(newMemberEntity);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newMemberEntity);
                em.flush();
                return newMemberEntity.getUserEntityId();
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new MemberEntityUsernameExistException();
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

    @Override
    public List<MemberEntity> retrieveAllMemberEntities() {
        Query query = em.createQuery("SELECT e FROM MemberEntity e");
        return query.getResultList();
    }

    @Override
    public MemberEntity retrieveMemberEntityByMemberEntityId(Long memberId) throws MemberEntityNotFoundException {
        MemberEntity memberEntity = em.find(MemberEntity.class, memberId);

        if (memberEntity != null) {
            return memberEntity;
        } else {
            throw new MemberEntityNotFoundException("Member ID " + memberId + " does not exist!");
        }
    }

    @Override
    public MemberEntity retrieveMemberEntityByUsername(String username) throws MemberEntityNotFoundException {
        Query query = em.createQuery("SELECT s FROM MemberEntity s WHERE s.username = :inUsername");
        query.setParameter("inUsername", username);

        try {
            return (MemberEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new MemberEntityNotFoundException("Member Username " + username + " does not exist!");
        }
    }

    @Override
    public MemberEntity memberEntityLogin(String username, String password) throws InvalidLoginCredentialException {
        try {
            MemberEntity memberEntity = retrieveMemberEntityByUsername(username);
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + memberEntity.getSalt()));

            if (memberEntity.getPassword().equals(passwordHash)) {
                return memberEntity;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        } catch (MemberEntityNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }

    @Override
    public void updateMemberEntity(MemberEntity memberEntity) throws MemberEntityNotFoundException, UpdateMemberEntityException, InputDataValidationException {
        if (memberEntity != null && memberEntity.getUserEntityId() != null) {
            Set<ConstraintViolation<MemberEntity>> constraintViolations = validator.validate(memberEntity);

            if (constraintViolations.isEmpty()) {
                MemberEntity memberEntityEntityToUpdate = retrieveMemberEntityByMemberEntityId(memberEntity.getUserEntityId());

                if (memberEntityEntityToUpdate.getUsername().equals(memberEntity.getUsername())) {
                    memberEntityEntityToUpdate.setFirstname(memberEntity.getFirstname());
                    memberEntityEntityToUpdate.setLastname(memberEntity.getLastname());
                    memberEntityEntityToUpdate.setEmail(memberEntity.getEmail());
                    memberEntityEntityToUpdate.setContact(memberEntity.getContact());
                    // Username and password are deliberately NOT updated to demonstrate that client is not allowed to update account credential through this business method
                } else {
                    throw new UpdateMemberEntityException("Username of memberEntity record to be updated does not match the existing record");
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new MemberEntityNotFoundException("MemberEntity ID not provided for memberEntity to be updated");
        }
    }

    @Override
    public void deleteMemberEntity(Long memberEntityId) throws MemberEntityNotFoundException, DeleteMemberEntityException {
        MemberEntity memberEntityToRemove = retrieveMemberEntityByMemberEntityId(memberEntityId);
        //need to check for any conditions before removing member?
        em.remove(memberEntityToRemove);

//        if (memberEntityToRemove.getSaleTransactionEntities().isEmpty()) {
//            em.remove(memberEntityToRemove);
//        } else {
//            // New in v4.1 to prevent deleting staff with existing sale transaction(s)
//            throw new DeleteMemberEntityException("Staff ID " + memberId + " is associated with existing sale transaction(s) and cannot be deleted!");
//        }

    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<MemberEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
