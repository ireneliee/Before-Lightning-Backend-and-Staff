/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AccessoryEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.AccessoryEntityNotFoundException;
import util.exception.AccessoryNameExistsException;
import util.exception.InputDataValidationException;
import util.exception.UnableToDeleteAccessoryEntityException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateAccessoryEntityException;

/**
 *
 * @author kaiyu
 */
@Local
public interface AccessoryEntitySessionBeanLocal {

    public Long createNewAccessoryEntity(AccessoryEntity newAccessoryEntity) throws AccessoryNameExistsException, UnknownPersistenceException, InputDataValidationException;

    public List<AccessoryEntity> retrieveAllAccessoryEntities();

    public AccessoryEntity retrieveAccessoryEntityById(Long id) throws AccessoryEntityNotFoundException;

    public AccessoryEntity retrieveAccessoryEntityByAccessoryName(String accessoryName) throws AccessoryEntityNotFoundException;

    public void deleteAccessoryEntity(Long accessoryEntityId) throws AccessoryEntityNotFoundException, UnableToDeleteAccessoryEntityException;

    public void updateAccessoryEntity(AccessoryEntity newAccessoryEntity) throws UpdateAccessoryEntityException, InputDataValidationException, AccessoryEntityNotFoundException, UnknownPersistenceException, AccessoryNameExistsException;

    public void toggleDisableAccessoryEntity(Long accessoryEntityId) throws UpdateAccessoryEntityException;

    public List<AccessoryEntity> retrieveAllAccessoryEntitiesNotDisabled();

}
