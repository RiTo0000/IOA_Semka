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
 *
 * @author namer
 */
public class Hrana extends JComponent {
    
    private double dlzkaTrasy;
    private boolean hranaPovolena;
    private Uzol pociatocnyUzol;
    private Uzol koncovyUzol;
    
    private Line2D.Double lineToDraw;
    
    public Hrana(boolean pHranaPovolena, Uzol pPociatocnyUzol, Uzol pKoncovyUzol) {
        this.hranaPovolena = pHranaPovolena;
        this.pociatocnyUzol = pPociatocnyUzol;
        this.koncovyUzol = pKoncovyUzol;
        
        this.autoCalculateLength(); //automaticky vyratat dlzku trasy pri vytvarani hrany
        
        this.setDefault();
    }
    
    /**
     * Metoda pre automaticky vypocet dlzky hrany pomocou Euklidovskej vzdialenosti
     */
    public void autoCalculateLength() {
        Point pocUzolCenter = this.pociatocnyUzol.getCenter();
        Point konUzolCenter = this.koncovyUzol.getCenter();
        this.dlzkaTrasy = Math.sqrt(Math.pow(Math.abs(pocUzolCenter.getX() - konUzolCenter.getX()), 2) + 
                                    Math.pow(Math.abs(pocUzolCenter.getY() - konUzolCenter.getY()), 2));
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
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
    public double getDlzkaTrasy() {
        return this.dlzkaTrasy;
    }
    
    /**
     * Setter pre dlzku trasy
     * @param dlzkaTrasy nova dlzka trasy
     */
    public void setDlzkaTrasy(double dlzkaTrasy) {
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
    
    
    
    
}
