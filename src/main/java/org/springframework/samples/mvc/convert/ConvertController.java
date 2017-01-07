package org.springframework.samples.mvc.convert;

import java.util.Collection;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/convert")
public class ConvertController {

	/**
	 * @param value
	 * リクエストパラメータ（GETパラメータ＆POSTパラメータ）の取得
	 * http://javatechnology.net/spring/requestparam/
	 * @return
	 */
	@RequestMapping("primitive")
	public @ResponseBody String primitive(@RequestParam Integer value) {
		return "Converted primitive " + value;
	}

	// requires Joda-Time on the classpath
	/**
	 * @DateTimeFormatアノテーションを用いて日付と文字列の間の相互変換も処理できます。
	 * http://d.hatena.ne.jp/ryoasai/20110503/1304424587
	 * https://blog.ik.am/entries/375
	 *
	 * @param value
	 * パラメータを動的に取得するときは、@PathVariableアノテーションを使う。
	 * RequestMappingで指定したマッピングとひも付けを行う。
	 * http://blog.codebook-10000.com/entry/20140301/1393628782
	 * @return
	 */
	@RequestMapping("date/{value}")
	public @ResponseBody String date(@PathVariable @DateTimeFormat(iso=ISO.DATE) Date value) {
		return "Converted date " + value;
	}

	@RequestMapping("collection")
	public @ResponseBody String collection(@RequestParam Collection<Integer> values) {
		return "Converted collection " + values;
	}

	@RequestMapping("formattedCollection")
	public @ResponseBody String formattedCollection(@RequestParam @DateTimeFormat(iso=ISO.DATE) Collection<Date> values) {
		return "Converted formatted collection " + values;
	}

	@RequestMapping("bean")
	public @ResponseBody String bean(JavaBean bean) {
		return "Converted " + bean;
	}

	@RequestMapping("value")
	public @ResponseBody String valueObject(@RequestParam SocialSecurityNumber value) {
		return "Converted value object " + value;
	}

	@RequestMapping("custom")
	public @ResponseBody String customConverter(@RequestParam @MaskFormat("###-##-####") String value) {
		return "Converted '" + value + "' with a custom converter";
	}

}
