import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import interfaces.ITrie;


public class Benchmark_old {
	private ITrie tree;
	
	private long elapsed = 0;
	private int nChars = 0;
	private int nWords = 0;
	
	public ITrie getTree() {
		return tree;
	}

	public long getElapsed() {
		return elapsed;
	}

	public int getnChars() {
		return nChars;
	}
	
	public int getnWords() {
		return nWords;
	}
	
	public Benchmark_old() {
	}
	
	public void initBench(ITrie t) {
		tree = t;
		elapsed = 0;
		nChars = 0;
	}
	
	private List<String> getStrings(BufferedReader br) throws IOException {
		int c;
		
		ArrayList<String> strs = new ArrayList<>();
		
		StringBuilder sb = new StringBuilder();
		while((c = br.read()) != -1) {
			if(Character.isLetter(c)) {
				nChars++;
				sb.append((char)c);
			} else {
				if(sb.length() > 0) {
					strs.add(sb.toString());
					sb = new StringBuilder();
				}
			}
		}
		
		if(sb.length() > 0) {
			strs.add(sb.toString());
			nWords++;
		}
		return strs;
	}
	
	public long insertBench(String fn) {
		long deb = 0, fin = 0;
		try {
			BufferedReader br;
			br = new BufferedReader(new FileReader(fn));
			
			ArrayList<String> words = (ArrayList<String>) getStrings(br);
			
			deb = System.nanoTime();
			
			for(String word : words) {
				tree.insert(word);
			}
			
			fin = System.nanoTime();
			
			elapsed = (fin - deb)/1000;
			
			br.close();
		} catch(IOException ex) {
			System.out.println("IOException : "+ex.getMessage());
		}
		
		return deb - fin;
	}
	
	public long lookupBench(String fn, ITrie t) {
		long deb = 0, fin = 0;
		try {
			BufferedReader br;
			br = new BufferedReader(new FileReader(fn));
			
			ArrayList<String> words = (ArrayList<String>) getStrings(br);
			
			deb = System.nanoTime();
			
			for(String word : words) {
				t.lookup(word);
			}
			
			fin = System.nanoTime();
			
			elapsed = (fin - deb)/1000;
			
			br.close();
		} catch(IOException ex) {
			System.out.println("IOException : "+ex.getMessage());
		}
		
		return deb - fin;
	}
}
