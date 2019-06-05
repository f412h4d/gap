package com.company.paw.models;

import com.company.paw.models.Audits.AuditModel;
import com.company.paw.models.Audits.Product;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Document
@GraphQLType
public class Record extends AuditModel {
    private Employee user;
    private Product product;
    private Organization organization;
    private Date time;
    private Request request;
    private String description;
    private boolean status;
    private boolean isReturning;
}
