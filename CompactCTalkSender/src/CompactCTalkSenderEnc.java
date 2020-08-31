import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.github.realizm.tmup4j.Tmup4J;
import com.google.gson.JsonObject;

public class CompactCTalkSenderEnc {
/*
	static Tmup4J tmup4j = null;

	public static void main(String[] args) {


		tmup4j = new Tmup4J("c", "s");

		tmup4j.setAuthDomain("https://talk-auth.smartcmc.or.kr");
		tmup4j.setEdgeDomain("https://talk-edge.smartcmc.or.kr");
		tmup4j.setFileDomain("https://talk-edge.smartcmc.or.kr");
		
		try {
			
			if (args.length == 0) {
				System.out.println("Argument: userid enckey password roomnumber messagefile");
			}
			
			Crypto crypto = new Crypto(args[1], args[1]);
			if("encode".equals(args[0])){
				System.out.println("encoded password : " + crypto.encode(args[2]));
				return;
			}
			
			if (args.length != 5) {
				System.out.println("Argument: userid enckey password roomnumber messagefile");
			}
			
			try{
				tmup4j.oAuth2(args[0], crypto.decode(args[2]));
			} catch (IOException ex) {
				System.out.println("연결실패!");
				return;
			} catch (IllegalAccessException ex){
				System.out.println("비밀번호 오류");
				return;
			}

			long roomNumber = Long.parseLong(args[3]);

			if (args[4].endsWith(".txt") || args[4].endsWith(".log")) {
				String message = readFromFile(args[4]);

				JsonObject param = new JsonObject();
				param.addProperty("content", message);

				tmup4j.sendMessage(roomNumber, param);
			} else {

				JsonObject uploadResult = tmup4j.uploadFile(3, new File(args[4]));

				String fileId = uploadResult.get("files").getAsJsonArray().get(0).getAsJsonObject().get("id")
						.getAsString();

				JsonObject param = new JsonObject();
				param.addProperty("content", fileId);

				tmup4j.sendMessage(roomNumber, 2, param);

			}
		} catch (Exception e){
			
		}
		
	}
*/
	private static String readFromFile(String filePath) throws IOException {
		return new String(Files.readAllBytes(Paths.get(filePath, new String[0])));
	}

}
