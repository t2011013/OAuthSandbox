import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main extends HttpServlet {


	public Main() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//String idToken = "";
		//int apiResCode = 0;

		String tokens = getTokens(request.getParameter("code"));

		response.getWriter().println("json:" + tokens);

		response.getWriter().println("hogehogehoge!2");
		ObjectMapper mapper = new ObjectMapper();
		response.getWriter().println("hogehogehoge!3");
		Hoge hoge = mapper.readValue(tokens, Hoge.class);

		response.getWriter().println("hogehogehoge!!!!!!!!!!");
		response.getWriter().println("access_token:" + hoge.access_token);
		response.getWriter().println("expires_in:" + hoge.expires_in);
		response.getWriter().println("id_token:" + hoge.id_token);
		response.getWriter().println("token_type:" + hoge.token_type);

		//Base64.Decoder decoder = Base64.getUrlDecoder();
		//String decoded = new String(decoder.decode(hoge.access_token.split(".")[1]));
		//response.getWriter().println("decoded:" + decoded);

		String tasks = getTask(hoge.access_token);

		response.getWriter().println("");
		response.getWriter().println("");
		response.getWriter().println("Task:");
		response.getWriter().println(tasks);

		//response.getWriter().append("Served at: ").append(String.valueOf(apiResCode)).append(";").append(postResponseStr).append("code=").append(request.getParameter("code"));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected String getTokens(String authorCode) throws IOException {

		String postResponseStr = "";

		URL url = new URL("https://www.googleapis.com/oauth2/v4/token");

		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setRequestMethod("POST");
		String parameterString = new String("code=" + authorCode + "&client_id=***&client_secret=***&grant_type=authorization_code&redirect_uri=http://v150-95-157-252.a096.g.tyo1.static.cnode.io:8080/auth/main");
		BufferedReader bufferReader = null;
		try {
			PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
			printWriter.print(parameterString);
			printWriter.close();
			bufferReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF8"));
		} catch (Exception e) {
		}
		String str;
		while ((str = bufferReader.readLine()) != null) {
			postResponseStr = postResponseStr + str;
		}
		bufferReader.close();
		connection.disconnect();

		return postResponseStr;
	}

	protected String getTask(String accessToken) throws IOException {

		String postResponseStr = "";

		URL url = new URL("https://www.googleapis.com/tasks/v1/users/@me/lists");

		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Authorization", "OAuth " + accessToken);
		String parameterString = "";
		BufferedReader bufferReader = null;
		try {
			PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
			printWriter.print(parameterString);
			printWriter.close();
			bufferReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF8"));
		} catch (Exception e) {
		}
		String str;
		while ((str = bufferReader.readLine()) != null) {
			postResponseStr = postResponseStr + str;
		}
		bufferReader.close();
		connection.disconnect();

		return postResponseStr;
	}

}

