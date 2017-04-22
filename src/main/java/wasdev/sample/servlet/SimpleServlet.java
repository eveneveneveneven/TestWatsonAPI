package wasdev.sample.servlet;

import java.io.IOException;
import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.watson.developer_cloud.language_translator.v2.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v2.model.Language;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslationResult;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;

/**
* Servlet implementation class SimpleServlet
*/
@WebServlet("/SimpleServlet")
public class SimpleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	* @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	*/
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String input_text = request.getParameter("input_text");
		String language = request.getParameter("language");
		if(language.equals("spanish")){
			response.getWriter().print(translateToSpanish(input_text));
		}else if(language.equals("italian")){
			response.getWriter().print(translateToItalian(input_text));
		}else if(language.equals("german")){
			response.getWriter().print(translateToGerman(input_text));
		}else{
			response.getWriter().print("Could not get language "+ language);
		}
	}
	
	private String translateToSpanish(String text){
		LanguageTranslator service = new LanguageTranslator();
		service.setUsernameAndPassword("05a89fd6-c5d3-4f05-81b8-7350c5b1cafa", "TeiQpIhLrkXz");
		TranslationResult translationResult = service.translate(text, Language.ENGLISH, Language.SPANISH).execute();
		return translationResult.getFirstTranslation();
	}
	private String translateToGerman(String text){
		LanguageTranslator service = new LanguageTranslator();
		service.setUsernameAndPassword("05a89fd6-c5d3-4f05-81b8-7350c5b1cafa", "TeiQpIhLrkXz");
		TranslationResult translationResult = service.translate(text, Language.ENGLISH, Language.GERMAN).execute();
		return translationResult.getFirstTranslation();
	}
	private String translateToItalian(String text){
		LanguageTranslator service = new LanguageTranslator();
		service.setUsernameAndPassword("05a89fd6-c5d3-4f05-81b8-7350c5b1cafa", "TeiQpIhLrkXz");
		TranslationResult translationResult = service.translate(text, Language.ENGLISH, Language.ITALIAN).execute();
		return translationResult.getFirstTranslation();
	}
}