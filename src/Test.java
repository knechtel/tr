import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	static List<String> listParametros = new ArrayList<String>();
	static List<String> lisObjetos = new ArrayList<String>();

	public static String sublexerDeclare(String string, String obj, String padrao) {
		Pattern r = Pattern.compile(obj + padrao);
		Matcher m = r.matcher(string);
		boolean isFind = false;
		boolean isInst = false;
		String troca = "";
		Integer cont = 0;
		Integer contAux = 0;
		Integer contParametro = 0;
		Integer i = 0;

		boolean flagVirgula = false;
		while (m.find()) {
			String min = m.group();

			cont++;
			if (contAux != 0 && !flagVirgula) {

				String[] arrayAuxParent = string.split("\\(");

				if (arrayAuxParent.length > 1) {
					
					
					String[] auxVirgula = arrayAuxParent[1].split(",");
					String stAux = auxVirgula[0].replace(obj, listParametros.get(i++));
					
					string = arrayAuxParent[0] + "(" + stAux + "," + auxVirgula[1];

				}
				contAux = 0;
			}

			if (isFind) {

				isFind = false;
			}
			if (min.equals("(")) {
				// System.err.println(min);
				isFind = true;

			}

			if (min.equals(obj) && cont != 1) {

				contAux = cont;
			}

			if (min.contains(",")) {
				flagVirgula = true;
			}
		}

		// if(!min.equals("("))
		// System.out.println(min);

		return string;
	}

	public static String sublexer(String string, String obj, String padrao) {
		Pattern r = Pattern.compile(obj + padrao);
		Matcher m = r.matcher(string);
		boolean isFind = false;
		boolean isInst = false;
		String troca = "";
		Integer cont = 0;
		Integer contAux = 0;
		while (m.find()) {
			String min = m.group();
			cont++;
			if (contAux != 0) {
				listParametros.add(min);
				contAux = 0;
			}

			if (isFind) {

				isFind = false;
			}
			if (min.equals("(")) {
				// System.err.println(min);
				isFind = true;

			}
			if (min.equals(obj) && cont != 1) {
				System.err.println(cont);
				contAux = cont;

			}

			// if(!min.equals("("))
			// System.out.println(min);
		}
		return string;
	}

	public static void main(String[] args) {

		String remove = "";
		String s = " FootestandoInsere(Foofoo,Fooaux)";

		remove = sublexer(s, "Foo", "|[A-Z][a-z]+|[a-z]+|\\(|,");

		// s=s.replaceAll(remove, ": foo");
		System.out.println(s);
		System.err.println("INICIO _________________________");
		for (String str : listParametros) {
			// foo
			System.out.println(str + "   do");
			System.out.println(sublexerDeclare(s, "Foo", "|[A-Z][a-z]+|[a-z]+|\\(|,"));
		}
		System.err.println("INICIO _________________________objetos");
		for (String string : lisObjetos) {
			// FOO
			System.out.println("FOO" + string);
		}

	}
}
