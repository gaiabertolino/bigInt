/** @author Gaia Bertolino */

package Bertolino.math;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class BigIntLL extends AbstractBigInt{

	private LinkedList<Integer> lista = new LinkedList<>();
	
	/**
	 * Costruttore di Default
	 */
	public BigIntLL() {} 
	
	/** Costruttore che genera un BigIntLL a partire da una stringa
	 * @param s Stringa da cui viene generato un BigIntLL
	 * @throws NumberFormatException se vi sono caratteri non numerici
	 */
	public BigIntLL(String s) throws NumberFormatException {
		for (int i=0; i<s.length(); i++ ) {
			this.lista.add(Integer.parseInt(s, i, i+1, 10));
		}
		this.eliminaZeri();
	}//BigIntLL(String s)
	
	/** Costruttore che genera un BigIntLL a partire da una intero
	 * @param i Intero da cui viene generato un BigIntLL
	 * @throws IllegalArgumentException se il numero è negativo
	 */
	public BigIntLL( int i ) throws IllegalArgumentException {
		if (i<0)
			throw new IllegalArgumentException();
		int exp = (int) Math.pow(10, (int) Math.log10(i));
		int n = 0;
		while ( exp>1 ) {
			n = i/exp;
			i = i - n * exp;
			lista.add(n);
			exp /= 10;
		}
		lista.add(i);
	}//BigIntLL(int i)

	@Override
	public BigIntLL factory(int i) throws IllegalArgumentException {
		BigIntLL numero = new BigIntLL(i);
		return numero;
	}//factory(int i)

	@Override
	public BigIntLL add(BigInt a) {
		BigIntLL ris = new BigIntLL();
		BigIntLL daSommare = (BigIntLL) a;
		ListIterator<Integer> i1 = listIterator(lista.size());
		ListIterator<Integer> i2 = daSommare.listIterator(daSommare.lista.size());
		int i = ((this.compareTo(daSommare) > 0) ? this.lista.size() : daSommare.lista.size());
		int riporto = 0;
		int val = 0;
		while (i>0) {
			if (i1.hasPrevious() && i2.hasPrevious()) {
				val = i1.previous() + i2.previous() + riporto;
			}
			else if (i1.hasPrevious()) {
				val = i1.previous() + riporto;
			}
			else if (i2.hasPrevious()) {
				val = i2.previous() + riporto;
			}
			riporto = val/10;
			ris.lista.add(0,val - riporto*10);
			i--;
		}
		if (riporto > 0)
			ris.lista.add(0,riporto);
		return ris;
	}//add(BigInt a)
	
	
	@Override
	public BigIntLL sub(BigInt s) throws IllegalArgumentException {
		BigIntLL daSottrarre = (BigIntLL) s;
		if (this.compareTo(daSottrarre) < 0) 
			throw new IllegalArgumentException();
		BigIntLL ris = new BigIntLL();
		ListIterator<Integer> i1 = listIterator(lista.size());
		ListIterator<Integer> i2 = daSottrarre.listIterator(daSottrarre.lista.size());
		int i = ((this.compareTo(daSottrarre) > 0) ? this.lista.size() : daSottrarre.lista.size());
		int val = 0;
		int riporto = 0;
		while (i>0) {
			if (i1.hasPrevious() && i2.hasPrevious())
				val = i1.previous() - i2.previous() - riporto;
			else if (i1.hasPrevious())
				val = i1.previous() - riporto;
			else if (i2.hasPrevious())
				val = i2.previous() - riporto;
			if (val<0) {
				val += 10;
				riporto = 1;
			}
			else
				riporto = 0;
			ris.lista.add(0, val);
			i--;
		}	
		return ris.eliminaZeri();
	}//sub(BigInt s)

	/** Metodo che elimina i nodi alle cifre più significative il cui valore è 0
	 * @return this senza zeri nelle cifre più significative
	 */
	private BigIntLL eliminaZeri() {
		for ( int i=0; i<lista.size(); ) {
			if (lista.size() == 1)
				return this;
			if (lista.get(i).equals(0))
				lista.remove(i);
			else
				return this;
		}
		return this;
	}//eliminaZeri()

	@Override
	public int compareTo(BigInt o) {
		BigIntLL b = (BigIntLL) o;
		if (lista.size() > b.lista.size())
			return 1;
		if (lista.size() < b.lista.size())
			return -1;
		for (Iterator<Integer> i1 = this.iterator(), i2 = b.iterator(); i1.hasNext();) {
			int one = i1.next();
			int two = i2.next();
			if (one > two )
				return 1;
			else if (one < two)
				return -1;
		}
		return 0;
	}//compareTo(BigInt o)

	@Override
	public Iterator<Integer> iterator() {
		return this.lista.iterator();
	}//iterator()
	
	/** Metodo che restituisce un listIterator() nella variabile d’istanza lista  che raccoglie le cifre del BigIntLL
	 * @return iteratore nella variabile d'istanza lista
	 */
	public ListIterator<Integer> listIterator() {
		return this.lista.listIterator();
	}//listIterator()
	
	/** Metodo che restituisce un listIterator() nella variabile d’istanza lista che raccoglie le cifre del BigIntLL in posizione i
	 * @param i intero che fa da indice 
	 * @return iteratore nella variabile d'istanza lista
	 */
	public ListIterator<Integer> listIterator(int i) {
		return this.lista.listIterator(i);
	}//listIterator(int i)
	
	/** Calcola la moltiplicazione fra this e un intero passato come argomento
	 * @param i int che fa da moltiplicatore 
	 * @return il risultato della moltiplicazione fra this e l'argomento i
	 */
	private BigIntLL mul(int i ) {
		BigIntLL ris = new BigIntLL();
		ListIterator<Integer> it = this.listIterator(this.lista.size());
		while (it.hasPrevious()) {
			ris.lista.add(0, i * it.previous());
		}
		return ris;
	}//mul(int i)
	
	@Override
	public BigIntLL mul(BigInt m) {
		BigIntLL ris = new BigIntLL();
		int exp = 1;
		ListIterator<Integer> i = ((BigIntLL) m).listIterator(((BigIntLL)m).lista.size());
		while (i.hasPrevious()) {
			ris = ris.add(this.mul(i.previous() * exp));
			exp *= 10;
		}
		return ris;
	}//mul(BigInt m)
	
	public static void main (String[] args) {
		BigInteger b = new BigInteger("2");
		BigInteger p = b.pow(128);
		System.out.println("BIGINTEGER 2^128 = " + p);
		BigIntLL l = new BigIntLL("2");
		BigIntLL pp = (BigIntLL) l.pow(128);
		System.out.println("BIGINT     2^128 = " + pp);
	}//main

}//BigIntLL
