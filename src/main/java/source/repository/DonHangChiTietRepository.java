package source.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import source.model.DonHang;
import source.model.DonHangChiTiet;

public interface DonHangChiTietRepository extends JpaRepository<DonHangChiTiet, String>{

    @Query("Select dhct from DonHangChiTiet dhct where dhct.DonHang = :donHang")
    List<DonHangChiTiet> findByDonHang(DonHang donHang);
}
