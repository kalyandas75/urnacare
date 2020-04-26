package fr.cooptalent.neodrive.repository;

import fr.cooptalent.neodrive.domain.AnnouncementAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnnouncementAddressRepository extends JpaRepository<AnnouncementAddress, UUID> {
}
