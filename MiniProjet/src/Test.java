import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Test {
	
	public static void gene_KT_Sample(int k, int d, int s) throws IOException {
		for(int i=1; i<=k; i++) {
			String filePath = new File("").getAbsolutePath() + "/src/KT" + "/init" + i + ".txt";
			File fichier = new File(filePath);
			BufferedWriter writer = new BufferedWriter(new FileWriter(fichier));
			writer.write(String.valueOf(s) + "\n");
			writer.write(String.valueOf(i) + "\n");
			writer.write(String.valueOf(d));
			writer.close();
		}
	}
	
	public static void gene_ST_Sample(int k, int d, int interval) throws IOException {
		int s = 0;
		for(int i=1; i<=20; i++) {
			String filePath = new File("").getAbsolutePath() + "/src/ST" + "/init" + i + ".txt";
			File fichier = new File(filePath);
			BufferedWriter writer = new BufferedWriter(new FileWriter(fichier));
			s += interval;
			writer.write(String.valueOf(s) + "\n");
			writer.write(String.valueOf(k) + "\n");
			writer.write(String.valueOf(d));
			writer.close();
		}
	}
	
	public static int[] test_KT(int numFichier, String folder) throws IOException {
		Solution s = new Solution(numFichier, folder);
		int[] res1 = s.resoudre(4, s.getK(), s.getBocaux(), s.getS());
		int[] res2 = s.resoudre(5, s.getK(), s.getBocaux(), s.getS());
		int[] r = new int[3];
		r[0] = s.getK();
		r[1] = res1[1];
		r[2] = res2[1];
		System.out.println(res1[0] == res2[0]);
		return r;
	}
	
	public static int[] test_ST(int numFichier, String folder) throws IOException {
		Solution s = new Solution(numFichier, folder);
		System.out.println(s);
		int[] res1 = s.resoudre(4, s.getK(), s.getBocaux(), s.getS());
		int[] res2 = s.resoudre(5, s.getK(), s.getBocaux(), s.getS());
		int[] r = new int[3];
		r[0] = s.getS();
		r[1] = res1[1];
		r[2] = res2[1];
		System.out.println(res1[0] == res2[0]);
		return r;
	}
	
	public static void output_KT_Data(int nb_test, int d) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("output_KT" + d + ".txt"));
		for(int i=1; i<=nb_test; i++) {
			System.out.println("KT");
			int[] tab = test_KT(i, "KT");
			String str = tab[0] + " " + tab[1] + " " + tab[2] + " \n";
			System.out.println(str);
			writer.write(str);
		}
		writer.close();
	}
	
	public static void output_ST_Data(int nb_test, int d) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("output_ST" + d + ".txt"));
		for(int i=1; i<=nb_test; i++) {
			int[] tab = test_ST(i, "ST");
			String str = tab[0] + " " + tab[1] + " " + tab[2] + " \n";
			System.out.println(str);
			writer.write(str);
		}
		writer.close();
	}
	
	/**
	 * tester de k 3 à 9, la probalibité d'être glouton compatible dans chaque system, ecrit dans le fichier.
	 * @param m
	 * @throws IOException
	 */
	public static void proba(Solution m) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter("proba.text"));
		for(int i=3;i<10;i++) {
			writer.write(i+" "+m.ProbaCompatible(i)+"\n");
			
		}
		writer.close();
	}
	
	/**
	 * Ce qu'il fallait faire dans exo14 XD
	 * @param m
	 * @throws IOException
	 */
	public static void exercice14(Solution m) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("abc.text"));
			Random r=new Random();
			int nb_test=100;
			int f=10;
			int k =3;
			int non_compatible=0;
			int glouton=0;
			int optimal=0;
			int somme=0;
			int ecart_max=Integer.MIN_VALUE;
			double ecart_moyen_somme = 0;
			double ecart_moyen=0;
			double ecart_moyen_moyen;
			//génerer 100 systemes
			for(int i=0;i<nb_test;i++) {
				ArrayList<Integer> tab= new ArrayList<Integer>();
				tab.add(1);
				while(tab.size()<k) {
					int e=r.nextInt(98)+2;
					if(!tab.contains(e))
					tab.add(e);
				}
				Collections.sort(tab);
				int pmax=tab.get(k-1);
				if(!m.TestGloutonCompatible(k, tab)){
					somme=0;
					ecart_max=Integer.MIN_VALUE;
					for(int j=pmax;j<10*pmax;j++) {
						glouton=m.AlgoGlouton(k, tab, j);
						optimal=m.AlgoProgDynIterBis(k, tab, j);
						int ecart=glouton-optimal;
						ecart_max= (ecart_max>ecart)? ecart_max:ecart;
						somme+=ecart;
					}
					non_compatible++;
					ecart_moyen=(double)somme/(double)((f-1)*pmax+1);
					ecart_moyen_somme+=ecart_moyen;
					writer.write(i+ " "+ecart_max + " " + ecart_moyen+ "\n");
				}
				/*System.out.println("glouton: "+glouton +" optimal: " +optimal);*/
				else writer.write(i+ " "+0 + " " + 0+ "\n");
			}
			
			ecart_moyen_moyen=ecart_moyen_somme/(double)non_compatible;
			System.out.println("non_compatible " + non_compatible + "  ecart_moyen_moyen " +ecart_moyen_moyen + "\n");
			
		
		writer.close();
	}

	public static void main(String[] args) throws IOException {
		
		// test RechercheExhaustive qui prend tres longtemps, il ne permet pas de trouver la solution exacte
		/*
		Solution sol = new Solution(1, "Test");
		int[] res = sol.resoudre(1, sol.getK(), sol.getBocaux(), sol.getS());
		System.out.println("nb: " + res[0] + " time: " + res[1]);
		System.out.println(sol);
		*/
		
		// test AlgoProgDynBis
		/*
		Solution sol = new Solution(0, "Test");
		int[] res = sol.resoudre(4, sol.getK(), sol.getBocaux(), sol.getS());
		sol.optimalProgDyn(sol.getM(), sol.getBocaux());
		System.out.println("nb: " + res[0] + " time: " + res[1]);
		System.out.println(sol);
		*/
		
		// test AlgoGlouton
		/*
		Solution sol = new Solution(0, "Test");
		int[] res = sol.resoudre(5, sol.getK(), sol.getBocaux(), sol.getS());
		System.out.println("nb: " + res[0] + " time: " + res[1]);
		System.out.println(sol);
		*/
		
		// générer des données de teste de glouton compatible, ecrit des resultat dans un texte
		/*
		Solution ss = new Solution(1, "Test");
		proba(ss);
		*/
				
		// tester la probalilité de glouton compatible
		/*
		Solution s = new Solution();
		exercice14(s);
		*/
				
		// générer des données de teste, permettant de tracer la courbe par Octave.
		// Il faut avoir la repertoire "KT" et "ST" dans le repertoire "src" qui stockent
		// les données de teste.
		/*
		int kk = 6;
		int dd = 3;
		int interval = 20000;
		int nb_test = 20;
		int d;
		for(d=2; d<=4; d++) {
			int s = 50000;
			gene_KT_Sample(nb_test, d, s);
			output_KT_Data(nb_test, d);
			gene_ST_Sample(kk, dd, interval);
			output_ST_Data(nb_test, d);
		}
		*/
		
	}

}
