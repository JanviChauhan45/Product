package in.main.Product;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ProductMasterController {

    @GetMapping("/")
    public String indexPage(){
        return "index";
    }

    @GetMapping("/signup")
    public String signupPage(){
        return "signup";
    }

    @GetMapping("/category_master")
    public String categoryMasterPage(HttpSession session){
        Object user = session.getAttribute("LoggedInUser");
        if(user == null){
            return "redirect:/";
        }
        return "category_master";
    }

    @GetMapping("/product_master")
    public String productMasterPage(HttpSession session){

        Object user = session.getAttribute("LoggedInUser");

        if(user == null){
            return "redirect:/";
        }
        return "product_master";
    }

    @GetMapping("/sub_category_master")
    public String subCategoryMasterPage(HttpSession session){
        Object user = session.getAttribute("LoggedInUser");
        if(user == null){
            return "redirect:/";
        }
        return "sub_category_master";
    }

    @GetMapping("/session-test")
    @ResponseBody
    public String sessionTest(HttpSession session) {
        Object user = session.getAttribute("LoggedInUser");

        if(user == null){
            return "SESSION EMPTY";
        }
        return user.toString();
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }


}
