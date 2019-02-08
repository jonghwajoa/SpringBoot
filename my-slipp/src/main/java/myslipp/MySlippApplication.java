package myslipp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@SpringBootApplication
public class MySlippApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySlippApplication.class, args);
	}

	@GetMapping("/jonghwa")
	@ResponseBody
	public static String jonghwa() {
		return "jonghwa!!!!";
	}

	@GetMapping("jong")
	public static String login() {
		return "login.html";
	}

	@PostMapping("hehe")
	@ResponseBody
	public static void signUp(@RequestBody String reqeust) {
		System.out.println("호출");
		System.out.println(reqeust);
	}

	@GetMapping("hehe")
	@ResponseBody
	public static String signUp() {
		return "힝힝힝힝힝";
	}

}
