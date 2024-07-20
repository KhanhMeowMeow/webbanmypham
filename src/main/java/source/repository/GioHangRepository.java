package source.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import source.model.GioHang;

public interface GioHangRepository extends JpaRepository<GioHang, String>{
    
}
