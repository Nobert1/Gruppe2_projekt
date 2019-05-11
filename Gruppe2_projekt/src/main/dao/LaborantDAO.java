package dao;

import dto.*;
import exception.DALException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.*;

import java.sql.*;


    //TODO Jeg ved ikke helt hvad den her klasse den har brug for.

/**
 * Author - Gustav EMil Nobert s185031 and Martin Wassman s185029?
 */

public class LaborantDAO extends UserDAO implements ILaborantDAO {


    @Override
    public void prepareProductBatch(IProductBatchDTO productBatchDTO) {
        try (Connection c = DataSource.getConnection()) {

            Scanner scan = new Scanner(System.in);
            c.setAutoCommit(false);


            PreparedStatement statement1 = c.prepareStatement("UPDATE Råvare_batch_lager WHERE ID = (?) SET Mængde (?)");

//Her ville det måske være fedt at lave noget andet end input scan. Men vi skal jo netop bruge noget input så der vel ingen anden måde at gøre det på.


            for (ICommodityBatchDTO commodityBatchDTO : productBatchDTO.getCommodityBatchDTOList()) {

                System.out.println("you need to withdraw " + commodityBatchDTO.getMængde() + " from batch number = " + commodityBatchDTO.getMængde());
                System.out.println("how much did you withdraw?");
                int withdrawen = scan.nextInt();

                while (withdrawen >= commodityBatchDTO.getMængde() * 0.98 && commodityBatchDTO.getMængde() * 1.02 >= withdrawen) {
                    System.out.println("withdrawn amount not within the boundaries, please withdraw agian");
                    System.out.println("How much did you withdraw?");
                    withdrawen = scan.nextInt();
                }

                statement1.setInt(1, commodityBatchDTO.getBatchID());
                statement1.setDouble(2, commodityBatchDTO.getActualAmount() - withdrawen);
                statement1.addBatch();
            }

            PreparedStatement statement2 = c.prepareStatement("UPDATE Productbatch WHERE ID = (?) SET status = (?)");
            statement2.setInt(1, productBatchDTO.getBatchID());
            statement2.setString(2, "under production");
            int row = statement2.executeUpdate();
            int[] rows = statement1.executeBatch();
            c.commit();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    @Override
    public void finishBatch(IProductBatchDTO productBatchDTO) {

        try (Connection c = DataSource.getConnection()) {

            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement("UPDATE Productbatch WHERE ProduktbatchID = (?) SET status = (?), Udløbsdato = (?)");


            PreparedStatement statement1 = c.prepareStatement("SELECT * FROM Produktbatch_beskrivelse WHERE BatchType = (?)");
            statement1.setString(1, productBatchDTO.getProduktnavn());
            ResultSet resultset1 = statement1.executeQuery();


            statement.setInt(1, productBatchDTO.getBatchID());
            statement.setString(2, "finished");

            if (resultset1.next()) {
                LocalDate localDate = LocalDate.now();
                LocalDate localDate1 = localDate.plusDays(resultset1.getInt("Opbevarings_dage"));
                statement.setDate(3, Date.valueOf(localDate1));
            }

            int row = statement.executeUpdate();
            c.commit();

        } catch (SQLException e) {
            e.getMessage();
        }
    }


    }