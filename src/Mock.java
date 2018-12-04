import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Mock {

	public static void main(String[] args) {
		Map<String, List<String>> mapRecord = new HashMap<String, List<String>>();

		mapRecord.put("Test", null);
		String ctx = "Testtest";
		String mount[] = null;
		if (ctx.contains(",")) {
			mount = ctx.split(",");
		}
		if(mount==null) {
			Iterator<String> itr2 = mapRecord.keySet().iterator();
			while (itr2.hasNext()) {
			String key =itr2.next();
			String string=ctx;
			String str = string.replace(key, "");
			String objeto = key.replaceAll(str, "");
			string = string.replace(objeto+str, str+":"+objeto);
			System.out.println(string);}
		}
		if (mount != null) {
			for (String string : mount) {

				Iterator<String> itr2 = mapRecord.keySet().iterator();
				while (itr2.hasNext()) {
					String key = itr2.next();
					if (string.contains(string)) {
						String str = string.replace(key, "");
						String objeto = key.replaceAll(str, "");
						string = string.replace(objeto + str, str + ":" + objeto);
						System.out.println(string);
					}
				}

			}
		}
	}

}
