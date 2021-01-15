package com.example.storehouse.web;

import static com.example.storehouse.util.ItemsUtil.toItemTo;
import static com.example.storehouse.util.ItemsUtil.toItemToWithBalance;

import com.example.storehouse.dto.ItemTo;
import com.example.storehouse.dto.RestResponseTo;
import com.example.storehouse.model.Item;
import com.example.storehouse.service.ItemsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "${app.endpoints.base_path}" + "${app.endpoints.items.base_url}",
    produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Items", description = "Items REST API controller")
public class ItemsController {

    private final ItemsService itemsService;

    @GetMapping
    @Operation(summary = "Get all items or list of items where item name contains [name]")
    @PreAuthorize("hasAuthority('db:users:read')")
    public RestResponseTo<Page<ItemTo>> getAllOrByName(
        @RequestParam(required = false) String name,
        @ParameterObject Pageable pageable) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, itemsService.get(pageable, name)
        );
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get an item by id")
    @PreAuthorize("hasAuthority('db:users:read')")
    public RestResponseTo<ItemTo> getById(@Parameter(description = "id of item to be searched") @PathVariable Integer id) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toItemToWithBalance(itemsService.getById(id))
        );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create new item")
    @PreAuthorize("hasAuthority('db:users:write')")
    public ResponseEntity<?> create(@Valid @RequestBody ItemTo itemTo) {
        Item created = itemsService.create(itemTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentRequest().
            path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(
            new RestResponseTo<>(HttpStatus.CREATED.toString(), null, toItemToWithBalance(created))
        );
    }

    // Валидацию попр. реализовать через @Validated для разделения проверок ItemTo и ItemStorehouseTo
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update an item by id")
    @PreAuthorize("hasAuthority('db:users:write')")
    public RestResponseTo<ItemTo> update(@RequestBody ItemTo itemTo,
                                         @Parameter(description = "id of item to be updated") @PathVariable Integer id) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toItemTo(itemsService.update(itemTo, id))
        );
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete an item by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('db:users:write')")
    public void delete(@Parameter(description = "id of item to be deleted") @PathVariable Integer id) {
        itemsService.delete(id);
    }

}
