package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * {"username":"hello", "age":20}
 * content-type: application/json
 */
@Slf4j
@Controller
public class RequestBodyJsonController {
    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request , HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}",messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={} , userage={}",helloData.getUsername(),helloData.getAge());
        response.getWriter().write("hello");
    }
    @PostMapping("/request-body-json-v2")
    @ResponseBody
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {

        log.info("messageBody={}",messageBody);
        //문자로 된 JSON 데이터인 messageBody 를 objectMapper 를 통해서 자바 객체로 변환한다.
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={} , userage={}",helloData.getUsername(),helloData.getAge());
        return "hello";
    }
    //문자로 변환하고 다시 json으로 변환하는 과정이 불편하다. @ModelAttribute처럼 한번에 객체로 변환할 수는 없을까?
    //V3에서 해결.
    //@RequestBody HelloData data
    //@RequestBody 에 직접 만든 객체를 지정할 수 있다.
    @PostMapping("/request-body-json-v3")
    @ResponseBody
    public String requestBodyJsonV3(@RequestBody HelloData helloData) throws IOException {
        log.info("username={} , userage={}",helloData.getUsername(),helloData.getAge());
        return "hello";
    }
    //**중요!! @RequestBody는 생략하면 안됨. ModelAttribute로 들어감;; (파라미터로 넘오언거 이건 바디잔슴)

    //4번쨰 HttpEntity
    @PostMapping("/request-body-json-v4")
    @ResponseBody
    public String requestBodyJsonV4(HttpEntity<HelloData> data) throws IOException {
        HelloData helloData = data.getBody();
        log.info("username={} , userage={}",helloData.getUsername(),helloData.getAge());
        return "hello";
    }

    //5번쨰 @ResponseBody 응답의 경우에도 @ResponseBody 를 사용하면 해당 객체를 HTTP 메시지 바디에 직접 넣어줄 수 있다.
    @PostMapping("/request-body-json-v5")
    @ResponseBody
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) throws IOException {
        log.info("username={} , userage={}",data.getUsername(),data.getAge());
        return data;
    }

    //중요 정리!
    //@RequestBody  요청 = JSON 요청 -> HTTP 메시지 컨버터 -> 객체
    //@ResponseBody 응답 = 객체 -> HTTP 메시지 컨버터  -> JSON 응답
}
