package mb;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashSet;

public class SteamTest<T> {
	
	public static <T,J> T instance(T fanT){
		return fanT;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		OutputStream outputStream = new FileOutputStream(new File("d://111.txt"));
		
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
		
		//ObjectOutputStream oo = new ObjectOutputStream(out);
		
		
	}

	
}
