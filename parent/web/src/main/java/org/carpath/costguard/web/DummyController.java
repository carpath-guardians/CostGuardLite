package org.carpath.costguard.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/analyze")
@RestController
public class DummyController {

    @GetMapping
    public List<JsonReport> dummy() {
        return List.of();
    }
}
