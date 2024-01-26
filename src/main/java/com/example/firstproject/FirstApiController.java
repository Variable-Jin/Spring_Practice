package com.example.firstproject;
import com.example.firstproject.entity.test;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController     // REST API 컨트롤러
public class FirstApiController {

    @Autowired
    private testRepository testRepository;

    @GetMapping("/api/hello/{id}")
        public ResponseEntity<test> hello(@PathVariable Long id) {
        test testEntity = testRepository.findById(id).orElse(null);

        if (testEntity != null) {
            return ResponseEntity.ok(testEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
