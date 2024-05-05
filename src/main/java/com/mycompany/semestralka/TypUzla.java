/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.semestralka;

/**
 *
 * @author namer
 */
public enum TypUzla {
    PrimarnyZdroj,
    Zakaznik,
    MozneUmiestnenieSkladu,
    BezSpecifikacie;
    
    public int convertToSave() {
        switch (this) {
            case PrimarnyZdroj:
                return 1;
            case Zakaznik:
                return 2;
            case MozneUmiestnenieSkladu:
                return 3;
            default: //BezSpecifikacie
                return 0;
        }
    }
    
    public static TypUzla convertAtLoad(int pTypUzla) {
        switch (pTypUzla) {
            case 1:
                return TypUzla.PrimarnyZdroj;
            case 2:
                return TypUzla.Zakaznik;
            case 3:
                return TypUzla.MozneUmiestnenieSkladu;
            default:
                return TypUzla.BezSpecifikacie;
        }
    }
}
