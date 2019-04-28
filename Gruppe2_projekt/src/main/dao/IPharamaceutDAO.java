package dao;

import dto.*;
import dao.*;
import Exception.*;

public interface IPharamaceutDAO extends IUserDAO {
    public void createRecipe();
    public void editRecipe();
    public void deleteRecipe();
}
