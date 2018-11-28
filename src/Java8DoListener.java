
/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
***/
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.misc.Interval;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Java8DoListener extends Java8BaseListener {
	static Integer i = 0;
	Java8Parser parser;

	private Map<String, List<String>> map = new HashMap<String, List<String>>();

	private Map<String, List<String>> mapRecord = new HashMap<String, List<String>>();

	private String key;
	
	private List<String> listClasses = new ArrayList<String>();

	static boolean isDo = false;

	private String classNamed;
	private Integer contFunction = 0;

	public Java8DoListener(Java8Parser parser) {
		this.parser = parser;
	}

	@Override
	public void enterMethodBody(Java8Parser.MethodBodyContext ctx) {
		contFunction++;
		// System.out.println("enterMethodBody " + ctx.getText());

	}

	@Override
	public void enterClassDeclaration(Java8Parser.ClassDeclarationContext ctx) {

		String nameClass = ctx.getText();

		Pattern r = Pattern.compile("class|\\{|[A-za-z]+");
		Matcher m = r.matcher(nameClass);
		boolean classFlag = false;
		while (m.find()) {
			String min = m.group();
			System.out.println();

			if (min.equals("class")) {
				classFlag = true;
			} else {

				if (min.contains("public")) {
					String str = min.replace("public", "");

					str = str.replace("class", "");
					System.out.println(" - >" + str);
					classNamed = str;

				} else {
					System.out.println(" - >" + min);

					classNamed = min;
				}
				classFlag = false;
				break;
			}

		}

		System.out.println("vai vsi");

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void enterBlockStatements(Java8Parser.BlockStatementsContext ctx) {

//		List<Java8Parser.BlockStatementContext> list = ctx.blockStatement();
//		// enterBlockStatement();
//		for (Java8Parser.BlockStatementContext blockStatementContext : list) {
//			enterBlockStatement(blockStatementContext);
//		}
	}

	public void exitMethodDeclaration(Java8Parser.MethodDeclarationContext ctx) {
		--contFunction;
		String nameMethod = ctx.getText();

		

	}

	public void enterMethodHeader(Java8Parser.MethodHeaderContext ctx) {

		key = ctx.methodDeclarator().getText();

		System.out.println("enterMethodHeader " + classNamed + key);
		map.put(classNamed + key, null);
	}

	public void enterMethodDeclarator(Java8Parser.MethodDeclaratorContext ctx) {

	}

	public Map<String, List<String>> getMap() {
		return map;
	}

	public void setMap(Map<String, List<String>> map) {
		this.map = map;
	}

	public void enterBlockStatement(Java8Parser.BlockStatementContext ctx) {

		List<String> listaVariavel = map.get(classNamed + key);
		if (listaVariavel == null) {
			listaVariavel = new ArrayList();
			String aux = ctx.getText().replaceAll("==", "=");
			aux = ctx.getText().replaceAll("\\&&", " and ");
			listaVariavel.add(aux);
			map.put(classNamed + key, listaVariavel);
		} else {
			String aux = ctx.getText().replaceAll("==", "=");
			aux = ctx.getText().replaceAll("==", "=");
			aux = ctx.getText().replaceAll("=", ":=");
			if (aux.contains("do{")) {
				aux = aux.replaceAll("do\\{", "repeat\nbegin\n");
				isDo = true;

			} else {

				if (aux.contains("while")) {
					aux = aux.replaceAll("\\{", "do begin");
				} else {
					aux = aux.replaceAll("\\{", "then begin");
				}
			}

			String aux2 = aux.replaceAll("\\}", "end");

			String auxFinal = aux2.replace("endelseif", "end else if");
			if (auxFinal.contains("System.out.println")) {
				auxFinal = auxFinal.replaceAll("Systen.out.println", "writeln");
			}
			listaVariavel.add(auxFinal);
			map.put(classNamed + key, listaVariavel);
		}
	}

	@Override
	public void enterAssignmentExpression(Java8Parser.AssignmentExpressionContext ctx) {
		System.out.println(ctx.getText().replaceAll("==", "="));
		ctx.getText().replaceAll("&&", "and");
		// System.out.println("me chamou !!!");

	}

	@Override
	public void exitNormalClassDeclaration(Java8Parser.NormalClassDeclarationContext ctx) {

	}

	@Override
	public void enterVariableDeclaratorList(Java8Parser.VariableDeclaratorListContext ctx) {

		if (contFunction == 1)
			System.out.println("variableDelaratio " + ctx.getText());
		else {
			System.out.println("variableDelaration record " + ctx.getText() + " >>" + classNamed);
			// map.put(classNamed, ctx.getText());

////			if(contFunction==0){
////				System.out.println(classNamed+">>>>>>>>>>>>>>>.mount record");
////				List<String>listAttri = map.get(classNamed);
////				
////				if(listAttri==null) {
////					listAttri = new ArrayList<String>();
////					listAttri.add(ctx.getText());
////					map.put(classNamed, listAttri);
////				}else {
////					listAttri.add(ctx.getText());
////					map.put(classNamed, listAttri);
////				}
//			}else {
//				
//			}

		}
	}

	@Override
	public void enterUnaryExpressionNotPlusMinus(Java8Parser.UnaryExpressionNotPlusMinusContext ctx) {
//		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<teste");
//		System.out.println(ctx.getText());
//
//		List<String> listaVariavel = map.get(classNamed + key);
//
//		for (String s : listaVariavel) {
//			if (s.contains("!")) {
//				if (!s.substring(1, 3).contains("=")) {
//					//listaVariavel.remove(s);
//					System.out.println(s     +"LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL   eu aqui");
//					//listaVariavel.add(s.replace("!", "not "));
//				}
//			}
//
//		}
	}

	@Override
	public void enterConditionalAndExpression(Java8Parser.ConditionalAndExpressionContext ctx) {

		List<String> listaVariavel = map.get(classNamed + key);

		for (String s : listaVariavel) {
			if (s.contains("&&")) {
				listaVariavel.remove(s);
				s = s.replaceAll("&&", ") and (");

				listaVariavel.add(s);

			}
		}

	}

	@Override
	public void enterConditionalOrExpression(Java8Parser.ConditionalOrExpressionContext ctx) {
		List<String> listaVariavel = map.get(classNamed + key);

		Iterator<String> it = listaVariavel.iterator();

		while (it.hasNext()) {
			String str = it.next();
			if (str.contains("||")) {

				String nova = str.replace("||", ") or (");
				listaVariavel.remove(str);
				System.out.println("wwwwwwwwwwwwwww" + nova);
				listaVariavel.add(nova);

			}
		}

//		for (String s : listaVariavel) {
//			if (s.contains("||")) {
//			
//			
//				System.out.println(ctx.getText());
//				System.out.println(s);
//				System.out.println("MMMMMMMMMMMMMMMMMmaiquel       ORRRRRRRRRRR");
//				
//
//			}
//		}
	}

	@Override
	public void enterClassBodyDeclaration(Java8Parser.ClassBodyDeclarationContext ctx) {

		List<String> listAttri = mapRecord.get(classNamed);
		if (listAttri == null) {
			listAttri = new ArrayList<String>();
			if (!ctx.getText().contains("{"))
				listAttri.add(ctx.getText());
			mapRecord.put(classNamed, listAttri);
		} else {
			if (!ctx.getText().contains("{"))
				listAttri.add(ctx.getText());
			mapRecord.put(classNamed, listAttri);
		}
		System.out.println(ctx.getText());
	}

	public Map<String, List<String>> getMapRecord() {
		return mapRecord;
	}
	
	@Override
	public void enterResult(Java8Parser.ResultContext ctx) { 
		
	//	System.err.println(ctx.getText());
	}

	public void setMapRecord(Map<String, List<String>> mapRecord) {
		this.mapRecord = mapRecord;
	}

}