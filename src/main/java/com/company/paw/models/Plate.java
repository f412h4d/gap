package com.company.paw.models;

import com.company.paw.models.Audits.Product;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
@GraphQLType
public class Plate extends Product {
    @GraphQLNonNull
    private boolean isPrivate;
    private Plate mappedPlate;
}
