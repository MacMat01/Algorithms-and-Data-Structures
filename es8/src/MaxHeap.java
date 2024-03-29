import java.util.ArrayList;
import java.util.List;

/**
 * Classe che implementa uno heap binario che può contenere elementi non nulli
 * possibilmente ripetuti.
 *
 * @param <E> il tipo degli elementi dello heap, che devono avere un
 *            ordinamento naturale.
 * @author Template: Luca Tesei, Implementation: collettiva
 */
public class MaxHeap<E extends Comparable<E>> {

    /*
     * L'array che serve come base per lo heap
     */
    private ArrayList<E> heap;

    /**
     * Costruisce uno heap vuoto.
     */
    public MaxHeap() {
        this.heap = new ArrayList<E>();
    }

    /**
     * Restituisce il numero di elementi nello heap.
     *
     * @return il numero di elementi nello heap
     */
    public int size() {
        return this.heap.size();
    }

    /**
     * Determina se lo heap è vuoto.
     *
     * @return true se lo heap è vuoto.
     */
    public boolean isEmpty() {
        return this.heap.isEmpty();
    }

    /**
     * Costruisce uno heap a partire da una lista di elementi.
     *
     * @param list lista di elementi
     * @throws NullPointerException se la lista è nulla
     */
    public MaxHeap(List<E> list) {
        if (list == null) {
            throw new NullPointerException("lista nulla");
        }
        for (int i = list.size() - 1; i >= 0; i--) {
            for (int j = i; j >= 0; j--) {
                if (list.get(j).compareTo(list.get(i)) > 0) {
                    E temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);
                }
            }
        }
        this.heap = new ArrayList<E>(list);
    }

    /**
     * Inserisce un elemento nello heap
     *
     * @param el l'elemento da inserire
     * @throws NullPointerException se l'elemento è null
     */
    public void insert(E el) {
        if (el == null) {
            throw new NullPointerException("elemento nulla");
        }
        /*
         * Inserisco l'elemento in fondo all'heap
         */
        if (this.heap.isEmpty()) {
            this.heap.add(el);
            /*
             * Se l'heap non è vuoto, devo ripristinare la proprietà di heap
             */
        } else {
            this.heap.add(el);
            heapify(this.heap.size() - 1);
        }
    }

    /*
     * Funzione di comodo per calcolare l'indice del figlio sinistro del nodo in
     * posizione i. Si noti che la posizione 0 è significativa e contiene sempre
     * la radice dello heap.
     */
    private int leftIndex(int i) {
        return (2 * i) + 1;
    }

    /*
     * Funzione di comodo per calcolare l'indice del figlio destro del nodo in
     * posizione i. Si noti che la posizione 0 è significativa e contiene sempre
     * la radice dello heap.
     */
    private int rightIndex(int i) {
        return (2 * i) + 2;
    }

    /*
     * Funzione di comodo per calcolare l'indice del genitore del nodo in
     * posizione i. Si noti che la posizione 0 è significativa e contiene sempre
     * la radice dello heap.
     */
    private int parentIndex(int i) {
        return (i - 1) / 2;
    }

    /**
     * Ritorna l'elemento massimo senza toglierlo.
     *
     * @return l'elemento massimo dello heap oppure null se lo heap è vuoto
     */
    public E getMax() {
        if (this.isEmpty()) {
            return null;
        }
        /*
         * L'elemento massimo è sempre la radice
         */
        return this.heap.get(0);
    }

    /**
     * Estrae l'elemento massimo dallo heap. Dopo la chiamata tale elemento non
     * è più presente nello heap.
     *
     * @return l'elemento massimo di questo heap oppure null se lo heap è vuoto
     */
    public E extractMax() {
        if (this.isEmpty()) {
            return null;
        }
        /*
         * Salvo l'elemento massimo
         */
        E max = this.heap.get(0);
        /*
         * Sposto l'ultimo elemento in testa
         */
        this.heap.set(0, this.heap.get(this.heap.size() - 1));
        /*
         * Rimuovo l'ultimo elemento
         */
        this.heap.remove(this.heap.size() - 1);
        /*
         * Ripristino la proprietà di heap
         */
        heapify(0);
        return max;
    }

    /*
     * Ricostituisce uno heap a partire dal nodo in posizione i assumendo che i
     * suoi sotto alberi sinistro e destro (se esistono) siano heap.
     */
    private void heapify(int i) {
        if (i > 0 && this.heap.get(parentIndex(i)).compareTo(this.heap.get(i)) < 0) {
            E tmp = this.heap.get(i);
            this.heap.set(i, this.heap.get(parentIndex(i)));
            this.heap.set(parentIndex(i), tmp);
            heapify(parentIndex(i));
        }
        int max = i;
        if (leftIndex(i) < this.heap.size() && this.heap.get(leftIndex(i)).compareTo(this.heap.get(i)) > 0) {
            max = leftIndex(i);
        }
        if (rightIndex(i) < this.heap.size() && this.heap.get(rightIndex(i)).compareTo(this.heap.get(i)) > 0) {
            if (this.heap.get(rightIndex(i)).compareTo(this.heap.get(leftIndex(i))) > 0) {
                max = rightIndex(i);
            }
        }
        if (max != i) {
            E temp = this.heap.get(i);
            this.heap.set(i, this.heap.get(max));
            this.heap.set(max, temp);
            heapify(max);
        }
    }

    /**
     * Only for JUnit testing purposes.
     *
     * @return the arraylist representing this max heap
     */
    protected ArrayList<E> getHeap() {
        return this.heap;
    }
}
