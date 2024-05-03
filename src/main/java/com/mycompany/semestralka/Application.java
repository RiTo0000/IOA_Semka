/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.semestralka;

import java.awt.Container;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * Hlavna aplikacia pre semestralku
 * @author namer
 */
public class Application {
    
    private ArrayList<Uzol> uzly;
    private Mode mode;
    
    public Application() {
        this.uzly = new ArrayList<>();
        this.mode = Mode.addNode; //TODO malo by tam byt normalne view ako default
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
    
    //Gettre/Settre
    
    /**
     * Getter pre zoznam uzlov
     * @return vsetky uzly
     */
    public ArrayList<Uzol> getUzly() {
        return this.uzly;
    }

    public Mode getMode() {
        return this.mode;
    }
    
}
