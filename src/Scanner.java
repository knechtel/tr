import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class Scanner {

	static String doScanner(String string) {
		boolean inFor = false;
		boolean inInteger = false;
		Pattern r = Pattern.compile("\\++|<=|\\/|\\*|\\+|=|\\d|\\,|\\.|<|=|while|\\}|\\{|:=|if|then|false|end|begin|\\(|"
				+ "\\)|if|[0-9]+;|String*[a-z]*\\;|Integer|int*[a-z]*\\;|[a-z]*;|[a-z]*" + "|[A-Za-z]*");

		Matcher m = r.matcher(string);
		System.err.println("Dofunction ------------");
		List<String> listaLinha = new ArrayList<String>();
		String match = "";
		String matchAux = "";

		String saveFlag = "";

		boolean doComp = false;
		while (m.find()) {

			match = m.group();

			if (!match.equals("")) {
				if(match.contains("++")&&inFor) {
				
					
				}else if (match.contains("=") && inFor) {
					if(!match.contains("<"))
					match = match.replaceAll("=", ":=");
					matchAux = matchAux + match;
				} else if (match.contains(";") && inFor) {
					match = match.replaceAll(";", "");
					matchAux = matchAux + match;
				} else if (match.equals("(") && inFor) {
					matchAux = matchAux + " ";
				} else if (match.equals(saveFlag)) {
				//	matchAux = matchAux + " do";
				} else if (match.equals(")") && inFor) {
					matchAux = matchAux + " do";
					inFor = false;
					
				} else if (inInteger) {
					saveFlag = match;
					matchAux = matchAux + match;
					inInteger = false;
				} else if (match.contains("(") && inFor) {
					matchAux = matchAux + match;
				} else if (match.equals("<")) {
					if (inFor) {
						matchAux = matchAux + match.replaceAll("<", " to ");

					}

				} else if (match.equals("Integer")) {
					inInteger = true;
					System.err.println("NAO ENTROU AQUI !!!");
				} else if (match.contains("for")) {
					///tira aqui se nao funcionar
					saveFlag="";
					inFor = true;
					inInteger=false;
					matchAux = matchAux + match;
				} else if (match.contains("}")) {
					matchAux = matchAux + "end" + "\n";
				} else if (match.contains("{")) {
					matchAux = matchAux + " \nbegin" + "\n";
				} else if (match.contains("{")) {
					saveFlag="";
					matchAux = matchAux + " do\nbegin" + "\n";
				} else if (match.contains(";")) {
					matchAux = matchAux + match + "\n";
				} else if (match.equals("end")) {
					matchAux = matchAux + match + "\n";
				} else if (match.equals("begin")) {
					matchAux = matchAux + match + "\n";
				} else if (match.equals("then")) {
					matchAux = matchAux + match + "\n";
				} else {
					matchAux = matchAux + match;
				}
			}

			System.out.println(match);
		}
		System.out.println(matchAux);
		return matchAux;
	}

	public static void main(String[] args) {

		boolean inFor = false;
		boolean inInteger = false;

		String string = "if(false)and(true)thenbegina:=7;if(false)then beginendif(false)thenbeginSystem.out.println();endend"
				+ "for(Integer i=0;i<10;i++){} if(false) {} for(Integer i=0;i<100;i++){} if(a=b){} ";
		Pattern r = Pattern.compile("\\.|<|=|\\{|\\}|:=|if|then|false|end|begin|\\(|"
				+ "\\)|if|[0-9]+;|String*[a-z]*\\;|Integer|int*[a-z]*\\;|[a-z]*;|[a-z]*" + "");

		Matcher m = r.matcher(string);
		System.err.println("Dofunction ------------");
		List<String> listaLinha = new ArrayList<String>();
		String match = "";
		String matchAux = "";

		String saveFlag = "";

		boolean doComp = false;
		while (m.find()) {

			match = m.group();

			if (!match.equals("")) {

				if (match.contains("=") && inFor) {
					match = match.replaceAll("=", ":=");
					matchAux = matchAux + match;
				} else if (match.contains(";") && inFor) {
					match = match.replaceAll(";", "");
					matchAux = matchAux + match;
				} else if (match.equals("(") && inFor) {
					matchAux = matchAux + " ";
				} else if (match.equals(saveFlag)&& inFor) {
			//		matchAux = matchAux + "do";
					
			
					System.err.println("aquiiiiiiiiii>>>>>> "+match);
					
				} else if (match.equals(")") && inFor) {
					inFor = false;
				} else if (inInteger) {
					saveFlag = match;
					matchAux = matchAux + match;
					inInteger = false;
				} else if (match.contains("(") && inFor) {
					matchAux = matchAux + match;
				} else if (match.equals("<")) {
					if (inFor) {
						matchAux = matchAux + match.replaceAll("<", " to ");

					}

				} else if (match.equals("Integer")) {
					inInteger = true;
					System.err.println("NAO ENTROU AQUI !!!");
				} else if (match.contains("for")) {
					///tira aqui se nao funcionar
					saveFlag="";
					inFor = true;
					inInteger=false;
					matchAux = matchAux + match;
				} else if (match.contains("}")) {
					matchAux = matchAux + "end" + "\n";
				} else if (match.contains("{")) {
					matchAux = matchAux + " \nbegin" + "\n";
				} else if (match.contains("{")) {
					saveFlag="";
					matchAux = matchAux + " \nbegin" + "\n";
				} else if (match.contains(";")) {
					matchAux = matchAux + match + "\n";
				} else if (match.equals("end")) {
					matchAux = matchAux + match + "\n";
				} else if (match.equals("begin")) {
					matchAux = matchAux + match + "\n";
				} else if (match.equals("then")) {
					matchAux = matchAux + match + "\n";
				} else {
					matchAux = matchAux + match;
				}
			}

			System.out.println(match);
		}
		System.out.println(matchAux);

		System.err.println(saveFlag);
	}

}
