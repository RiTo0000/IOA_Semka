package com.mycompany.semestralka;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Write a description of class Sit281211 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Sit281211 {
    // instance variables - replace the example below with your own

    /**
     * Total number of nodes
     */    
    private int Tnn;
    
    /**
     * IdUzl - Node`s number
     */
    private int[] Nn;
    
    /**
     * Node`s kapacity
     */
    private int[] Ni;
    
    /**
     * Node`s name
     */
    private String[] Na;
    
    /**
     * Total number of edges
     */
    private int Tne;
    
    /**
     * Edge`s beginning
     */
    private int[] Eb;
    
    /**
     * Edge`s end
     */
    private int[] Ee;
    
    /**
     * Edge`s length
     */
    private int[] El;
    
    // Array for Star
    
    /** 
     * Od - Array "From" i.e. pointer to the array Ts, where list of incidental nodes begins
     */
    int[] Fs;  
    
    /**
     * PocInUsk - Number of incidental edges
     */
    int[] Is;  
    
    /**
     * Kam - Lists of incidental nodes
     */
    int[] Ts;  
    
    /**
     * Kolik - Lengths of connecting edges
     */
    int[] Hs; 
    
    private PrQueue Q; // Declaration of object of prior queue
        
    /**
     * Constructor for objects of class Sit281211
     * 
     * @param JmnNodes Názov súboru s vrcholmi.
     * @param JmnEdges Názov súboru s hranami.
     * @param pNodes Zoznam uzlov (pri nacitavani so suboru sa sem zapisu informacie o nacitanych uzloch)
     * @param pEdges Zoznam hran (pri nacitavani so suboru sa sem zapisu informacie o nacitanych hranach)
     * @param pLoadFromFile true ak sa ma nacitavanie vykonat so suboru, false ak z prilozenych zoznamov
     * @throws FileNotFoundException 
     * 
     * <br><br>
     * Načíta dopravnú sieť a vytvorí v poliach štruktúru doprednej hviezdy.
     */
    public Sit281211(String JmnNodes, String JmnEdges, ArrayList<Uzol> pNodes, ArrayList<Hrana> pEdges, boolean pLoadFromFile) throws FileNotFoundException {
        if (pLoadFromFile) 
            this.loadDataFromFiles(JmnNodes, JmnEdges, pNodes, pEdges);
        else
            this.loadDataFromLists(pNodes, pEdges);
    } // End of constructor Sit281211
    
    /**
     * Metoda pre nacitanie dopravnej siete zo suboru
     * 
     * @param JmnNodes Názov súboru s vrcholmi.
     * @param JmnEdges Názov súboru s hranami.
     * @param pNodes Zoznam uzlov (sem sa zapisu informacie o nacitanych uzloch)
     * @param pEdges Zoznam hran (sem sa zapisu informacie o nacitanych hranach)
     * @throws FileNotFoundException 
     * 
     * <br><br>
     * Načíta dopravnú sieť a vytvorí v poliach štruktúru doprednej hviezdy.
     */
    public void loadDataFromFiles(String JmnNodes, String JmnEdges, ArrayList<Uzol> pNodes, ArrayList<Hrana> pEdges) throws FileNotFoundException {
        // Reading network sizes
        Scanner scn = new Scanner(new File(JmnNodes));
        Tnn = scn.nextInt();
        Scanner sce = new Scanner(new File(JmnEdges));
        Tne = sce.nextInt();
        // Declaration of input arrays
        Nn = new int[Tnn];
        Ni = new int[Tnn];
        Na = new String[Tnn];
        Eb = new int[Tne];
        Ee = new int[Tne]; 
        El = new int[Tne];
        // Reading from nodes.txt using Scanner
        for (int i = 0; i < Tnn; i++) {
            Nn[i] = scn.nextInt(); //ID uzla
            Ni[i] = scn.nextInt(); //kapacita / poziadavka
            Na[i] = scn.next(); //nazov uzla
            //TODO nacitavanie typu uzla
            //TODO nacitavanie suradnic uzla
            pNodes.add(new Uzol(Na[i], TypUzla.BezSpecifikacie, Ni[i], -1, -1)); //TODO nahradit -1 za realne values nacitane zo suboru
        }
        scn.close();
        // Reading from edges.txt using Scanner
        for (int i = 0; i < Tne; i++) {
            Eb[i] = sce.nextInt(); //ID pociatocneho uzla
            Ee[i] = sce.nextInt(); //ID koncoveho uzla
            El[i] = sce.nextInt(); //dlzka trasy
            //TODO nacitavanie ci je povolena alebo nie ta trasa
            pEdges.add(new Hrana(true, pNodes.get(Eb[i]), pNodes.get(Ee[i])));
        }                
        sce.close();
        // Declaration of arrays for stars
        Fs = new int[Tnn];
        Is = new int[Tnn];
        Ts = new int[2 * Tne];
        Hs = new int[2 * Tne];
        //Filling arrays with star data
        int[] P = new int[Tnn];
        //Initialization of Is with zeros
        for (int i = 0; i < Tnn; i++) {
            Is[i] = 0;
        }
        // Search over Eb and Ee and counting occurence of individual nodes numbered from 1 to Tnn
        for (int i = 0; i < Tne; i++) {
            Is[Eb[i] - 1]++;
            Is[Ee[i] - 1]++;
        }
        // Filling Fs and initialization of P
        Fs[0] = 0;
        P[0] = Fs[0];
        for (int i = 1; i < Tnn; i++) {
            Fs[i] = Fs[i - 1] + Is[i - 1];
            P[i] = Fs[i];
        }
        for (int i = 0; i < Tne; i++) {
            Ts[P[Eb[i] - 1]] = Ee[i] - 1;
            Hs[P[Eb[i] - 1]] = El[i];
            P[Eb[i] - 1]++;
            Ts[P[Ee[i] - 1]] = Eb[i] - 1;
            Hs[P[Ee[i] - 1]] = El[i];
            P[Ee[i] - 1]++;
        }

        Q = new PrQueue(Tnn); // The object prior queue is created
    }
    
    /**
     * Metoda pre nacitanie dopravnej siete zo zoznamov
     * 
     * @param pNodes Zoznam uzlov 
     * @param pEdges Zoznam hran 
     * 
     * <br><br>
     * Načíta dopravnú sieť a vytvorí v poliach štruktúru doprednej hviezdy.
     */
    public void loadDataFromLists(ArrayList<Uzol> pNodes, ArrayList<Hrana> pEdges) {
        //TODO prejst a vytvorit nacitavanie zo zoznamov
        // Reading network sizes
//        Scanner scn = new Scanner(new File(JmnNodes));
//        Tnn = scn.nextInt();
//        Scanner sce = new Scanner(new File(JmnEdges));
//        Tne = sce.nextInt();
//        // Declaration of input arrays
//        Nn = new int[Tnn];
//        Ni = new int[Tnn];
//        Na = new String[Tnn];
//        Eb = new int[Tne];
//        Ee = new int[Tne]; 
//        El = new int[Tne];
//        // Reading from edges.txt using Scanner
//        for (int i = 0; i < Tne; i++) {
//            Eb[i] = sce.nextInt();
//            Ee[i] = sce.nextInt();
//            El[i] = sce.nextInt();
//        }                
//        sce.close();
//        // Reading from nodes.txt using Scanner
//        for (int i = 0; i < Tnn; i++) {
//            Nn[i] = scn.nextInt();
//            Ni[i] = scn.nextInt();
//            Na[i] = scn.next();
//        }
//        scn.close();
//        // Declaration of arrays for stars
//        Fs = new int[Tnn];
//        Is = new int[Tnn];
//        Ts = new int[2 * Tne];
//        Hs = new int[2 * Tne];
//        //Filling arrays with star data
//        int[] P = new int[Tnn];
//        //Initialization of Is with zeros
//        for (int i = 0; i < Tnn; i++) {
//            Is[i] = 0;
//        }
//        // Search over Eb and Ee and counting occurence of individual nodes numbered from 1 to Tnn
//        for (int i = 0; i < Tne; i++) {
//            Is[Eb[i] - 1]++;
//            Is[Ee[i] - 1]++;
//        }
//        // Filling Fs and initialization of P
//        Fs[0] = 0;
//        P[0] = Fs[0];
//        for (int i = 1; i < Tnn; i++) {
//            Fs[i] = Fs[i - 1] + Is[i - 1];
//            P[i] = Fs[i];
//        }
//        for (int i = 0; i < Tne; i++) {
//            Ts[P[Eb[i] - 1]] = Ee[i] - 1;
//            Hs[P[Eb[i] - 1]] = El[i];
//            P[Eb[i] - 1]++;
//            Ts[P[Ee[i] - 1]] = Eb[i] - 1;
//            Hs[P[Ee[i] - 1]] = El[i];
//            P[Ee[i] - 1]++;
//        }
//
//        Q = new PrQueue(Tnn); // The object prior queue is created
    }

    /**
     * 
     * @param From Id vrchola, z ktorého hľadám najkratšiu cestu.
     * @param To Id vrchola, do ktorého hľadám najkratšiu cestu.
     * <br><br>
     * Metóda vypíše na obrazovku najkratšiu cestu medzi vrcholmi s id 
     * <code>From</code> a <code>To</code>. Vypíše dĺžku cesty a postupnosť 
     * vrcholov.
     */
    public void GetPath(int From, int To) {
        int []P = new int[this.Tnn];
        int []D = new int[this.Tnn];
        int i;
        
        GetRowI(From, P, D);
        System.out.println("Dĺžka:"+D[To-1]);
        System.out.print(To);
        
        i = P[To-1];        
        while(P[i] != -1) {
            System.out.print(" <- "+(i+1));
            i = P[i];
        }
        System.out.println(" <- "+From);
    }
    
    /**
     * 
     * @param nn ID vrchola, z ktorého hľadám najkratšie cesty
     * @param P Pole, do ktorého budú uložení predchodcovia vrcholov. Pole tu
     * vstupuje už alokované.
     * @param D Pole, do ktorého budú uložené vzdialenosti z vrchola <code>nn</code> 
     * do ostatných vrcholov. Pole tu vstupuje už alokované.
     * <br><br>
     * The procedure computes distances from node <code>nn</code> to each other 
     * node of the network, <code>D</code> is row of distances, <code>nn-1</code>
     * is position of <code>nn</code> and <code>P</code> is array of pointers, 
     * where <code>P(i)+1</code> shows predecessor of node <code>i+1</code> on the 
     * shortest path from <code>nn</code>
     */
    private void GetRowI(int nn, int[] P, int[] D) {
        int mxi = 2147483647;
        int a; // A current node, which is processed
        int s; // A neighboring node, which is labeled
        int o; // Old label of s
        // Initialization of P and D
        for (int i = 0; i < Tnn; i++) {
            P[i] = -1;
        }
        for (int i = 0; i < Tnn; i++) {
            D[i] = mxi;
        }
        D[nn - 1] = 0;
        Q.EmptyTQ();
        Q.InsertToOQ(nn - 1);
        while (!Q.IsEmpty()) {
            a = Q.GetFromPQ();
            for (int i = Fs[a]; i < Fs[a] + Is[a]; i++) {
                s = Ts[i];
                if (D[a] + Hs[i] < D[s]) {
                    o = D[s];
                    D[s] = D[a] + Hs[i];
                    P[s] = a;
                    if (o == mxi) {
                        Q.InsertToPQ(s);
                    } else {
                        Q.InsertToOQ(s);
                    }
                } // if    
            } // for       
        } // while
    } // End of GetRowI

    /**
     * 
     * @param tr Počet riadkov matice vzdialeností
     * @param tc Počet stĺpcov matice vzdialeností
     * @param R Pole s ID-čkami vrcholov, z ktorých hľadám najkratšie vzdialenosti.
     * @param C Pole s ID-čkami vrcholov, do ktorých hľadám najkratšie vzdialenosti.
     * @param D Výsledná matica vzdialeností. Pole tu vstupuje už alokované.
     * <br><br>
     * The procedure computes matrix of distances from nodes saved in <code>R</code> 
     * to nodes saved in <code>C</code>. Size of the resulting matrix is <code>tr x tc</code>
     */
    public void GetMatrixI(int tr, int tc, int[] R, int[] C, int[][] D) {
        int []P = new int[this.Tnn];
        int []D2 = new int[this.Tnn];
        int i, j;
        
        
        for(i=0; i<R.length; i++) {
            GetRowI(R[i], P, D2);
            for(j=0; j<C.length; j++) {
                D[i][j] = D2[C[j]-1];
            }
        }
        
    } // End of GetMatrixI 

    public int GetTnn() {
        // put your code here
        return Tnn;
    }

    public int GetTne() {
        // put your code here
        return Tne;
    }

    public void ShowI(int I) {
        System.out.println(I);
    }

    public int GetEb(int i) {
        return Eb[i];
    }

    public int GetEe(int i) {
        return Ee[i];
    }

    public int GetEl(int i) {
        return El[i];
    }

    public int GetNn(int i) {
        return Nn[i];
    }

    public int GetNi(int i) {
        return Ni[i];
    }

    public String GetNa(int i) {
        return Na[i];
    }

    public int GetIs(int i) {
        return Is[i];
    }

    public int GetFs(int i) {
        return Fs[i];
    }

    public int GetTs(int i) {
        return Ts[i];
    }
    
    /**
     * 
     * @param id id vrchola
     * 
     * <br/><br/>
     * Procedúra vypíše všetkých susedov vrchola s ID == <code>id</code>.<br/>
     * Vypisuje ID vrcholov.
     */    
    public void getNeighbours(int id) {
        int od, kam, pocSus, i, idx;
        
        for(idx=0; idx < Tnn; idx++) {
            if(Nn[idx] == id) {
                break;
            }
        }
        
        if(idx < Tnn) { // idx obsahuje index vrchola s ID == id
            System.out.println("kam | IdUzl");
            pocSus = Is[idx];
            od = Fs[idx];
            for(i=0; i < pocSus; i++) {
                kam = Ts[od+i];
                System.out.println(kam+" | "+Nn[kam]);
            }
        }
        else {
            System.out.println("Vrchol s ID = "+id+" neexistuje");
        }
    }
}
