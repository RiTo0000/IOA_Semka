/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.semestralka;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import javax.swing.JComponent;

/**
 * Trieda hrany pre siet
 * @author namer
 */
public class Hrana extends JComponent {
    /**
     * Dlzka hrany (jej ohodnotenie)
     */
    private int dlzkaTrasy;
    /**
     * Informacia ci je hrana povolena
     */
    private boolean hranaPovolena;
    /**
     * Referencia na pociatocny uzol hrany
     */
    private Uzol pociatocnyUzol;
    /**
     * Referencia na koncovy uzol hrany
     */
    private Uzol koncovyUzol;
    /**
     * Objekt ciary ktora je vykreslovana a podla ktorej sa aj rata ci na nu uzivatel klikol
     */
    private Line2D.Double lineToDraw;
    /**
     * True ak je hrana sucast riesenia
     */
    private boolean hranaSucastRiesenia;
    
    public Hrana(boolean pHranaPovolena, Uzol pPociatocnyUzol, Uzol pKoncovyUzol, int pDlzkaTrasy, boolean pHranaSucastRiesenia) {
        this.hranaPovolena = pHranaPovolena;
        this.pociatocnyUzol = pPociatocnyUzol;
        this.koncovyUzol = pKoncovyUzol;
        this.hranaSucastRiesenia = pHranaSucastRiesenia; 
        
        if (pDlzkaTrasy == -1) { //pri vytvarani uzivatelom v aplikacii tam davame -1 aby automaticky vyratalo
            this.autoCalculateLength(); //automaticky vyratat dlzku trasy pri vytvarani hrany
        }
        else
            this.dlzkaTrasy = pDlzkaTrasy;
        
        this.setDefault();
    }
    
    /**
     * Metoda pre automaticky vypocet dlzky hrany pomocou Euklidovskej vzdialenosti
     */
    public void autoCalculateLength() {
        Point pocUzolCenter = this.pociatocnyUzol.getCenter();
        Point konUzolCenter = this.koncovyUzol.getCenter();
        this.dlzkaTrasy = (int)Math.sqrt(Math.pow(Math.abs(pocUzolCenter.getX() - konUzolCenter.getX()), 2) + 
                                    Math.pow(Math.abs(pocUzolCenter.getY() - konUzolCenter.getY()), 2));
    }
    
    /**
     * Metoda pre vykreslovanie objektu
     * @param g 
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Common.getEdgeColor(this));
        g2d.setStroke(new BasicStroke(4f)); //sirka lajny
        g2d.draw(this.lineToDraw);
    }
    
    /**
     * Metoda sluzi na identifikaciu ci mys ukazuje na dany objekt
     * @param x pozicia na osi X
     * @param y pozicia na osi Y
     * @return true ak mys ukazuje na komponent, false inak
     */
    @Override
    public boolean contains(int x, int y) {
        return this.lineToDraw.ptSegDist(x, y) < 5; // 
    }
    
    /**
     * Metoda pre nastavenie defaultnych hodnot potrebnych 
     * pre spravne zobrazovanie / fungovanie objektu
     */
    private void setDefault() {
        Point pocUzolCenter = this.pociatocnyUzol.getCenter();
        Point konUzolCenter = this.koncovyUzol.getCenter();
        //potrebne pre jeho zobrazenie
        setSize(new Dimension((int)Math.abs(pocUzolCenter.getX() - konUzolCenter.getX()),
                                (int)Math.abs(pocUzolCenter.getY() - konUzolCenter.getY()))); //velkost je jeho boundingBox prakticky

        setVisible(true); //nastavenie viditelnosti
        setLocation((int)Double.min(pocUzolCenter.getX(), konUzolCenter.getX()), 
                    (int)Double.min(pocUzolCenter.getY(), konUzolCenter.getY())); //nastavujeme lavy horny roh
        
        calculateLine(); //ciaru si pripravim takto aby sa lahko vyhodnocovalo ci som blizko nej
    }
    
    /**
     * Metoda pre vypocitanie ciary medzi danymi bodmi
     */
    private void calculateLine() {
        double posunX = Double.min(this.pociatocnyUzol.getCenter().getX(), this.koncovyUzol.getCenter().getX());
        double posunY = Double.min(this.pociatocnyUzol.getCenter().getY(), this.koncovyUzol.getCenter().getY());
        this.lineToDraw = new Line2D.Double((this.pociatocnyUzol.getCenter().getX() - posunX), //odkial X
                (this.pociatocnyUzol.getCenter().getY() - posunY), //odkial Y
                (this.koncovyUzol.getCenter().getX() - posunX), //kam X
                (this.koncovyUzol.getCenter().getY() - posunY)); //kam Y
    }
    
    /**
     * Getter pre dlzku trasy
     * @return dlzka trasy
     */
    public int getDlzkaTrasy() {
        return this.dlzkaTrasy;
    }
    
    /**
     * Setter pre dlzku trasy
     * @param dlzkaTrasy nova dlzka trasy
     */
    public void setDlzkaTrasy(int dlzkaTrasy) {
        this.dlzkaTrasy = dlzkaTrasy;
    }
    
    /**
     * Getter pre info ci je hrana povolena
     * @return true ak je hrana povolena, false inak
     */
    public boolean isHranaPovolena() {
        return this.hranaPovolena;
    }

    /**
     * Setter pre info ci je hrana povolena
     * @param hranaPovolena nova hodnota pre info ci je hrana povolena
     */
    public void setHranaPovolena(boolean hranaPovolena) {
        this.hranaPovolena = hranaPovolena;
    }
    
    /**
     * Getter pre pociatocny uzol hrany
     * @return pociatocny uzol hrany
     */
    public Uzol getPociatocnyUzol() {
        return this.pociatocnyUzol;
    }

    /**
     * Getter pre koncovy uzol hrany
     * @return koncovy uzol hrany
     */
    public Uzol getKoncovyUzol() {
        return this.koncovyUzol;
    }

    /**
     * Getter pre informaciu ci je hrana sucastou riesenia
     * @return informaciu ci je hrana sucastou riesenia
     */
    public boolean isHranaSucastRiesenia() {
        return this.hranaSucastRiesenia;
    }

    /**
     * Setter pre informaciu ci je hrana sucastou riesenia
     * @param hranaSucastRiesenia 
     */
    public void setHranaSucastRiesenia(boolean hranaSucastRiesenia) {
        this.hranaSucastRiesenia = hranaSucastRiesenia;
    }
       
    
}
