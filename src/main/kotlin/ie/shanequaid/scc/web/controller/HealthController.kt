package ie.shanequaid.scc.web.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/public/health"])
class HealthController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    fun checkHealth() {
    }
}
