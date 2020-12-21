package com.example.storehouse.web;

import com.example.storehouse.dto.ItemTo;
import com.example.storehouse.dto.RestResponseTo;
import com.example.storehouse.model.Item;
import com.example.storehouse.service.ItemsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.endpoints.items.base_url}")
@RequiredArgsConstructor
public class ItemsController {

    private final ItemsService itemsService;

    @GetMapping
    public RestResponseTo<List<Item>> get(@RequestParam(required = false) String name) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, itemsService.get(name)
        );
    }

    @GetMapping(path = "/{id}")
    public RestResponseTo<Item> getById(@PathVariable Integer id) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, itemsService.getById(id)
        );
    }

    // Пока ничего умнее /balance не придумал
    @GetMapping(path = "/{id}/balance")
    public RestResponseTo<ItemTo> getByIdWithBalance(@PathVariable Integer id) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, itemsService.getByIdWithBalance(id)
        );
    }

}
