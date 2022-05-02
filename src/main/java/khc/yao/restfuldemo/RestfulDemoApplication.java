package khc.yao.restfuldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class RestfulDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestfulDemoApplication.class, args);
    }

    @RestController
    class HelloWorldController {
        @GetMapping("/")
        public String hello() {
            return "請直接呼叫 API。(API 可參考 <a href=\"https://restful-demo-dot-gcp-various-demo.uc.r.appspot.com/swagger-ui/index.html\">Swagger 說明</a>)";
        }
    }
}
