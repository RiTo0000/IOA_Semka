/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.semestralka;

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
public class Uzol extends JComponent {
    public final int defaultWidth = 20;
    /**
     * Nazov uzla
     */
    private String nazov;
    /**
     * Kapacita resp. poziadavka uzla
     */
    private double kapacita;
    /**
     * Typ uzla
     */
    private TypUzla typUzla;
    
    /**
     * Priznak oznaceneho uzla
     */
    private boolean selected;
    
    public Uzol(String pNazov, TypUzla pTypUzla, double pKapacita, int pX, int pY) {
        this.nazov = pNazov;
        this.typUzla = pTypUzla;
        this.kapacita = pKapacita;
        
        //potrebne pre jeho zobrazenie
        setSize(new Dimension(defaultWidth, defaultWidth));
        
        setVisible(true); //nastavenie viditelnosti
        setLocation(pX-defaultWidth/2, pY-defaultWidth/2); //nastavenie umiestnenia
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Common.getNodeColor(this));
        g.fillOval(0, 0, defaultWidth, defaultWidth); //uvadzam poziciu vramci objektu preto zacinam na 0, 0
    }

    public String getNazov() {
        return this.nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public double getKapacita() {
        return this.kapacita;
    }

    public void setKapacita(double kapacita) {
        this.kapacita = kapacita;
    }

    public TypUzla getTypUzla() {
        return this.typUzla;
    }

    public void setTypUzla(TypUzla typUzla) {
        this.typUzla = typUzla;
    }
    
    public Point getCenter() {
        return new Point(this.getX()+defaultWidth/2, this.getY()+defaultWidth/2);
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    
    
}
