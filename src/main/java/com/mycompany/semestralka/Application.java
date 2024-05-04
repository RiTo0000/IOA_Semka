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
        this.mode = Mode.addNode; //TODO malo by tam byt normalne view ako default
        
        this.otvorenyUzol = null;
    }
    
    /**
     * Metoda volana po kliknuti mysou
     * @param posX pozicia kliknutia mysi X
     * @param posY pozicia kliknutia mysi Y
     * @param pMainPanel hlavna obrazovka
     * @return true ak treba prekreslit obrazovku
     */
    public boolean mouseClicked(int posX, int posY, JPanel pMainPanel) {
        Uzol node;
        switch (this.mode) {
            case addNode:
                node = this.addNode(posX, posY);
                pMainPanel.add(node);
                return true;
            case view:
                node = (Uzol)pMainPanel.getComponentAt(posX, posY); //TODO kontrola na castovanie
                return false;
            default:
                return false;
        }
    }
    
    /**
     * Metoda pre pridanie uzla
     * @param posX pozicia uzla X
     * @param posY pozicia uzla Y
     * @return vrati vytvoreny uzol
     */
    public Uzol addNode(int posX, int posY) {
        //Pridanie samotneho uzla s default hodnotami
        Uzol node = new Uzol("", TypUzla.BezSpecifikacie, 0, posX, posY);
        this.uzly.add(node);
        
        return node;
    }
    
    public void changeMode() { //TODO spravit tak aby bol vstupom mode na ktory to chceme zmenit
        if (this.mode == Mode.addNode) {
            this.mode = Mode.view;
        }
        else
            this.mode = Mode.addNode;
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
        
}
