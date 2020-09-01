import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.github.realizm.tmup4j.Tmup4J;
import com.google.gson.JsonObject;

public class RoomList {

	static Tmup4J tmup4j = null;

	public static void main(String[] args) {

		tmup4j = new Tmup4J("c", "s");

		tmup4j.setAuthDomain("https://talk-auth.smartcmc.or.kr");
		tmup4j.setEdgeDomain("https://talk-edge.smartcmc.or.kr");
		tmup4j.setFileDomain("https://talk-edge.smartcmc.or.kr");
		
		try {

			tmup4j.oAuth2(args[0], args[1]);
			JsonObject jObj = tmup4j.getRoomList();
			System.out.println(jObj.toString());
			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

}
