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
import org.springframework.http.ResponseEntity;
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
@RequestMapping("${app.endpoints.items.base_url}")
@RequiredArgsConstructor
public class ItemsController {

    private final ItemsService itemsService;

    @GetMapping
    public RestResponseTo<List<ItemTo>> getAllOrByName(@RequestParam(required = false) String name) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toItemTos(itemsService.get(name))
        );
    }

    @GetMapping(path = "/{id}")
    public RestResponseTo<ItemTo> getById(@PathVariable Integer id) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toItemTo(itemsService.getById(id))
        );
    }

    // TODO: Возвращать ТО после создания
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ItemTo itemTo) {
        Item created = itemsService.create(itemTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentRequest().
            path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(
            new RestResponseTo<>(HttpStatus.CREATED.toString(), null, toItemToWithBalance(created))
        );
    }

    // Валидацию попр. реализовать через @Validated для разделения проверок ItemTo и ItemStorehouseTo
    @PutMapping(path = "/{id}")
    public RestResponseTo<ItemTo> update(@RequestBody ItemTo itemTo, @PathVariable Integer id) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toItemTo(itemsService.update(itemTo, id))
        );
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        itemsService.delete(id);
    }

}