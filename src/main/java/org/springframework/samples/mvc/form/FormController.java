package org.springframework.samples.mvc.form;

import javax.validation.Valid;

import org.springframework.mvc.extensions.ajax.AjaxUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *フォームBean*1をセッションスコープに格納して処理をしたい場合、以下のように@SessionAttributesアノテーションをコントローラークラスに付加することで行うことができます。
 *また、処理の完了時には SessionStatusをパラメーターで受け取り、setComplete()メソッドを呼び出すことでセッション中のフォームを削除します。
 *http://d.hatena.ne.jp/ryoasai/20110125/1295970894
 */
@Controller
@RequestMapping("/form")
@SessionAttributes("formBean")
public class FormController {

	// Invoked on every request

	@ModelAttribute
	public void ajaxAttribute(WebRequest request, Model model) {
		model.addAttribute("ajaxRequest", AjaxUtils.isAjaxRequest(request));
	}

	// Invoked initially to create the "form" attribute
	// Once created the "form" attribute comes from the HTTP session (see @SessionAttributes)

	/**
	 * form属性を初回リクエスト時に生成する
	 * form属性生成後、HTTP Sessionに格納される。
	 */
	@ModelAttribute("formBean")
	public FormBean createFormBean() {
		return new FormBean();
	}

	@RequestMapping(method=RequestMethod.GET)
	public void form() {
	}

	/**
	 * @param formBean
	 * コントローラでバリデーションの結果を受け取るには以下のようにします。
	 * @ValidアノテーションをFormクラスにつける
	 * BindingResultインターフェースを@Validを付けたパラメータのすぐ後ろに追加する
	 * bindingReusltのhasErrorsメソッドでエラーの有無が確認できます。フィールド単位のエラーが欲しかったら、hasFieldErrorsを使います。getAllErrors, getFieldErrorsでエラーを取得します。
	 * getAllErrorsやgetFieldErrorsメソッドの戻り値はList>ObjectError<でgetDefaultMessageでエラーメッセージを取得できます。
	 * http://blog.okazuki.jp/entry/2015/07/05/115155
	 *
	 * @param redirectAttrs
	 * 3.1ではFlash Scopeというものが導入されていて、RedirectAttributesではそこにデータを格納してリダイレクトするかたち。日本語もencode/decode無しに送れるし、リダイレクト先コントローラでModelに値をセットする作業もいらなくて楽。
	 * http://qiita.com/horimislime/items/387fa7805d1552149edb
	 */
	@RequestMapping(method=RequestMethod.POST)
	public String processSubmit(@Valid FormBean formBean, BindingResult result,
								@ModelAttribute("ajaxRequest") boolean ajaxRequest,
								Model model, RedirectAttributes redirectAttrs) {
		if (result.hasErrors()) {
			return null;
		}
		// Typically you would save to a db and clear the "form" attribute from the session
		// via SessionStatus.setCompleted(). For the demo we leave it in the session.
		String message = "Form submitted successfully.  Bound " + formBean;
		// Success response handling
		if (ajaxRequest) {
			// prepare model for rendering success message in this request
			model.addAttribute("message", message);
			return null;
		} else {
			// store a success message for rendering on the next request after redirect
			// redirect back to the form to render the success message along with newly bound values
			redirectAttrs.addFlashAttribute("message", message);

			/*
			 * リダイレクトしたい場合は"redirect:"を付ける。
			 * http://qiita.com/tag1216/items/3680b92cf96eb5a170f0
			 */
			return "redirect:/form";
		}
	}

}
