package fr.cooptalent.neodrive.repository;

import fr.cooptalent.neodrive.domain.OptionReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OptionReferenceRepository extends JpaRepository<OptionReference, UUID> {
}
