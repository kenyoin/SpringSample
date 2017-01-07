package org.springframework.samples.mvc.convert;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

/**
 * フォーマットアノテーションを作成するファクトリークラス
 */
public class MaskFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<MaskFormat> {

	public Set<Class<?>> getFieldTypes() {
		Set<Class<?>> fieldTypes = new HashSet<Class<?>>(1, 1);
		fieldTypes.add(String.class);
		return fieldTypes;
	}

	public Parser<?> getParser(MaskFormat annotation, Class<?> fieldType) {
		return new MaskFormatter(annotation.value());
	}

	public Printer<?> getPrinter(MaskFormat annotation, Class<?> fieldType) {
		return new MaskFormatter(annotation.value());
	}

	/**
	 * String型を変換するフォーマッター
	 */
	private static class MaskFormatter implements Formatter<String> {

		/**
		 * MaskFormatter は、文字列の書式設定および編集に使用されます。MaskFormatter の動作は Document モデルの特定の位置にある有効な文字を指定する String マスク経由で制御されます。次の文字を指定できます。
		 * 文字 説明
		 * #	任意の有効な数字。Character.isDigit を使用する
		 * '	エスケープ文字。特殊フォーマット文字をエスケープする
		 * U	任意の文字 (Character.isLetter)すべての小文字は大文字にマッピングされる。
		 * L	任意の文字 (Character.isLetter)すべての大文字は小文字にマッピングされる。
		 * A	任意の文字または数字 (Character.isLetter または Character.isDigit)
		 * ?	任意の文字 (Character.isLetter)
		 * *	すべての文字および数字
		 * H	任意の 16 進数文字 (0-9、a-f または A-F)
		 * http://e-class.center.yuge.ac.jp/jdk_docs/ja/api/index.html?javax/swing/text/MaskFormatter.html
		 */
		private javax.swing.text.MaskFormatter delegate;

		public MaskFormatter(String mask) {
			try {
				this.delegate = new javax.swing.text.MaskFormatter(mask);
				this.delegate.setValueContainsLiteralCharacters(false);
			} catch (ParseException e) {
				throw new IllegalStateException("Mask could not be parsed " + mask, e);
			}
		}

		/**
		 * @param object フォーマット対象文字列
		 */
		public String print(String object, Locale locale) {
			try {
				return delegate.valueToString(object);
			} catch (ParseException e) {
				throw new IllegalArgumentException("Unable to print using mask " + delegate.getMask(), e);
			}
		}

		public String parse(String text, Locale locale) throws ParseException {
			return (String) delegate.stringToValue(text);
		}

	}

}
