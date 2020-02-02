package life.majiang.community.community.exception;

import org.springframework.boot.web.servlet.error.ErrorController;

public enum CustomizeErrorController implements ErrorController {
    ;


    @Override
    public String getErrorPath() {
        return "error";
    }
}
