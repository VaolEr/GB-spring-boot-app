package com.example.storehouse.model;

import com.example.storehouse.model.abstractentity.AbstractNamedEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity(name = "Item")
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
public class Item extends AbstractNamedEntity {

    @NotNull
    @NotBlank
    @Column(name = "sku")
    private String sku;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) // тоже можно будет обсудить, пока пусть так
    @JsonManagedReference
    private Supplier supplier;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) // тоже можно будет обсудить, пока пусть так
    @JsonManagedReference
    private Category category;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private Set<ItemStorehouse> itemStorehouses;

}
