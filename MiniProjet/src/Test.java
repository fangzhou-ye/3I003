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
			String filePath = new File("").getAbsolutePath() + "/src/KT/init" + i + ".txt";
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
			String filePath = new File("").getAbsolutePath() + "/src/ST/init" + i + ".txt";
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
		int[] res1 = s.resoudre(3, s.getK(), s.getBocaux(), s.getS());
		int[] res2 = s.resoudre(4, s.getK(), s.getBocaux(), s.getS());
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
		int[] res1 = s.resoudre(3, s.getK(), s.getBocaux(), s.getS());
		int[] res2 = s.resoudre(4, s.getK(), s.getBocaux(), s.getS());
		int[] r = new int[3];
		r[0] = s.getS();
		r[1] = res1[1];
		r[2] = res2[1];
		System.out.println(res1[0] == res2[0]);
		return r;
	}
	
	public static void output_KT_Data(int nb_test) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("output_KT.txt"));
		for(int i=1; i<=nb_test; i++) {
			int[] tab = test_KT(i, "KT");
			String str = tab[0] + " " + tab[1] + " " + tab[2] + " \n";
			System.out.println(str);
			writer.write(str);
		}
		writer.close();
	}
	
	public static void output_ST_Data(int nb_test) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("output_ST.txt"));
		for(int i=1; i<=nb_test; i++) {
			int[] tab = test_ST(i, "ST");
			String str = tab[0] + " " + tab[1] + " " + tab[2] + " \n";
			System.out.println(str);
			writer.write(str);
		}
		writer.close();
	}
	
	public static void blabla(Solution m) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("abc.text"));
		for (int a=1;a<10;a++) {
			Random r=new Random();
			int nb =10;
			int oui=0;
			int valide=0;
			int glouton=0;
			int optimal=0;
			for(int i=0;i<100;i++) {
				ArrayList<Integer> tab= new ArrayList<Integer>();
				tab.add(1);
				for(int j=1;j<nb;j++) {
					tab.add(r.nextInt(40)+2);
				}
				Collections.sort(tab);
				glouton=m.AlgoGlouton(nb, tab, a*10);
				optimal=m.AlgoProgDynIter(nb, tab, a*10);
				
				if(glouton==optimal) valide++; 
				/*System.out.println("glouton: "+glouton +" optimal: " +optimal);*/
		}
		writer.write(a+" "+valide+" \n");
		System.out.println("a: "+ a+" valide "+ valide);
		}
		writer.close();
	}

	public static void main(String[] args) throws IOException {
		
		/*
		int k = 20;
		int d = 3;
		int s = 20000;
		gene_KT_Sample(k, d, s);
		output_KT_Data(k);
		*/
		
		
		/*
		int k = 20;
		int d = 3;
		int interval = 1000;
		int nb_test = 20;
		gene_ST_Sample(k, d, interval);
		output_ST_Data(nb_test);
		*/
		
		// Test algo dynamique iteratif
		/*
		Solution sol = new Solution(0, "Test");
		int[] res = sol.resoudre(4, sol.getK(), sol.getBocaux(), sol.getS());
		int nb = res[0];
		int temps = res[1];
		System.out.println(sol);
		System.out.println("nb: " + nb + " temps: " + temps + "nano-s");
		*/
		
		/*
		Solution sol = new Solution(0, "Test");
		int[] res = sol.resoudre(3, sol.getK(), sol.getBocaux(), sol.getS());
		int nb = res[0];
		int temps = res[1];
		System.out.println(sol);
		System.out.println("nb: " + nb + " temps: " + temps + "nano-s");
		*/
	}

}
