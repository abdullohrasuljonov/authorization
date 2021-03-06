package uz.pdp.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.springsecurity.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    boolean existsByName(String name);
}
