package dao;

import dto.*;
import dao.*;
import Exception.*;

public interface IPharamaceutDAO extends IUserDAO {

    public void createRecipe(IRecipeDTO recipeDTO);
    public void editRecipe(IRecipeDTO recipeDTO);
    public void deleteRecipe(int recipeID, int versionNumber);

}
