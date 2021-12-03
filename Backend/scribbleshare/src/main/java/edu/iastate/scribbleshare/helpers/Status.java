package edu.iastate.scribbleshare.helpers;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import java.io.IOException;

/**
 * General class for forming status responses easily. Methods in this class should be static. 
 */
public class Status {
  /**
   * Form a given status response
   * @param response response object
   * @param status the status code
   */
    public static void formResponse(HttpServletResponse response, HttpStatus status){
        response.setStatus(status.value());
    }

    /**
     * Form a given status response with a message
     * @param response response object
     * @param status the status code
     * @param message status message
     */
    public static void formResponse(HttpServletResponse response, HttpStatus status, String message){
        response.setStatus(status.value());
        try {
          response.getWriter().println(message);
        } catch (IOException e) {
          e.printStackTrace();
        }
    }

    /**
     * Form a given status response with a single custom header and a message
     * @param response response object
     * @param status the status code
     * @param message status message
     * @param headerName custom header name
     * @param headerValue value for the custom header
     */
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
