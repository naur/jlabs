package labs.web.controller;

import labs.common.entities.Information;
import labs.common.entities.InformationLevel;
import labs.common.patterns.exception.Func;
import labs.web.ControllerBase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 8/13/12
 * Time: 5:38 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "js", method = {RequestMethod.GET, RequestMethod.POST})
public class JsController extends ControllerBase {

    @RequestMapping()
    public String view() {
        return "upload";
    }
}
