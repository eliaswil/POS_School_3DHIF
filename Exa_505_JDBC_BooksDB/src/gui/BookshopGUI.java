/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import beans.Book;
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
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
    private JList liBooks;
    private JEditorPane epBookDetails;
    private boolean skip = false;
    
    private DB_Access dba;
    private List<SearchType> filtering; // priority of filtering
            
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
        fillComponentsWithData();
        
    }

    /**
     * initialises all components
     */
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
                    rbBook.setSelected(true);
                    rbAuthor = new JRadioButton("Autor");
                    
                    rbBook.setPreferredSize(new Dimension(100, 50));
                    rbAuthor.setPreferredSize(new Dimension(100, 50));
                    
                    rbBook.addActionListener(this::onChangeSelection);
                    rbAuthor.addActionListener(this::onChangeSelection);

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
                liBooks = new JList(dlmBooks);
                
                liBooks.addListSelectionListener(this::onSelectBook);
                
                spBooks.setViewportView(liBooks);
            
            paBooks.add(spBooks);
        
        // Book details (CENTER)
        JPanel paBookDetails = new JPanel(new BorderLayout());
        paBookDetails.setPreferredSize(new Dimension(590, 390));
        paBookDetails.setBorder(new TitledBorder("Buchdetails"));
            
            epBookDetails = new JEditorPane();
            epBookDetails.setContentType("text/html");
            epBookDetails.setEditable(false);
            
            
            paBookDetails.add(epBookDetails, BorderLayout.CENTER);
        
        this.add(paSearch, BorderLayout.NORTH);
        this.add(paBooks, BorderLayout.WEST);
        this.add(paBookDetails, BorderLayout.CENTER);
    }
    
    /**
     * is responsible for filling the two comboboxes (genres & publishers) and the list (book titles9 with data
     */
    private void fillComponentsWithData(){
        try {
            filtering = new ArrayList<>();
            
            cbmVerlag.removeAllElements();
            cbmVerlag.addAll(dba.getAllPublishers());
            
            cbmGenre.removeAllElements();
            cbmGenre.addAll(dba.getAllGenres());
            
            cbVerlag.setSelectedIndex(0);
            cbGenre.setSelectedIndex(0);
            
            dlmBooks.clear();
            dlmBooks.addAll(dba.getFilteredBookTitles("", "", "", SearchType.AUTHOR));
            
        } catch (FileNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * handler method for selecting a publisher
     * @param e 
     */
    private void onSelectVerlag(ActionEvent e){ // TODO
        if(skip){
            skip = false;
            return;
        }
        String publisher = "";
        if(cbVerlag.getSelectedItem() != null){
            // if selected "all"
            if(cbVerlag.getSelectedItem().equals("All")){
                if(filtering.contains(SearchType.PUBLISHER)){
                    filtering.remove(SearchType.PUBLISHER);
                }
            }else{ // if a specific publisher is selected
                publisher = cbVerlag.getSelectedItem().toString();
                if(!filtering.contains(SearchType.PUBLISHER)){
                    filtering.add(SearchType.PUBLISHER);
                }
                if(filtering.indexOf(SearchType.PUBLISHER) < filtering.indexOf(SearchType.GENRE)){
                    filtering.remove(SearchType.GENRE);
                }
            } 
            if(!(filtering.contains(SearchType.GENRE) && filtering.contains(SearchType.PUBLISHER))){
                String selecedGenre = filtering.contains(SearchType.GENRE) && filtering.size() == 1 ? 
                        cbGenre.getSelectedItem().toString() :
                        "";
                skip = true;
                cbmGenre.removeAllElements();

                try {
                    if(publisher.equals("")){
                        cbmGenre.addAll(dba.getAllGenres());
                    }else{
                        cbmGenre.addAll(dba.getFilteredGenres(publisher)); 
                    }
                    
                    skip = true;
                    int index = 0;
                    if(filtering.contains(SearchType.GENRE) && filtering.size() == 1){
                        index = cbmGenre.getIndexOf(selecedGenre);
                        skip = false;
                    }
                    cbGenre.setSelectedIndex(index);
                    skip = false;
                } catch (FileNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        dlmBooks.clear();
        try {
            String genre = "";
            if(cbGenre.getSelectedItem() != null){
                genre = cbGenre.getSelectedItem().toString();
                if(genre.equals("All")){ 
                    genre = "";
                }
            }
            
            dlmBooks.addAll(dba.getFilteredBookTitles(genre, publisher, tfSearchBar.getText(), 
                    rbAuthor.isSelected() ? SearchType.AUTHOR : SearchType.BOOK));
        } catch (FileNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * handler method for selecting a genre
     * @param e 
     */
    private void onSelectGenre(ActionEvent e){
        if(skip){
            skip = false;
            return;
        }
        // add or remove searchtype
        String genre = "";
        if(cbGenre.getSelectedItem() != null){
            if(cbGenre.getSelectedItem().equals("All")){
                if(filtering.contains(SearchType.GENRE)){
                    filtering.remove(SearchType.GENRE);
                }
            }else{
                genre = cbGenre.getSelectedItem().toString();
                if(!filtering.contains(SearchType.GENRE)){
                    filtering.add(SearchType.GENRE);
                }
                if(filtering.indexOf(SearchType.GENRE) < filtering.indexOf(SearchType.PUBLISHER)){
                    filtering.remove(SearchType.PUBLISHER);
                }
            }
            if(!(filtering.contains(SearchType.PUBLISHER) && filtering.contains(SearchType.GENRE))){
                String selectedPublisher = filtering.contains(SearchType.PUBLISHER) && filtering.size() == 1?
                        cbVerlag.getSelectedItem().toString() :
                        "";
                skip = true;
                cbmVerlag.removeAllElements();

                try {
                    if(genre.equals("")){
                        cbmVerlag.addAll(dba.getAllPublishers());
                    }else{
                        cbmVerlag.addAll(dba.getFilteredPublishers(genre));
                    }
                    skip = true;
                    int index = 0;
                    if(filtering.contains(SearchType.PUBLISHER) && filtering.size() == 1){
                        index = cbmVerlag.getIndexOf(selectedPublisher);
                        skip = false;
                    }
                    cbVerlag.setSelectedIndex(index);
                    skip = false;
                } catch (FileNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        dlmBooks.clear();
        try {
            String verlag = "";
            if(cbVerlag.getSelectedItem() != null){
                verlag = cbVerlag.getSelectedItem().toString();
                if(verlag.equals("All")){
                    verlag = "";
                }
            }
            
            dlmBooks.addAll(dba.getFilteredBookTitles(genre, verlag, 
                    tfSearchBar.getText(), rbAuthor.isSelected() ? SearchType.AUTHOR : SearchType.BOOK));
        } catch (FileNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * handler method for any key-events (when searching)
     * @param e 
     */
    private void onSearch(DocumentEvent e){
        try {
            String searchString = tfSearchBar.getText();
            String genre = "";
            String verlag = "";
            
            if(cbVerlag.getSelectedItem() != null){
                verlag = cbVerlag.getSelectedItem().toString();
                if(verlag.equals("All")){
                    verlag = "";
                }
            }
            if(cbGenre.getSelectedItem() != null){
                genre = cbGenre.getSelectedItem().toString();
                if(genre.equals("All")){ 
                    genre = "";
                }
            }
            
            dlmBooks.clear();
            dlmBooks.addAll(dba.getFilteredBookTitles(genre, verlag,
                    searchString, rbAuthor.isSelected() ? SearchType.AUTHOR : SearchType.BOOK));
        } catch (FileNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } 
    }
    
    /**
     * Handler method for the radio-buttons
     * @param e 
     */
    private void onChangeSelection(ActionEvent e){
        try {
            String searchString = tfSearchBar.getText();
            String verlag = "";
            String genre = "";
            if(cbVerlag.getSelectedItem() != null){
               verlag = cbVerlag.getSelectedItem().toString();
                if(verlag.equals("All")){
                    verlag = "";
                } 
            }
            if(cbGenre.getSelectedItem() != null){
                genre = cbGenre.getSelectedItem().toString();
                if(genre.equals("All")){ 
                    genre = "";
                }
            }
            
            dlmBooks.clear();
            dlmBooks.addAll(dba.getFilteredBookTitles(genre, verlag,
                    searchString, rbAuthor.isSelected() ? SearchType.AUTHOR : SearchType.BOOK));
        } catch (FileNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * formats the detailed book information for the JEditorPane
     * @param book
     * @return 
     */
    private String formatHTMLString(Book book){
        String htmlString = "<html><body style=\"padding: 10px;\">";
        htmlString += "<p style=\"text-align: center; font-size: 16px;color: #B68F40; \"><b>" + book.getTitle() + "</b></p>";
        htmlString = book.getAuthors().stream().map((author) -> "<p style=\"text-align: center; font-size: 14px; color: #545454\">" + author + "</p>")
                .reduce(htmlString, String::concat);
        htmlString += "<hr>";
        if(book.getIsbnNr() != null && !book.getIsbnNr().isBlank()){
            htmlString += "<p style=\"text-align: left; font-size: 12px; color: #000; line-height: 1.1;\">" 
                + "<span style=\"color: green; font-weight: bold;\">ISBN:\t\t\t</span>"
                + book.getIsbnNr()
                + "</p>";
        }
        
        if(book.getPages() > 0){
            htmlString += "<p style=\"text-align: left; font-size: 12px; color: #000; line-height: 1.1;\">" 
                + "<span style=\"color: green; font-weight: bold;\">Seitenzahl:\t\t\t</span>"
                + book.getPages()
                + "</p>";
        }
        if(book.getGenres().size() > 0){
            htmlString += "<p style=\"text-align: left; font-size: 12px; color: #000; line-height: 1.1;\">" 
                + "<span style=\"color: green; font-weight: bold;\">Genres:\t\t\t</span>"
                + book.getGenres().stream().map((genre) -> genre).collect(Collectors.joining(", "))
                + "</p>";
        }
        if(book.getRating() > 0.0f){
            htmlString += "<p style=\"text-align: left; font-size: 12px; color: #000; line-height: 1.1;\">" 
                + "<span style=\"color: green; font-weight: bold;\">Bewertungen:\t\t\t</span>"
                + book.getRating()
                + "</p>";
        }
        if(book.getPublishedDate() != null){
            htmlString += "<p style=\"text-align: left; font-size: 12px; color: #000; line-height: 1.1;\">" 
                + "<span style=\"color: green; font-weight: bold;\">Erscheinungsdatum:\t\t\t</span>"
                + book.getPublishedDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                + "</p>";
        }
        if(book.getPublisher() != null && !book.getPublisher().isBlank()){
            htmlString += "<p style=\"text-align: left; font-size: 12px; color: #000; line-height: 1.1;\">" 
                + "<span style=\"color: green; font-weight: bold;\">Verlag:\t\t\t</span>"
                + book.getPublisher()
                + "</p>";
        }
        
        
        
        
        htmlString += "<p>" + "</p>";
        
        htmlString += "</body></html>";
        return htmlString;
    }
    
    /**
     * handler method for selecting a book title --> display detailed book information
     * @param e 
     */
    private void onSelectBook(ListSelectionEvent e){
        if(liBooks.getSelectedValue() == null){
            return;
        }
        try {
            Book book = dba.getBookDetails(liBooks.getSelectedValue().toString());
            System.out.println("TODO: " + book.toString());
            
            epBookDetails.setText(formatHTMLString(book));
            
        } catch (FileNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } 
    }
    
    /**
     * before exit -> disconnect from DB
     */
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
            JOptionPane.showMessageDialog(bookShopGUI, "Je nachdem was zuerst ausgewählt wird (Verlag|Genre), werden beim jeweiligen anderen \n"
                + "nur die zur Verfügung stehenden Elemente angezeigt. \n(zb: 1. Auswahl Verlag, "
                    + "2. es werden nur verfügbare Genres zu DIESEM Verlag angezeigt. \n[aber auch umgekehrt])\n "
                    + ".. ist als Feature und nicht als Fehler gedacht :) "
                , "Information", JOptionPane.INFORMATION_MESSAGE);
        }catch(UnsupportedOperationException e){
            e.printStackTrace();
        }
        
    }
    
    
}
