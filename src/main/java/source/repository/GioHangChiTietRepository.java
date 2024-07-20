package source.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import source.model.GioHangChiTiet;

public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, String> {
    
}
