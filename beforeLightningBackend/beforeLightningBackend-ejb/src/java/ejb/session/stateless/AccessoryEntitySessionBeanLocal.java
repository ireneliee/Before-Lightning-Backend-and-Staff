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
import util.exception.AccessoryItemEntityNotFoundException;
import util.exception.AccessoryNameExistsException;
import util.exception.InputDataValidationException;
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

    public void updateAccessoryEntity(AccessoryEntity newAccessoryEntity) throws UpdateAccessoryEntityException, InputDataValidationException, AccessoryEntityNotFoundException;

    public void deleteAccessoryEntity(Long productTypeId) throws AccessoryEntityNotFoundException;

    public void toggleDisableAccessoryEntity(AccessoryEntity accessoryEntity) throws AccessoryEntityNotFoundException, UpdateAccessoryEntityException, InputDataValidationException;

}
