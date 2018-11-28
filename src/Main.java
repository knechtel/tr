import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import org.antlr.v4.runtime.tree.*;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class Main {
	private static Map<String, List<String>> declaracaoMap = new HashMap<String, List<String>>();
	private static Map<String, List<String>> commandMap = new HashMap<String, List<String>>();
	private static Map<String, List<String>> recordMap = new HashMap<String, List<String>>();

	private static String chave;
	static Integer contIF = 0;
	private static String buffer;

	private static void writeFile() {
		String str = "program main;\n";

		if (recordMap != null) {
			Iterator<String> itr2 = recordMap.keySet().iterator();
			while (itr2.hasNext()) {
				String key = itr2.next();

				str = str + "type" + "\n";
				str = str + key + " = record " + "\n";

				System.out.println(key);

				List<String> list = recordMap.get(key);
				for (String var : list) {
					String subVar = var.replace("private", "");

					if (subVar.contains("String")) {

						String subVarString = subVar.replace("String", "");
						String subVarStringV = subVarString.replace(";", "");
						str = str + subVarStringV + ": string; \n";
					}
					if (subVar.contains("Integer")) {

						String subVarString = subVar.replace("Integer", "");
						String subVarStringV = subVarString.replace(";", "");
						str = str + subVarStringV + ": integer; \n";
					}
					if (subVar.contains("char")) {

						String subVarString = subVar.replace("char", "");
						String subVarStringV = subVarString.replace(";", "");
						str = str + subVarStringV + ": char; \n";
					}
					if (subVar.contains("boolean")) {

						String subVarString = subVar.replace("boolean", "");
						String subVarStringV = subVarString.replace(";", "");
						str = str + subVarStringV + ": boolean; \n";
					}
					if (subVar.contains("float")) {

						String subVarString = subVar.replace("float", "");
						String subVarStringV = subVarString.replace(";", "");
						str = str + subVarStringV + ": float; \n";
					}
				}
				System.out.println(list + "<<<");
				str = str + "end;" + "\n\n";
			}

		}

		// assinatura da funcao
		String variable = "var ";

		System.out.println("montando meu teste");
		Iterator<String> itr2 = commandMap.keySet().iterator();
		while (itr2.hasNext()) {
			String key = itr2.next();
			chave = key;
			List<String> list = commandMap.get(key);
			int frequency = 0;
			String stringFre = "";
			if (list != null) {
				for (String string : list) {
					if (string.contains("if")) {
						frequency++;

					}

				}
			}
			
			System.out.println(stringFre);
			// frequency = new StringTokenizer(stringFre, "if\\(").countTokens();

			// JOptionPane.showMessageDialog(null, "frequencia "+frequency+" "+chave);
			if (list != null) {
				for (String string : list) {

					Pattern r1 = Pattern.compile("String|Integer|int|[A-za-z]+");
					Matcher m1 = r1.matcher(string);
					boolean dealInt = false;
					boolean dealString = false;
					while (m1.find()) {
						String min = m1.group();

						if (dealInt) {

							variable = variable + min + " :integer;";

							dealInt = false;
						}

						if (min.equals("int") || min.equals("Integer")) {
							dealInt = true;
						} else {
							dealInt = false;
						}

						if (dealString) {
							variable = variable + min + " :string;";
							dealString = false;
						}

						if (min.equals("String")) {
							dealString = true;
						} else {
							dealString = false;
						}
					}
					
					
					
				}

			}

			// assinatura

			Pattern r1 = Pattern.compile("\\,|String|Integer|int|[A-za-z]+");
			Matcher m1 = r1.matcher(key);
			boolean dealInt = false;
			boolean dealString = false;
			while (m1.find()) {
				String min = m1.group();

				if (dealInt) {
					key = key.replaceAll("Integer" + min, min + " : integer");
					
				}
				if (dealString) {
					key = key.replaceAll("String" + min, min + " : string");
					
				}

				if (min.equals("int") || min.equals("Integer")) {
					dealInt = true;
				} else {
					dealInt = false;
				}

				if (min.equals("String") ) {
					dealString = true;
				} else {
					dealString = false;
				}
			}

			str = str + key + ";\n";

			// declaracao de variaveis

			// tirei o var daqui

			if (variable.length() > 4) {
				str = str + "" + variable + "\n";
			}
			Integer ifCont = 0;
			Integer whileCont = 0;
			Integer forCont = 0;
			str = str + "Begin\n";

			String matchAux = "";
			// codigo -> dentro da funcao
			if (list != null) {
				for (String string : list) {
					
					if(string.contains(");")) {
						
						matchAux = matchAux+Scanner.doScanner(string);
						
					
					}
					
					//rever esse if
//					if(string.contains("println")) {
//						
//						matchAux = matchAux+Scanner.doScanner(string);
//						
//					
//					}
					
					if(string.contains("for")) {
						if(forCont==0) {
						matchAux = matchAux+Scanner.doScanner(string);
						}
						forCont++;
					}

					if (string.contains("while")) {

						if (whileCont == 0) {
							matchAux = matchAux+Scanner.doScanner(string);
							if(matchAux.contains("do")) {
								matchAux=	matchAux.replaceAll("do", "do\n");
							}
						}
						whileCont++;
					}
					
					if (string.contains("if")) {

						if (ifCont == 0) {
							matchAux = matchAux+Scanner.doScanner(string);
						}
						ifCont++;
						
					}

				}

			}

			str = str + matchAux;
			matchAux = "";

			ifCont = 0;
			frequency = 0;
			str = str + "\nEnd;\n\n";

			variable = "var ";
			System.out.println(key);
		}

		str = str + "\nBegin\n";
		str = str + "\n";
		str = str + "End\n";
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/src/" + "main.pascal"));
			writer.write(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {

		String inputFile = null;
		inputFile = System.getProperty("user.dir") + "/src/Test.java.test";
		InputStream is = System.in;
		if (inputFile != null) {
			is = new FileInputStream(inputFile);
		}
		ANTLRInputStream input = new ANTLRInputStream(is);

		Java8Lexer lexer = new Java8Lexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		Java8Parser parser = new Java8Parser(tokens);
		ParseTree tree = parser.compilationUnit(); // parse

		ParseTreeWalker walker = new ParseTreeWalker();
		Java8DoListener teste = new Java8DoListener(parser);
		walker.walk(teste, tree);
		commandMap = teste.getMap();
		recordMap = teste.getMapRecord();

		writeFile();

	}

}