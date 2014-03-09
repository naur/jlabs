package labs.web.controller;

import labs.web.ControllerBase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 8/13/12
 * Time: 5:38 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "dom", method = {RequestMethod.GET, RequestMethod.POST})
public class DomController extends ControllerBase {

    @RequestMapping("jsrender")
    public String jsrender() {
        return view("jsrender");
    }

    public DomController() {
        viewPath = "dom";
    }
}
