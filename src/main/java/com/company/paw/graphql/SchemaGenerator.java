package com.company.paw.graphql;

import com.company.paw.graphql.services.EmployeeService;
import com.company.paw.graphql.services.OrganizationService;
import com.company.paw.graphql.services.PositionService;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.geantyref.GenericTypeReflector;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.annotations.types.GraphQLInterface;
import io.leangen.graphql.generator.mapping.strategy.InterfaceMappingStrategy;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;
import io.leangen.graphql.util.ClassUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.lang.reflect.AnnotatedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class SchemaGenerator {
    private final EmployeeService employeeService;
    private final PositionService positionService;
    private final OrganizationService organizationService;

    @Bean
    public GraphQL getGraphQL() {
        GraphQLSchema schema = new GraphQLSchemaGenerator()
                .withInterfaceMappingStrategy(new InterfaceMappingStrategy() {
                    @Override
                    public boolean supports(AnnotatedType interfase) {
                        return interfase.isAnnotationPresent(GraphQLInterface.class);
                    }

                    @Override
                    public Collection<AnnotatedType> getInterfaces(AnnotatedType type) {
                        Class clazz = ClassUtils.getRawType(type.getType());
                        Set<AnnotatedType> interfaces = new HashSet<>();
                        do {
                            AnnotatedType currentType = GenericTypeReflector.getExactSuperType(type, clazz);
                            if (supports(currentType)) {
                                interfaces.add(currentType);
                            }
                            Arrays.stream(clazz.getInterfaces())
                                    .map(inter -> GenericTypeReflector.getExactSuperType(type, inter))
                                    .filter(this::supports)
                                    .forEach(interfaces::add);
                        } while ((clazz = clazz.getSuperclass()) != Object.class && clazz != null);
                        return interfaces;
                    }
                })
                .withResolverBuilders(
                        //Resolve by annotations
                        new AnnotatedResolverBuilder())
                .withOperationsFromSingletons(employeeService, positionService, organizationService)
                .withValueMapperFactory(new JacksonValueMapperFactory())
                .generate();
        return GraphQL.newGraphQL(schema).build();
    }
}
