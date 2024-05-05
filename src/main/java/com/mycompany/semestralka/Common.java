/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.semestralka;

import java.awt.Color;

/**
 * Trieda s common funkcionalitou
 * @author namer
 */
public class Common {
    
    /**
     * Metoda pre nacitanie spravnej farby pre vykreslenie uzla
     * @param node uzol pre ktory sa farba nacitava
     * @return farba uzla pre vykreslenie
     */
    public static Color getNodeColor(Uzol node) {
        if (node.isSelected()) {
            return Color.GREEN;
        }
        else
            return Color.RED;
    }
}
