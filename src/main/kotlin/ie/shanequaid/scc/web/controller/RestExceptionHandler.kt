package ie.shanequaid.scc.web.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.ws.rs.NotFoundException

@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [(NotFoundException::class)])
    fun handleAccessDeniedException(
        ex: Exception, request: WebRequest
    ): ResponseEntity<String> {
        return ResponseEntity(
            "There was a down stream dependency issue, please try again later", HttpHeaders(), HttpStatus.FAILED_DEPENDENCY
        );
    }
}