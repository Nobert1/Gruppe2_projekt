package dao;

import dao.*;
import java.util.List;
import dto.*;
import Exception.*;

public interface IUserDAO {
    IUserDTO getUser(int userId) throws DALException;
    List<IUserDTO> getUserList() throws DALException;

}
