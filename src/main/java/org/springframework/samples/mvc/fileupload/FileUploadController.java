package org.springframework.samples.mvc.fileupload;

import java.io.IOException;

import org.springframework.mvc.extensions.ajax.AjaxUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/fileupload")
public class FileUploadController {

	/**
	 * @param request org.springframework.web.context.request.WebRequestとorg.springframework.web.context.request.NativeWebRequest。
	 * これらのオブジェクトを通じて、ネイティブのサーブレットAPIに縛られることなく、
	 * リクエストやセッションの属性、みならずリクエスト・パラメータにもアクセスできるようになります。
	 * http://m12i.hatenablog.com/entry/2014/11/16/173614
	 * @param model
	 */
	@ModelAttribute
	public void ajaxAttribute(WebRequest request, Model model) {
		/*
		 * リクエストがAjaxリクエストか判定。
		 * リクエストヘッダーにX-Requested-Withが存在するか。
		 */
		model.addAttribute("ajaxRequest", AjaxUtils.isAjaxRequest(request));
	}

	@RequestMapping(method=RequestMethod.GET)
	public void fileUploadForm() {
	}

	/**
	 *
	 * @param file アップロードされたファイルは「org.springframework.web.multipart.MultipartFile」で受け取ります。
	 * http://qiita.com/nvtomo1029/items/316c5e8fe5d0cd92339c
	 */
	@RequestMapping(method=RequestMethod.POST)
	public void processUpload(@RequestParam MultipartFile file, Model model) throws IOException {
		model.addAttribute("message", "File '" + file.getOriginalFilename() + "' uploaded successfully");
	}

}
