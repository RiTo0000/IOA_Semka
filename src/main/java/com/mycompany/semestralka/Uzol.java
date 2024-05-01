/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.semestralka;

/**
 *
 * @author namer
 */
public class Uzol {
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
    
    public Uzol(String pNazov, TypUzla pTypUzla, double pKapacita) {
        this.nazov = pNazov;
        this.typUzla = pTypUzla;
        this.kapacita = pKapacita;
    }
}
