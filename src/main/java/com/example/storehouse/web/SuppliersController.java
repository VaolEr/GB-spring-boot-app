package com.example.storehouse.web;

import static com.example.storehouse.util.ItemsUtil.toItemTos;
import static com.example.storehouse.util.SuppliersUtil.toSupplierTo;
import static com.example.storehouse.util.SuppliersUtil.toSupplierTos;

import com.example.storehouse.dto.ItemTo;
import com.example.storehouse.dto.RestResponseTo;
import com.example.storehouse.dto.SupplierTo;
import com.example.storehouse.model.Supplier;
import com.example.storehouse.service.SuppliersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping(value = "${app.endpoints.base_path}" + "${app.endpoints.suppliers.base_url}",
    produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Suppliers", description = "Suppliers REST API controller")
public class SuppliersController {

    private final SuppliersService suppliersService;

    @GetMapping
    @Operation(summary = "Get all suppliers or list of suppliers where supplier name contains [name]")
    public RestResponseTo<List<SupplierTo>> getAllOrByName(@RequestParam(required = false) String name) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toSupplierTos(suppliersService.get(name))
        );
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get a supplier by id")
    public RestResponseTo<SupplierTo> getById(@PathVariable Integer id) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toSupplierTo(suppliersService.getById(id))
        );
    }

    @GetMapping(path = "/{id}/items")
    @Operation(summary = "Get a list of supplier items by supplier id")
    public RestResponseTo<List<ItemTo>> getSuppliersItems(@PathVariable Integer id) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toItemTos(suppliersService.getSupplierItems(id))
        );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create new supplier")
    public ResponseEntity<?> create(@Valid @RequestBody SupplierTo supplierTo) {
        Supplier created = suppliersService.create(supplierTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentRequest().
            path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(
            new RestResponseTo<>(HttpStatus.CREATED.toString(), null, toSupplierTo(created))
        );
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update a supplier by id")
    public RestResponseTo<SupplierTo> update(@RequestBody SupplierTo supplierTo, @PathVariable Integer id) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toSupplierTo(suppliersService.update(supplierTo, id))
        );
    }

// Не используем, так как при удалении поставщика удалятся и все товары, связанные с ним,
// что недопустимо!
//    @DeleteMapping(path = "/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete(@PathVariable Integer id) {
//        suppliersService.delete(id);
//    }
}
