/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.semestralka;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 * Hlavna aplikacia pre semestralku
 * @author namer
 */
public class Application {
    /**
     * Trieda pre konverziu siete na graf, vypocet matice vzdialenosti
     */
    private Sit281211 grafSiete;
    /**
     * Zoznam vsetkych uzlov
     */
    private ArrayList<Uzol> uzly;
    /**
     * Zoznam vsetkych hran
     */
    private ArrayList<Hrana> hrany;
    /**
     * Mod aplikacie
     */
    private Mode mode;
    /**
     * Prave otvoreny uzol
     */
    private Uzol otvorenyUzol;
    /**
     * Prave otvorena hrana
     */
    private Hrana otvorenaHrana;
    /**
     * Pociatocny uzol novovytvaranej hrany
     */
    private Uzol pocUzolHrany;
    /**
     * Koncovy uzol novovytvaranej hrany
     */
    private Uzol konUzolHrany;
    /**
     * Informacia o tom ci bola siet konvertovana na graf a graf je aj aktualny
     */
    private boolean grafVytvoreny;
    
    public Application() {
        this.uzly = new ArrayList<>();
        this.hrany = new ArrayList<>();
        this.mode = Mode.view; //default mode je view
        
        this.otvorenyUzol = null;
        this.otvorenaHrana = null;
        try {
            this.grafSiete = new Sit281211("", "", this.uzly, this.hrany, false); //vytvorenie instancie aj ked tam nic nebude to nevadi
        } catch (FileNotFoundException ex) {
            //Error nenastane lebo nacitavam z listov
        }
        this.grafVytvoreny = false;
    }
    
    /**
     * Metoda volana po kliknuti mysou
     * @param posX pozicia kliknutia mysi X
     * @param posY pozicia kliknutia mysi Y
     * @param pMainPanel hlavna obrazovka
     * @return  1 ak treba otvorit detail Uzla a repaint, 
     *          2 ak treba iba repaint,
     *          3 ak treba otvorit detail Hrany a repaint,
     *          0 inak
     */
    public int mouseClicked(int posX, int posY, JPanel pMainPanel) {
        Uzol node;
        Hrana edge;
        
        switch (this.mode) {
            case addNode:
                node = this.addNode(posX, posY);
                pMainPanel.add(node);
                this.setOtvorenyUzol(node);
                this.grafVytvoreny = false;
                return 1;
            case editNode:
                try {
                    node = (Uzol)pMainPanel.getComponentAt(posX, posY);
                    this.setOtvorenyUzol(node);
                    this.grafVytvoreny = false;
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
                        this.grafVytvoreny = false;
                        return 2;
                    }
                    else
                        return 0;
                    
                } catch (ClassCastException e) {
                    //do nothing
                    return 0;
                }
            case addEdge:
                try {
                    node = (Uzol)pMainPanel.getComponentAt(posX, posY);
                    if (this.pocUzolHrany == null) {
                        this.pocUzolHrany = node;
                        this.pocUzolHrany.setSelected(true);
                        return 2;
                    }
                    else if (this.konUzolHrany == null) {
                        this.konUzolHrany = node;
                        this.konUzolHrany.setSelected(true);
                        //vytvorit hranu
                        edge = this.addEdge();
                        pMainPanel.add(edge);
                        this.setOtvorenaHrana(edge);
                        //vycistit pociatocny a koncovy uzol hrany
                        this.pocUzolHrany.setSelected(false);
                        this.pocUzolHrany = null;
                        this.konUzolHrany.setSelected(false);
                        this.konUzolHrany = null;
                        this.grafVytvoreny = false;
                        return 3;
                    }
                    return 0;
                    
                } catch (ClassCastException e) {
                    //do nothing
                    return 0;
                }
            case editEdge:
                try {
                    edge = (Hrana)pMainPanel.getComponentAt(posX, posY);
                    this.setOtvorenaHrana(edge);
                    this.grafVytvoreny = false;
                    return 3;
                    
                } catch (ClassCastException e) {
                    //do nothing
                    return 0;
                }
            case removeEdge:
                try {
                    edge = (Hrana)pMainPanel.getComponentAt(posX, posY);
                    if (this.removeEdge(edge)) {
                        pMainPanel.remove(edge);
                        this.grafVytvoreny = false;
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
     * odstranovany uzol nesmie mat hrany
     * @param pUzol uzol na odstranenie
     * @return true ak sa odstranenie podarilo, false inak
     */
    private boolean removeNode(Uzol pUzol) {
        if (pUzol.getHrany().isEmpty()) { //uzol nesmie mat hrany ak ma byt vymazany
            return this.uzly.remove(pUzol);
        }
        return false;
    }
    
    private Hrana addEdge() {
        Hrana edge = new Hrana(true, this.pocUzolHrany, this.konUzolHrany, -1); //dlzku trasy davam na -1 aby sa uskutocnil automaticky vypocet
        this.hrany.add(edge);
        
        //Hranu pridam k uzlom ako incidentnu
        this.pocUzolHrany.addHrana(edge);
        this.konUzolHrany.addHrana(edge);
        
        return edge;
    }
    
    /**
     * Metoda pre odstranovanie hran zo siete
     * odstranovana hrana sa bezpecne odstrani aj so zoznamov z uzlov
     * @param pHrana hrana na odstranenie
     * @return true ak sa odstranenie podarilo, false inak
     */
    private boolean removeEdge(Hrana pHrana) {
        if (this.hrany.remove(pHrana)) {
            pHrana.getPociatocnyUzol().getHrany().remove(pHrana);
            pHrana.getKoncovyUzol().getHrany().remove(pHrana);
            return true;
        }
        return false;           
    }
    
    /**
     * Metoda na nacitanie dat zo zuboru
     * @param filePatghNodes cesta k suboru uzlov
     * @param filePathEdges cesta k suboru hran
     * @param pMainPanel hlavna obrazovka
     * @throws java.io.FileNotFoundException
     */
    public void loadData(String filePatghNodes, String filePathEdges, JPanel pMainPanel) throws FileNotFoundException {
        this.grafSiete.loadDataFromFiles(filePatghNodes, filePathEdges, this.uzly, this.hrany);
        //Teraz prejdeme vsetky uzly a hrany a pridame ich na hlavnu obrazovku
        for (Uzol uzol : this.uzly) {
            pMainPanel.add(uzol);
        }
        for (Hrana hrana : this.hrany) {
            pMainPanel.add(hrana);
        }
        this.grafVytvoreny = true;
    }
    
    /**
     * Metoda pre ukladanie siete do suborov
     * @param filePathDirectory cesta do priecinka kde sa ulozia data
     * @throws java.io.IOException
     */
    public void saveData(String filePathDirectory) throws IOException {
        String filePathNodes = filePathDirectory + "\\nodes.txt";
        String filePathEdges = filePathDirectory + "\\edges.txt";
        
        Uzol tempUzol = null;
        Hrana tempHrana = null;
        
        FileWriter nodeWriter = new FileWriter(filePathNodes);
        FileWriter edgeWriter = new FileWriter(filePathEdges);
        
        //Zapis uzlov
        nodeWriter.write(this.uzly.size() + "\n"); //pocet uzlov na zaciatku suboru
        for (int i = 0; i < this.uzly.size(); i++) {
            tempUzol = this.uzly.get(i);
            nodeWriter.write((i + 1) + " " +
                    tempUzol.getKapacita() + " " + 
                    tempUzol.getTypUzla().convertToSave()+ " " +
                    tempUzol.getX() + " " +
                    tempUzol.getY() + " " +
                    "\"" + tempUzol.getNazov() + "\"" + "\n");
            
        }
        nodeWriter.close();
        
        //Zapis hran
        edgeWriter.write(this.hrany.size() + "\n"); //pocet hran na zaciatku suboru
        for (int i = 0; i < this.hrany.size(); i++) {
            tempHrana = this.hrany.get(i);
            edgeWriter.write((this.uzly.indexOf(tempHrana.getPociatocnyUzol())+1) + " " +
                    (this.uzly.indexOf(tempHrana.getKoncovyUzol())+1) + " " +
                    tempHrana.getDlzkaTrasy() + " " +
                    tempHrana.isHranaPovolena() + "\n");
            
        }
        edgeWriter.close();
    }
    
    /**
     * Metoda pre konverziu editovanej siete na graf v ktorom uz bude mozne 
     * vyhladavat najkratsiu cestu
     */
    public void convertToGraph() {
        //Iba presunieme data do triedy pre vypocet vzdialenosti a podobne 
        this.grafSiete.loadDataFromLists(this.uzly, this.hrany);
        this.grafVytvoreny = true;
    }
    
    /**
     * Metoda pre vykonanie kontroly siete
     * @return true ak kontrola siete dopadla v poriadku, false inak
     */
    public boolean checkNetwork() {
        boolean checkOK = true;
        int numOfNodes = this.uzly.size();
        int[] nodeIDs = new int[numOfNodes];
        for (int i = 0; i < nodeIDs.length; i++) {
            nodeIDs[i] = i+1;            
        }
        int[][] D = new int[numOfNodes][numOfNodes];
        if (!this.grafVytvoreny) { //ak nie je vytvoreny graf tak ho musim vytvorit
            this.convertToGraph();
        }
        this.grafSiete.GetMatrixI(numOfNodes, numOfNodes, nodeIDs, nodeIDs, D); //ziskam celu maticu vzdialenosti medzi vsetkymi uzlami
        //samotna kontrola matice vzdialenosti
        for (int i = 0; i < D.length; i++) { //prechadzam riadky matice
            for (int j = 0; j < D[i].length; j++) { //prechadzam stlpce matice
                if (D[i][j] == Integer.MAX_VALUE) { //znamena ze do toho nodu sa nedostanem
                    checkOK = false;
                    break;
                }
            }
            if (!checkOK) { //ak uz check nie je OK tak nemusim dalej kontrolovat
                break;
            }
        }
        return checkOK;
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
     * zmena modu zresetuje nastavene vrcholy pre tvorbu hrany 
     * @param mode novy mod aplikacie
     */
    public void setMode(Mode mode) {
        this.pocUzolHrany = null;
        this.konUzolHrany = null;
        //nastavenie samotneho modu
        this.mode = mode;
    }

    /**
     * Getter pre prave otvorenu hranu
     * @return prave otvorena hrana
     */
    public Hrana getOtvorenaHrana() {
        return this.otvorenaHrana;
    }
    
    /**
     * Setter pre prave otvorenu hranu
     * @param otvorenaHrana prave otvorena hrana
     */
    public void setOtvorenaHrana(Hrana otvorenaHrana) {
        this.otvorenaHrana = otvorenaHrana;
    }
    
    
        
}
