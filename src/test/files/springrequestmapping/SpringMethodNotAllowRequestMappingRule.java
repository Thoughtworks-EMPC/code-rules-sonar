import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
@Controller
public class CustomerPageController{

  @RequestMapping( "baseUrl")// Noncompliant
  public void method1(){

  }
  @RequestMapping(value = "baseUrl")// Noncompliant
  public void method2(){

  }



}
