package com.verizon.springtesting.rest;

import com.verizon.springtesting.models.DBUser;
import com.verizon.springtesting.models.Snippet;
import com.verizon.springtesting.repository.SnippetRepo;
import com.verizon.springtesting.repository.UserRepo;
import com.verizon.springtesting.utils.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/snippets")
public class SnipController {

    @Autowired
    private SnippetRepo snippetRepo;

    @Autowired
    private UserRepo userRepo;

    @PostMapping
    public Snippet createSnippet(@RequestBody SnippetRequest snippetReq) {
        DBUser user = userRepo.findById(snippetReq.userId()).orElseThrow(() -> new RuntimeException("User wasnt found"));

        Snippet snippet = new Snippet();
        snippet.setLanguage(snippetReq.language());

        try { //encrypt the code
            String encryptedCode = EncryptionUtil.encrypt(snippetReq.code());
            snippet.setCode(encryptedCode);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting snippet: " + e.getMessage());
        }

        snippet.setDBUser(user);

        return snippetRepo.save(snippet);
    }

    @GetMapping
    public List<Snippet> getSnippets(@RequestParam(required = false) String language) {
        //return all snippets if language is null, else return al snippets with the right language
        List<Snippet> snippets = (language != null)
                ? snippetRepo.findByLanguageIgnoreCase(language)
                : snippetRepo.findAll();

        //for loop to iterate over the list
        for (Snippet snippet : snippets) {
            try { //decrypt code
                String decryptedCode = EncryptionUtil.decrypt(snippet.getCode());
                snippet.setCode(decryptedCode);
            } catch (Exception e) {
                throw new RuntimeException("Error decrypting snippet at GET " + e.getMessage());
            }
        }

        return snippets;
    }

    @GetMapping("/{id}")
    public Snippet getSnippetById(@PathVariable Integer id) {
        //find snippet by id
        Snippet snippet = snippetRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Snippet could not be found"));

        try { //decrypt
            String decryptedCode = EncryptionUtil.decrypt(snippet.getCode());
            snippet.setCode(decryptedCode);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting snippet GET /{id}: " + e.getMessage());
        }

        return snippet;
    }

    public record SnippetRequest(Integer userId, String language, String code) {}
}
