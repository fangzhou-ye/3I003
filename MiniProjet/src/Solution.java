import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Solution {
	
	private int s;
	private int k;
	private Map<Integer, Integer> V;
	private ArrayList<Integer> bocaux;
	
	public Solution(File fichier) throws IOException{
		init(fichier);
	}
	
	public String toString() {
		String s = "";
		s += "s:" + this.s + "\n";
		s += "k:" + this.k + "\n";
		s += "[ ";
		for(Integer key : V.keySet()) {
			s += "(" + key + ":" + V.get(key) + ") ";
		}
		return s + "]";
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
	
	public void init(File fichier) throws IOException {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(fichier));
			s = Integer.parseInt(in.readLine());
			k = Integer.parseInt(in.readLine());
			V = new LinkedHashMap<Integer, Integer>(k);
			bocaux = new ArrayList<Integer>();
			String[] arr = in.readLine().split(" ");
			for(String str : arr) {
				V.put(Integer.parseInt(str), 0);
				bocaux.add(Integer.parseInt(str));
			}
		} catch(FileNotFoundException e) {
			System.out.println("fichier texte pas trouve");
		}
	}
	
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
	
	// supposons que tab est trié par ordre croissant selon le poids
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

	public int[] searchTime(int numAlgo, int k, ArrayList<Integer> tab, int s) {
		int[] res = new int[2];
		long start = System.currentTimeMillis();
		long end = 0;
		int nb = 0;
		switch(numAlgo) {
			case 1:
				nb = RechercheExhaustive(k, tab, s);
				end = System.currentTimeMillis();
				break;
			case 3:
				nb = AlgoGlouton(k, tab, s);
				end = System.currentTimeMillis();
				break;
		}
		res[0] = nb;
		res[1] = (int) (end - start);
		return res;
	}

}
