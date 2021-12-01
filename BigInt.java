/** @author Gaia Bertolino */

package Bertolino.math;
import java.util.Iterator;

public interface BigInt extends Comparable<BigInt>,Iterable<Integer> {
	
	/** Metodo che trasforma this in una stringa. Il numero viene costruito cifra per cifra tramite un iteratore
	 * @return this sotto forma di Stringa
	 */
	default String value()  {
		StringBuilder s = new StringBuilder();
		for (Iterator<Integer> i = iterator(); i.hasNext();)
			s.append(i.next());
		return s.toString();
	}//value()
	
	/** Metodo che indica da quante cifre è composto this
	 * @return numero di cifre che compongono this
	 */
	default int length() {
		int len = 0;
		for (Iterator<Integer> i = iterator(); i.hasNext(); i.next(), len++);
		return len;
	}//length()
	
	/** Metodo che incrementa this di una unità 
	 * @return this+1
	 */
	default BigInt incr() {
		return this.add(factory(1));
	}//incr()
	
	/** Metodo che decrementa this di una unità 
	 * @return this-1
	 * @throws IllegalArgumentException se this è pari a zero e dunque il decremento genera un numero negativo
	 */
	default BigInt decr() throws IllegalArgumentException {
		if (this.equals(factory(0)))
			throw new IllegalArgumentException();
		return this.sub(factory(1));
	}//decr()
	
	/** Calcola la moltiplicazione fra this e un BigInt passato come argomento
	 * @param m BigInt che fa da moltiplicatore
	 * @return il risultato della moltiplicazione fra this e l'argomento m
	 */
	default BigInt mul( BigInt m ) {
		BigInt zero = factory(0);
		BigInt ris = factory(0);
		while (!m.equals(zero)) {
			ris = ris.add(this);
			m = m.decr();
		}
		return ris;
	}//mul(BigInt m)
	
	/** Calcola la divisione fra this e un BigInt passato come argomento
	 * @param d BigInt che fa da divisore
	 * @return il risultato della divisione fra this e l'argomento d
	 * @throws IllegalArgumentException se viene passato un argomento uguale a zero o maggiore di this
	 */
	default BigInt div( BigInt d ) throws IllegalArgumentException {
		if (this.compareTo(d) < 0 || d.compareTo(factory(0)) <= 0)
			throw new IllegalArgumentException();
		int ris = 0;
		BigInt tmp = factory(0);
		while (tmp.add((BigInt) d).compareTo(this) <= 0) {
			ris++;
			tmp = tmp.add((BigInt) d);
		}
		return factory(ris);
	}//div(BigInt d)
	
	/** Calcola il resto della divisione fra this e un BigInt passato come argomento
	 * @param d BigInt che fa da divisore
	 * @return il resto della divisione fra this e l'argomento d
	 */
	default BigInt rem( BigInt d ) {
		if (this.compareTo(d) < 0 || d.compareTo(factory(0)) == 0)
			throw new IllegalArgumentException();
		BigInt ris = factory(0);
		while (ris.add((BigInt) d).compareTo(this) <= 0) {
			ris = ris.add((BigInt) d);
		}
		return this.sub(ris);
	}//rem(BigInt d)
	
	/** Calcola l'elevamento a potenza fra this e un intero passato come argomento
	 * @param exp int che fa da esponente
	 * @return il risultato dell'elevamento a potenza di this all'argomento exp
	 */
	default BigInt pow( int exp ) {
		BigInt ris = factory(1);
		while (exp>0) {
			ris = ris.mul(this);
			exp--;
		}
		return ris;
	}//pow(int exp)

	/** Genera un BigInt a partire da un intero passato come argomento
	 * @param i int
	 * @return un BigInt generato dall'argomento i
	 * @throws IllegalArgumentException se gli viene passato come argomento un numero minore di zero
	 */
		BigInt factory( int i ) throws IllegalArgumentException; 
		
	/** Calcola l'addizione fra this e un BigInt passato come argomento
	 * @param a BigInt che fa da addendo
	 * @return risultato dell'addizione fra this e l'argomento passato
	 */
		BigInt add( BigInt a );
		
	/** Calcola la sottrazione fra this e un BigInt passato come argomento
	 * @param s BigInt che fa da sottraendo
	 * @return risultato della sottrazione fra this e l'argomento passato
	 * @throws IllegalArgumentException  se il sottraendo è minore del minuendo
	 */
		BigInt sub( BigInt s ) throws IllegalArgumentException;
		
}//BigInt