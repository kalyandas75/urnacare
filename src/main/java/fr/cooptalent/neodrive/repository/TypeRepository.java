package fr.cooptalent.neodrive.repository;

import fr.cooptalent.neodrive.domain.referential.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TypeRepository extends JpaRepository<Type, UUID> {
}
