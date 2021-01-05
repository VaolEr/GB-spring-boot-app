package com.example.storehouse.model;

import com.example.storehouse.model.abstractentity.AbstractNamedEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
    @ManyToOne(fetch = FetchType.EAGER) // was lazy, but for storehouse service we need to eager
    @JoinColumn(name = "supplier_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) // тоже можно будет обсудить, пока пусть так
    @JsonManagedReference
    private Supplier supplier;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER) // was lazy, but for storehouse service we need to eager
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) // тоже можно будет обсудить, пока пусть так
    @JsonManagedReference
    private Category category;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("item")
    private Set<ItemStorehouse> itemStorehouses = new HashSet<>();

    public void addItemStorehouse(ItemStorehouse itemStorehouse) {
//        this.itemStorehouses = getItemStorehousesInstance();
        itemStorehouse.setItem(this);
        itemStorehouses.add(itemStorehouse);
    }

    public void setItemStorehouses(ItemStorehouse... itemsStorehouses) {
//        this.itemStorehouses = getItemStorehousesInstance();
        this.setItemStorehouses(Stream.of(itemsStorehouses).collect(Collectors.toSet()));
    }

    public void setItemStorehouses(Set<ItemStorehouse> itemsStorehouses) {
//        this.itemStorehouses = getItemStorehousesInstance();
        itemStorehouses.addAll(itemsStorehouses.stream()
                                               .peek(itemStorehouse -> itemStorehouse.setItem(this))
                                               .collect(Collectors.toSet())
        );
    }

    private Set<ItemStorehouse> getItemStorehousesInstance() {
        return (itemStorehouses != null) ? itemStorehouses : new HashSet<>();
    }

}
