package fr.cooptalent.neodrive.repository;

import fr.cooptalent.neodrive.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, String> {
}
