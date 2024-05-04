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
        double posunX = Double.min(this.pociatocnyUzol.getCenter().getX(), this.koncovyUzol.getCenter().getX());
        double posunY = Double.min(this.pociatocnyUzol.getCenter().getY(), this.koncovyUzol.getCenter().getY());
        g2d.drawLine((int)(this.pociatocnyUzol.getCenter().getX() - posunX), //odkial X
                (int)(this.pociatocnyUzol.getCenter().getY() - posunY), //odkial Y
                (int)(this.koncovyUzol.getCenter().getX() - posunX), //kam X
                (int)(this.koncovyUzol.getCenter().getY() - posunY)); //kam Y
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
                                (int)Math.abs(pocUzolCenter.getY() - konUzolCenter.getY())));

        setVisible(true); //nastavenie viditelnosti
        setLocation((int)Double.min(pocUzolCenter.getX(), konUzolCenter.getX()), 
                    (int)Double.min(pocUzolCenter.getY(), konUzolCenter.getY())); //nastavujeme lavy horny roh
    }
}
