package org.springframework.samples.mvc.async;

import java.util.concurrent.Callable;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.WebAsyncTask;

/**
 * MVCのCを作成する。 Spring bootではJSPは避けたほうがいいらしい。以下が推奨されている。 FreeMarker Groovy
 * Thymeleaf Velocity (deprecated in 1.4) Mustache
 *
 * http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-
 * developing-web-applications.html#boot-features-spring-mvc-template-engines
 *
 * どのテンプレートを使用すべき？ http://aoiso.hatenablog.com/entry/2015/10/14/173333
 *
 * @Controller http://m12i.hatenablog.com/entry/2014/11/15/150515
 *             付与したクラスをMVCのCとして定義する。
 *
 * @RequestMapping http://yyama1556.hateblo.jp/entry/2016/08/21/134349
 *
 *                 クラスとメソッドの両方に使用可能です。
 *                 クラスに使用した場合は、URLのプレフィックス（つまり、親パス）に一致させることができます。
 *                 メソッドに使用した場合、親パスからの相対パスを指定できます。
 *
 *                 headers属性
 *
 *                 HTTPのヘッダを指定できます。 　　記法は以下のとおりです。 　　　例：
 *                 Accept　　・・・"Accept"というヘッダが存在する場合にマッチ。
 *                 !Accept　　・・・"Accept"というヘッダが存在しない場合にマッチ。
 *                 foo=abc　　・・・fooヘッダに"abc"の文字列が設定されている場合にマッチ。
 *                 foo!=abc　　・・・fooヘッダに"abc"の文字列が設定されていない場合にマッチ。
 *
 *                 一部のヘッダ（"Accept"、"Content-Type"）には、ワイルドカードも使用できるようです。
 *                 他のヘッダについては完全一致マッチになります。 　　複数指定した場合は、ANDでマッチするようです。
 *
 *                 https://sites.google.com/site/soracane/home/springnitsuite/
 *                 spring-mvc/07-controllerno-chu-limesoddotonomappingu-fang-fa
 *
 *                 params属性
 *
 *                 リクエストパラメタを指定できます。 　　記法は、headersと同じです。
 *
 *
 */
@Controller
@RequestMapping("/async/callable")
public class CallableController {


	@RequestMapping("/response-body")
	public @ResponseBody Callable<String> callable() {

		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(2000);
				return "Callable result";
			}
		};
	}

	@RequestMapping("/view")
	public Callable<String> callableWithView(final Model model) {

		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(2000);
				model.addAttribute("foo", "bar");
				model.addAttribute("fruit", "apple");
				return "views/html";
			}
		};
	}

	@RequestMapping("/exception")
	public @ResponseBody Callable<String> callableWithException(
			final @RequestParam(required=false, defaultValue="true") boolean handled) {

		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(2000);
				if (handled) {
					// see handleException method further below
					throw new IllegalStateException("Callable error");
				}
				else {
					throw new IllegalArgumentException("Callable error");
				}
			}
		};
	}

	@RequestMapping("/custom-timeout-handling")
	public @ResponseBody WebAsyncTask<String> callableWithCustomTimeoutHandling() {

		Callable<String> callable = new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(2000);
				return "Callable result";
			}
		};

		return new WebAsyncTask<String>(1000, callable);
	}

	@ExceptionHandler
	@ResponseBody
	public String handleException(IllegalStateException ex) {
		return "Handled exception: " + ex.getMessage();
	}

}
