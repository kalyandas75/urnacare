package fr.cooptalent.neodrive.repository;

import fr.cooptalent.neodrive.domain.referential.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {
}
