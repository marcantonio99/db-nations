package org.lessons.java;

import com.sun.source.tree.TryTree;

import javax.xml.transform.Result;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/db-nations";
        String user = "root";
        String password = "root";

        try(Connection connection = DriverManager.getConnection(url, user, password)){
            System.out.println(connection.getCatalog());

            String sql = "SELECT n.name AS Nazione, n.country_id AS ID_Nazione, r.name AS Regione, c.name AS Continente\n" +
                    "FROM countries n\n" +
                    "INNER JOIN regions r ON n.region_id = r.region_id\n" +
                    "INNER JOIN continents c ON r.continent_id = c.continent_id\n" +
                    "ORDER BY n.name;";
            try (PreparedStatement ps = connection.prepareStatement(sql)){
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
