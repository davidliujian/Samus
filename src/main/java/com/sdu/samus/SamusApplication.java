package com.sdu.samus;

import com.sdu.samus.util.StringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SamusApplication {

	@RequestMapping("/")
	String index(){
		return "Hello Spring Boot";
	}

	public static void main(String[] args) {
		SpringApplication.run(SamusApplication.class, args);
//		System.out.println("haha\r\nheihei");
//		StringUtil.split("00335201228133654\\1000519201598046755\\1000519201618350398\\1000519201823384941\\1000583201869933929\\1010002201599177541\\1010004201390321697","\\10");
	}
}
