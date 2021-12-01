/** @author Gaia Bertolino */
package Bertolino.math;
import java.util.Scanner;
public class Main {
	
	/** Metodo che, tramite uno scanner passato come argomento, legge cosa viene digitato da tastiera fin quando non è possibile generare un BigIntLL. 
	 * Nel caso in cui sia possibile, esce dal ciclo e restituisce il BigIntLL creato
	 * @param sc Scanner che legge da tastiera 
	 * @return un BigIntLL generato tramite l'input da tastiera dell'utente
	 */
	private static BigIntLL nuovo (Scanner sc) {
		BigIntLL ris = new BigIntLL();
		inizio:
		for (;;) {
			try { 
				System.out.print("Digita una stringa o un numero per costruire un BigInt>> ");
				String s = "";
				if (sc.hasNext()) {
					s = sc.next();
					if (s.toUpperCase().equalsIgnoreCase("STOP")) {
						System.out.println("BYE");
						sc.close();
						break;
					}
					else {
						ris = new BigIntLL(s);
						break inizio;
					}
				}
			}
			catch (NumberFormatException e) {
				System.out.println("Hai inserito un simbolo non numerico. Ricorda di inserire solo numeri positivi. Riprova! \n");
				continue inizio;
				}
			
		}
		return ris;
	}//nuovo(Scanner sc)
	
	/** Metodo che genera ciclicamente la possibilità di fare delle operazioni su un BigIntLL passato come argomento
	 * @param sc Scanner che legge da tastiera 
	 * @param numero BigIntLL su cui vengono eseguite le operazioni
	 */
	private static void scelte(Scanner sc, BigIntLL numero) {
		elab:
		for (;;) {
			System.out.print("\nCosa vuoi fare con BigInt? Digita "
					+ "\n>> INC per incrementarlo"
					+ "\n>> DEC per decrementarlo" 
					+ "\n>> C per confrontarlo con un altro BigInt "
					+ "\n>> P per fare un'elevamento a potenza "
					+ "\n>> L per sapere da quante cifre è composto" 
					+ "\n>> D per fare una divisione"
					+ "\n>> R per ottenere il resto di una divisione "
					+ "\n>> A per fare un'addizione "
					+ "\n>> S per fare una sottrazione >>>  ");
			if (sc.hasNext()) {
				String s = sc.next();
				s = s.toUpperCase();
				switch (s) {
					case "INC" : { System.out.print("\nHai scelto di incrementare BigInt. " + numero + " incrementato è: "); 
						numero = (BigIntLL) numero.incr();
						System.out.println(numero); break;}
					case "DEC" : { System.out.print("\nHai scelto di decrementare BigInt. ");
						try {numero.decr(); } catch (IllegalArgumentException e) {System.out.println("Il decremento genera un numero negativo! Scegli una nuova operazione"); continue elab;}  
						System.out.print(numero + " decrementato è: "); numero = (BigIntLL) numero.decr(); System.out.println(numero); break;}
					case "C" : { System.out.print("\nHai scelto di confrontare " + numero + " con un altro BigInt.\nPer inserire il secondo BigInt ");
						BigIntLL secondo = nuovo(sc);   
						if (numero.compareTo(secondo) > 0) System.out.println(numero + " è maggiore di " + secondo);
						else if (numero.compareTo(secondo) < 0) System.out.println(numero + " è minore di " + secondo);
						else System.out.println("I due BigInt sono uguali"); break;}
					case "P" : { System.out.print("\nHai scelto di fare un elevamento a potenza. Inserisci l'esponente>> ");
						int exp = 1; if (sc.hasNext()) exp = Integer.parseInt(sc.next()); 
						System.out.print("\n" + numero + " elevato a " + exp + " è: "); numero = (BigIntLL) numero.pow(exp);
						System.out.println(numero); break;}
					case "L" : { System.out.print("\nHai scelto di sapere da quante cifre è composto " + numero + ".\n" + numero + " è composto da " + numero.length() + " cifre"); break; }
					case "D" : { System.out.print("\nHai scelto di fare una divisione. \nPer inserire il secondo BigInt "); 
						BigIntLL secondo = nuovo(sc);  
						try { numero.div(secondo); } catch (IllegalArgumentException e) { System.out.println("\nHai digitato un divisore minore del dividendo o un termine pari a zero. Scegli una nuova operazione"); continue elab;}
						System.out.print("\nIl risultato della divisione fra " + numero); numero = (BigIntLL) numero.div(secondo); System.out.println(" e " + secondo + " è: " + numero); break;}
					case "R" : { System.out.print("\nHai scelto di fare una divisione e calcolare il resto. \nPer inserire il secondo BigInt "); 
						BigIntLL secondo = nuovo(sc);  
						try { numero.rem(secondo); } catch (IllegalArgumentException e) { System.out.println("\nHai digitato un divisore minore del dividendo o un termine pari a zero. Scegli una nuova operazione"); continue elab;}
						System.out.print("Il resto della divisione fra " + numero + " e " + secondo + " è: " + numero.rem(secondo) + 
								"\n" + numero + " è diventato "); numero = (BigIntLL) numero.div(secondo); System.out.println(numero); break;}
					case "M" : { System.out.print("\nHai scelto di fare una moltiplicazione. \nPer inserire il secondo BigInt "); 
						BigIntLL secondo = nuovo(sc);  System.out.print("Il prodotto fra " + numero + " e " + secondo + " è: "); numero = (BigIntLL) numero.mul(secondo); 
						System.out.println(numero); break;}
					case "A" : { System.out.print("\nHai scelto di fare una'addizione. \nPer inserire il secondo BigInt "); 
						BigIntLL secondo = nuovo(sc);  System.out.print("La somma fra " + numero + " e " + secondo + " è: "); numero = numero.add(secondo); 
						System.out.println(numero); break;}
					case "S" : { System.out.print("\nHai scelto di fare una sottrazione. \nPer inserire il secondo BigInt "); BigIntLL secondo = nuovo(sc);
						try {numero.sub(secondo); } catch (IllegalArgumentException e) {System.out.println("\nLa sottrazione dà origine ad un numero negativo! Scegli una nuova operazione"); continue elab; }  
						 System.out.print("\nLa differenza fra " + numero + " e " + secondo + " è: ");  numero = numero.sub(secondo);
						  System.out.println(numero); break;} 
						default : System.out.println("\nDigita un comando corretto dall'elenco!"); continue elab;
				}//switch
			}//ifHasNext
			System.out.print(	"\n>> Per fare una nuova operazione digita OP " +
								"\n>> Per generare un nuovo BigInt digita N " + 
								"\n>> Per terminare digita STOP >>> ");
			if (sc.hasNext()) {
				String s = sc.next();
				s = s.toUpperCase();
				switch (s) {
					case "N" : { System.out.println(); numero = nuovo(sc); break;}
					case "STOP" : { System.out.println("BYE"); sc.close(); break elab; }
				}
			}
		}
	}//scelte(Scanner sc, BigIntLL numero)
	
	public static void main (String[] args) {
		
		Scanner sc = new Scanner(System.in);
		BigIntLL numero = nuovo(sc);
		System.out.println("Il numero digitato è " + numero);
		scelte(sc, numero);
	}//main

}//Main
