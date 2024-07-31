package source.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import source.model.GioHang;
import source.model.NguoiDung;

import java.util.List;


public interface GioHangRepository extends JpaRepository<GioHang, String>{
    
    @Query("From GioHang gh Where gh.NguoiDung.MaNguoiDung = :maNguoiDung")
    GioHang findByMaNguoiDung(String maNguoiDung);
}
