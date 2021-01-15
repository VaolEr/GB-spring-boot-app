package com.example.storehouse.repository;

import com.example.storehouse.model.Item;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ItemsRepository extends JpaRepository<Item, Integer> {

    // проверить - вытаскивать только id через граф зависимостей:
    //https://stackoverflow.com/questions/53490655/how-to-use-jpa-entitygraph-to-load-only-a-subset-of-entity-basic-attributes
    // ?поменять дублирующийся @EntityGraph(attributePaths = {"category", "supplier"} на NamedEntityGraph?

    @Override
    @EntityGraph(attributePaths = {"category", "supplier"}, type = EntityGraphType.LOAD)
    Page<Item> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"category", "supplier"}, type = EntityGraphType.LOAD)
    Page<Item> findByNameContaining(String name, Pageable pageable);

    @EntityGraph(attributePaths = {"category", "supplier"}, type = EntityGraphType.LOAD)
    Optional<Item> findById(Integer id);

    // Work correct. Need to set fetch type to eager in Item class.
    // Return list of items, where category and supplier are objects with id and name
    List<Item> getByItemStorehousesStorehouseId(Integer storehouseId);

    // @Query(value = "SELECT items.id, items.name, items.category_id, items.sku, items.supplier_id FROM items, items_storehouses WHERE items.id = :itemId and storehouse_id = :storehouseId", nativeQuery = true)
    @Query(value = "SELECT i.id, i.name, i.category, i.sku, i.supplier FROM Item i, ItemStorehouse i_s"
        + " WHERE i.id = :itemId AND i_s.storehouse.id = :storehouseId")
    Optional<Item> getStorehouseItemByStorehouseIdAndItemId(@Param("storehouseId") Integer storehouseId, @Param("itemId") Integer itemId);

}
