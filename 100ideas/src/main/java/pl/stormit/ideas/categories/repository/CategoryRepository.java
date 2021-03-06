package pl.stormit.ideas.categories.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.stormit.ideas.categories.domain.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends CrudRepository<Category, UUID> {
    List<Category> findAllById(UUID id);

    List<Category> findAll();

    Optional<Category> findByName(String name);
}
