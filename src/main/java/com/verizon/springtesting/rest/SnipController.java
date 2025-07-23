package com.verizon.springtesting.rest;

import com.verizon.springtesting.models.Snippet;
import com.verizon.springtesting.models.DBUser;
import com.verizon.springtesting.repository.SnippetRepo;
import com.verizon.springtesting.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/snippets") //path I'm targeting
public class SnipController {

    @Autowired
    private SnippetRepo snippetRepo;

    @Autowired
    private UserRepo userRepo;

    //post to db
    @PostMapping
    public Snippet createSnippet(@RequestBody SnippetRequest snippetReq) {
        DBUser user = userRepo.findById(snippetReq.userId()).orElseThrow(() -> new RuntimeException("User wasnt found"));

        Snippet snippet = new Snippet(); //new snippet
        //set snippet data
        snippet.setLanguage(snippetReq.language());
        snippet.setCode(snippetReq.code());
        snippet.setDBUser(user);

        return snippetRepo.save(snippet);
    }
    //get everything
    @GetMapping
    public List<Snippet> getSnippets(@RequestParam(required = false) String language) {
        if (language != null) {
            return snippetRepo.findByLanguageIgnoreCase(language);
        }
        return snippetRepo.findAll();
    }

//    @GetMapping
//    public List<Snippet> snippetRepo(String language) {
//        if (language != null) {
//            return snippetRepo(language);
//        }
//        return snippetRepo.findAll();
//    }
    
    //get by id
    @GetMapping("/{id}")
    public Snippet getSnippetById(@PathVariable Integer id) {
        return snippetRepo.findById(id).orElseThrow(() -> new RuntimeException("Snippet could not be found"));
    }

    public record SnippetRequest(Integer userId, String language, String code) {}

}
