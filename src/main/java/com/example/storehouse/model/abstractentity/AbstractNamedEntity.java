package com.example.storehouse.model.abstractentity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractNamedEntity extends AbstractBaseEntity {

    // Данная сущность у нас расширяет AbstractBaseEntity доп. полем name,
    // поэтому просто наследуем, без дублирования id

    @NotNull
    @NotBlank
    @Column(name = "name", nullable = false) //для наглядности
    public String name; // раз положили в отдельный пакет - модификатор доступа меняем

}
