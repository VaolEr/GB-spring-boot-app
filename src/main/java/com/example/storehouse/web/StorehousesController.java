package com.example.storehouse.web;

import static com.example.storehouse.util.ItemsUtil.toItemTos;
import static com.example.storehouse.util.StorehousesUtil.toStorehouseTo;
import static com.example.storehouse.util.StorehousesUtil.toStorehouseTos;

import com.example.storehouse.dto.ItemTo;
import com.example.storehouse.dto.RestResponseTo;
import com.example.storehouse.dto.StorehouseTo;
import com.example.storehouse.model.Storehouse;
import com.example.storehouse.service.StorehousesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping(value = "${app.endpoints.base_path}" + "${app.endpoints.storehouses.base_url}",
    produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Storehouses", description = "Storehouses REST API controller")
public class StorehousesController {

    private final StorehousesService storehousesService;

    @GetMapping
    @PreAuthorize("hasAuthority('db:users:read')")
    @Operation(summary = "Get all storehouses or list of storehouses where storehouse name contains [name]")
    public RestResponseTo<List<StorehouseTo>> getAllOrByName(@RequestParam(required = false) String name) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toStorehouseTos(storehousesService.get(name))
        );
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('db:users:read')")
    @Operation(summary = "Get a storehouse by id")
    public RestResponseTo<StorehouseTo> getById(@PathVariable Integer id) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toStorehouseTo(storehousesService.getById(id))
        );
    }

    @GetMapping(path = "/{id}/items")
    @PreAuthorize("hasAuthority('db:users:read')")
    @Operation(summary = "Get a list of storehouse items by storehouse id")
    public RestResponseTo<List<ItemTo>> getStorehousesItems(@PathVariable Integer id) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, storehousesService.getStorehouseItems(id)
        );
    }

    //  Not correct work. Updated getById method in Items controller
//    @GetMapping(path = "/{storehouseId}/{itemId}")
//    public RestResponseTo<ItemTo> getStorehouseItemById(@PathVariable Integer storehouseId, @PathVariable Integer itemId) {
//        //TODO add check for item with id not found
//        return new RestResponseTo<>(
//            HttpStatus.OK.toString(), null, toItemToWithBalance(storehousesService.getStorehouseItem(storehouseId, itemId))
//        );
//    }

// Prototype func. Realise if need it
//    @GetMapping(path = "/{storehouseId}/{name}")
//    public RestResponseTo<ItemTo> getStorehouseItemByName(@PathVariable Integer storehouseId, @PathVariable String itemName) {
//        //TODO add check for item with id not found
//        return new RestResponseTo<>(
//            HttpStatus.OK.toString(), null, toItemToWithBalance(storehousesService.getStorehouseItem(storehouseId, itemName))
//        );
//    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('db:users:write')")
    @Operation(summary = "Create new storehouse")
    public ResponseEntity<?> create(@Valid @RequestBody StorehouseTo storehouseTo) {
        Storehouse created = storehousesService.create(storehouseTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentRequest().
            path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(
            new RestResponseTo<>(HttpStatus.CREATED.toString(), null, toStorehouseTo(created))
        );
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('db:users:write')")
    @Operation(summary = "Update a storehouse by id")
    public RestResponseTo<StorehouseTo> update(@RequestBody StorehouseTo storehouseTo, @PathVariable Integer id) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toStorehouseTo(storehousesService.update(storehouseTo, id))
        );
    }

}
