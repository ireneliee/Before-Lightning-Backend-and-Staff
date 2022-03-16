/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PartEntity;
import entity.ProductEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewPartEntityException;
import util.exception.DeletePartEntityException;
import util.exception.InputDataValidationException;
import util.exception.PartChoiceEntityNotFoundException;
import util.exception.PartEntityExistException;
import util.exception.PartEntityNotFoundException;
import util.exception.PartNameNotFoundException;
import util.exception.ProductEntityNotFoundException;
import util.exception.UnableToAddPartChoiceToPartException;
import util.exception.UnableToAddPartToProductException;
import util.exception.UnableToRemovePartChoiceFromPartException;
import util.exception.UnableToRemovePartFromProductException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdatePartEntityException;

/**
 *
 * @author irene
 */
@Local
public interface PartEntitySessionBeanLocal {

    public Long createNewPartEntity(PartEntity newPartEntity) throws PartEntityExistException, InputDataValidationException, UnknownPersistenceException;

    public PartEntity retrievePartEntityByPartEntityId(Long partEntityId) throws PartEntityNotFoundException;

    public PartEntity retrievePartEntityByPartName(String partName) throws PartNameNotFoundException;

    public List<PartEntity> retrieveAllPartEntitiess();

    public void deletePartEntity(Long partEntityId) throws PartEntityNotFoundException, DeletePartEntityException;

    public void toggleDisablePartEntity(PartEntity partEntity) throws PartEntityNotFoundException, UpdatePartEntityException, InputDataValidationException;

    public void updatePartEntity(PartEntity partEntity) throws PartEntityNotFoundException, UpdatePartEntityException, InputDataValidationException;

    public void addPartToProduct(Long partEntityId, Long productEntityId) throws PartEntityNotFoundException, ProductEntityNotFoundException, UnableToAddPartToProductException;

    public void removePartFromProduct(Long partEntityId, Long productEntityId) throws PartEntityNotFoundException, ProductEntityNotFoundException, UnableToRemovePartFromProductException;

    public void addPartChoiceToPart(Long partChoiceEntityId, Long partEntityId) throws PartEntityNotFoundException, PartChoiceEntityNotFoundException, UnableToAddPartChoiceToPartException;

    public void removePartChoiceFromPart(Long partChoiceEntityId, Long partEntityId) throws PartEntityNotFoundException, PartChoiceEntityNotFoundException, UnableToRemovePartChoiceFromPartException;

}
