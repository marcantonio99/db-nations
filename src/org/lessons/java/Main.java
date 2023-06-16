package org.lessons.java;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/db-nations";
        String user = "root";
        String password = "root";

        try(Connection connection = DriverManager.getConnection(url, user, password)){
            System.out.println(connection.getCatalog());

            Scanner scanner = new Scanner(System.in);
            System.out.print("Inserisci la stringa di ricerca: ");
            String searchString = scanner.nextLine();

            String sql = "SELECT n.name AS Nazione, n.country_id AS ID_Nazione, r.name AS Regione, c.name AS Continente\n" +
                    "FROM countries n\n" +
                    "INNER JOIN regions r ON n.region_id = r.region_id\n" +
                    "INNER JOIN continents c ON r.continent_id = c.continent_id\n" +
                    "WHERE n.name LIKE ? OR r.name LIKE ? OR c.name LIKE ?\n" +
                    "ORDER BY n.name;";

            try (PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setString(1, "%" + searchString + "%");
                ps.setString(2, "%" + searchString + "%");
                ps.setString(3, "%" + searchString + "%");

               try (ResultSet rs = ps.executeQuery()){
                   while (rs.next()){
                       String nomeNazione = rs.getString("Nazione");
                       int idNazione = rs.getInt("ID_Nazione");
                       String nomeRegione = rs.getString("Regione");
                       String nomeContinente = rs.getString("Continente");

                       System.out.println("Nation: " + nomeNazione + "ID: " + idNazione + "Region: " + nomeRegione + "Continent: " + nomeContinente);
                   }
               }
            }

        }catch (SQLException e){
            System.out.println("Unable to connect to database");
            e.printStackTrace();
        }
    }
}
