package org.springframework.samples.mvc.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controllerを横断して例外をハンドリングする場合、まずは例外ハンドリング用のクラスを作成する。このクラスには@ControllerAdviceアノテーションを付与する。
 * そうすることで全てのControllerクラスで発生した例外に対する共通の設定を行うことができる。
 * http://qiita.com/NagaokaKenichi/items/2f199134a881a776b717
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Controller内にExceptionHandlerアノテーションを付与したメソッドを定義すると、
	 * そのController内で指定の型の例外がthrowされた場合にハンドリングできる。
	 * http://qiita.com/nenokido2000/items/91c39e4aa5cbd9dfacef
	 */
	@ExceptionHandler
	public @ResponseBody String handleBusinessException(BusinessException ex) {
		return "Handled BusinessException";
	}

}
