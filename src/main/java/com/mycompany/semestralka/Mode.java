/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.semestralka;

/**
 *
 * @author namer
 */
public enum Mode {
    addNode,
    editNode,
    removeNode,
    addEdge,
    editEdge,
    removeEdge,
    view;
    
    @Override
    public String toString() {
        switch (this) {
            case addNode:
                return "Mod pridavania uzlov";
            case editNode:
                return "Mod editacie uzlov";
            case removeNode:
                return "Mod mazania uzlov";
            case addEdge:
                return "Mod pridavania hran";
            case editEdge:
                return "Mod editacie hran";
            case removeEdge:
                return "Mod mazania hran";
            case view:
                return "Mod prehliadania siete";
            default:
                return "Neznamy mod";
        }
    }
}
