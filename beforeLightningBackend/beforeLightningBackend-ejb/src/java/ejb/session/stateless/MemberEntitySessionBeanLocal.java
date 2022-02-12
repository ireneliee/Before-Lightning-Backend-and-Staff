/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.MemberEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.DeleteMemberEntityException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.MemberEntityNotFoundException;
import util.exception.MemberEntityUsernameExistException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateMemberEntityException;

/**
 *
 * @author Koh Wen Jie
 */
@Local
public interface MemberEntitySessionBeanLocal {

    public Long createNewMemberEntity(MemberEntity newMemberEntity) throws MemberEntityUsernameExistException, InputDataValidationException, UnknownPersistenceException;

    public List<MemberEntity> retrieveAllMemberEntities();

    public MemberEntity retrieveMemberEntityByMemberEntityId(Long employeeId) throws MemberEntityNotFoundException;

    public MemberEntity retrieveMemberEntityByUsername(String username) throws MemberEntityNotFoundException;

    public MemberEntity memberEntityLogin(String username, String password) throws InvalidLoginCredentialException;

    public void updateMemberEntity(MemberEntity memberEntity) throws MemberEntityNotFoundException, UpdateMemberEntityException, InputDataValidationException;

    public void deleteMemberEntity(Long memberEntityId) throws MemberEntityNotFoundException, DeleteMemberEntityException;
    
}
