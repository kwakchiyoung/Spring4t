package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {
    //V1 ModelAndView반환
    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1(){
        ModelAndView mav = new ModelAndView("response/hello").addObject("data","hello");
        return mav;
    }
    //V2 String반환
    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model){
        model.addAttribute("data","hello!");
        return "response/hello";//@Controller면서 String을 반환하면 view의 논리적 이름이 된다.
    }
    //V3 반환을 하지않고 매핑에 그대로 넣는것. 권장하지 않는 방법
    @RequestMapping("/resoibse/hello")
    public void responseViewV3(Model model){
        model.addAttribute("data","hello!");
    }
    //@Controller 를 사용하고, HttpServletResponse , OutputStream(Writer) 같은 HTTP 메시지
    //바디를 처리하는 파라미터가 없으면 요청 URL을 참고해서 논리 뷰 이름으로 사용
    //요청 URL: /response/hello
    //실행: templates/response/hello.html
    //참고로 이 방식은 명시성이 너무 떨어지고 이렇게 딱 맞는 경우도 많이 없어서, 권장하지 않는다.
}
