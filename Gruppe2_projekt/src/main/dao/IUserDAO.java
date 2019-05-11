package dao;

import java.util.List;
import dto.*;
import exception.*;

public interface IUserDAO {
    IUserDTO getUser(int userId) throws DALException;
    List<IUserDTO> getUserList() throws DALException;

}
