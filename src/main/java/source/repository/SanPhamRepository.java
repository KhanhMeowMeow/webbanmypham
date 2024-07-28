package source.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import source.model.LoaiSanPham;
import source.model.SanPham;

@Service
public interface SanPhamRepository extends JpaRepository<SanPham, String> {

    @Query("SELECT sp FROM SanPham sp ORDER BY sp.id DESC")
    List<SanPham> findOrderByDesc();

    @Query("Select sp from SanPham sp where sp.LoaiSanPham = :loaiSanPham")
    List<SanPham> findByLoaiSanPham(LoaiSanPham loaiSanPham);
}