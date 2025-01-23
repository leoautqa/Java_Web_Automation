package locators;

import java.util.HashMap;
import java.util.Map;

public class amazon_locator {
	
	public static final Map<String, String> amazon = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;{
			put("logo", "//div[@id='nav-logo']");
			put("searchBar", "//input[contains(@id,'twotabsearchtextbox')]");
			put("butSearch", "//input[contains(@id,'nav-search-submit-button')]");
			put("filter", "//div[@id='departments']//span[text()='Books']");
			put("booksFil", "//img[contains(@src,'BooksLogo.svg')]");
			put("bookLink", "(//h2[@class='a-size-medium a-spacing-none a-color-base a-text-normal'])[1]");
			put("bookAuthor", "(//span[contains(@class,'a-truncate-cut')])[1]");
			put("customerReviews", "//h2[contains(.,'Customer reviews')]");
			put("bookRate", "//a[contains(@aria-label, '5 stars')]//span[contains(text(), '%')]");
			put("btAddToCart", "//input[@id='add-to-cart-button']");
			put("msAddedToCart", "//h1[contains(.,'Added to cart')]");
			put("quantyBar", "(//span[@class='a-button-text a-declarative'][contains(.,'Quantity:1')])[1]");
    }};
}