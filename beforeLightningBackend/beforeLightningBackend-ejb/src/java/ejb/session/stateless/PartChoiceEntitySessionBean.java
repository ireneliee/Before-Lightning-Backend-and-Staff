package ejb.session.stateless;

import entity.PartChoiceEntity;
import entity.PartEntity;
import entity.PurchaseOrderLineItemEntity;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import util.exception.DeletePartChoiceEntityException;
import util.exception.InputDataValidationException;
import util.exception.PartChoiceEntityExistException;
import util.exception.PartChoiceEntityNotFoundException;
import util.exception.UnableToAddPartChoiceToPartChoiceException;
import util.exception.UnableToRemovePartChoiceFromPartChoiceException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdatePartChoiceEntityException;

/**
 *
 * @author irene
 */
@Stateless
public class PartChoiceEntitySessionBean implements PartChoiceEntitySessionBeanLocal {

    @PersistenceContext(unitName = "beforeLightningBackend-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public PartChoiceEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewPartChoiceEntity(PartChoiceEntity newPartChoiceEntity) throws PartChoiceEntityExistException, InputDataValidationException, UnknownPersistenceException {

        Set<ConstraintViolation<PartChoiceEntity>> constraintViolations = validator.validate(newPartChoiceEntity);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newPartChoiceEntity);
                em.flush();
                return newPartChoiceEntity.getPartChoiceEntityId();
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        System.out.println("====================");
                        System.out.println(ex.getCause());
                        throw new PartChoiceEntityExistException();
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
    public List<PartChoiceEntity> retrieveAllPartChoiceEntities() {
        Query query = em.createQuery("SELECT pc FROM PartChoiceEntity pc");

        List<PartChoiceEntity> list = query.getResultList();
        for (PartChoiceEntity pc : list) {
            pc.getCompatibleChassisPartChoiceEntities();
            pc.getCompatiblePartsPartChoiceEntities();
            pc.getPromotionEntities();
        }
        return list;
    }

    @Override
    public PartChoiceEntity retrievePartChoiceEntityByPartChoiceEntityId(Long partChoiceId) throws PartChoiceEntityNotFoundException {
        PartChoiceEntity partChoiceEntity = em.find(PartChoiceEntity.class, partChoiceId);

        if (partChoiceEntity != null) {
            partChoiceEntity.getCompatibleChassisPartChoiceEntities();
            partChoiceEntity.getCompatiblePartsPartChoiceEntities();
            partChoiceEntity.getPromotionEntities();
            return partChoiceEntity;
        } else {
            throw new PartChoiceEntityNotFoundException("PartChoice ID " + partChoiceId + " does not exist!");
        }
    }

    @Override
    public PartChoiceEntity retrievePartChoiceEntityByPartChoiceName(String partChoiceName) throws PartChoiceEntityNotFoundException {
        Query query = em.createQuery("SELECT pc FROM PartChoiceEntity pc WHERE pc.partChoiceName = :inPartChoicename");
        query.setParameter("inPartChoicename", partChoiceName);

        try {
            return (PartChoiceEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new PartChoiceEntityNotFoundException("PartChoice Username " + partChoiceName + " does not exist!");
        }
    }

    @Override
    public void deletePartChoiceEntity(Long partChoiceEntityId) throws PartChoiceEntityNotFoundException, DeletePartChoiceEntityException {
        PartChoiceEntity partChoiceEntityToRemove = retrievePartChoiceEntityByPartChoiceEntityId(partChoiceEntityId);
        Query query = em.createQuery("SELECT polie FROM PurchaseOrderLineItemEntity polie");
        List<PurchaseOrderLineItemEntity> list = query.getResultList();
        boolean check = false;
        for (PurchaseOrderLineItemEntity polie : list) {
            if (polie.getPartChoiceEntities().contains(partChoiceEntityToRemove)) {
                check = true;
                break;
            }
        }
        if (partChoiceEntityToRemove.getCompatibleChassisPartChoiceEntities().size() > 0
                || partChoiceEntityToRemove.getCompatiblePartsPartChoiceEntities().size() > 0
                || partChoiceEntityToRemove.getPromotionEntities().size() > 0
                || list.size() > 0) {
            throw new DeletePartChoiceEntityException("Unable to delete Part Choice!");
        } else {
            em.remove(partChoiceEntityToRemove);
        }
    }

    @Override
    public void toggleDisablePartChoiceEntity(PartChoiceEntity partChoiceEntity) throws PartChoiceEntityNotFoundException, UpdatePartChoiceEntityException, InputDataValidationException {
        if (partChoiceEntity != null && partChoiceEntity.getPartChoiceEntityId() != null) {
            Set<ConstraintViolation<PartChoiceEntity>> constraintViolations = validator.validate(partChoiceEntity);

            if (constraintViolations.isEmpty()) {
                PartChoiceEntity partChoiceEntityToUpdate = retrievePartChoiceEntityByPartChoiceEntityId(partChoiceEntity.getPartChoiceEntityId());

                if (partChoiceEntityToUpdate.getPartChoiceEntityId().equals(partChoiceEntity.getPartChoiceEntityId())) {
                    partChoiceEntityToUpdate.setIsDisabled(partChoiceEntity.getIsDisabled());

                } else {
                    throw new UpdatePartChoiceEntityException("Part Choice ID record to be updated does not match the existing record");
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new PartChoiceEntityNotFoundException("Part Choice ID not provided for Part Choice to be updated");
        }
    }

    @Override
    public void updatePartChoiceEntity(PartChoiceEntity partChoiceEntity) throws PartChoiceEntityNotFoundException, UpdatePartChoiceEntityException, InputDataValidationException {
        if (partChoiceEntity != null && partChoiceEntity.getPartChoiceEntityId() != null) {
            Set<ConstraintViolation<PartChoiceEntity>> constraintViolations = validator.validate(partChoiceEntity);

            if (constraintViolations.isEmpty()) {
                PartChoiceEntity partChoiceEntityToUpdate = retrievePartChoiceEntityByPartChoiceEntityId(partChoiceEntity.getPartChoiceEntityId());

                if (partChoiceEntityToUpdate.getPartChoiceEntityId().equals(partChoiceEntity.getPartChoiceEntityId())) {
                    partChoiceEntityToUpdate.setPartChoiceName(partChoiceEntity.getPartChoiceName());
                    partChoiceEntityToUpdate.setQuantityOnHand(partChoiceEntity.getQuantityOnHand());
                    partChoiceEntityToUpdate.setReorderQuantity(partChoiceEntity.getReorderQuantity());
                    partChoiceEntityToUpdate.setBrand(partChoiceEntity.getBrand());
                    partChoiceEntityToUpdate.setPrice(partChoiceEntity.getPrice());
                    partChoiceEntityToUpdate.setPartDescription(partChoiceEntity.getPartDescription());
                    partChoiceEntityToUpdate.setPartOverview(partChoiceEntity.getPartOverview());
                    partChoiceEntityToUpdate.setImageLink(partChoiceEntity.getImageLink());

                } else {
                    throw new UpdatePartChoiceEntityException("Username of partChoiceEntity record to be updated does not match the existing record");
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new PartChoiceEntityNotFoundException("PartChoiceEntity ID not provided for partChoiceEntity to be updated");
        }
    }

    @Override
    public void addPartChoiceToChassisChoice(Long partChoiceToAddId, Long chassisToAddId) throws PartChoiceEntityNotFoundException, UnableToAddPartChoiceToPartChoiceException {
        PartChoiceEntity partChoiceEntityToAdd = retrievePartChoiceEntityByPartChoiceEntityId(partChoiceToAddId);
        PartChoiceEntity chassisEntityToAdd = retrievePartChoiceEntityByPartChoiceEntityId(chassisToAddId);

        Query query = em.createQuery("SELECT p FROM PartEntity p WHERE p.partName = :iChassis");
        String searchWord = "Chassis";
        query.setParameter("iChassis", searchWord);
        try {
            PartEntity chassisPart = (PartEntity) query.getSingleResult();
            if (chassisPart.getPartChoiceEntities().contains(chassisEntityToAdd)) {
                if (!chassisEntityToAdd.getCompatiblePartsPartChoiceEntities().contains(partChoiceEntityToAdd)
                        && !partChoiceEntityToAdd.getCompatibleChassisPartChoiceEntities().contains(chassisEntityToAdd)) {
                    partChoiceEntityToAdd.getCompatibleChassisPartChoiceEntities().add(chassisEntityToAdd);
                    chassisEntityToAdd.getCompatiblePartsPartChoiceEntities().add(partChoiceEntityToAdd);

                } else {
                    throw new UnableToAddPartChoiceToPartChoiceException("Part Choice has already been added before!");
                }
            } else {
                throw new UnableToAddPartChoiceToPartChoiceException("Part Choice is not a Chassis!");
            }
        } catch (NoResultException ex) {
            throw new UnableToAddPartChoiceToPartChoiceException("No Part called Chassis exist!");
        }
    }

    @Override
    public void addPartChoiceToListOfChassisChoice(Long partChoiceToAddId, List<PartChoiceEntity> listOfChassisPartChoiceEntities) throws UnableToAddPartChoiceToPartChoiceException{
        for (PartChoiceEntity chassis : listOfChassisPartChoiceEntities) {
            try {
                addPartChoiceToChassisChoice(partChoiceToAddId, chassis.getPartChoiceEntityId());
            } catch (PartChoiceEntityNotFoundException | UnableToAddPartChoiceToPartChoiceException ex) {
                throw new UnableToAddPartChoiceToPartChoiceException(ex.getMessage());
            }
        }
    }

    @Override
    public void removePartChoiceFromChassisChoice(Long partChoiceToRemoveId, Long chassisToRemoveId) throws PartChoiceEntityNotFoundException, UnableToRemovePartChoiceFromPartChoiceException {

        PartChoiceEntity partChoiceEntityToRemove = retrievePartChoiceEntityByPartChoiceEntityId(partChoiceToRemoveId);
        PartChoiceEntity chassisEntityToRemove = retrievePartChoiceEntityByPartChoiceEntityId(chassisToRemoveId);

        Query query = em.createQuery("SELECT p FROM PartEntity p WHERE p.partName = :iChassis");
        String searchWord = "Chassis";
        query.setParameter("iChassis", searchWord);
        try {
            PartEntity chassisPart = (PartEntity) query.getSingleResult();
            if (chassisPart.getPartChoiceEntities().contains(chassisEntityToRemove)) {
                if (chassisEntityToRemove.getCompatiblePartsPartChoiceEntities().contains(partChoiceEntityToRemove)
                        && partChoiceEntityToRemove.getCompatibleChassisPartChoiceEntities().contains(chassisEntityToRemove)) {
                    partChoiceEntityToRemove.getCompatibleChassisPartChoiceEntities().remove(chassisEntityToRemove);
                    chassisEntityToRemove.getCompatiblePartsPartChoiceEntities().remove(partChoiceEntityToRemove);
                } else {
                    throw new UnableToRemovePartChoiceFromPartChoiceException("Part Choice has already been added before!");
                }
            } else {
                throw new UnableToRemovePartChoiceFromPartChoiceException("Part Choice is not a Chassis!");
            }
        } catch (NoResultException ex) {
            throw new UnableToRemovePartChoiceFromPartChoiceException("No Part called Chassis exist!");
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<PartChoiceEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

//    // update the part choice entity alone, without promotions
//    @Override
//    public void updatePartChoiceEntity(List<Long> newRelatedPartChoiceEntityIds, PartChoiceEntity updatedPartChoiceEntity) throws PartChoiceEntityNotFoundException,
//            UpdatePartChoiceEntityException {
//        if (updatedPartChoiceEntity != null && updatedPartChoiceEntity.getPartChoiceEntityId() != null) {
//
//            Set<ConstraintViolation<PartChoiceEntity>> constraintViolations = validator.validate(updatedPartChoiceEntity);
//
//            if (constraintViolations.isEmpty()) {
//                PartChoiceEntity partChoiceToBeUpdated = entityManager.find(PartChoiceEntity.class, updatedPartChoiceEntity.getPartChoiceEntityId());
//
//                if (partChoiceToBeUpdated == null) {
//                    throw new PartChoiceEntityNotFoundException("Part choice ID given does not exist within the system. ");
//                }
//
//                partChoiceToBeUpdated.getLeftSuitablePartChoices().clear();
//                partChoiceToBeUpdated.getRightSuitablePartChoices().clear();
//
//                for (Long partChoiceId : newRelatedPartChoiceEntityIds) {
//                    PartChoiceEntity p = entityManager.find(PartChoiceEntity.class, partChoiceId);
//                    if (p == null) {
//                        eJBContext.setRollbackOnly();
//                        throw new UpdatePartChoiceEntityException("An error has occured while updating the part choice: id of related part choice does not exist .");
//                    }
//
//                    partChoiceToBeUpdated.getLeftSuitablePartChoices().add(p);
//                    p.getRightSuitablePartChoices().add(partChoiceToBeUpdated);
//
//                }
//
//                partChoiceToBeUpdated.setBrand(updatedPartChoiceEntity.getBrand());
//                partChoiceToBeUpdated.setSpecification(updatedPartChoiceEntity.getSpecification());
//                partChoiceToBeUpdated.setQuantityOnHand(updatedPartChoiceEntity.getQuantityOnHand());
//                partChoiceToBeUpdated.setReorderQuantity(updatedPartChoiceEntity.getReorderQuantity());
//                partChoiceToBeUpdated.setPrice(updatedPartChoiceEntity.getPrice());
//                partChoiceToBeUpdated.setPartOverview(updatedPartChoiceEntity.getPartOverview());
//                partChoiceToBeUpdated.setPartDescription(updatedPartChoiceEntity.getPartDescription());
//            }
//
//        } else {
//            throw new PartChoiceEntityNotFoundException("Part choice ID not provided for the part choice to be updated. ");
//        }
//    }
//
//    // when one delete a part choice, it will be deleted from the list of related part choices, but the promotions linked to the part choices remain
//    @Override
//    public void deletePartChoiceEntity(Long toBeDeletedId) throws PartChoiceEntityNotFoundException {
//        PartChoiceEntity partChoiceToBeDeleted = retrievePartChoiceEntityById(toBeDeletedId);
//        List<PartChoiceEntity> listOfLeftRelatedPartChoices = partChoiceToBeDeleted.getLeftSuitablePartChoices();
//        for (PartChoiceEntity p : listOfLeftRelatedPartChoices) {
//            p.getRightSuitablePartChoices().remove(partChoiceToBeDeleted);
//        }
//
//        List<PartChoiceEntity> listOfRightRelatedPartChoices = partChoiceToBeDeleted.getRightSuitablePartChoices();
//        for (PartChoiceEntity p : listOfRightRelatedPartChoices) {
//            p.getLeftSuitablePartChoices().remove(partChoiceToBeDeleted);
//        }
//
//    }
//
//    @Override
//    public List<PartChoiceEntity> retrieveAllPartChoices() {
//        String queryInString = "SELECT p FROM PartChoiceEntity p";
//        Query query = entityManager.createQuery(queryInString);
//        List<PartChoiceEntity> queryResult = query.getResultList();
//
//        for (PartChoiceEntity p : queryResult) {
//            p.getLeftSuitablePartChoices().size();
//            p.getRightSuitablePartChoices().size();
//            p.getPromotions().size();
//        }
//
//        return queryResult;
//    }
//
//    @Override
//    public PartChoiceEntity retrievePartChoiceEntityById(Long partChoiceEntityId) throws PartChoiceEntityNotFoundException {
//
//        PartChoiceEntity partChoiceToRetrieve = entityManager.find(PartChoiceEntity.class, partChoiceEntityId);
//
//        if (partChoiceToRetrieve == null) {
//            throw new PartChoiceEntityNotFoundException("An error has occured while trying to retrieve part choice entity: part choice ID cannot be found. ");
//        }
//
//        return partChoiceToRetrieve;
//
//    }
//
//    // with existing related part choice entity
//    @Override
//    public PartChoiceEntity createPartChoiceEntity(List<Long> relatedPartChoiceEntityIds, Long partEntityId, PartChoiceEntity newPartChoice) throws UnknownPersistenceException, InputDataValidationException, CreateNewPartChoiceEntityException, PartEntityNotFoundException {
//
//        Set<ConstraintViolation<PartChoiceEntity>> constraintViolations = validator.validate(newPartChoice);
//
//        if (constraintViolations.isEmpty()) {
//            PartEntity partEntity = partEntitySessionBeanLocal.retrievePartEntityByPartEntityId(partEntityId);
//
//            entityManager.persist(newPartChoice);
//            entityManager.flush();
//
//            partEntity.getPartChoices().add(newPartChoice);
//
//            for (Long id : relatedPartChoiceEntityIds) {
//                PartChoiceEntity relatedPartChoice = entityManager.find(PartChoiceEntity.class, id);
//                if (relatedPartChoice == null) {
//                    eJBContext.setRollbackOnly();
//                    throw new CreateNewPartChoiceEntityException("An error has occured while creating the part choice entity: id of related part choice does not exist. ");
//                } else {
//                    relatedPartChoice.getLeftSuitablePartChoices().add(newPartChoice);
//                    newPartChoice.getRightSuitablePartChoices().add(relatedPartChoice);
//                }
//
//            }
//
//            return newPartChoice;
//
//        } else {
//            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
//        }
//
//    }
//
}
