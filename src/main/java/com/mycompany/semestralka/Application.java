/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.semestralka;

import java.util.ArrayList;

/**
 * Hlavna aplikacia pre semestralku
 * @author namer
 */
public class Application {
    
    private ArrayList<Uzol> uzly;
    
    public Application() {
        this.uzly = new ArrayList<>();
    }

    
    /**
     * Getter pre zoznam uzlov
     * @return vsetky uzly
     */
    public ArrayList<Uzol> getUzly() {
        return this.uzly;
    }

}
