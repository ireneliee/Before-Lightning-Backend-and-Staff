/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PartChoiceEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewPartChoiceEntityException;
import util.exception.InputDataValidationException;
import util.exception.PartChoiceEntityNotFoundException;
import util.exception.PartEntityNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdatePartChoiceEntityException;

/**
 *
 * @author irene
 */
@Local
public interface PartChoiceEntitySessionBeanLocal {

    public PartChoiceEntity createPartChoiceEntity(List<Long> relatedPartChoiceEntityIds, Long partEntityId, PartChoiceEntity newPartChoice) throws UnknownPersistenceException, InputDataValidationException, CreateNewPartChoiceEntityException, PartEntityNotFoundException;

    public void updatePartChoiceEntity(List<Long> newRelatedPartChoiceEntityIds, PartChoiceEntity updatedPartChoiceEntity) throws PartChoiceEntityNotFoundException, UpdatePartChoiceEntityException;

    public List<PartChoiceEntity> retrieveAllPartChoices();

    public PartChoiceEntity retrievePartChoiceEntityById(Long partChoiceEntityId) throws PartChoiceEntityNotFoundException;
    
}
