/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.isimed.form;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Form extends JFrame {
    private JTextField textFieldNom;
    private JTextField textFieldPrenom;
    private JTextField textFieldEmail;
    private JTextField textFieldDateNaissance;
    private JRadioButton radioButtonHomme;
    private JRadioButton radioButtonFemme;
    private JCheckBox checkBoxSport;
    private JCheckBox checkBoxMusic;
    private JCheckBox checkBoxVoyage;
    private javax.swing.JButton delete;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField search;
    private javax.swing.JButton searchBtn;
    private javax.swing.JButton update;
    public Form() {
        
        // Configuration de la fenêtre
        setTitle("Formulaire");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Création des composants
        JLabel labelNom = new JLabel("Nom:");
        JLabel labelPrenom = new JLabel("Prénom:");
        JLabel labelEmail = new JLabel("Email:");

        textFieldNom = new JTextField(20);
        textFieldPrenom = new JTextField(20);
        textFieldEmail = new JTextField(20);
       

        JButton buttonEnregistrer = new JButton("Enregistrer");
        JButton buttonVider = new JButton("Vider");
        JButton buttonAnnuler = new JButton("Annuler");
        update = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        search = new javax.swing.JTextField(20);
        jLabel4 = new javax.swing.JLabel();
        searchBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        				
        update.setText("Update");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });

        delete.setText("Delete");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        jLabel4.setText("Find By ID");

        searchBtn.setText("Find");
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "First Name", "Last Name", "Email"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        
        // Utilisation de JPanel avec FlowLayout pour organiser les composants
        JPanel panelInfoPersonnelle = new JPanel();
        panelInfoPersonnelle.setLayout(new FlowLayout());
        panelInfoPersonnelle.add(labelNom);
        panelInfoPersonnelle.add(textFieldNom);
        panelInfoPersonnelle.add(labelPrenom);
        panelInfoPersonnelle.add(textFieldPrenom);

        JPanel panelEmailDate = new JPanel();
        panelEmailDate.setLayout(new FlowLayout());
        panelEmailDate.add(labelEmail);
        panelEmailDate.add(textFieldEmail);


        JPanel panelBoutons = new JPanel();
        panelBoutons.setLayout(new FlowLayout());
        panelBoutons.add(buttonEnregistrer);
        panelBoutons.add(buttonVider);
        panelBoutons.add(buttonAnnuler);
        panelBoutons.add(update);
        panelBoutons.add(delete);
        JPanel panelFind = new JPanel();
        panelFind.add(jLabel4);
        panelFind.add(search);
        panelFind.add(searchBtn);
        JPanel panelUser = new JPanel();
        // Utilisation d'un conteneur BorderLayout pour organiser les panneaux
        setLayout(new BorderLayout());
        add(panelInfoPersonnelle, BorderLayout.NORTH);
        add(panelEmailDate, BorderLayout.CENTER);
        add(panelBoutons, BorderLayout.SOUTH);
        
        // Gestion des événements
        buttonEnregistrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enregistrerFormulaire();
            }
        });

        buttonVider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viderFormulaire();
            }
        });

        buttonAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        setLayout(new GridLayout(8, 1));
        add(panelInfoPersonnelle);
        add(panelEmailDate);
        add(panelBoutons);
        add(panelUser);
        add(panelFind);
        add(jScrollPane1);
    }
  public void loadData() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/java_users_db";
            String user = "root";
            String pass = "";

            Connection con = DriverManager.getConnection(url, user, pass);
            Statement st = con.createStatement();
            DefaultTableModel model = new DefaultTableModel(new String[] { "ID", "First Name",
                "Last Name", "email" },0);
            jTable1.setModel(model);
            String sql = "SELECT * FROM user";
            ResultSet rs = st.executeQuery(sql);
            String i, f, l, e;
            while (rs.next()) {
                i = rs.getString("id");
                f = rs.getString("first_name");
                l = rs.getString("last_name");
                e = rs.getString("email");
                model.addRow(new Object[] { i, f, l, e });
            }
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }
    private void formWindowOpened(java.awt.event.WindowEvent evt) {                                  
        // TODO add your handling code here:
        loadData();
    }                                 
                                 

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {                                       
         String ID;
        int notFound = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/java_users_db";
            String user = "root";
            String pass = "";

            Connection con = DriverManager.getConnection(url, user, pass);
            Statement st = con.createStatement();

            ID = search.getText();
            if ("".equals(ID)) {
                JOptionPane.showMessageDialog(new JFrame(), "ID is require", "Dialog",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                String sql = "SELECT * FROM user WHERE id=" + ID;
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    notFound = 1;
                    String sql2 = "DELETE FROM user WHERE id=" + ID;
                    st.executeUpdate(sql2);
                    loadData();
                    con.close();
                }
                if (notFound == 0) {
                    JOptionPane.showMessageDialog(new JFrame(), "invalid ID", "Dialog",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());

        } //
        // TODO add your handling code here:
    }                                      

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {                                          
         String recherche;
        int notFound = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/java_users_db";
            String user = "root";
            String pass = "";

            Connection con = DriverManager.getConnection(url, user, pass);
            Statement st = con.createStatement();

            recherche = search.getText();
            if ("".equals(recherche)) {
                JOptionPane.showMessageDialog(new JFrame(), "ID is require", "Dialog",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                String sql = "SELECT * FROM user WHERE id=" + recherche;
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    textFieldNom.setText(rs.getString("first_name"));
                    textFieldPrenom.setText(rs.getString("last_name"));
                    textFieldEmail.setText(rs.getString("email"));
                    notFound = 1;
                    loadData();
                    con.close();
                }
                if (notFound == 0) {
                    JOptionPane.showMessageDialog(new JFrame(), "invalid ID", "Dialog",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());

        }        
// TODO add your handling code here:
    }                                         

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {                                       
        String ID;
        int notFound = 0;
        String fN, lN, em;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/java_users_db";
            String user = "root";
            String pass = "";

            Connection con = DriverManager.getConnection(url, user, pass);
            Statement st = con.createStatement();

            ID = search.getText();
            if ("".equals(ID)) {
                JOptionPane.showMessageDialog(new JFrame(), "ID is require", "Dialog",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                String sql = "SELECT * FROM user WHERE id=" + ID;
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    notFound = 1;
                    fN = textFieldNom.getText();
                    lN = textFieldPrenom.getText();
                    em = textFieldEmail.getText();
                    String sql2 = "UPDATE user SET first_name='" + fN + "', last_name='" + lN + "', email='" + em
                            + "'  WHERE id=" + ID;
                    st.executeUpdate(sql2);
                    loadData();
                    con.close();
                }
                if (notFound == 0) {
                    JOptionPane.showMessageDialog(new JFrame(), "invalid ID", "Dialog",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());

        }         // TODO add your handling code here:
    }                                      

    private void enregistrerFormulaire() {
         // Récupérer les données du formulaire
    String nom = textFieldNom.getText();
    String prenom = textFieldPrenom.getText();
    String email = textFieldEmail.getText();
   
    try{
    Connection connection = seconnecter();
         System.out.println("connexion etablie");
// Requête d'insertion
        String sql = "INSERT INTO user (first_name, last_name, email) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nom);
            statement.setString(2, prenom);
            statement.setString(3, email);
            // Exécution de la requête
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Enregistrement réussi !");
            }
        }
    } catch (SQLException e) {
        System.out.println("Erreur lors de l'enregistrement : " + e.getMessage());
    }
    }
public static Connection seconnecter() {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/java_users_db";
        String nom_user = "root";
        String mdp = "";
        Connection cnx = DriverManager.getConnection(url, nom_user, mdp);
        System.out.println("connexion établie");
        return cnx;
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace(); // Print stack trace for debugging
        return null;
    }
}
   
    private void viderFormulaire() {
        // Ajoutez le code pour vider les champs du formulaire ici
        textFieldNom.setText("");
        textFieldPrenom.setText("");
        textFieldEmail.setText("");
       
    }

    public static void main(String[] args) {
        
         Form exp = new Form();
         exp.loadData();
         exp.setVisible(true);
         
    }
}
