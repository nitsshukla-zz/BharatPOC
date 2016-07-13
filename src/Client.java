import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class Client {

	public static void main(String[] args) throws Exception {
		File f = new File("A.java");
		f.delete();
		f = new File("A.class");
		f.delete();
		BufferedWriter bw = new BufferedWriter(new FileWriter("A.java"));
		bw.write("public class A{");
		bw.write("public static void main(String[] args) throws Exception{");
		bw.write("System.out.println(123);");
		bw.write("}");
		bw.write("}");

		bw.close();
		Process p1 = Runtime.getRuntime().exec("javac A.java");
		//printOutput(p1);
		//printError(p1);
		Process p = Runtime.getRuntime().exec("java A");
		String out = printOutput(p);
		System.out.println("Printing console");
		System.out.println(out);
		String error = printError(p);
		System.out.println("Printing error");
		System.out.println(error);
	}

	/**
	 * Prints output stream to webserver log.
	 * 
	 * @param p
	 *            Input is Process object which is an ouput of executing any
	 *            process
	 * @throws IOException
	 */
	public static String printOutput(Process p) throws IOException {
		InputStream os = p.getInputStream();
		int b = 0;
		String ret = "";
		while ((b = os.read()) != -1)
			ret += ((char) b);
		return ret;
	}

	private static String printError(Process p) throws IOException {
		String ret = "";
		int b = 0;
		InputStream es = p.getErrorStream();
		while ((b = es.read()) != -1) {
			System.out.print((char) b);
			ret += (char) b;
		}
		return ret.replaceAll("[^\\x20-\\x7e]", "");
	}

}
