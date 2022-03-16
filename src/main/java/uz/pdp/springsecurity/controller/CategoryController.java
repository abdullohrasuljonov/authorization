package uz.pdp.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springsecurity.entity.Category;
import uz.pdp.springsecurity.repository.CategoryRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @GetMapping
    public HttpEntity<?> allCategory() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @GetMapping("/{id}")
    public HttpEntity<?> getCategoryById(@PathVariable Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent())
            return ResponseEntity.ok(optionalCategory.get());
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN')")
    @PostMapping
    public HttpEntity<?> addCategory(@RequestBody Category category) {
        boolean exists = categoryRepository.existsByName(category.getName());
        if (exists)
            return ResponseEntity.notFound().build();
        Category savedCategory = categoryRepository.save(category);
        return ResponseEntity.ok(savedCategory);
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN')")
    @PutMapping("/{id}")
    public HttpEntity<?> editCategory(@PathVariable Integer id,@RequestBody Category category){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent())
            return ResponseEntity.notFound().build();
        Category category1 = optionalCategory.get();
        category1.setName(category.getName());
        Category savedCategory = categoryRepository.save(category1);
        return ResponseEntity.ok(savedCategory);
    }

    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCategory(@PathVariable Integer id){
        try {
            categoryRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
