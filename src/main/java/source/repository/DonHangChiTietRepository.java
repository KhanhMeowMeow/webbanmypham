package source.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import source.model.DonHangChiTiet;

public interface DonHangChiTietRepository extends JpaRepository<DonHangChiTiet, String>{
    
}
