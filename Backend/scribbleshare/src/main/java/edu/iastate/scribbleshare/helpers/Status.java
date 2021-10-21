package edu.iastate.scribbleshare.helpers;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import java.io.IOException;

public class Status {
    public static void formResponse(HttpServletResponse response, HttpStatus status){
        response.setStatus(status.value());
    }

    public static void formResponse(HttpServletResponse response, HttpStatus status, String message){
        response.setStatus(status.value());
        try {
          response.getWriter().println(message);
        } catch (IOException e) {
          e.printStackTrace();
        }
    }

    public static void formResponse(HttpServletResponse response, HttpStatus status, String message, String headerName, String headerValue){
        response.setStatus(status.value());
        try {
          response.getWriter().println(message);
        } catch (IOException e) {
          e.printStackTrace();
        }
        response.setHeader(headerName, headerValue);
    }
}
