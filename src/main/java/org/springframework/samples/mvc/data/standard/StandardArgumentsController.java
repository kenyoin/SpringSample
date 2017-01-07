package org.springframework.samples.mvc.data.standard;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StandardArgumentsController {

	// request related

	/**
	 *
	 * @param request リクエストされると値がこのオブジェクトに自動的に設定される。
	 * http://stackoverflow.com/questions/8504258/spring-3-mvc-accessing-httprequest-from-controller
	 * @param user 認証情報
	 * http://itdoc.hitachi.co.jp/manuals/link/cosmi_v0870/APKX/EU080373.HTM
	 * @param locale locale情報
	 * @return
	 */
	@RequestMapping(value="/data/standard/request", method=RequestMethod.GET)
	public @ResponseBody String standardRequestArgs(HttpServletRequest request, Principal user, Locale locale) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("request = ").append(request).append(", ");
		buffer.append("userPrincipal = ").append(user).append(", ");
		buffer.append("requestLocale = ").append(locale);
		return buffer.toString();
	}

	@RequestMapping(value="/data/standard/request/reader", method=RequestMethod.POST)
	public @ResponseBody String requestReader(Reader requestBodyReader) throws IOException {
		/*
		 * ここで使用したメソッドはcloseもしてくれるので、特にプログラマー側でclose処理する必要はありません。
		 * https://sites.google.com/site/soracane/home/springnitsuite/spring-mvc/81-can-kao-appurodo-daunrodono-shi-zhuang-li
		 */
		return "Read char request body = " + FileCopyUtils.copyToString(requestBodyReader);
	}

	@RequestMapping(value="/data/standard/request/is", method=RequestMethod.POST)
	public @ResponseBody String requestReader(InputStream requestBodyIs) throws IOException {
		return "Read binary request body = " + new String(FileCopyUtils.copyToByteArray(requestBodyIs));
	}

	// response related

	@RequestMapping("/data/standard/response")
	public @ResponseBody String response(HttpServletResponse response) {
		return "response = " + response;
	}

	@RequestMapping("/data/standard/response/writer")
	public void availableStandardResponseArguments(Writer responseWriter) throws IOException {
		responseWriter.write("Wrote char response using Writer");
	}

	@RequestMapping("/data/standard/response/os")
	public void availableStandardResponseArguments(OutputStream os) throws IOException {
		os.write("Wrote binary response using OutputStream".getBytes());
	}

	// HttpSession

	/**
	 * Sessionオブジェクトを取得
	 */
	@RequestMapping("/data/standard/session")
	public @ResponseBody String session(HttpSession session) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("session=").append(session);
		return buffer.toString();
	}

}
