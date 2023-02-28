package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;

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
    public void requestBodyString(HttpServletRequest request , HttpServletResponse response) throws IOException {
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
    public void requestBodyString(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={} ",messageBody);
        responseWriter.write("ok");

    }
}