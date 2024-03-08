/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.isimed.form;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Users extends JFrame {
    private JPanel mainPanel;
    private JTextArea userListTextArea;

    public Users() {
        setTitle("Liste des Utilisateurs");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        userListTextArea = new JTextArea();
        userListTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(userListTextArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        displayUserList();
    }

    private void displayUserList() {
        List<String> userList = getUserListFromDatabase();
        StringBuilder sb = new StringBuilder();
        for (String user : userList) {
            sb.append(user).append("\n");
        }
        userListTextArea.setText(sb.toString());
    }

    private List<String> getUserListFromDatabase() {
        List<String> userList = new ArrayList<>();
        try {
            Connection connection = seconnecter();
            String sql = "SELECT * FROM user";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    String nom = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");
                    String email = resultSet.getString("email");
                    String dateNaissance = resultSet.getString("date_naissance");
                    String genre = resultSet.getString("genre");
                    String interets = resultSet.getString("interets");
                    String userInfo = "Nom: " + nom + ", Prénom: " + prenom + ", Email: " + email +
                            ", Date de Naissance: " + dateNaissance + ", Genre: " + genre +
                            ", Intérêts: " + interets;
                    userList.add(userInfo);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la liste des utilisateurs : " + e.getMessage());
        }
        return userList;
    }

    public static Connection seconnecter() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/jarray";
            String nom_user = "root";
            String mdp = "";
            return DriverManager.getConnection(url, nom_user, mdp);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Afficher la trace de la pile pour le débogage
            return null;
        }
    }

   
}

