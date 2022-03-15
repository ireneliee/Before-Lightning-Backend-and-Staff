/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AccessoryItemEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.AccessoryItemEntityNotFoundException;
import util.exception.AccessoryItemNameExists;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateAccessoryItemEntityException;

/**
 *
 * @author kaiyu
 */
@Local
public interface AccessoryItemEntitySessionBeanLocal {
	
	public Long createNewAccessoryItemEntity(AccessoryItemEntity newAccessoryItem) throws AccessoryItemNameExists, InputDataValidationException, UnknownPersistenceException;
	public List<AccessoryItemEntity> retrieveAllAccessoryItemEntities();
	public AccessoryItemEntity retrieveAccessoryItemById(Long id) throws AccessoryItemEntityNotFoundException;
	public void updateAccessoryItem(AccessoryItemEntity newAccessoryItem) throws UpdateAccessoryItemEntityException, InputDataValidationException, AccessoryItemEntityNotFoundException;

    public void toggleDisableAccessoryItemEntity(AccessoryItemEntity accessoryItemEntity) throws AccessoryItemEntityNotFoundException, UpdateAccessoryItemEntityException, InputDataValidationException;
	
}
