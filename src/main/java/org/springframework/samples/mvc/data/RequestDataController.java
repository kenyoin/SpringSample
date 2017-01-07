package org.springframework.samples.mvc.data;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/data")
public class RequestDataController {

	@RequestMapping(value="param", method=RequestMethod.GET)
	public @ResponseBody String withParam(@RequestParam String foo) {
		return "Obtained 'foo' query parameter value '" + foo + "'";
	}

	@RequestMapping(value="group", method=RequestMethod.GET)
	public @ResponseBody String withParamGroup(JavaBean bean) {
		return "Obtained parameter group " + bean;
	}

	/**
	 * URLにユーザーIDとかを含めて、その値を受け取りたい。
	 * たとえば、
	 * http://localhost:8080/samples/user/hoge
	 * としたときは、最後の「hoge」を取得。
	 * http://localhost:8080/samples/user/fuga
	 * としたときは、最後の「fuga」を取得するみたいに。
	 * で、そういうパラメータを動的に取得するときは、@PathVariableアノテーションを使う。
	 * http://blog.codebook-10000.com/entry/20140301/1393628782
	 *
	 * @param var
	 * @return
	 */
	@RequestMapping(value="path/{var}", method=RequestMethod.GET)
	public @ResponseBody String withPathVariable(@PathVariable String var) {
		return "Obtained 'var' path variable value '" + var + "'";
	}

	/**
	 * @MatrixVariableでマトリクスパラメタを取得する。
	 * URI仕様RFC3986では、パス断片のなかに名前-値ペアを含める方法が定義されています。
	 * http://m12i.hatenablog.com/entry/2014/11/15/150515
	 *
	 * @param path
	 * @param foo 以下の場合、例えば/data/{path};foo=strでfooの値を取得できます。
	 * @return
	 */
	@RequestMapping(value="{path}/simple", method=RequestMethod.GET)
	public @ResponseBody String withMatrixVariable(@PathVariable String path, @MatrixVariable String foo) {
		return "Obtained matrix variable 'foo=" + foo + "' from path segment '" + path + "'";
	}

	/**
	 * 例えば、/data/{path1};foo=str1/{path2};foo=str2
	 */
	@RequestMapping(value="{path1}/{path2}", method=RequestMethod.GET)
	public @ResponseBody String withMatrixVariablesMultiple (
			@PathVariable String path1, @MatrixVariable(value="foo", pathVar="path1") String foo1,
			@PathVariable String path2, @MatrixVariable(value="foo", pathVar="path2") String foo2) {

		return "Obtained matrix variable foo=" + foo1 + " from path segment '" + path1
				+ "' and variable 'foo=" + foo2 + " from path segment '" + path2 + "'";
	}

	/**
	 * @RequestHeader リクエストヘッダー文字列を取得
	 */
	@RequestMapping(value="header", method=RequestMethod.GET)
	public @ResponseBody String withHeader(@RequestHeader String Accept) {
		return "Obtained 'Accept' header '" + Accept + "'";
	}

	/**
	 * @CookieValue リクエストクッキー文字列を取得
	 */
	@RequestMapping(value="cookie", method=RequestMethod.GET)
	public @ResponseBody String withCookie(@CookieValue String openid_provider) {
		return "Obtained 'openid_provider' cookie '" + openid_provider + "'";
	}

	/**
	 * @RequestBody リクエストボディーを取得
	 */
	@RequestMapping(value="body", method=RequestMethod.POST)
	public @ResponseBody String withBody(@RequestBody String body) {
		return "Posted request body '" + body + "'";
	}

	/**
	 * HttpEntity<T> をパラメーターとして使用すると、メソッドの中に HttpEntity<T> を自動的に注入することができます。
	 * https://www.ibm.com/developerworks/jp/web/library/wa-restful/
	 * @param entity リクエストヘッダーとリクエストボディから構成されたオブジェクト
	 */
	@RequestMapping(value="entity", method=RequestMethod.POST)
	public @ResponseBody String withEntity(HttpEntity<String> entity) {
		return "Posted request body '" + entity.getBody() + "'; headers = " + entity.getHeaders();
	}

}
