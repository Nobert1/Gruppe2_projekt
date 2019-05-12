package dao;

import dto.IRecipeDTO;
import exception.DALException;

public interface IPharamaceutDAO extends IUserDAO {
    public void createRecipe(IRecipeDTO recipeDTO) throws DALException;
    public void editRecipe(IRecipeDTO recipeDTO) throws DALException;
    public void deleteRecipe(int recipeID, int versionNumber) throws DALException;
    public IRecipeDTO getRecipe(int recipeID, int versionNumber) throws DALException;
    public void deletefromgenbestillingsliste(String commodity) throws DALException;
    public void insertintoGenbestillingsliste(String commodity) throws DALException;


    }
