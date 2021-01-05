package com.example.storehouse.web;

import static com.example.storehouse.util.ItemsUtil.toItemTo;
import static com.example.storehouse.util.ItemsUtil.toItemToWithBalance;
import static com.example.storehouse.util.ItemsUtil.toItemTos;

import com.example.storehouse.dto.ItemTo;
import com.example.storehouse.dto.RestResponseTo;
import com.example.storehouse.model.Item;

import com.example.storehouse.service.ItemsService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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
public class ItemsController {

    private final ItemsService itemsService;

    @GetMapping
    @PreAuthorize("hasAuthority('db:users:read')")
    public RestResponseTo<List<ItemTo>> getAllOrByName(@RequestParam(required = false) String name) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toItemTos(itemsService.get(name))
        );
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('db:users:read')")
    public RestResponseTo<ItemTo> getById(@PathVariable Integer id) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toItemTo(itemsService.getById(id))
        );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
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
    @PreAuthorize("hasAuthority('db:users:write')")
    public RestResponseTo<ItemTo> update(@RequestBody ItemTo itemTo, @PathVariable Integer id) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toItemTo(itemsService.update(itemTo, id))
        );
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('db:users:write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        itemsService.delete(id);
    }

}
