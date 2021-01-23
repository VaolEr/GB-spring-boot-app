package com.example.storehouse.web;

import static com.example.storehouse.util.UsersUtil.toUserTo;
import static com.example.storehouse.util.UsersUtil.toUserTos;

import com.example.storehouse.dto.RestResponseTo;
import com.example.storehouse.dto.UserTo;
import com.example.storehouse.model.User;
import com.example.storehouse.service.UsersService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping(value = "${app.endpoints.base_path}" + "${app.endpoints.users.base_url}",
    produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Users", description = "User profiles REST API controller")
public class UsersController {

    private final UsersService usersService;

    @GetMapping
    @PreAuthorize("hasAuthority('db:users:write')")
    public RestResponseTo<List<UserTo>> getAllOrByEmail(@RequestParam(required = false) String email) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toUserTos(usersService.get(email))
        );
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('db:users:write')")
    public RestResponseTo<UserTo> getById(@PathVariable Integer id) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toUserTo(usersService.getById(id))
        );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('db:users:write')")
    public ResponseEntity<?> create(@Valid @RequestBody UserTo userTo) {
        User created = usersService.create(userTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentRequest().
            path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(
            new RestResponseTo<>(HttpStatus.CREATED.toString(), null, toUserTo(created))
        );
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('db:users:write')")
    public RestResponseTo<UserTo> update(@RequestBody UserTo userTo, @PathVariable Integer id) {
        return new RestResponseTo<>(
            HttpStatus.OK.toString(), null, toUserTo(usersService.update(userTo, id))
        );
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('db:users:write')")
    public void delete(@PathVariable Integer id) {
        usersService.delete(id);
    }

}
