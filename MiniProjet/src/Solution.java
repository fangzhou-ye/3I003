import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class Solution {
	
	private int s;
	private int k;
	private int d;
	private Map<Integer, Integer> V;
	private ArrayList<Integer> bocaux;
	private int[][] M;
	// utilisé pour progDyn de façon recursif, ce qui est le moins optimal, tant pire :(
	private LinkedHashMap<Integer[], Integer> temp;
	
	public Solution() {
		V = new LinkedHashMap<Integer, Integer>(k);
	}
	
	public Solution(int numFichier, String folder) throws IOException{
		init(numFichier, folder);
	}
	
	public int[][] getM(){
		return M;
	}
	
	public Map<Integer, Integer> getV(){
		return V;
	}
	
	public void print_temp() {
		for(Integer[] t : temp.keySet()) {
			System.out.println("s:" + t[0] + " k:" + t[1]);
		}
	}
	
	public String print_V() {
		String str = "[ ";
		for(Integer bocal : V.keySet()) {
			str += bocal + ":" + V.get(bocal) + " ";
		}
		return str + "]";
	}
	
	public String print_bocaux() {
		String s = "[ ";
		for(Integer b : bocaux) {
			s += b + " ";
		}
		s += "]";
		return s;
	}
	
	public String toString() {
		String s = "";
		s += "s:" + this.s + "\n";
		s += "k:" + this.k + "\n";
		s += "system bocaux: " + print_bocaux();
		s += "\n";
		s += "solution: " + print_V();
		return s;
	}
	
	public int getS() {
		return s;
	}
	
	public int getK() {
		return k;
	}
	
	public ArrayList<Integer> getBocaux(){
		return bocaux;
	}
	
	public static boolean isNum(String str) {
	    boolean res = true;
	    try {
	        Double.parseDouble(str);
	    }catch (NumberFormatException e) {
	        res = false;
	    }
	    return res;
	}
	
	
	/***
	 * initialiser le system bocaux, k, d, s par lecture de fichier
	 * @param numFichier
	 * @param folder
	 * @throws IOException
	 */
	public void init(int numFichier, String folder) throws IOException {
		BufferedReader in = null;
		try {
			String filePath = new File("").getAbsolutePath() + "/src/" + folder + "/init" + numFichier +".txt";
			File fichier = new File(filePath);
			in = new BufferedReader(new FileReader(fichier));
			s = Integer.parseInt(in.readLine());
			k = Integer.parseInt(in.readLine());
			bocaux = new ArrayList<Integer>();
			String line3 = in.readLine();
			V = new LinkedHashMap<Integer, Integer>(k);
			temp = new LinkedHashMap<Integer[], Integer>();
			if(Solution.isNum(line3)) {
				d = Integer.parseInt(line3);
				for(int i=0; i<k; i++) {
					V.put((int)Math.pow(d, i), 0);
					bocaux.add((int)Math.pow(d, i));
				}
			}else {
				String[] arr = line3.split(" ");
				for(String str : arr) {
					V.put(Integer.parseInt(str), 0);
					bocaux.add(Integer.parseInt(str));
				}
			}
		} catch(FileNotFoundException e) {
			System.out.println("fichier init" + numFichier + ".txt pas trouve");
		}
	}
	
	/***
	 * Algorithme de recherche exhaustive
	 * @param k : nombre de bocal disponible
	 * @param tab : system de bocaux
	 * @param s : poids de confiture
	 * @return
	 */
	public int RechercheExhaustive(int k, ArrayList<Integer> tab, int s) {
		int nbCount = 0, x;
		if(s<0) return Integer.MAX_VALUE - 1;
		if(s == 0) return 0;
		nbCount = s;
		for(int i=0; i<tab.size(); i++) {
			x = RechercheExhaustive(k, tab, s-tab.get(i));
			if(x+1 < nbCount) {
				nbCount = x+1;
			}
		}
		return nbCount;
	}
	
	public int min(int a, int b) {
		if(a>b)
			return b;
		return a;
	}
	
	/***
	 * Algorithme de programmation dynamique de façon récursive
	 * @param k : nombre de bocal disponible
	 * @param tab : system de bocaux
	 * @param s : poids de confiture
	 * @return nombre minimum de bocal
	 */
	public int AlgoProgDynRec(int k, ArrayList<Integer> tab, int s) {
		if(s == 0) return 0;
		if(s<0) return Integer.MAX_VALUE-1;//ne pas deborder la limite de integer;
		if(k==1) return s;
		int left, right;
		Integer[] indice_left = new Integer[2];
		indice_left[0] = s;
		indice_left[1] = k-1;
		if(temp.containsKey(indice_left)) {
			left = temp.get(indice_left);
		}else {
			left = AlgoProgDynRec(k-1, tab, s);
			temp.put(indice_left, left);
		}
		Integer[] indice_right = new Integer[2];
		indice_right[0] = s-tab.get(k-1);
		indice_right[1] = k;
		if(temp.containsKey(indice_right)) {
			right = temp.get(indice_right);
		}else {
			right = AlgoProgDynRec(k, tab, s-tab.get(k-1))+1;
			temp.put(indice_right, right);
		}
		return min(left, right);
	}
	
	/***
	 * Algorithme de programmation dynamique de façon itérative
	 * teste toutes les combinaisons possibles
	 * @param k : nombre de bocal disponible
	 * @param tab : system de bocaux
	 * @param s : poids de confiture
	 * @return nombre minimum de bocal
	 */
	public int AlgoProgDynIter(int k, ArrayList<Integer> tab, int s) {
		ArrayList<Integer> opt = new ArrayList<Integer>();
		System.out.println(Arrays.toString(tab.toArray()));
		opt.add(0, 0);
		for(int a=1; a<=s; a++) {
			int j = k-1;
			while(tab.get(j) > a) {
				j--;
			}
			int min = (a%tab.get(j) == 0) ? a/tab.get(j) : a;
			int left = 1;
			int right = a-1;
			while(left <= right) {
				int nb1 = opt.get(right);
				int nb2 = opt.get(left);
				if(min > nb1+nb2)
					min = nb1+nb2;
				left++;
				right--;
			}
			opt.add(a, min);
		}
		return opt.get(s);
	}
	
	/***
	 * Algorithme de programmation dynamique de façon itérative plus optimal
	 * teste que m(s, i-1) et m(s-V[i]) + 1
	 * @param k : nombre de bocal disponible
	 * @param tab : system de bocaux
	 * @param s : poids de confiture
	 * @return nombre minimum de bocal
	 */
	public int AlgoProgDynIterBis(int k, ArrayList<Integer> tab, int s) {
		M = new int[k][s+1];
		for(int i=0; i<k; i++) {
			for(int j=0; j<=s; j++) {
				if(j == 0) M[i][j] = 0;
				if(i == 0) M[i][j] = j/tab.get(i);
				else {
					if(j >= tab.get(i)) {
						M[i][j] = Math.min(M[i-1][j], M[i][j-tab.get(i)] + 1);
					}else {
						M[i][j] = M[i-1][j];
					}
				}
			}
		}
		return M[k-1][s];
	}
	
	/***
	 * Algorithme glouton
	 * @param k : nombre de bocal disponible
	 * @param tab : system de bocaux
	 * @param s : poids de confiture
	 * @return nombre minimum de bocal
	 */
	public int AlgoGlouton(int k, ArrayList<Integer> tab, int s) {
		if(k==1) {
			V.put(1, s);
			return s;
		} 
		int nb = s/tab.get(k-1);
		V.put(tab.get(k-1), nb);
		int reste = s%tab.get(k-1);
		nb += AlgoGlouton(k-1, tab, reste);
		return nb;
	}
	
	/**
	 * tester si un system bocal est glouton compatible
	 * @param k : nombre de bocal disponible
	 * @param bocaux : system de bocaux
	 * @return si un system bocal est glouton compatible
	 */
	public boolean TestGloutonCompatible(int k, ArrayList<Integer> bocaux) {
		if(k >= 3) {
			for(int s = bocaux.get(2)+2; s<=bocaux.get(k-2)+bocaux.get(k-1)-1; s++) {
				for(int j=0; j<k; j++) {
					if(bocaux.get(j) < s && AlgoGlouton(k, bocaux, s)>1+AlgoGlouton(k, bocaux, s-bocaux.get(j))) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param k1 : nombre de bocal disponible
	 * @return la probalilité d'être glouton compatible
	 */
	public double ProbaCompatible(int k1) {
		Random r=new Random();
		int oui=0;
		for(int i=0;i<10000;i++) {
			ArrayList<Integer> tab1= new ArrayList<Integer>();
			tab1.add(1);
			while(tab1.size()<k1) {
				int a=r.nextInt(98)+2;
				if(!tab1.contains(a))
					tab1.add(a);
			}
			Collections.sort(tab1);
			if(TestGloutonCompatible(k1, tab1)) oui++;
			
		}
		return (double)oui/(double)10000;
	}
	
	/**
	 * trouver la solution exacte par AlgoProgDynamiqueBis
	 * @param M : la matrice de taille k*s
	 * @param tab : system de bocaux
	 */
	public void optimalProgDyn(int[][] M,ArrayList<Integer> tab){
        int k=M.length-1;
        int s=M[0].length-1;
        while(k>=1 &&s>0) {
            if(M[k-1][s]>M[k][s]) {
                V.put(tab.get(k), V.get(tab.get(k))+1);
                s=s-tab.get(k);
            }
            else {
                k=k-1;
            }
        }
        if(s>0)
        V.put(1, V.get(tab.get(k))+s);
    }

	/**
	 * tester le temps d'exécution de chaque algo
	 * @param numAlgo : numero d'algo à tester
	 * @param k : nombre de bocal disponible
	 * @param tab : system de bocaux
	 * @param s : poids de confiture
	 * @return tableau à 2 dimension. le premier étant le nombre min, le deuxième étant le temps d'exécution de algo choisi
	 */
	public int[] resoudre(int numAlgo, int k, ArrayList<Integer> tab, int s) {
		int[] res = new int[2];
		long start = System.nanoTime();
		long end = 0;
		int nb = 0;
		switch(numAlgo) {
			case 1:
				nb = RechercheExhaustive(k, tab, s);
				end = System.nanoTime();
				break;
			case 2:
				nb = AlgoProgDynRec(k, tab, s);
				end = System.nanoTime();
				break;
			case 3:
				nb = AlgoProgDynIter(k, tab, s);
				end = System.nanoTime();
				break;
			case 4:
				nb = AlgoProgDynIterBis(k, tab, s);
				end = System.nanoTime();
				break;
			case 5:
				nb = AlgoGlouton(k, tab, s);
				end = System.nanoTime();
				break;
		}
		res[0] = nb;
		res[1] = (int) (end - start)/100;
		return res;
	}

}
