/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.semestralka;

import java.awt.Color;

/**
 *
 * @author namer
 */
public class Common {
    
    public static Color getNodeColor(Uzol node) {
        if (node.isSelected()) {
            return Color.GREEN;
        }
        else
            return Color.RED;
    }
}
