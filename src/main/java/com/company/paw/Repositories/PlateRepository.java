package com.company.paw.Repositories;

import com.company.paw.models.Plate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlateRepository extends MongoRepository<Plate, String> {
}
