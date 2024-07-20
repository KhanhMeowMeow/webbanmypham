package source.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import source.model.SanPham;

@Service
public interface SanPhamRepository extends JpaRepository<SanPham, String> {
    
}
