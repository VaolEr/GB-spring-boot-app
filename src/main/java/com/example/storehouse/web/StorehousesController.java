package com.example.storehouse.web;

import static com.example.storehouse.util.ItemsUtil.toItemTos;
import static com.example.storehouse.util.StorehousesUtil.toStorehouseTo;
import static com.example.storehouse.util.StorehousesUtil.toStorehouseTos;
import static com.example.storehouse.util.SuppliersUtil.toSupplierTo;
import static com.example.storehouse.util.SuppliersUtil.toSupplierTos;

import com.example.storehouse.dto.ItemTo;
import com.example.storehouse.dto.RestResponseTo;
import com.example.storehouse.dto.StorehouseTo;
import com.example.storehouse.dto.SupplierTo;
import com.example.storehouse.model.Storehouse;
import com.example.storehouse.model.Supplier;
import com.example.storehouse.service.StorehousesService;
import com.example.storehouse.service.SuppliersService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
public class StorehousesController {

    private final StorehousesService storehousesService;

    @GetMapping
    public RestResponseTo<List<StorehouseTo>> getAllOrByName(@RequestParam(required = false) String name) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toStorehouseTos(storehousesService.get(name))
        );
    }

    @GetMapping(path = "/{id}")
    public RestResponseTo<StorehouseTo> getById(@PathVariable Integer id) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toStorehouseTo(storehousesService.getById(id))
        );
    }

    @GetMapping(path = "/{id}/items")
    public RestResponseTo<List<ItemTo>> getStorehousesItems(@PathVariable Integer id) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toItemTos(storehousesService.getStorehouseItems(id))
        );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody StorehouseTo storehouseTo) {
        Storehouse created = storehousesService.create(storehouseTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentRequest().
            path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(
            new RestResponseTo<>(HttpStatus.CREATED.toString(), null, toStorehouseTo(created))
        );
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RestResponseTo<StorehouseTo> update(@RequestBody StorehouseTo storehouseTo, @PathVariable Integer id) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toStorehouseTo(storehousesService.update(storehouseTo, id))
        );
    }

}
