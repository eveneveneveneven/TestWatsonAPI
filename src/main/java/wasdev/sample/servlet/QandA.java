package wasdev.sample.servlet;

import java.io.IOException;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.language_translator.v2.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v2.model.Language;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslationResult;

import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.AudioFormat;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;
import com.ibm.watson.developer_cloud.text_to_speech.v1.util.WaveUtils;

/**
* Servlet implementation class SimpleServlet
*/
@WebServlet("/QandA")
public class QandA extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	* @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	*/
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String input_text = request.getParameter("input_text");
		String response_text = askWatson(input_text);
		
		response.getWriter().print("<textarea>"+response_text+"</textarea>");
		response.getWriter().print("<br><textarea>"+""+"</textarea>");
		response.getWriter().print("<br><button onclick=doGet()/>");
		String button1 = request.getParameter("button1");
	}
	
	private String askWatson(String text){
		ConversationService service = new ConversationService("2017-04-21");
		service.setUsernameAndPassword("cbc6683c-9524-448f-8095-8880119df50e", "Vx2aE1PPPfM6");
		MessageRequest newMessage = new MessageRequest.Builder().inputText(text).build();
		String workspaceId = "cb295066-912b-4b3a-8b42-09fd89a6a0b6";
		MessageResponse response = service.message(workspaceId, newMessage).execute();
		String text_response = response.getText().get(0);
		readResponse(text_response);
		return text_response;
	}
	private void readResponse(String text){
		TextToSpeech service = new TextToSpeech();
		service.setUsernameAndPassword("f02277b7-0f88-41c6-b555-801539e7b078", "oLIeAROQTGZI");
		Voice voice = service.getVoice("en-GB_KateVoice").execute();
		try {
			  InputStream stream = service.synthesize(text, voice,
			    AudioFormat.WAV).execute();
			  InputStream in = WaveUtils.reWriteWaveHeader(stream);
			  OutputStream out = new FileOutputStream("response.wav");
			  byte[] buffer = new byte[1024];
			  int length;
			  while ((length = in.read(buffer)) > 0) {
			    out.write(buffer, 0, length);
			  }
			  out.close();
			  in.close();
			  stream.close();
			}
			catch (Exception e) {
			  e.printStackTrace();
			}
		
	}
}