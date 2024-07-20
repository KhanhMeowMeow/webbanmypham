package source.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import source.model.DonHang;

public interface DonHangRepository extends JpaRepository<DonHang, String> {
    
}
