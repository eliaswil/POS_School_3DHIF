/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import beans.SearchType;
import database.DB_Access;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;

/**
 *
 * @author Elias Wilfinger
 */
public class BookshopGUI extends JFrame{
    private DefaultComboBoxModel cbmVerlag = new DefaultComboBoxModel();
    private DefaultComboBoxModel cbmGenre = new DefaultComboBoxModel();
    private DefaultListModel dlmBooks = new DefaultListModel();
    private JComboBox<String> cbVerlag;
    private JComboBox<String> cbGenre;
    private JTextField tfSearchBar;
    private JRadioButton rbBook;
    private JRadioButton rbAuthor;
    
    private DB_Access dba;
    private List<SearchType> searchingOrder;
            
    public BookshopGUI(String title) throws HeadlessException {
        super(title);
        this.setSize(1000, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        dba = DB_Access.getInstance();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });
        
        initComponents();
//        fillComponentsWithData();
    }

    private void initComponents() {
        this.setLayout(new BorderLayout());
        
        // Search (TOP)
        JPanel paSearch = new JPanel(new GridLayout(1, 2));        
        paSearch.setPreferredSize(new Dimension(950, 130));
        paSearch.setBorder(new TitledBorder("Suchen"));
        
            // Labels + ComboBoxes
            JPanel paSearchLabelsBoxes = new JPanel(new GridBagLayout());
                JLabel lbVerlag = new JLabel("Verlag:");
                JLabel lbGenre = new JLabel("Genre:");
                
                cbVerlag = new JComboBox<>(cbmVerlag);
                cbGenre = new JComboBox<>(cbmGenre);
                cbVerlag.setPreferredSize(new Dimension(300, 30));
                cbGenre.setPreferredSize(new Dimension(300, 30));
                
                GridBagConstraints gbcLbVerlag = new GridBagConstraints();
                GridBagConstraints gbcLbGenre = new GridBagConstraints();
                GridBagConstraints gbcCbVerlag = new GridBagConstraints();
                GridBagConstraints gbcCbGenre = new GridBagConstraints();
                gbcLbVerlag.gridwidth = 1;
                gbcLbVerlag.gridx = 0;
                gbcLbVerlag.gridy = 0;
                
                
                gbcLbVerlag.gridwidth = 1;
                gbcLbVerlag.gridx = 0;
                gbcLbVerlag.gridy = 1;
                
                gbcCbGenre.gridwidth = 3;
                gbcCbGenre.gridx = 1;
                gbcCbGenre.gridy = 0;
                gbcCbGenre.insets = new Insets(5, 20, 5, 0);
                
                gbcCbVerlag.gridwidth = 3;
                gbcCbVerlag.gridx = 1;
                gbcCbVerlag.gridy = 1;
                gbcCbVerlag.insets = new Insets(5, 20, 5, 0);
                
                

                cbVerlag.addActionListener(this::onSelectVerlag);
                cbGenre.addActionListener(this::onSelectGenre);
                
                
                paSearchLabelsBoxes.add(lbVerlag, gbcLbVerlag);
                paSearchLabelsBoxes.add(cbVerlag, gbcCbVerlag);
                paSearchLabelsBoxes.add(lbGenre, gbcLbGenre);
                paSearchLabelsBoxes.add(cbGenre, gbcCbGenre);
            
            // Search-Bar + Radio Buttons
            JPanel paSearchBarRadioButtons = new JPanel(new BorderLayout());
                tfSearchBar = new JTextField();
                tfSearchBar.setPreferredSize(new Dimension(400, 100));
                tfSearchBar.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent arg0) {
                        onSearch(arg0);
                    }

                    @Override
                    public void removeUpdate(DocumentEvent arg0) {
                        onSearch(arg0);
                    }

                    @Override
                    public void changedUpdate(DocumentEvent arg0) {
                        onSearch(arg0);
                    }
                });

                // Radio-Buttons
                JPanel paRadioButtons = new JPanel(new BorderLayout());
                    ButtonGroup bgSearch = new ButtonGroup();
                    rbBook = new JRadioButton("Buch");
                    rbAuthor = new JRadioButton("Autor");
                    
                    rbBook.setPreferredSize(new Dimension(100, 50));
                    rbAuthor.setPreferredSize(new Dimension(100, 50));
                    
                    rbBook.addActionListener(this::onBook);
                    rbAuthor.addActionListener(this::onAuthor);

                    bgSearch.add(rbBook);
                    bgSearch.add(rbAuthor);

                    paRadioButtons.add(rbAuthor, BorderLayout.CENTER);
                    paRadioButtons.add(rbBook, BorderLayout.WEST);
                    
                paSearchBarRadioButtons.add(tfSearchBar, BorderLayout.CENTER);
                paSearchBarRadioButtons.add(paRadioButtons, BorderLayout.SOUTH);
            
            paSearch.add(paSearchLabelsBoxes);
            paSearch.add(paSearchBarRadioButtons);

        
        // Books (LEFT)
        JPanel paBooks = new JPanel(new BorderLayout());
        paBooks.setPreferredSize(new Dimension(390, 390));
        paBooks.setBorder(new TitledBorder("Bücher"));
        
            JScrollPane spBooks = new JScrollPane();
                JList liBooks = new JList(dlmBooks);
                
                liBooks.addListSelectionListener(this::onSelectBook);
                
                spBooks.add(liBooks);
            
            paBooks.add(spBooks);
        
        // Book details (CENTER)
        JPanel paBookDetails = new JPanel(new BorderLayout());
        paBookDetails.setPreferredSize(new Dimension(590, 390));
        paBookDetails.setBorder(new TitledBorder("Buchdetails"));
        
        
        this.add(paSearch, BorderLayout.NORTH);
        this.add(paBooks, BorderLayout.WEST);
        this.add(paBookDetails, BorderLayout.CENTER);
    }
    
    private void fillComponentsWithData(){
        searchingOrder = new ArrayList<>();
        throw new UnsupportedOperationException("TODO: fillComponentsWithData");
    }
    
    private void onSelectVerlag(ActionEvent e){
        // add or remove searchtype
        throw new UnsupportedOperationException("TODO: onSelectVerlag");
    }
    
    private void onSelectGenre(ActionEvent e){
        // add or remove searchtype
        throw new UnsupportedOperationException("TODO: onSelectGenre");
    }
    
    private void onSearch(DocumentEvent e){
        // add or remove searchtype
        throw new UnsupportedOperationException("TODO: onSearch");
    }
    
    private void onBook(ActionEvent e){
        throw new UnsupportedOperationException("TODO: onBook");
    }
    
    private void onAuthor(ActionEvent e){
        throw new UnsupportedOperationException("TODO: onAuthor");
    }
    
    private void onSelectBook(ListSelectionEvent e){
        throw new UnsupportedOperationException("TODO: onSelectBook");
    }
    
    private void onExit(){
        try {
            dba.disconnect();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.exit(0);
    }
    
    public static void main(String[] args) {
        try{
            BookshopGUI bookShopGUI = new BookshopGUI("Book Shop");
            bookShopGUI.setVisible(true);
        }catch(UnsupportedOperationException e){
            e.printStackTrace();
        }
        
    }
    
    
}
