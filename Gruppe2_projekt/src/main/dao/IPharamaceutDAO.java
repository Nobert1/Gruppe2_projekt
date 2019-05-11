package dao;

import dto.IRecipeDTO;
import exception.DALException;

public interface IPharamaceutDAO extends IUserDAO {
    public void createRecipe(IRecipeDTO recipeDTO) throws DALException;
    public void editRecipe(IRecipeDTO recipeDTO) throws DALException;
    public void deleteRecipe(int recipeID, int versionNumber) throws DALException;
}
