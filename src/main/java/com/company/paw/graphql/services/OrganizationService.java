package com.company.paw.graphql.services;

import com.company.paw.graphql.InputTypes.OrganizationInput;
import com.company.paw.models.Organization;
import com.company.paw.repositories.OrganizationRepository;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@GraphQLApi
@AllArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final ConvertService convertService;

    @GraphQLQuery
    public List<Organization> allOrganization() {
        return organizationRepository.findAll();
    }

    @GraphQLQuery
    public Organization getOrganization(String organizationId) {
        return organizationRepository.findById(organizationId).orElse(null);
    }

    @GraphQLMutation
    public Organization addOrganization(OrganizationInput input) {
        Organization organization = convertService.setOrganization(new Organization(), input);
        organization.setReports(Collections.emptyList());
        organization.setEmployees(Collections.emptyList());
        organization.setWeapons(Collections.emptyList());
        organization.setPlates(Collections.emptyList());
        return organization;
    }

    @GraphQLMutation
    public Organization deleteOrganization(String organizationId) {
        Optional<Organization> organizationOptional = organizationRepository.findById(organizationId);
        organizationOptional.ifPresent(organizationRepository::delete);
        return organizationOptional.orElse(null);
    }

    @GraphQLMutation
    public Organization editOrganization(String organizationId, OrganizationInput input) {
        return organizationRepository.save(
                convertService.setOrganization(organizationRepository.findById(organizationId).get(), input)
        );
    }
}
