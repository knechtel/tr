import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import org.antlr.v4.runtime.tree.*;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import sun.security.x509.IssuingDistributionPointExtension;

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

	private static String methodHead = "";
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

			if (list != null) {
				for (String string : list) {

					Pattern r1 = Pattern.compile("\\,|String|Integer|int|[A-za-z]+");
					Matcher m1 = r1.matcher(string);
					boolean dealInt = false;
					boolean dealString = false;
					String[] strVec;
					boolean retorno = false;
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

				// assinatura

				Pattern r2 = Pattern.compile("\\(|[a-z]+|String|Integer|int|[A-za-z]+");
				Matcher m2 = r2.matcher(key);
				boolean dealInt = false;
				boolean dealString = false;
				boolean dealChar = false;
				boolean dealClass = false;
				boolean dealFloat = false;
				boolean dealDouble = false;
				boolean isParameter = false;
				while (m2.find()) {
					String min = m2.group();
					System.err.println(min);

					String[] strVec;
					boolean retorno = false;

					if (dealInt && !retorno) {
						key = key.replaceAll("Integer" + min, min + " : integer");
						System.err.println(key);

					}
					if (dealString) {
						key = key.replaceAll("String" + min, min + " : string");

					}

					if (min.equals("int") || min.equals("Integer") && isParameter) {
						dealInt = true;
					} else {
						dealInt = false;
					}

					if (min.equals("String") && retorno) {
						dealString = true;
					} else {
						dealString = false;
					}
					if (min.equals("char") && retorno) {
						dealChar = true;
					} else {
						dealChar = false;
					}
					if (min.equals("double") || min.equals("Double") || min.equals("double") && retorno) {
						dealDouble = true;
					} else {
						dealDouble = false;
					}
					if (dealClass) {
						key = key.replaceAll(min, min + " : SIMMM");

					}
					if (min.equals("(")) {
						isParameter = true;
					}

					if (key.contains("void")) {

						key = key.replaceAll("void", "");
					}

					if (key.contains("(")) {

						// JOptionPane.showMessageDialog(null, key);
						if (key.split("\\(").length > 1) {
							strVec = key.split("\\(");
							if (strVec[0].contains("Integer")) {
								retorno = true;
							} else {
								retorno = false;
							}
							if (strVec[0].contains("String")) {
								retorno = true;
							} else {
								retorno = false;
							}
							if (strVec[0].contains("char")) {
								retorno = true;
							} else {
								retorno = false;
							}

							if (strVec[0].contains("double") || strVec[0].contains("Double")) {
								retorno = true;
							} else {
								retorno = false;
							}
						}
					}

					for (String s : Java8DoListener.listClass) {

						if (key.contains("(") && min.contains(s)) {
							String troca = Test.sublexer(key, s, "|\\(|[a-z]+");
							System.out.println(troca + "==troca");
//							key = key.replaceAll(troca, " :"+s);
						}
					}

				}
			} else {
				// assinatura caso o método esteja vazio

				Pattern r2 = Pattern.compile("[a-z]+|String|Integer|int|[A-za-z]+");
				Matcher m2 = r2.matcher(key);
				boolean dealInt = false;
				boolean dealString = false;

				while (m2.find()) {
					String min = m2.group();

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

					if (min.equals("String")) {
						dealString = true;
					} else {
						dealString = false;
					}
//					for (String s : Java8DoListener.listClass) {
//						System.out.println(">>>>>" + s);
//						System.err.println("<<<<<<"+min);
//						System.out.println(">>>>>>"+key);
//						if (key.contains("(")&& min.contains(s)) {
//							String troca =Test.sublexer(key,s, "|\\(|[a-z]+");
//							key = key.replaceAll(troca, " :"+s);
//						}
//					}
				}

			}

			String[] strTrataRetorno = key.split("\\(");

			String vlrInt = "";
			boolean isInt = false;
			boolean isString = false;
			boolean isChar = false;
			boolean isDouble = false;
			if (strTrataRetorno.length > 1) {
				if (strTrataRetorno[0].contains("Integer")) {
					key = key.replaceAll("Integer", "");

					isInt = true;
				} else {
					isInt = false;
				}
				if (strTrataRetorno[0].contains("String")) {
					key = key.replaceAll("String", "");

					isString = true;
				} else {
					isString = false;
				}
				if (strTrataRetorno[0].contains("char")) {
					key = key.replaceAll("char", "");

					isChar = true;
				} else {
					isDouble = false;
				}
				if (strTrataRetorno[0].contains("double") || strTrataRetorno[0].contains("Double")) {
					key = key.replaceAll("double", "");

					isDouble = true;
				} else {
					isDouble = false;
				}
			}

			if (isDouble) {
				str = str + "function " + key + ":double;\n";
			} else if (isChar) {
				str = str + "function " + key + ":char;\n";
			} else if (isString) {
				str = str + "function " + key + ":string;\n";
			} else if (isInt) {
				str = str + "function " + key + ":integer;\n";
			} else {
				str = str + "procedure " + key + ";\n";
			}
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

					if (string.contains(");")) {

						matchAux = matchAux + Scanner.doScanner(string);

					}

					if (string.contains("\\++")) {
						matchAux = matchAux + Scanner.doScanner(string);
					}
					if (string.contains("=")) {

						matchAux = matchAux + Scanner.doScanner(string);

					}

					// rever esse if
//					if(string.contains("println")) {
//						
//						matchAux = matchAux+Scanner.doScanner(string);
//						
//					
//					}

					if (string.contains("for")) {
						if (forCont == 0) {
							matchAux = matchAux + Scanner.doScanner(string);
						}
						forCont++;
					}

					if (string.contains("while")) {

						if (whileCont == 0) {
							if (string.contains("==")) {

								string = string.replace("==", "=");
							}
							if (string.contains("!=")) {

								string = string.replace("==", "<>");
							}
							matchAux = matchAux + Scanner.doScanner(string);
							if (matchAux.contains("do")) {
								matchAux = matchAux.replaceAll("do", "do\n");
							}
						}
						whileCont++;
					}

					if (string.contains("if")) {

						if (ifCont == 0) {
							if (string.contains("==")) {

								string = string.replace("==", "=");
							}
							if (string.contains("!=")) {

								string = string.replace("==", "<>");
							}
							matchAux = matchAux + Scanner.doScanner(string);
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

		List<Block> functionList = new ArrayList<Block>();
		String addFunction = null;
		boolean isBuild = true;

		for (String str1 : teste.listaAssinatura) {
			Iterator<String> itr2 = commandMap.keySet().iterator();
			while (itr2.hasNext()) {
				String key = itr2.next();

				String[] antKey = key.split("\\(");

				if (str1.contains(antKey[0])) {
					Block b = new Block();
					b.assinatura = str1;
					b.assinaturaAntiga=key;
					b.list = commandMap.get(key);
					functionList.add(b);
					
					System.out.println(str1);

					System.err.println(key);

					isBuild = false;

				} else {

				}
			}

		}
		for (Block b : functionList) {
			commandMap.remove(b.assinaturaAntiga);
		}
		for (Block b : functionList) {
			commandMap.put(b.assinatura, b.list);
		}
			
		System.out.println("WRITE ()");
		writeFile();

	}

}