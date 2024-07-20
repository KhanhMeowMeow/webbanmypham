package source.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import source.model.LoaiSanPham;

public interface LoaiSanPhamRepository extends JpaRepository<LoaiSanPham, String>{
    
}
