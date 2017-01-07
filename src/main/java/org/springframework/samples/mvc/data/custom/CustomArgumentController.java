package org.springframework.samples.mvc.data.custom;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CustomArgumentController {

	/**
	 * @ModelAttributeアノテーションを利用して、受け取りたいFormクラスを初期化してreturnします。このアノテーションがついているメソッドは、このクラスのRequestMappingアノテーションがついたいずれかのメソッド実行前に呼ばれます(そのメソッドの中でそのFormを利用している・していないは関係なし)。
	 * http://qiita.com/NagaokaKenichi/items/ea61420b01d37189d997
	 * http://stackoverflow.com/questions/13895552/understanding-the-use-of-modelattribute-and-requestattribute-annotations-in-sp
	 *
	 * custom()実行前に呼ばれる。
	 *
	 * @param request
	 */
	@ModelAttribute
	void beforeInvokingHandlerMethod(HttpServletRequest request) {
		request.setAttribute("foo", "bar");
	}

	@RequestMapping(value="/data/custom", method=RequestMethod.GET)
	public @ResponseBody String custom(@RequestAttribute("foo") String foo) {
		return "Got 'foo' request attribute value '" + foo + "'";
	}

}
