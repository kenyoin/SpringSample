package org.springframework.samples.mvc.exceptions;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExceptionController {

	@RequestMapping("/exception")
	public @ResponseBody String exception() {
		throw new IllegalStateException("Sorry!");
	}

	@RequestMapping("/global-exception")
	public @ResponseBody String businessException() throws BusinessException {
		throw new BusinessException();
	}

	/**
	 * Controller内にExceptionHandlerアノテーションを付与したメソッドを定義すると、
	 * そのController内で指定の型の例外がthrowされた場合にハンドリングできる。
	 * http://qiita.com/nenokido2000/items/91c39e4aa5cbd9dfacef
	 */
	@ExceptionHandler
	public @ResponseBody String handle(IllegalStateException e) {
		return "IllegalStateException handled!";
	}

}
