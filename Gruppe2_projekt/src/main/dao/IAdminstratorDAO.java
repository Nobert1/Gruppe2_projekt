package dao;

import dto.*;
import exception.*;

public interface IAdminstratorDAO extends IUserDAO  {
    //Create
    void createUser(IUserDTO user) throws DALException;
    //Read
    //Update
    void updateUser(IUserDTO user) throws DALException;
    //Delete
    void deleteUser(int userId) throws DALException;

   }
