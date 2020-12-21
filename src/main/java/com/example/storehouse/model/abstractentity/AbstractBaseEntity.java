package com.example.storehouse.model.abstractentity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

@MappedSuperclass
@Data
@NoArgsConstructor
@Access(AccessType.FIELD)
// Думаю, что позже попробуем заменить эту сущность на AbstractPersistable<PK>
public abstract class AbstractBaseEntity implements Persistable<Integer> {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Override
    public boolean isNew() {
        return null == getId();
    }

}
