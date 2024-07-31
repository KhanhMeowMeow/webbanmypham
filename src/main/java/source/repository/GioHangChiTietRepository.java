package source.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import source.model.GioHang;
import source.model.GioHangChiTiet;
import source.model.SanPham;

public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, String> {
    

    @Query("SELECT gct.SanPham FROM GioHangChiTiet gct INNER JOIN gct.GioHang gh WHERE gh.NguoiDung.MaNguoiDung = :maNguoiDung")
    List<SanPham> findSanPhamByMaNguoiDung(String maNguoiDung);

    @Query("FROM GioHangChiTiet gct INNER JOIN gct.GioHang gh WHERE gh.NguoiDung.MaNguoiDung = :maNguoiDung")
    List<GioHangChiTiet> findGioHangChiTietByMaNguoiDung(String maNguoiDung);
}
