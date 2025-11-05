package trade.up.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
		System.err.println("Shop Application Started Successfully");
		System.err.println("Visit http://localhost:8080 to access the application");
	}

}
