/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.semestralka;

import java.util.Random;

/**
 * Trieda implementujuca heuristiku Variable Neighborhood Descent na ulohe o p-mediane
 * @author namer
 */
public class VariableNeighborhoodDescent {
    
    Random rand;
    
    /**
     * Maximálny index topológie.
     */
    private static int kMax = 6; //6 operacii 
    
    /**
     * Matica vzdialeností úplnej dopravnej siete
     */
    private int [][] D;

    /**
     * Počet vrcholov dopravnej siete
     */
    private int pocetVrcholov;

    /**
     * Najlepšie riešenie. 
     * Riešenie je dané 0 - 1 reprezentaciou indexov vrcholov kde 1 reprezentuje ze dane stredisko bude vybudovane a 0 ze nie.
     */
    private int [] best;

    /**
     * Dočasné riešenie
     */
    private int [] temp;

    /**
     * Aktuálne riešenie
     */
    private int [] aktual;

    /**
     * Hodnota účelovej funkcie najlepšieho riešenia
     */
    private int ufBest;

    /**
     * Hodnota účelovej funkcie dočasného riešenia
     */
    private int ufTemp;

    /**
     * Hodnota účelovej funkcie aktuálneho riešenia
     */
    private int ufAktual;
    
    /**
     * Fixne ceny na postavenie strediska
     */
    private int[] fixedCosts;
    
    /**
     * Jednotkova cena na kilometer pri preprave medzi strediskami
     */
    private int jednCena;
    
    /**
     * Maximalny pocet postavenych stredisk
     */
    private int p;
    
    
    /**
     * 
     * @param D matica vzdialenosti
     * @param fixedCosts fixne ceny na postavenie strediska v jednotlivych vrcholoch
     * @param jednCena jednotkova cena prepravy 
     * @param pocetVrcholov pocet vrcholov
     * @param p max pocet povolenych stredisk
     */
    public VariableNeighborhoodDescent(int[][] D, int[] fixedCosts, int jednCena, int pocetVrcholov, int p) {
        this.rand = new Random();
        
        this.D = D;
        this.fixedCosts = fixedCosts;
        this.jednCena = jednCena;
        this.pocetVrcholov = pocetVrcholov;
        this.best = new int [pocetVrcholov];
        this.temp = new int [pocetVrcholov];
        this.aktual = new int [pocetVrcholov];
        initAktual();
        initBest();
        initTemp();
        saveBest(aktual, ufAktual);
    }
    
    
    /**
     * Metoda pre ohodnotenie vstupujuceho riesenia sol
     * @param sol ohodnocované riešenie
     * @return Hodnota účelovej funkcie riešenia <code>sol</code>.
     */
    private int costFunction(int [] sol) {
        int i, sum = 0;
        int min_dist = Integer.MAX_VALUE;
        
        //naratam fixne naklady na postavenie stredisk
        for (i = 0; i < sol.length; i++) {
            if (sol[i] == 1) {
                sum += this.fixedCosts[i];
            }
        }
        
        //naratam variabilne naklady
        for(i=0; i<pocetVrcholov; i++) { //prechadzam cez vsetky vrcholy a hladam im postavene stredisko ktore maju najblizsie
            min_dist = Integer.MAX_VALUE;
            //najdem najmensiu vzdialenost pre kazde stredisko
            for (int j = 0; j < sol.length; j++) { //prechadzam postavene strediska
                if (sol[j] == 1) { //stredisko je postavene
                    if (this.D[i][j] < min_dist) { // nasiel som mensiu vzdialenost tak to beriem ako najblizsie stredisko
                        min_dist = this.D[i][j];
                    }
                }
                
            }
            sum += min_dist * this.jednCena;
        }
        return sum;
    }

    /**
     * Metóda inicializuje riešenie <code>best</code> na prázdne
     * riešenie (samé nuly) s hodnotou účelovej funkcie <code>infinity</code>
     */
    private void initBest() {
        int i;
        for(i=0; i<=pocetVrcholov; i++) {
            best[i] = 0;
        }
        ufBest = Integer.MAX_VALUE;
    }

    /**
     * Metóda inicializuje riešenie <code>temp</code> na prázdne
     * riešenie (samé nuly) s hodnotou účelovej funkcie <code>infinity</code>
     */
    private void initTemp() {
        int i;
        for(i=0; i<=pocetVrcholov; i++) {
            temp[i] = 0;
        }
        ufTemp = Integer.MAX_VALUE;
    }
    
    /**
     *
     * Metóda inicializuje riešenie <code>aktual</code> na prazdne <br/>
     * riesenie (same nuly) s hodnotou ucelovej funkcie infinity
     */
    private void initAktual() {
        int i;
        for(i=0; i<pocetVrcholov; i++) {
            aktual[i] = 0;
        }
        ufAktual = Integer.MAX_VALUE;
    }

    /**
     * Metoda pre ulozenie najlepsieho riesenia
     * @param sol vstupné riešenie
     * @param ufSol účelová funkcia vstupného riešenia <code>sol</code>
     * <br/><br/>
     * Metóda uloží do riešenia <code>best</code> vstupné riešenie <code>sol</code>
     */
    private void saveBest(int [] sol, int ufSol) {
        int i;
        for(i=0; i<=pocetVrcholov; i++) {
            best[i] = sol[i];
        }
        ufBest = ufSol;
    }

    /**
     *
     * @param sol vstupné riešenie
     * @param ufSol účelová funkcia vstupného riešenia <code>sol</code>
     * @return <code>True</code> ak bolo aktualizované riešenie <code>best</code>.
     * <br/><code>False</code> inak.
     * <br/><br/>
     * Ak je vstupné riešenie <code>sol</code> lepšie ako riešenie
     * <code>best</code>, metóda uloží vstupné riešenie do riešenia <code>best</code>.
     */
    private boolean keepBest(int [] sol, int ufSol) {
        if(ufSol < ufBest) {
            saveBest(sol, ufSol);
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param sol vstupné riešenie
     * @param ufSol účelová funkcia vstupného riešenia <code>sol</code>
     * <br/><br/>
     * Metóda uloží do riešenia <code>temp</code> vstupné riešenie <code>sol</code>.
     */
    private void saveTemp(int [] sol, int ufSol) {
        int i;
        for(i=0; i<=pocetVrcholov; i++) {
            temp[i] = sol[i];
        }
        ufTemp = ufSol;
    }
    
    /**
     *
     * @param sol vstupné riešenie
     * @param ufSol účelová funkcia vstupného riešenia <code>sol</code>
     * @return <code>True</code> ak bolo aktualizované riešenie <code>temp</code>.
     * <br/><code>False</code> inak.
     * <br/><br/>
     * Ak je vstupné riešenie <code>sol</code> lepšie ako riešenie
     * <code>temp</code>, metóda uloží vstupné riešenie do riešenia <code>temp</code>.
     */
    private boolean keepTemp(int [] sol, int ufSol) {
        if(ufSol < ufTemp) {
            saveTemp(sol, ufSol);
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param sourceSol riešenie, ktoré kopírujem
     * @param ufSourceSol účelová funkcia riešenia <code>sourceSol</code>
     * @param destSol riešenie, do ktorého nakopírujem riešenie <code>sourceSol</code>
     * @return <code>ufSourceSol</code>
     * <br/><br/>
     * Metóda skopíruje riešenie <code>sourceSol</code> do riešenia
     * <code>destSol</code> a vráti hodnotu <code>ufSourceSol</code>.
     */
    private int copySol(int [] sourceSol, int ufSourceSol, int [] destSol) {
        int i;
        for(i = 0; i <= pocetVrcholov; i++) {
            destSol[i] = sourceSol[i];
        }
        return ufSourceSol;
    }
    
    /**
     * Metoda pre aplikovanie pridania noveho strediska so strategiou random
     * @param sol vstupne riesenie
     * @param ufSol ucelova funkcia vstupneho riesenia
     * @return hodnota ucelovej funkcie noveho riesenia
     */
    public int pridanieNovehoStrediska_rand(int[] sol, int ufSol) {
        int randomNum, zeroCount = 0;
        int pocetStredisk = this.spocitajStrediska(sol);
        
        if (pocetStredisk < this.p) { //kobntrola ci mozem este postavit dalsie stredisko
            randomNum = this.rand.nextInt(sol.length - pocetStredisk); //generujem si index prvku kde budem vkladat (v poradi teda ked mi da 2 tak na druhe miesto kde je nula)
            for (int i = 0; i < sol.length; i++) {
                if (sol[i] == 0) {
                    if (zeroCount == randomNum) { 
                        sol[i] = 1;
                        break;
                    }
                    zeroCount++;
                } 
            }
            
            ufSol = this.costFunction(sol);
        }
        return ufSol;
    }
    
    /**
     * Metoda pre aplikovanie pridania noveho strediska so strategiou Best Admisible
     * @param sol vstupne riesenie
     * @param ufSol ucelova funkcia vstupneho riesenia
     * @return hodnota ucelovej funkcie noveho riesenia
     */
    public int pridanieNovehoStrediska_BA(int[] sol, int ufSol) {
        int indexBestAdd = 0;
        int dummyUF = 0;
        int minUF = Integer.MAX_VALUE;
        
        if (this.spocitajStrediska(sol) < this.p) { //kobntrola ci mozem este postavit dalsie stredisko
            for (int i = 0; i < sol.length; i++) {
                if (sol[i] == 0) {
                    sol[i] = 1; //nastavim na jedna pre vypocet UF
                    dummyUF = this.costFunction(sol);
                    if (dummyUF < minUF) {
                        minUF = dummyUF;
                        indexBestAdd = i;
                    }
                    sol[i] = 0; //nastavim spat na nula
                }
            }
            sol[indexBestAdd] = 1;
            ufSol = minUF;
        }
        return ufSol;
    }
    
    /**
     * Metoda pre aplikovanie vymeny miesta postavenia strediska so strategiou random
     * @param sol vstupne riesenie
     * @param ufSol ucelova funkcia vstupneho riesenia
     * @return hodnota ucelovej funkcie noveho riesenia
     */
    public int vymenaStrediska_rand(int[] sol, int ufSol) {
        int randomNumOut = 0;
        int randomNumIn = 0;
        int zeroCount = 0;
        int oneCount = 0;
        int pocetStredisk = this.spocitajStrediska(sol);
        
        if (pocetStredisk > 0) { //na vymenu musim mat aspon jedno stredisko postavene
            
            randomNumIn = this.rand.nextInt(sol.length - pocetStredisk); //generujem si index prvku kde budem vkladat (v poradi teda ked mi da 2 tak na druhe miesto kde je nula)
            randomNumOut = this.rand.nextInt(pocetStredisk); //generujem si index prvku kde budem mazat (v poradi teda ked mi da 2 tak na druhe miesto kde je jednotka)
            for (int i = 0; i < sol.length; i++) {
                if (sol[i] == 0) {
                    if (zeroCount == randomNumIn) { 
                        sol[i] = 1;
                    }
                    zeroCount++;
                } 
                else if (sol[i] == 1) {
                    if (oneCount == randomNumOut) { 
                        sol[i] = 0;
                    }
                    oneCount++;
                } 
            }
            ufSol = this.costFunction(sol); 
        }
        
        return ufSol;
    }
    
    /**
     * Metoda pre aplikovanie vymeny miesta postavenia strediska so strategiou Best Admisible
     * @param sol vstupne riesenie
     * @param ufSol ucelova funkcia vstupneho riesenia
     * @return hodnota ucelovej funkcie noveho riesenia
     */
    public int vymenaStrediska_BA(int[] sol, int ufSol) {
        int indexOut = 0;
        int indexIn = 0;
        int dummyUF = 0;
        int minUF = Integer.MAX_VALUE;
        
        if (this.spocitajStrediska(sol) > 0) { //kobntrola ci mozem este postavit dalsie stredisko
            for (int i = 0; i < sol.length; i++) { //prechadzam strediska kde su nuly (kandidatov na vstup do riesenia)
                if (sol[i] == 0) {
                    for (int j = 0; j < sol.length; j++) { //a hladam k nim jednotku na vymenu (kandidatov na vystup z riesenia)
                        if (sol[j] == 1) {
                            sol[i] = 1;
                            sol[j] = 0;
                            dummyUF = this.costFunction(sol);
                            if (dummyUF < minUF) { //ak som nasiel lepsie riesenie tak si zapisem indexy ktore budem menit
                                minUF = dummyUF;
                                indexOut = j;
                                indexIn = i;
                            }
                            sol[i] = 0;
                            sol[j] = 1;
                        }
                    }
                }
            }
            
            //vykonam samotnu vymenu
            sol[indexOut] = 0;
            sol[indexIn] = 1;
            ufSol = minUF;
         }
        return ufSol;
    }
    
    /**
     * Metoda pre aplikovanie odobratia strediska so strategiou random
     * @param sol vstupne riesenie
     * @param ufSol ucelova funkcia vstupneho riesenia
     * @return hodnota ucelovej funkcie noveho riesenia
     */
    public int odobratieStrediska_rand(int[] sol, int ufSol) {
        int randomNum, oneCount = 0;
        int pocetStredisk = this.spocitajStrediska(sol);
        
        if (pocetStredisk > 0) { //kobntrola ci mam ake stredisko vymazat
            randomNum = this.rand.nextInt(pocetStredisk); //generujem si index prvku kde budem mazat (v poradi teda ked mi da 2 tak na druhe miesto kde je jednotka)
            for (int i = 0; i < sol.length; i++) {
                if (sol[i] == 1) {
                    if (oneCount == randomNum) { 
                        sol[i] = 0;
                        break;
                    }
                    oneCount++;
                } 
            }
            ufSol = this.costFunction(sol);
        }
        return ufSol;
    }
    
    /**
     * Metoda pre aplikovanie odobratia strediska so strategiou Best Admisible
     * @param sol vstupne riesenie
     * @param ufSol ucelova funkcia vstupneho riesenia
     * @return hodnota ucelovej funkcie noveho riesenia
     */
    public int odobratieStrediska_BA(int[] sol, int ufSol) {
        int indexBestRemove = 0;
        int dummyUF = 0;
        int minUF = Integer.MAX_VALUE;
        
        if (this.spocitajStrediska(sol) > 0) { //kobntrola ci mozem odobrat stredisko
            for (int i = 0; i < sol.length; i++) {
                if (sol[i] == 1) {
                    sol[i] = 0; //nastavim na nula pre vypocet UF
                    dummyUF = this.costFunction(sol);
                    if (dummyUF < minUF) {
                        minUF = dummyUF;
                        indexBestRemove = i;
                    }
                    sol[i] = 1; //nastavim spat na jedna
                }
            }
            sol[indexBestRemove] = 0;
            ufSol = minUF;
        }
        return ufSol;
    }
    
    /**
     * Metoda pre spocitanie postavenych stredisk
     * @param sol vstupne riesenie
     * @return pocet postavenych stredisk
     */
    private int spocitajStrediska(int[] sol) {
        int pocetStredisk = 0;
        for (int i : sol) {
            if (i == 1) 
                pocetStredisk ++;
        }
        return pocetStredisk;
    }

    /**
     *
     * @param k aktuálna topológia
     * @param sol vstupné riešenie, ktoré porovnávam s riešením <code>temp</code>
     * @param ufSol účelová funkcia vstupného riešenia <code>sol</code>
     * @return nová aktuálna topológia, podľa toho, či je riešenie <code>sol</code>
     * lepšie ako riešenie <code>temp</code>.
     * <br/><br/>
     * Ak je vstupujúce riešenie <code>sol</code> lepšie ako riešenie <code>temp</code>
     * tak, aktualizujem riešenie <code>temp</code> riešením <code>sol</code>
     * a znížim topológiu na 1, inak zvýšim topológiu o 1.
     */
    public int changeNeighbourhoodTemp(int k, int [] sol, int ufSol) {
        
        if (this.ufTemp > ufSol) {
            this.keepTemp(sol, ufSol);
            return 1;
        }
        else
            return k + 1;        
    }

    /**
     *
     * @param k aktuálna topológia
     * @param sol vstupné riešenie, ktoré pozmením
     * @param ufSol účelová funkcia vstupného riešenia <code>sol</code>
     * @return účelová funkcia pozmeneného vstupného riešenia <code>sol</code>
     * <br/><br/>
     * Metóda aplikuje na vstupné riešenie <code>sol</code> postupne všetky
     * operácie z množiny povolených operácií a to najlepšie riešenie uloží 
     * do riešenia <code>sol</code>. Nové riešenie však môže byť horšie ako vstupné.
     * <br/><br/>
     * Množinu povolených operácií určuje index topológie <code>k</code>.<br/>
     * &nbsp&nbsp&nbsp<code>k = 1</code>&nbsp&nbsp Pridanie noveho strediska so strategiou random<br/>
     * &nbsp&nbsp&nbsp<code>k = 2</code>&nbsp&nbsp Pridanie noveho strediska BA<br/>
     * &nbsp&nbsp&nbsp<code>k = 3</code>&nbsp&nbsp Operacia vymeny dvoch indexov so strategiou random<br/>
     * &nbsp&nbsp&nbsp<code>k = 4</code>&nbsp&nbsp Operacia vymeny dvoch indexov BA<br/>
     * &nbsp&nbsp&nbsp<code>k = 5</code>&nbsp&nbsp Operacia odobratia jedneho strediska so strategiou random<br/> 
     * &nbsp&nbsp&nbsp<code>k = 5</code>&nbsp&nbsp Operacia odobratia jedneho strediska BA<br/> 
     */
    public int getNext(int k, int [] sol, int ufSol) {
        switch (k) { 
            case 1:
                return this.pridanieNovehoStrediska_rand(sol, ufSol); 
            case 2:
                return this.pridanieNovehoStrediska_BA(sol, ufSol); 
            case 3:
                return this.vymenaStrediska_rand(sol, ufSol); 
            case 4:
                return this.vymenaStrediska_BA(sol, ufSol); 
            case 5: 
                return this.odobratieStrediska_rand(sol, ufSol); 
            case 6:
                return this.odobratieStrediska_BA(sol, ufSol);
            default:
                throw new AssertionError(); 
        }
    }
    
    /**
     * 
     * @param kMax Maximálny index topológie
     * @param sol vstupné riešenie
     * @param ufSol účelová funkcia vstupného riešenia <code>sol</code>
     * @return účelová funkcia zmeneného riešenia <code>sol</code>
     * <br/><br/>
     * Metóda zabezpečuje vykonanie metaheuristiky Variable Neighborhood Descent.
     * Začína v riešení <code>sol</code>, ktoré pozmení.<br/>
     * <strong color="red">Metóda zmení riešenie <code>temp</code>.</strong>
     */
    public int variableNeighborhoodDescent(int kMax, int [] sol, int ufSol) {
        int k = 1; //zacinam topologiou 1
        this.ufTemp = Integer.MAX_VALUE;
        this.keepTemp(sol, ufSol);
        int tempSol = ufSol;
        
        while (k <= kMax) {            
            tempSol = this.getNext(k, sol, tempSol);
            k = this.changeNeighbourhoodTemp(k, sol, tempSol); //aktualizuje k 
        }
        return tempSol;
    }       
    
    /**
     * Metóda zavolá vykonanie metódy variableNeighborhoodDescent() a získané
     * riešenie uloží do riešenia <code>best</code>.
     */
    public void aplikujVnd() {
        initAktual();
        ufAktual = variableNeighborhoodDescent(kMax, aktual, ufAktual);
        ufBest = copySol(aktual, ufAktual, best);        
    }
}
