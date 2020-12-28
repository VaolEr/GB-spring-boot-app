package com.example.storehouse.util;


import com.example.storehouse.dto.SupplierTo;
import com.example.storehouse.model.Supplier;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SuppliersUtil {

    public static SupplierTo toSupplierTo(Supplier supplier) {
        return SupplierTo
            .builder()
            .id(supplier.getId())
            .name(supplier.getName())
            .build();
    }

    public static List<SupplierTo> toSupplierTos(List<Supplier> suppliers) {
        return suppliers.stream().map(SuppliersUtil::toSupplierTo).collect(Collectors.toList());
    }

    public static Supplier fromSupplierTo(SupplierTo supplierTo) {
        Supplier newSupplier = new Supplier();
        newSupplier.setName(supplierTo.getName());
        return newSupplier;
    }

}
