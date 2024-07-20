package source.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import source.model.NguoiDung;

public interface NguoiDungRepository extends JpaRepository<NguoiDung, String> {
    
}
