/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PartChoiceEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.DeletePartChoiceEntityException;
import util.exception.InputDataValidationException;
import util.exception.PartChoiceEntityExistException;
import util.exception.PartChoiceEntityNotFoundException;
import util.exception.UnableToAddPartChoiceToPartChoiceException;
import util.exception.UnableToLinkPartChoicesException;
import util.exception.UnableToRemovePartChoiceFromPartChoiceException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdatePartChoiceEntityException;

/**
 *
 * @author irene
 */
@Local
public interface PartChoiceEntitySessionBeanLocal {

    public List<PartChoiceEntity> retrieveAllPartChoiceEntities();

    public PartChoiceEntity retrievePartChoiceEntityByPartChoiceEntityId(Long partChoiceId) throws PartChoiceEntityNotFoundException;

    public PartChoiceEntity retrievePartChoiceEntityByPartChoiceName(String partChoiceName) throws PartChoiceEntityNotFoundException;

    public void deletePartChoiceEntity(Long partChoiceEntityId) throws PartChoiceEntityNotFoundException, DeletePartChoiceEntityException;

    public Long createNewPartChoiceEntity(PartChoiceEntity newPartChoiceEntity) throws PartChoiceEntityExistException, InputDataValidationException, UnknownPersistenceException;

    public void updatePartChoiceEntity(PartChoiceEntity partChoiceEntity) throws PartChoiceEntityNotFoundException, UpdatePartChoiceEntityException, InputDataValidationException;

    public void addPartChoiceToChassisChoice(Long partChoiceToAddId, Long chassisToAddId) throws PartChoiceEntityNotFoundException, UnableToAddPartChoiceToPartChoiceException;

    public void removePartChoiceFromChassisChoice(Long partChoiceToRemoveId, Long chassisToRemoveId) throws PartChoiceEntityNotFoundException, UnableToRemovePartChoiceFromPartChoiceException;

    public void addPartChoiceToListOfChassisChoice(Long partChoiceToAddId, List<PartChoiceEntity> listOfChassisPartChoiceEntities) throws UnableToAddPartChoiceToPartChoiceException;

    public void toggleDisablePartChoiceEntity(Long partChoiceEntityId) throws UpdatePartChoiceEntityException;

    public void updateLinkedPartChoiceEntities(List<PartChoiceEntity> listOfPartChoiceEntitiesToLink, PartChoiceEntity partChoiceEntityToUpdate) throws UnableToLinkPartChoicesException;

}
