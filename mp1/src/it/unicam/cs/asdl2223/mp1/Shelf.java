package it.unicam.cs.asdl2223.mp1;

/**
 * Un oggetto di questa classe rappresenta una mensola su cui possono essere
 * appoggiati degli oggetti. Tali oggetti possono essere di diverso tipo, ma
 * tutti implementano l'interface ShelfItem. Un oggetto non può essere
 * appoggiato sulla mensola se ha lunghezza o larghezza che eccedono quelle
 * della mensola stessa. La mensola può contenere un numero non precisato di
 * oggetti, ma ad un certo punto non si possono appoggiare oggetti la cui
 * superficie occupata o il cui peso fanno eccedere la massima superficie
 * occupabile o il massimo peso sostenibile definiti nel costruttore della
 * mensola.
 *
 * @author Matteo Machella
 * matteo.machella@studenti.unicam.it
 */
public class Shelf {
    /*
     * Dimensione iniziale dell'array items. Quando non è più sufficiente
     * l'array deve essere raddoppiato, anche più volte se necessario.
     */
    private final int INITIAL_SIZE = 5;

    /*
     * massima lunghezza di un oggetto che può essere appoggiato sulla mensola
     * in cm
     */
    private final double maxLength;

    /*
     * massima larghezza di un oggetto che può essere appoggiato sulla mensola
     * in cm
     */
    private final double maxWidth;

    /*
     * massima superficie occupabile della mensola in cm^2
     */
    private final double maxOccupableSurface;

    /*
     * massimo peso sostenibile dalla mensola in grammi
     */
    private final double maxTotalWeight;

    /*
     * array contenente tutti gli oggetti attualmente poggiati sulla mensola. In
     * caso di necessità viene raddoppiato nel momento che si poggia un nuovo
     * oggetto che fa superare la capacità dell'array.
     */
    private ShelfItem[] items;

    /*
     * variabile che indica il numero corrente di caselle nell'array che sono
     * occupate
     */
    private int numberOfItems;

    /**
     * Costruisce una mensola con le sue caratteristiche. All'inizio nessun
     * oggetto è posato sulla mensola.
     *
     * @param maxLength           lunghezza massima di un oggetto
     *                            appoggiabile in cm
     * @param maxWidth            larghezza massima di un oggetto
     *                            appoggiabile in cm
     * @param maxOccupableSurface massima superficie occupabile di questa
     *                            mensola in cm^2
     * @param maxTotalWeight      massimo peso sostenibile da questa mensola
     *                            in grammi
     */
    public Shelf(double maxLength, double maxWidth, double maxOccupableSurface, double maxTotalWeight) {
        this.maxLength = maxLength;
        this.maxWidth = maxWidth;
        this.maxOccupableSurface = maxOccupableSurface;
        this.maxTotalWeight = maxTotalWeight;
        this.items = new ShelfItem[INITIAL_SIZE];
        this.numberOfItems = 0;
    }

    /**
     * Aggiunge un nuovo oggetto sulla mensola. Qualora non ci sia più spazio
     * nell'array che contiene gli oggetti correnti, tale array viene
     * raddoppiato per fare spazio al nuovo oggetto.
     *
     * @param i l'oggetto da appoggiare
     * @return true se l'oggetto è stato inserito, false se è già presente
     * @throws IllegalArgumentException se il peso dell'oggetto farebbe
     *                                  superare il massimo peso consentito
     *                                  oopure se la superficie dell'oggetto
     *                                  farebbe superare la massima
     *                                  superficie occupabile consentita,
     *                                  oppure se la lunghezza o larghezza
     *                                  dell'oggetto superano quelle massime
     *                                  consentite
     * @throws NullPointerException     se l'oggetto passato è null
     */
    public boolean addItem(ShelfItem i) {
        if (i == null) {
            throw new NullPointerException("L'oggetto passato è nullo");
        }
        if ((i.getWeight() + getCurrentTotalWeight()) > this.maxTotalWeight) {
            throw new IllegalArgumentException("Il peso dell'oggetto eccede il limite");
        }
        if ((i.getOccupiedSurface() + getCurrentTotalOccupiedSurface()) > this.maxOccupableSurface) {
            throw new IllegalArgumentException("La superficie occupata dell'oggetto eccede il limite");
        }
        if (i.getLength() > this.maxLength) {
            throw new IllegalArgumentException("La lunghezza dell'oggetto eccede il limite");
        }
        if (i.getWidth() > this.maxWidth) {
            throw new IllegalArgumentException("La larghezza dell'oggetto eccede il limite");
        }
        // se esiste almeno un oggetto, controllo se ce ne sia uno uguale
        if (this.numberOfItems > 0) {
            for (int j = 0; j < this.numberOfItems; j++) {
                if (this.items[j].equals(i)) {
                    // oggetto già inserito
                    return false;
                }
            }
        }
        // se gli oggetti presenti hanno riempito l'array, lo raddoppio
        if (this.numberOfItems == this.items.length) {
            ShelfItem[] tmps = this.items;
            this.items = new ShelfItem[this.items.length * 2];
            for (int k = 0; k < tmps.length; k++) {
                this.items[k] = tmps[k];
            }
        }
        // inserisco l'oggetto alla prima posizione libera e incremento il numero di oggetti
        this.items[numberOfItems++] = i;
        return true;
    }

    /**
     * Cerca se è presente un oggetto sulla mensola. La ricerca utilizza il
     * metodo equals della classe dell'oggetto.
     *
     * @param i un oggetto per cercare sulla mensola un oggetto uguale a i
     * @return null se sulla mensola non c'è nessun oggetto uguale a i,
     * altrimenti l'oggetto x che si trova sulla mensola tale che
     * i.equals(x) == true
     * @throws NullPointerException se l'oggetto passato è null
     */
    public ShelfItem search(ShelfItem i) {
        if (i == null) {
            throw new NullPointerException("L'oggetto passato è nullo");
        }
        // ciclo per ricercare l'oggetto i
        if (this.numberOfItems > 0) {
            for (int j = 0; j < this.numberOfItems; j++) {
                if (this.items[j].equals(i)) {
                    // ritorno l'oggetto della mensola
                    return this.items[j];
                }
            }
        }
        // se i non è stato trovato ritorna null
        return null;
    }

    /**
     * @return il numero attuale di oggetti appoggiati sulla mensola
     */
    public int getNumberOfItems() {
        return this.numberOfItems;
    }

    /*
     * protected, per solo scopo di JUnit testing
     */
    protected ShelfItem[] getItems() {
        return this.items;
    }

    /**
     * @return the currentTotalWeight
     */
    public double getCurrentTotalWeight() {
        double totalCurrentWeight = 0;
        // se c'è almeno un oggetto svolgo il ciclo
        if (this.numberOfItems > 0) {
            for (int i = 0; i < this.numberOfItems; i++) {
                totalCurrentWeight += this.items[i].getWeight();
            }
        }
        return totalCurrentWeight;
    }

    /**
     * @return the currentTotalOccupiedSurface
     */
    public double getCurrentTotalOccupiedSurface() {
        double totalCurrentOccupiedSurface = 0;
        // se c'è almeno un oggetto svolgo il ciclo
        if (this.numberOfItems > 0) {
            for (int i = 0; i < this.numberOfItems; i++) {
                totalCurrentOccupiedSurface += this.items[i].getOccupiedSurface();
            }
        }
        return totalCurrentOccupiedSurface;
    }

    /**
     * @return the maxLength
     */
    public double getMaxLength() {
        return maxLength;
    }

    /**
     * @return the maxWidth
     */
    public double getMaxWidth() {
        return maxWidth;
    }

    /**
     * @return the maxOccupableSurface
     */
    public double getMaxOccupableSurface() {
        return maxOccupableSurface;
    }

    /**
     * @return the maxTotalWeight
     */
    public double getMaxTotalWeight() {
        return maxTotalWeight;
    }
}
