package dao;

import dto.IAdminstratorDTO;
import dto.*;
import dao.*;
import Exception.*;
import java.util.List;

public interface IAdminstratorDAO extends IUserDAO  {
    //Create
    void createUser(IUserDTO user) throws DALException;
    //Read
    //Update
    void updateUser(IUserDTO user) throws DALException;
    //Delete
    void deleteUser(int userId) throws DALException;

   }
