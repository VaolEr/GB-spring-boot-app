package com.example.storehouse.web;

import static com.example.storehouse.util.CategoriesUtil.toCategoryTo;
import static com.example.storehouse.util.CategoriesUtil.toCategoryTos;
import static com.example.storehouse.util.ItemsUtil.toItemTos;

import com.example.storehouse.dto.CategoryTo;
import com.example.storehouse.dto.ItemTo;
import com.example.storehouse.dto.RestResponseTo;
import com.example.storehouse.model.Category;
import com.example.storehouse.service.CategoriesService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("${app.endpoints.base_path}" + "${app.endpoints.categories.base_url}")
@RequiredArgsConstructor
public class CategoriesController {

    private final CategoriesService categoriesService;

    @GetMapping
    @PreAuthorize("hasAuthority('db:users:read')")
    public RestResponseTo<List<CategoryTo>> getAllOrByName(
        @RequestParam(required = false) String name) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toCategoryTos(categoriesService.get(name))
        );
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('db:users:read')")
    public RestResponseTo<CategoryTo> getById(@PathVariable Integer id) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toCategoryTo(categoriesService.getById(id))
        );
    }

    @GetMapping(path = "/{id}/items")
    @PreAuthorize("hasAuthority('db:users:read')")
    public RestResponseTo<List<ItemTo>> getCategoriesItems(@PathVariable Integer id) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toItemTos(categoriesService.getCategoryItems(id))
        );
    }

    // TODO: Возвращать ТО после создания
    @PostMapping
    @PreAuthorize("hasAuthority('db:users:write')")
    public ResponseEntity<?> create(@Valid @RequestBody CategoryTo categoryTo) {
        Category created = categoriesService.create(categoryTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentRequest().
            path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(
            new RestResponseTo<>(HttpStatus.CREATED.toString(), null, toCategoryTo(created))
        );
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('db:users:write')")
    public RestResponseTo<CategoryTo> update(@RequestBody CategoryTo categoryTo,
                                             @PathVariable Integer id) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toCategoryTo(categoriesService.update(categoryTo, id))
        );
    }

// Не используем, так как при удалении поставщика удалятся и все товары, связанные с ним,
// что недопустимо!
//    @DeleteMapping(path = "/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete(@PathVariable Integer id) {
//        categoriesService.delete(id);
//    }

}
