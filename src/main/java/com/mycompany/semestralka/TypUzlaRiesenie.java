/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.semestralka;

/**
 * Enum pre typ uzla v sieti vramci riesenia
 * @author namer
 */
public enum TypUzlaRiesenie {
    Zakaznik,
    Sklad,
    BezSpecifikacie;
    
    /**
     * Metoda pre konverziu tohto datoveho typu pre ukladanie
     * @return int hodnotu pre ukladanie
     */
    public int convertToSave() {
        switch (this) {
            case Sklad:
                return 1;
            case Zakaznik:
                return 2;
            default: //BezSpecifikacie
                return 0;
        }
    }
    
    /**
     * Metoda pre nacitavanie daneho datoveho typu z ulozeneho suboru
     * @param pTypUzlaRiesenie 
     * @return 
     */
    public static TypUzlaRiesenie convertAtLoad(int pTypUzlaRiesenie) {
        switch (pTypUzlaRiesenie) {
            case 1:
                return TypUzlaRiesenie.Sklad;
            case 2:
                return TypUzlaRiesenie.Zakaznik;
            default:
                return TypUzlaRiesenie.BezSpecifikacie;
        }
    }
}
