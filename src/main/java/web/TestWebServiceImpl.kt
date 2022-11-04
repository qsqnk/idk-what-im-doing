package web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController

class TestWebServiceImpl {

    @GetMapping("/")
    fun test() {
    }
}