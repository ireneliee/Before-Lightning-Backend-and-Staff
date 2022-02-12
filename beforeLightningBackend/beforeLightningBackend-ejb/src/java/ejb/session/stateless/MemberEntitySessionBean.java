/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AddressEntity;
import entity.CreditCardEntity;
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
import util.exception.AddressEntityNotFoundException;
import util.exception.CreditCardEntityNotFoundException;
import util.exception.DeleteAddressEntityException;
import util.exception.DeleteCreditCardEntityException;
import util.exception.DeleteMemberEntityException;
import util.exception.MemberEntityNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.MemberEntityUsernameExistException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateAddressEntityException;
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

    @Override
    //Need to make address also when you first create a member
    public Long createNewMemberEntity(MemberEntity newMemberEntity, AddressEntity newAddressEntity) throws MemberEntityUsernameExistException, InputDataValidationException, UnknownPersistenceException, AddressEntityNotFoundException {

        Set<ConstraintViolation<MemberEntity>> constraintViolations = validator.validate(newMemberEntity);

        Long addressEntityId = createNewAddressEntity(newAddressEntity);
        AddressEntity addressEntity = retrieveAddressEntityByAddressEntityId(addressEntityId);

        if (constraintViolations.isEmpty()) {
            try {
                newMemberEntity.getAddresses().add(addressEntity);
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
        List<MemberEntity> list = query.getResultList();
        for (MemberEntity memberEntity : list) {
            memberEntity.getAddresses().size();
            memberEntity.getCreditCards().size();
            memberEntity.getPurchaseOrders().size();
            memberEntity.getShoppingCart();
        }
        return list;
    }

    @Override
    public MemberEntity retrieveMemberEntityByMemberEntityId(Long memberId) throws MemberEntityNotFoundException {
        MemberEntity memberEntity = em.find(MemberEntity.class, memberId);

        if (memberEntity != null) {
            memberEntity.getAddresses().size();
            memberEntity.getCreditCards().size();
            memberEntity.getPurchaseOrders().size();
            memberEntity.getShoppingCart();
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
            MemberEntity memberEntity = (MemberEntity) query.getSingleResult();
            memberEntity.getAddresses().size();
            memberEntity.getCreditCards().size();
            memberEntity.getPurchaseOrders().size();
            memberEntity.getShoppingCart();
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
//        em.remove(memberEntityToRemove);

        if (memberEntityToRemove.getPurchaseOrders().isEmpty()) {
            for (CreditCardEntity cc : memberEntityToRemove.getCreditCards()) {
                //deleteCreditCardEntity(cc);
            }
            List<AddressEntity> listOfAddresses = memberEntityToRemove.getAddresses();
            em.remove(memberEntityToRemove);
            for (AddressEntity ad : listOfAddresses) {
                em.remove(ad);
            }
            //HOW TO REMOVE SHOPPING CART??????
        } else {
            throw new DeleteMemberEntityException("Member ID " + memberEntityId + " is associated with existing sale transaction(s) and cannot be deleted!");
        }
    }

    @Override
    public Long createNewAddressEntity(AddressEntity newAddressEntity) throws InputDataValidationException, UnknownPersistenceException {

        Set<ConstraintViolation<AddressEntity>> constraintViolations = validator.validate(newAddressEntity);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newAddressEntity);
                em.flush();
                return newAddressEntity.getAddressEntityId();
            } catch (PersistenceException ex) {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareAddressInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public AddressEntity retrieveAddressEntityByAddressEntityId(Long addressId) throws AddressEntityNotFoundException {
        AddressEntity addressEntity = em.find(AddressEntity.class, addressId);

        if (addressEntity != null) {
            return addressEntity;
        } else {
            throw new AddressEntityNotFoundException("Address ID " + addressId + " does not exist!");
        }
    }

    @Override
    public void updateAddressEntity(AddressEntity addressEntity) throws AddressEntityNotFoundException, UpdateAddressEntityException, InputDataValidationException {
        if (addressEntity != null && addressEntity.getAddressEntityId() != null) {
            Set<ConstraintViolation<AddressEntity>> constraintViolations = validator.validate(addressEntity);

            if (constraintViolations.isEmpty()) {
                AddressEntity addressEntityToUpdate = retrieveAddressEntityByAddressEntityId(addressEntity.getAddressEntityId());
                addressEntityToUpdate.setBlock(addressEntity.getBlock());
                addressEntityToUpdate.setUnit(addressEntity.getUnit());
                addressEntityToUpdate.setCountry(addressEntity.getCountry());
                addressEntityToUpdate.setPostalCode(addressEntity.getPostalCode());
            } else {
                throw new InputDataValidationException(prepareAddressInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new AddressEntityNotFoundException("AddressEntity ID not provided for addressEntity to be updated");
        }
    }

    @Override
    public void deleteAddressEntity(Long memberEntityId, Long addressEntityId) throws AddressEntityNotFoundException, DeleteAddressEntityException, MemberEntityNotFoundException {

        MemberEntity memberEntity = retrieveMemberEntityByMemberEntityId(memberEntityId);

        AddressEntity addressEntityToRemove = retrieveAddressEntityByAddressEntityId(addressEntityId);

        if (memberEntity.getAddresses().contains(addressEntityToRemove) && memberEntity.getAddresses().size() >= 2) {
            memberEntity.getAddresses().remove(addressEntityToRemove);
            em.remove(addressEntityToRemove);
        } else {
            throw new DeleteAddressEntityException("Address ID " + addressEntityId + " does not exist in Member " + memberEntityId + " list of addresses");
        }

    }

    @Override
    public Long createNewCreditCardEntity(Long memberEntityId, CreditCardEntity newCreditCardEntity) throws InputDataValidationException, UnknownPersistenceException, MemberEntityNotFoundException {

        MemberEntity memberEntity = retrieveMemberEntityByMemberEntityId(memberEntityId);

        Set<ConstraintViolation<CreditCardEntity>> constraintViolations = validator.validate(newCreditCardEntity);

        if (constraintViolations.isEmpty()) {
            try {
                newCreditCardEntity.setMemberEntity(memberEntity);
                memberEntity.getCreditCards().add(newCreditCardEntity);
                em.persist(newCreditCardEntity);
                em.flush();
                return newCreditCardEntity.getCreditCardEntityId();
            } catch (PersistenceException ex) {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareCreditCardInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public CreditCardEntity retrieveCreditCardEntityByCreditCardEntityId(Long creditCardEntityId) throws CreditCardEntityNotFoundException {
        CreditCardEntity creditCardEntity = em.find(CreditCardEntity.class, creditCardEntityId);

        if (creditCardEntity != null) {
            return creditCardEntity;
        } else {
            throw new CreditCardEntityNotFoundException("CreditCard ID " + creditCardEntityId + " does not exist!");
        }
    }

    @Override
    public void updateCreditCardEntity(CreditCardEntity creditCardEntity) throws CreditCardEntityNotFoundException, UpdateAddressEntityException, InputDataValidationException {
        if (creditCardEntity != null && creditCardEntity.getCreditCardEntityId() != null) {
            Set<ConstraintViolation<CreditCardEntity>> constraintViolations = validator.validate(creditCardEntity);

            if (constraintViolations.isEmpty()) {
                CreditCardEntity creditCreditEntityToUpdate = retrieveCreditCardEntityByCreditCardEntityId(creditCardEntity.getCreditCardEntityId());
                creditCreditEntityToUpdate.setCreditCardNumber(creditCardEntity.getCreditCardNumber());
                creditCreditEntityToUpdate.setExpiryDate(creditCardEntity.getExpiryDate());
                creditCreditEntityToUpdate.setNameOnCard(creditCardEntity.getNameOnCard());

            } else {
                throw new InputDataValidationException(prepareCreditCardInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new CreditCardEntityNotFoundException("CreditCardEntity ID not provided for CreditCardEntity to be updated");
        }
    }

    @Override
    public void deleteCreditCardEntity(Long memberEntityId, Long creditCardEntityId) throws CreditCardEntityNotFoundException, DeleteCreditCardEntityException, MemberEntityNotFoundException {

        MemberEntity memberEntity = retrieveMemberEntityByMemberEntityId(memberEntityId);

        CreditCardEntity creditCardEntityToRemove = retrieveCreditCardEntityByCreditCardEntityId(creditCardEntityId);

        if (memberEntity.getCreditCards().contains(creditCardEntityToRemove)) {
            memberEntity.getCreditCards().remove(creditCardEntityToRemove);
            em.remove(creditCardEntityToRemove);
        } else {
            throw new DeleteCreditCardEntityException("CreditCard ID " + creditCardEntityId + " does not exist in Member " + memberEntityId + " list of addresses");
        }

    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<MemberEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

    private String prepareAddressInputDataValidationErrorsMessage(Set<ConstraintViolation<AddressEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

    private String prepareCreditCardInputDataValidationErrorsMessage(Set<ConstraintViolation<CreditCardEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
