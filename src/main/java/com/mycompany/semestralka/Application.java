/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.semestralka;

import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * Hlavna aplikacia pre semestralku
 * @author namer
 */
public class Application {
    
    private ArrayList<Uzol> uzly;
    private Mode mode;
    
    private Uzol otvorenyUzol;
    
    public Application() {
        this.uzly = new ArrayList<>();
        this.mode = Mode.view; //default mode je view
        
        this.otvorenyUzol = null;
    }
    
    /**
     * Metoda volana po kliknuti mysou
     * @param posX pozicia kliknutia mysi X
     * @param posY pozicia kliknutia mysi Y
     * @param pMainPanel hlavna obrazovka
     * @return  1 ak treba otvorit detail Uzla a repaint, 
     *          2 ak treba iba repaint,
     *          0 inak
     */
    public int mouseClicked(int posX, int posY, JPanel pMainPanel) {
        Uzol node;
        switch (this.mode) {
            case addNode:
                node = this.addNode(posX, posY);
                pMainPanel.add(node);
                this.setOtvorenyUzol(node);
                return 1;
            case editNode:
                try {
                    node = (Uzol)pMainPanel.getComponentAt(posX, posY);
                    this.setOtvorenyUzol(node);
                    return 1;
                    
                } catch (ClassCastException e) {
                    //do nothing
                    return 0;
                }
            case removeNode:
                try {
                    node = (Uzol)pMainPanel.getComponentAt(posX, posY);
                    if (this.removeNode(node)) {
                        pMainPanel.remove(node);
                        return 2;
                    }
                    else
                        return 0;
                    
                } catch (ClassCastException e) {
                    //do nothing
                    return 0;
                }
            default:
                return 0;
        }
    }
    
    /**
     * Metoda pre pridanie uzla
     * @param posX pozicia uzla X
     * @param posY pozicia uzla Y
     * @return vrati vytvoreny uzol
     */
    private Uzol addNode(int posX, int posY) {
        //Pridanie samotneho uzla s default hodnotami
        Uzol node = new Uzol("", TypUzla.BezSpecifikacie, 0, posX, posY);
        this.uzly.add(node);
        
        return node;
    }
    
    /**
     * Metoda pre odstranovanie uzlov zo siete
     * @param pUzol uzol na odstranenie
     * @return true ak sa odstranenie podarilo, false inak
     */
    private boolean removeNode(Uzol pUzol) {
        return this.uzly.remove(pUzol);
    }
    
    //Gettre/Settre
    
    /**
     * Getter pre zoznam uzlov
     * @return vsetky uzly
     */
    public ArrayList<Uzol> getUzly() {
        return this.uzly;
    }
    
    /**
     * Getter pre mod v ktorom sa aplikacia nachadza
     * @return aktualny mod aplikacie
     */
    public Mode getMode() {
        return this.mode;
    }
    
    /**
     * Getter pre prave otvoreny uzol
     * @return prave otvoreny uzol
     */
    public Uzol getOtvorenyUzol() {
        return this.otvorenyUzol;
    }
    
    /**
     * Setter pre prave otvoreny uzol
     * @param otvorenyUzol prave otvoreny uzol
     */
    public void setOtvorenyUzol(Uzol otvorenyUzol) {
        this.otvorenyUzol = otvorenyUzol;
    }

    /**
     * Setter pre mod v ktorom sa aplikacia nachadza
     * @param mode novy mod aplikacie
     */
    public void setMode(Mode mode) {
        this.mode = mode;
    }
    
    
        
}
