package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {
    //1번쨰 방법 파라미터로 HttpServletRequest를 받은 후 getParameber로 뽑는다.
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request , HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}",username,age);
        response.getWriter().write("ok");
    }

    //2번째 방법 @RequestParam을 사용하여 바로 뽑느다.
    @ResponseBody //Controller를 사용했기때문에 리턴을 문자열로 봔한하려면 ResponseBody사용 해야함.
    @RequestMapping("/request-param-v2")
    public String requestParam(
            @RequestParam("username") String memberName, //결국 request.getParameter이랑 똑같은것임.
            @RequestParam("age") int memberAge){
        log.info("username={}, age={}",memberName,memberAge);
        return "ok";
    }

    //3번째 방법 @RequestParam을 사용하여 바로 뽑는데 더 줄여서 뽑는다. 변수명과 리퀘스트파라미터키값이 일치할경우.
    @ResponseBody //Controller를 사용했기때문에 리턴을 문자열로 봔한하려면 ResponseBody사용 해야함.
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username, // 어노테이션뒤에()생략가능 대신 변수명이 받는 파라미터값 키값이랑 이름이 일치하여야함.
            @RequestParam int age){
        log.info("username={}, age={}",username,age);
        return "ok";
    }
    //4번째 방법 @RequestParam이것도 생략한다..
    @ResponseBody //Controller를 사용했기때문에 리턴을 문자열로 봔한하려면 ResponseBody사용 해야함.
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username,int age){
        //미쳤다. 그냥 파라미터만 넣어도 매핑해준다.대신 요청파라미터 값과 일치하여야 한다.
        //대신 String,Integer,int와 같은 단순타입일 경우에만 생략이 가능하다.
        log.info("username={}, age={}",username,age);
        return "ok";
    }

    //5번쨰 required로 필수값 정의
    @ResponseBody //Controller를 사용했기때문에 리턴을 문자열로 봔한하려면 ResponseBody사용 해야함.
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username, //필수로 받아야함 아니면 400에러
            @RequestParam(required = false) int age) {      //필수가 아님.
        // @RequestParam.required
        // 파라미터 필수 여부
        // 기본값이 파라미터 필수( true )이다 
        log.info("username={}, age={}",username,age);
        return "ok";
    }
    //6번째 defaultValue로 값이 안들어올경우 기본값 정의 "" null 둘다 포함
    @ResponseBody //Controller를 사용했기때문에 리턴을 문자열로 봔한하려면 ResponseBody사용 해야함.
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true , defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int age) {
        log.info("username={}, age={}",username,age);
        return "ok";
    }
    //7번째 모든 요청 파라미터 받는법
    @ResponseBody //Controller를 사용했기때문에 리턴을 문자열로 봔한하려면 ResponseBody사용 해야함.
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        //파라미터값이 한개라면 Map을 사용해도된다. 그렇지 않다면 MultiValueMap을 사용할것.
        log.info("username={}, age={}",paramMap.get("username"),paramMap.get("age"));
        return "ok";
    }
    /**
     * @ModelAttribute 사용
     * 참고: model.addAttribute(helloData) 코드도 함께 자동 적용됨, 뒤에 model을 설명할 때
    자세히 설명
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    //@ModelAttribute는
    //객체의 프로퍼티를 찾는다. 그리고 해당 프로퍼티의 setter를
    //호출해서 파라미터의 값을 입력(바인딩) 한다.
    //예) 파라미터 이름이 username 이면 setUsername() 메서드를 찾아서 호출하면서 값을 입력한다.
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(),
                helloData.getAge());
        return "ok";
    }
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) { //@ModelAttribute도 생략이 가능하다.
        log.info("username={}, age={}", helloData.getUsername(),
                helloData.getAge());
        return "ok";
    }
    //정리 중요!
    //스프링은 해당 생략시 다음과 같은 규칙을 적용한다.
    //String , int , Integer 같은 단순 타입 = @RequestParam
    //나머지 = @ModelAttribute (argument resolver 로 지정해둔 타입 외)
}
