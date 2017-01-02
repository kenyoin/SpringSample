package org.springframework.samples.mvc.async;

import java.util.concurrent.Callable;

import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.CallableProcessingInterceptorAdapter;

/**
 * http://qiita.com/kazuki43zoo/items/ce88dea403c596249e8a
 * タイムアウトのところで軽く紹介しましたが、Spring MVCは、いくつかのポイント(タイミング)で共通的な処理を実行できる仕組み(CallableProcessingInterceptor or DeferredResultProcessingInterceptor)を提供しています。
 * CallableProcessingInterceptor	Spring MVC管理下のスレッドで実行する非同期処理向けの仕組み。
 * DeferredResultProcessingInterceptor	Spring MVC管理外のスレッドで実行する非同期処理向けの仕組み。
 * メソッド名	実行スレッド	説明
 * beforeConcurrentHandling	APサーバのリクエスト受付スレッド	ServletRequest#startAsyncを実行する直前に呼び出される。
 * preProcess		ServletRequest#startAsyncを実行した直後に呼び出される。
 * postProcess		非同期処理が終了し、AsyncContext#dispatchを実行する直前に呼び出される。
 * handleTimeout	APサーバのレスポンス返却スレッド	サーブレットコンテナがタイムアウトを検知した際に呼び出される。 (AsyncListener#onTimeoutがトリガー)
 * afterCompletion		Servlet標準の非同期処理を終了した際に呼び出される。 (AsyncListener#onCompleteとonErrorがトリガー)
 */
public class TimeoutCallableProcessingInterceptor extends CallableProcessingInterceptorAdapter {

	@Override
	public <T> Object handleTimeout(NativeWebRequest request, Callable<T> task) throws Exception {
		throw new IllegalStateException("[" + task.getClass().getName() + "] timed out");
	}

}
