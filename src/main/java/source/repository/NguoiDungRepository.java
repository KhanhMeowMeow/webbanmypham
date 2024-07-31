package source.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import source.model.NguoiDung;
import java.util.List;


public interface NguoiDungRepository extends JpaRepository<NguoiDung, String> {
    
    @Query("From NguoiDung where TrangThai = true")
    List<NguoiDung> findNguoiDungOn();

}
