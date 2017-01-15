package org.springframework.samples.mvc.mapping;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * クラスに付いている@XMLRootElementというアノテーションがポイントです。
 * @XMLRootElementを付けることで、このクラスはXMLのルート要素になりますよ、って意味になります。
 * http://murayama.hatenablog.com/entry/20100829/1283051083
 */
@XmlRootElement
public class JavaBean {

	private String foo = "bar";

	private String fruit = "apple";

	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}

	public String getFruit() {
		return fruit;
	}

	public void setFruit(String fruit) {
		this.fruit = fruit;
	}

	@Override
	public String toString() {
		return "JavaBean {foo=[" + foo + "], fruit=[" + fruit + "]}";
	}

}
