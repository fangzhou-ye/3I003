import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Test {

	public static void main(String[] args) throws IOException {
		String filePath = new File("").getAbsolutePath() + "/src/init.txt";
		File fichier = new File(filePath);
		Solution m = new Solution(fichier);
		System.out.println(m);
		int k = m.getK();
		int s = m.getS();
		ArrayList<Integer> tab = m.getBocaux();
		
		int[] resultat = m.searchTime(3, k, tab, s);
		System.out.println("resultat: " + resultat[0]);
		System.out.println("temps: " + resultat[1] + "ms");
		System.out.println(m);
	}

}
