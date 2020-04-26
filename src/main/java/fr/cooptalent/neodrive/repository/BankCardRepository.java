package fr.cooptalent.neodrive.repository;

import fr.cooptalent.neodrive.domain.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BankCardRepository extends JpaRepository<BankCard, UUID> {
}
