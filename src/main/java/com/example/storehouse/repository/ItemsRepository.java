package com.example.storehouse.repository;

import com.example.storehouse.model.Item;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

//@Repository
@Transactional(readOnly = true)
public interface ItemsRepository extends JpaRepository<Item, Integer> {

    // проверить - вытаскивать только id через граф зависимостей:
    //https://stackoverflow.com/questions/53490655/how-to-use-jpa-entitygraph-to-load-only-a-subset-of-entity-basic-attributes
    // ?поменять дублирующийся @EntityGraph(attributePaths = {"category", "supplier"} на NamedEntityGraph?

    @Override
    @EntityGraph(attributePaths = {"category", "supplier"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Item> findAll();

    @EntityGraph(attributePaths = {"category", "supplier"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Item> findByNameContaining(String name);

    @EntityGraph(attributePaths = {"category", "supplier"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Item> findById(Integer id);

    // NOTE: поискать, где-то я уже с циклическими зависимостями разбирался : @JsonIgnoreProperty (будет всё TO - можно убрать)
//    @EntityGraph(attributePaths = {"category", "supplier", "itemStorehouses"},
    @EntityGraph(attributePaths = {"category", "supplier"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Item> getItemById(Integer id);

}