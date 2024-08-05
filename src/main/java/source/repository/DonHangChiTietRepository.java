package source.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import source.model.DonHang;
import source.model.DonHangChiTiet;
import source.model.SanPham;

@Repository
public interface DonHangChiTietRepository extends JpaRepository<DonHangChiTiet, String>{

    @Query("Select dhct from DonHangChiTiet dhct where dhct.DonHang = :donHang")
    List<DonHangChiTiet> findByDonHang(DonHang donHang);

    @Query("SELECT dhct.SanPham FROM DonHangChiTiet dhct GROUP BY dhct.SanPham ORDER BY COUNT(dhct.SanPham) DESC")
    List<SanPham> findBestSell();
}
