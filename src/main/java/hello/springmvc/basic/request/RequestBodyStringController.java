package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {
    //1번쨰 방법
    @PostMapping("/request-body-string-v1")
    public void requestBodyStringV1(HttpServletRequest request , HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        //스트림은 바이트 코드라서 어떤 문자열로 받을지를 지정해줘야한다.
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={} ",messageBody);
        response.getWriter().write("ok");

    }
    //2번쨰 방법 매개변수로 바로 인풋스트림을 받는다.
    //InputStream(Reader): HTTP 요청 메시지 바디의 내용을 직접 조회
    //OutputStream(Writer): HTTP 응답 메시지의 바디에 직접 결과 출력
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={} ",messageBody);
        responseWriter.write("ok");

    }
    //3번쨰 HttpEntity
    //HttpEntity: HTTP header, body 정보를 편리하게 조회
    //메시지 바디 정보를 직접 조회
    //요청 파라미터를 조회하는 기능과 관계 없음 @RequestParam X, @ModelAttribute X
    //HttpEntity는 응답에도 사용 가능
    //메시지 바디 정보 직접 반환
    //헤더 정보 포함 가능
    //view 조회X
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3 (HttpEntity<String> entity) throws IOException {
        //String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        //HttpHeaders headers = entity.getHeaders();

        String messageBody = entity.getBody();
        log.info("messageBody={} ",messageBody);
        return new HttpEntity<>("ok");

    }
    //4번째 @RequestBody , @ResponseBody
    //@RequestBody 를 사용하면 HTTP 메시지 바디 정보를 편리하게 조회할 수 있다. 참고로 헤더 정보가
    //필요하다면 HttpEntity 를 사용하거나 @RequestHeader 를 사용하면 된다.
    //이렇게 메시지 바디를 직접 조회하는 기능은 요청 파라미터를 조회하는 @RequestParam ,
    //@ModelAttribute 와는 전혀 관계가 없다.
    @PostMapping("/request-body-string-v4")
    @ResponseBody
    public String requestBodyStringV4 (@RequestBody String messageBody) throws IOException {
        log.info("messageBody={} ",messageBody);
        return "ok";
    }

    //중요! 정리!
    //요청 파라미터 vs HTTP 메시지 바디
    //요청 파라미터를 조회하는 기능: @RequestParam , @ModelAttribute
    //HTTP 메시지 바디를 직접 조회하는 기능: @RequestBody
    //@ResponseBody
    //@ResponseBody 를 사용하면 응답 결과를 HTTP 메시지 바디에 직접 담아서 전달할 수 있다.
    //물론 이 경우에도 view를 사용하지 않는다
}
