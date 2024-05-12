/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.semestralka;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
 * Trieda uzla pre siet
 * @author namer
 */
public class Uzol extends JComponent {
    /**
     * Sirka vykreslovaneho objektu (kruhu)
     */
    public final int defaultWidth = 20;
    /**
     * Nazov uzla
     */
    private String nazov;
    /**
     * Poziadavka uzla
     */
    private int poziadavka;
    /**
     * Typ uzla
     */
    private TypUzla typUzla;
    
    /**
     * Priznak oznaceneho uzla
     */
    private boolean selected;
    
    /**
     * Hrany ktore su napojene na tento uzol
     */
    private ArrayList<Hrana> hrany;
    
    /**
     * Cena za vybudovanie strediska v danom uzle
     */
    private int cenaZaVybudovanieStrediska;
    
    /**
     * Typ uzla vramci riesenia
     */
    private TypUzlaRiesenie typUzlaRiesenie;
    
    public Uzol(String pNazov, TypUzla pTypUzla, int pPoziadavka, int pX, int pY, int pCenaZaVybudovanieStrediska, TypUzlaRiesenie pTypUzlaRiesenie) {
        this.nazov = pNazov;
        this.typUzla = pTypUzla;
        this.poziadavka = pPoziadavka;
        this.hrany = new ArrayList<>();
        this.cenaZaVybudovanieStrediska = pCenaZaVybudovanieStrediska;
        this.typUzlaRiesenie = pTypUzlaRiesenie;
        
        //potrebne pre jeho zobrazenie
        setSize(new Dimension(defaultWidth, defaultWidth));
        
        setVisible(true); //nastavenie viditelnosti
        setLocation(pX-defaultWidth/2, pY-defaultWidth/2); //nastavenie umiestnenia
    }
    
    /**
     * Metoda pre vykreslenie objektu
     * @param g 
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Common.getNodeColor(this));
        g.fillOval(0, 0, defaultWidth, defaultWidth); //uvadzam poziciu vramci objektu preto zacinam na 0, 0
    }

    /**
     * Getter pre nazov uzla
     * @return nazov uzla
     */
    public String getNazov() {
        return this.nazov;
    }
    
    /**
     * Setter pre nazov uzla
     * @param nazov novy nazov uzla
     */
    public void setNazov(String nazov) {
        this.nazov = nazov;
    }
    
    /**
     * Getter pre poziadavku uzla
     * @return poziadavka uzla
     */
    public int getPoziadavka() {
        return this.poziadavka;
    }
    
    /**
     * Setter pre poziadavku uzla
     * @param poziadavka nova poziadavka uzla
     */
    public void setPoziadavka(int poziadavka) {
        this.poziadavka = poziadavka;
    }

    /**
     * Getter pre typ uzla
     * @return typ uzla
     */
    public TypUzla getTypUzla() {
        return this.typUzla;
    }

    /**
     * Setter pre typ uzla
     * @param typUzla novy typ uzla
     */
    public void setTypUzla(TypUzla typUzla) {
        this.typUzla = typUzla;
    }
    
    /**
     * Metoda pre ziskanie centra objektu uzla
     * @return bod centra objektu 
     */
    public Point getCenter() {
        return new Point(this.getX()+defaultWidth/2, this.getY()+defaultWidth/2);
    }

    /**
     * Getter pre informaciu ci je uzol oznaceny alebo vybraty
     * @return informaciu ci je uzol oznaceny alebo vybraty
     */
    public boolean isSelected() {
        return this.selected;
    }

    /**
     * Setter pre informaciu ci je uzol oznaceny alebo vybraty
     * @param selected nova hodnota pre informaciu ci je uzol oznaceny alebo vybraty
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    /**
     * Getter pre hrany napojene na tento uzol
     * @return hrany napojene na tento uzol
     */
    public ArrayList<Hrana> getHrany() {
        return this.hrany;
    }
    
    /**
     * Metoda pre pridanie incidentnej hrany s danym uzlom
     * @param hrana hrana na pridanie
     */
    public void addHrana(Hrana hrana) {
        this.hrany.add(hrana);
    }
    
    /**
     * Getter pre cenu za vybudovanie strediska v danom uzle
     * @return cena za vybudovanie strediska v danom uzle
     */
    public int getCenaZaVybudovanieStrediska() {
        return this.cenaZaVybudovanieStrediska;
    }

    /**
     * Setter pre cenu za vybudovanie strediska v danom uzle
     * @param cenaZaVybudovanieStrediska nova cena za vybudovanie strediska v danom uzle
     */
    public void setCenaZaVybudovanieStrediska(int cenaZaVybudovanieStrediska) {
        this.cenaZaVybudovanieStrediska = cenaZaVybudovanieStrediska;
    }

    /**
     * Getter pre typ uzla riesenia
     * @return typ uzla riesenia
     */
    public TypUzlaRiesenie getTypUzlaRiesenie() {
        return this.typUzlaRiesenie;
    }
    
    /**
     * Setter pre typ uzla riesenie
     * @param typUzlaRiesenie 
     */
    public void setTypUzlaRiesenie(TypUzlaRiesenie typUzlaRiesenie) {
        this.typUzlaRiesenie = typUzlaRiesenie;
    }
        
}
