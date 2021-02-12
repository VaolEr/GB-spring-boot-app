package com.example.storehouse.web;

import com.example.storehouse.dto.CategoryTo;
import com.example.storehouse.dto.RestResponseTo;
import com.example.storehouse.dto.UnitTo;
import com.example.storehouse.model.Category;
import com.example.storehouse.model.Unit;
import com.example.storehouse.service.UnitsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.example.storehouse.util.CategoriesUtil.toCategoryTo;
import static com.example.storehouse.util.UnitsUtil.toUnitTo;
import static com.example.storehouse.util.UnitsUtil.toUnitTos;

@RestController
@RequestMapping(value = "${app.endpoints.base_path}" + "${app.endpoints.units.base_url}",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Units", description = "Units REST API controller")
public class UnitsController {

    private final UnitsService unitsService;

    @GetMapping
    @PreAuthorize("hasAuthority('db:users:read')")
    @Operation(summary = "Get all units or list of units where unit name contains [name]")
    public RestResponseTo<List<UnitTo>> getAllOrByName(@RequestParam(required = false) String name) {
        return new RestResponseTo<>(
                HttpStatus.OK.toString(), null, toUnitTos(unitsService.get(name))
        );
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('db:users:read')")
    @Operation(summary = "Get an unit by id")
    public RestResponseTo<UnitTo> getById(@PathVariable Integer id) {
        return new RestResponseTo<>(
                HttpStatus.OK.toString(), null, toUnitTo(unitsService.getById(id))
        );
    }

//    @GetMapping(path = "/{id}/items")
//    @PreAuthorize("hasAuthority('db:users:read')")
//    @Operation(summary = "Get a list of unit items by unit id")
//    public RestResponseTo<List<ItemTo>> getSuppliersItems(@PathVariable Integer id) {
//        return new RestResponseTo<>(
//                HttpStatus.OK.toString(), null, toItemTos(unitsService.getSupplierItems(id))
//        );
//    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create new unit")
    @PreAuthorize("hasAuthority('db:users:write')")
    public ResponseEntity<?> create(@Valid @RequestBody UnitTo unitTo) {
        Unit created = unitsService.create(unitTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(
                new RestResponseTo<>(HttpStatus.CREATED.toString(), null, toUnitTo(created))
        );
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('db:users:write')")
    @Operation(summary = "Update an unit by id")
    public RestResponseTo<UnitTo> update(@RequestBody UnitTo unitTo, @PathVariable Integer id) {
        return new RestResponseTo<>(
                HttpStatus.OK.toString(), null, toUnitTo(unitsService.update(unitTo, id))
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
