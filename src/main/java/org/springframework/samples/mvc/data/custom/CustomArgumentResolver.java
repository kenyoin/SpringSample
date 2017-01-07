package org.springframework.samples.mvc.data.custom;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * このインターフェースは、Controllerメソッドで引数としてデータを受け取るための処理になります。
 * @ModelAttributeの付いたオブジェクトや、その他のいろんなオブジェクトを自動でバインドするのはHandlerMethodArgumentResolverの実装クラスであるModelAttributeMethodProcessorと、ServletModelAttributeMethodProcessorで定義されてます。
 * http://shibuya-3percent.hatenablog.com/entry/2016/07/03/190549
 */
public class CustomArgumentResolver implements HandlerMethodArgumentResolver {

	/**
	 * このメソッドはHandlerメソッドのパラメータの1つ1つに対して呼ばれます。（ただしSpringが自動的に値をセットするパラメータは除外されます。たとえば@PathVariableアノテーションが付いているパラメータやModel、UriComponentsBuilderなど）
	 * このメソッドがtrueを返した場合に、次にresolveArgumentメソッドが実行されます。
	 * http://qiita.com/rubytomato@github/items/37d58c2748c9f0a8566a
	 *
	 * RequestAttributeアノテーションが付いているCustomArgumentController#customが実行された時、trueを返す。
	 */
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(RequestAttribute.class) != null;
	}

	/**
	 * supportsParameterメソッドがtrueを返した場合に実行され、このメソッドが返すオブジェクトが引数として使用されます。
	 * http://qiita.com/rubytomato@github/items/37d58c2748c9f0a8566a
	 *
	 * CustomArgumentController#customが実行された時、パラメタfooにはリクエストスコープに格納されたキーが「foo」である値、「bar」が返却される。
	 */
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		RequestAttribute attr = parameter.getParameterAnnotation(RequestAttribute.class);
		return webRequest.getAttribute(attr.value(), WebRequest.SCOPE_REQUEST);
	}

}
