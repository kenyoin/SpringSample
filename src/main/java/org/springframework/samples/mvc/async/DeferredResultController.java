package org.springframework.samples.mvc.async;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/async")
public class DeferredResultController {

	/*
	 * http://m12i.hatenablog.com/entry/2014/11/17/222816
	 * 非同期処理を実現するもう一つの選択肢は、コントローラのメソッドにDeferredResultのインスタンスを戻り値として返させるというものです。この場合も結果値の生成は別スレッドで行われることになります。
	 */
	private final Queue<DeferredResult<String>> responseBodyQueue = new ConcurrentLinkedQueue<DeferredResult<String>>();

	/*
	 * http://qiita.com/tag1216/items/3680b92cf96eb5a170f0
	 * ModevAndViewを使用するとViewに渡したい情報を一緒に返すことができる。
	 * http://kic4o1.tumblr.com/post/19999595886/spring-mvc%E3%81%A7%E7%94%BB%E9%9D%A2%E9%81%B7%E7%A7%BB%E3%81%95%E3%81%9B%E3%82%8Bcontroller%E3%83%A1%E3%82%BD%E3%83%83%E3%83%89%E3%81%AB%E3%81%AA%E3%82%84%E3%82%93%E3%81%A0
	 * ビュー名、およびビューに使用する属性を設定する方法。
	 */
	private final Queue<DeferredResult<ModelAndView>> mavQueue = new ConcurrentLinkedQueue<DeferredResult<ModelAndView>>();

	private final Queue<DeferredResult<String>> exceptionQueue = new ConcurrentLinkedQueue<DeferredResult<String>>();


	@RequestMapping("/deferred-result/response-body")
	public @ResponseBody DeferredResult<String> deferredResult() {
		DeferredResult<String> result = new DeferredResult<String>();
		this.responseBodyQueue.add(result);
		return result;
	}

	@RequestMapping("/deferred-result/model-and-view")
	public DeferredResult<ModelAndView> deferredResultWithView() {
		DeferredResult<ModelAndView> result = new DeferredResult<ModelAndView>();
		this.mavQueue.add(result);
		return result;
	}

	@RequestMapping("/deferred-result/exception")
	public @ResponseBody DeferredResult<String> deferredResultWithException() {
		DeferredResult<String> result = new DeferredResult<String>();
		this.exceptionQueue.add(result);
		return result;
	}

	@RequestMapping("/deferred-result/timeout-value")
	public @ResponseBody DeferredResult<String> deferredResultWithTimeoutValue() {

		// Provide a default result in case of timeout and override the timeout value
		// set in src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml

		return new DeferredResult<String>(1000L, "Deferred result after timeout");
	}

	/**
	 * http://qiita.com/rubytomato@github/items/4f0c64eb9a24eaceaa6e
	 * 定期実行したいtaskに実行周期をScheduledアノテーションで指定します。
	 * fixedDelay	taskの実行完了時点から指定時間後に次のtaskを実行する. 単位はms.
	 */
	@Scheduled(fixedRate=2000)
	public void processQueues() {
		for (DeferredResult<String> result : this.responseBodyQueue) {
			result.setResult("Deferred result");
			this.responseBodyQueue.remove(result);
		}
		for (DeferredResult<String> result : this.exceptionQueue) {
			result.setErrorResult(new IllegalStateException("DeferredResult error"));
			this.exceptionQueue.remove(result);
		}
		for (DeferredResult<ModelAndView> result : this.mavQueue) {
			result.setResult(new ModelAndView("views/html", "javaBean", new JavaBean("bar", "apple")));
			this.mavQueue.remove(result);
		}
	}

	@ExceptionHandler
	@ResponseBody
	public String handleException(IllegalStateException ex) {
		return "Handled exception: " + ex.getMessage();
	}

}
