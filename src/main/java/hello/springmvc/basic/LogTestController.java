package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller 이걸사용하면 return시키면 뷰 이름이 반환되지만, RestController를 사용하면 RESTAPI
@Slf4j // 이거 달면 아래 log객체 생성 안해줘도 됨.
@RestController
public class LogTestController {
    //private final Logger log = LoggerFactory.getLogger(LogTestController.class); //slf4j 로그 라이브러리

    @RequestMapping("/log-test")
    public String logTest(){
        String name = "Spring";
        System.out.println("name = " + name);//sadlkjasdjlk
        System.out.println("hi");

        //{} 치환되는거. 되도록이면 이렇게 쓸것. 자바가 의미없는 연산을 안함.
        log.trace(" trace log={}",name);  //중요한정보야
        log.debug(" trace debug={}",name); //개발서버에서 보는거야
        log.info (" info log ={}",name); //중요한정보야, 또는 운영시스템에서도 봐야될 정보야
        log.warn (" trace warn={}",name); //경고 위험한거야
        log.error(" trace error={}",name); //에러야.  sdfsdfasdasd ss

        return "ok"; //바로 스트링이 반환된다.
    }
}
