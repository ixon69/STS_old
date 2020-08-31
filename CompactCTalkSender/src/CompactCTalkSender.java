import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.github.realizm.tmup4j.Tmup4J;
import com.google.gson.JsonObject;

public class CompactCTalkSender {

	static Tmup4J tmup4j = null;

	public static void main(String[] args) {
		if (args.length != 4) {
			System.out.println("Argument: userid password roomnumber messagefile");
		}

		tmup4j = new Tmup4J("c", "s");

		tmup4j.setAuthDomain("https://talk-auth.smartcmc.or.kr");
		tmup4j.setEdgeDomain("https://talk-edge.smartcmc.or.kr");
		tmup4j.setFileDomain("https://talk-edge.smartcmc.or.kr");
		
		try {

			
			tmup4j.oAuth2(args[0], args[1]);

			long roomNumber = Long.parseLong(args[2]);

			if (args[3].endsWith(".txt") || args[3].endsWith(".log")) {
				String message = readFromFile(args[3]);

				JsonObject param = new JsonObject();
				param.addProperty("content", message);

				tmup4j.sendMessage(roomNumber, param);
			} else {

				JsonObject uploadResult = tmup4j.uploadFile(3, new File(args[3]));

				String fileId = uploadResult.get("files").getAsJsonArray().get(0).getAsJsonObject().get("id")
						.getAsString();

				JsonObject param = new JsonObject();
				param.addProperty("content", fileId);

				tmup4j.sendMessage(roomNumber, 2, param);

			}


		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	private static String readFromFile(String filePath) throws IOException {
		return new String(Files.readAllBytes(Paths.get(filePath, new String[0])));
	}

}
