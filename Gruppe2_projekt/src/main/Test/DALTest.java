package Test;

//TODO: This is dev branch.

import dao.*;
import dto.*;
import exception.*;
import org.junit.Test;
//import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DALTest {
    //TODO: Indsæt din egen implementering
    IAdminstratorDAO userDAO = new AdminstratorDAO();
    ILaborantDAO laborantDAO = new LaborantDAO();
    IPharamaceutDAO pharamaceutDAO = new PharamaceutDAO();
    IProduktionsLederDAO produktionsLederDAO = new ProduktionsLederDAO();

    @Test
    public void userTest() throws DALException {
        try {

            IUserDTO testUser = new UserDTO();
            testUser.setUserId(13);
            testUser.setUserName("Per Hansen");
            testUser.setIni("PH");
            ArrayList<String> roles = new ArrayList();
            roles.add("administrator");
            testUser.setRoles(roles);
            userDAO.deleteUser(13);
            userDAO.createUser(testUser);
            IUserDTO receivedUser = userDAO.getUser(13);
            assertEquals(testUser.getUserName(), receivedUser.getUserName());
            assertEquals(testUser.getIni(), receivedUser.getIni());
            assertEquals(testUser.getRoles().get(0), receivedUser.getRoles().get(0));
            assertEquals(testUser.getRoles().size(), receivedUser.getRoles().size());
            List<IUserDTO> allUsers = userDAO.getUserList();
            boolean found = false;
            for (IUserDTO user : allUsers) {
                if (user.getUserId() == testUser.getUserId()) {
                    assertEquals(testUser.getUserName(), user.getUserName());
                    assertEquals(testUser.getIni(), user.getIni());
                    assertEquals(testUser.getRoles().get(0), user.getRoles().get(0));
                    assertEquals(testUser.getRoles().size(), user.getRoles().size());
                    found = true;
                }
            }
            if (!found) {
                fail();
            }

            testUser.setUserName("Per petersen");
            testUser.setIni("PP");
            roles.remove(0);
            roles.add("pedel");
            testUser.setRoles(roles);
            userDAO.updateUser(testUser);

            receivedUser = userDAO.getUser(13);
            assertEquals(testUser.getUserName(), receivedUser.getUserName());
            assertEquals(testUser.getIni(), receivedUser.getIni());
            assertEquals(testUser.getRoles().get(0), receivedUser.getRoles().get(0));
            assertEquals(testUser.getRoles().size(), receivedUser.getRoles().size());

            userDAO.deleteUser(testUser.getUserId());
            allUsers = userDAO.getUserList();

            for (IUserDTO user : allUsers) {
                if (user.getUserId() == testUser.getUserId()) {
                    fail();
                }
            }

        } catch (DALException e) {
            e.getMessage();
            fail();
        }

    }


    /**
     * færdi
     *
     * @throws DALException
     */
    @Test
    public void PharmaceutTest() throws DALException {
        try {

            pharamaceutDAO.deleteRecipe(1, 1);
            pharamaceutDAO.deleteRecipe(1, 2);
            IRecipeDTO recipeDTO = new RecipeDTO();
            recipeDTO.setRecipeID(1);
            recipeDTO.setStatus("aktiv");
            LocalDate localDate = LocalDate.now();
            recipeDTO.setChangeDate(Date.valueOf(localDate));
            recipeDTO.setProductName("Ipren");
            recipeDTO.setVersionnumber(1);
            recipeDTO.setDurability(180);


            List<IIngredientDTO> ingredientListDTO = new ArrayList<>();
            IIngredientDTO ingredientDTO = new IngredientDTO();
            ingredientDTO.setAmount(2.5);
            ingredientDTO.setCommodityName("salt");
            ingredientDTO.setIngredientListID(1);
            ingredientDTO.setVersionsnummer(1);
            IIngredientDTO ingredientDTO1 = new IngredientDTO();
            ingredientDTO1.setAmount(3.0);
            ingredientDTO1.setCommodityName("pepper");
            ingredientDTO1.setIngredientListID(1);
            ingredientDTO1.setVersionsnummer(1);
            ingredientListDTO.add(ingredientDTO);
            ingredientListDTO.add(ingredientDTO1);
            recipeDTO.setIngredientListDTOList(ingredientListDTO);


            pharamaceutDAO.createRecipe(recipeDTO);
            IRecipeDTO recipeDTO1 = pharamaceutDAO.getRecipe(1, 1);
            assertEquals(recipeDTO.getProductName(), recipeDTO1.getProductName());

            recipeDTO.setVersionnumber(2);
            pharamaceutDAO.editRecipe(recipeDTO);
            recipeDTO1 = pharamaceutDAO.getRecipe(1, 2);
            ingredientDTO.setVersionsnummer(2);
            ingredientDTO1.setVersionsnummer(2);
            recipeDTO1.setIngredientListDTOList(ingredientListDTO);

            assertEquals(recipeDTO1.getStatus(), "aktiv");


        } catch (DALException e) {
            e.getMessage();
            fail();
        }

    }


    @Test
    public void Laboranttest() throws DALException {

        try {
            //Samme opskrifter som tidligere.
            pharamaceutDAO.deleteRecipe(1, 1);
            pharamaceutDAO.deleteRecipe(1, 2);
            produktionsLederDAO.DeleteCommodityBatch(1, "SaltMinen");
            IRecipeDTO recipeDTO = new RecipeDTO();
            recipeDTO.setRecipeID(1);
            recipeDTO.setStatus("aktiv");
            LocalDate localDate = LocalDate.now();
            recipeDTO.setChangeDate(Date.valueOf(localDate));
            recipeDTO.setProductName("Ipren");
            recipeDTO.setVersionnumber(1);
            recipeDTO.setDurability(180);


            List<IIngredientDTO> ingredientListDTO = new ArrayList<>();
            IIngredientDTO ingredientDTO = new IngredientDTO();
            ingredientDTO.setAmount(2.5);
            ingredientDTO.setCommodityName("salt");
            ingredientDTO.setIngredientListID(1);
            ingredientDTO.setVersionsnummer(1);
            IIngredientDTO ingredientDTO1 = new IngredientDTO();
            ingredientDTO1.setAmount(3.0);
            ingredientDTO1.setCommodityName("pepper");
            ingredientDTO1.setIngredientListID(1);
            ingredientDTO1.setVersionsnummer(1);
            ingredientListDTO.add(ingredientDTO);
            ingredientListDTO.add(ingredientDTO1);
            recipeDTO.setIngredientListDTOList(ingredientListDTO);

            pharamaceutDAO.createRecipe(recipeDTO);


            IProductBatchDTO productBatchDTO = new ProductBatchDTO();
            productBatchDTO.setProductBatchID(1);
            productBatchDTO.setStatus("Afventer_produktion");
            productBatchDTO.setVersionsnummer(1);
            productBatchDTO.setRecipeID(1);

            ICommodityBatchDTO commodityBatchDTO = new CommodityBatchDTO();

            commodityBatchDTO.setProducerName("Leo-pharma");
            commodityBatchDTO.setBatchID(1);
            commodityBatchDTO.setActualAmount(250.00);
            commodityBatchDTO.setCommodityName("salt");
            commodityBatchDTO.setRemainder(false);

            ICommodityBatchDTO commodityBatchDTO1 = new CommodityBatchDTO();
            commodityBatchDTO1.setProducerName("Leo-pharma");
            commodityBatchDTO1.setBatchID(2);
            commodityBatchDTO1.setActualAmount(220.00);
            commodityBatchDTO1.setCommodityName("pepper");

            produktionsLederDAO.DeleteCommodityBatch(1, commodityBatchDTO.getProducerName());
            produktionsLederDAO.DeleteCommodityBatch(2, commodityBatchDTO1.getProducerName());

            produktionsLederDAO.createCommodityBatch(commodityBatchDTO);
            produktionsLederDAO.createCommodityBatch(commodityBatchDTO1);

            ICommodityBatchDTO commodityBatchDTO2 = produktionsLederDAO.getCommodity(commodityBatchDTO.getBatchID(), commodityBatchDTO.getProducerName());


            assertEquals(commodityBatchDTO.getCommodityName(), commodityBatchDTO2.getCommodityName());

            List<ICommodityBatchDTO> commodityBatchDTOList = new ArrayList<>();

            commodityBatchDTOList.add(commodityBatchDTO);
            commodityBatchDTOList.add(commodityBatchDTO1);

//For at den her test skal køre skal de her to være de eneste i vores program.
            produktionsLederDAO.DeleteProductBatch(productBatchDTO.getProductBatchID());
            produktionsLederDAO.CreateProductBatch(productBatchDTO);
            IProductBatchDTO productBatchDTO1 = produktionsLederDAO.getProductBatch(1);
            assertEquals(productBatchDTO.getStatus(), productBatchDTO1.getStatus());

            List<ICommodityBatchDTO> commodityBatchDTOList1 = new ArrayList<>();
            commodityBatchDTOList1 = produktionsLederDAO.getCommodityBatches(productBatchDTO);

            productBatchDTO.setCommodityBatchDTOList(commodityBatchDTOList1);

            assertEquals(productBatchDTO.getCommodityBatchDTOList().get(1).getProducerName(), commodityBatchDTOList.get(1).getProducerName());

            laborantDAO.prepareProductBatch(productBatchDTO);

            IProductBatchDTO productBatchDTO2 = new ProductBatchDTO();
            productBatchDTO2 = produktionsLederDAO.getProductBatch(productBatchDTO.getProductBatchID());

            assertEquals(productBatchDTO2.getStatus(), "Afventer_produktion");

            laborantDAO.finishBatch(productBatchDTO);
            productBatchDTO2 = produktionsLederDAO.getProductBatch(productBatchDTO.getProductBatchID());

            assertEquals(productBatchDTO2.getStatus(), "færdig");

            ICommodityBatchDTO commodityBatchDTO3 = new CommodityBatchDTO();
            commodityBatchDTO3.setProducerName("SaltMinen");
            commodityBatchDTO3.setBatchID(1);
            commodityBatchDTO3.setActualAmount(250.00);
            commodityBatchDTO3.setCommodityName("salt");
            commodityBatchDTO3.setRemainder(false);
            produktionsLederDAO.createCommodityBatch(commodityBatchDTO3);

            double sum1 = commodityBatchDTO3.getActualAmount() + commodityBatchDTO.getActualAmount();

            assertTrue(produktionsLederDAO.SumCommodityBatches("salt") == sum1);




        } catch (DALException e) {
            e.getMessage();
            fail();
        }
    }
}


