package fr.cooptalent.neodrive.repository;

import fr.cooptalent.neodrive.domain.RecoveryPointReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecoveryPointReferenceRepository extends JpaRepository<RecoveryPointReference, UUID> {
}
