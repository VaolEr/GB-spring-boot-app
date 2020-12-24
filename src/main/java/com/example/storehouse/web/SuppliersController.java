package com.example.storehouse.web;

import static com.example.storehouse.util.ItemsUtil.toItemTo;
import static com.example.storehouse.util.SuppliersUtil.toSupplierTo;
import static com.example.storehouse.util.SuppliersUtil.toSupplierTos;

import com.example.storehouse.dto.ItemTo;
import com.example.storehouse.dto.RestResponseTo;
import com.example.storehouse.dto.SupplierTo;
import com.example.storehouse.model.Item;
import com.example.storehouse.model.Supplier;
import com.example.storehouse.service.SuppliersService;
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
@RequestMapping("${app.endpoints.suppliers.base_url}")
@RequiredArgsConstructor
public class SuppliersController {

    private final SuppliersService suppliersService;

    @GetMapping
    public RestResponseTo<List<SupplierTo>> getAllOrByName(@RequestParam(required = false) String name) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toSupplierTos(suppliersService.get(name))
        );
    }

    @GetMapping(path = "/{id}")
    public RestResponseTo<SupplierTo> getById(@PathVariable Integer id) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toSupplierTo(suppliersService.getById(id))
        );
    }

    // TODO: Возвращать ТО после создания
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody SupplierTo supplierTo) {
        Supplier created = suppliersService.create(supplierTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentRequest().
            path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(
            new RestResponseTo<>(HttpStatus.CREATED.toString(), null, toSupplierTo(created))
        );
    }

    @PutMapping(path = "/{id}")
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
