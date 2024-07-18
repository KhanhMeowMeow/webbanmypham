package source.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import source.model.nguoiDung;
import java.util.List;


@Service
public interface nguoiDungDAO extends JpaRepository<nguoiDung, String>{
    nguoiDung findByIdNguoiDung(String idNguoiDung);
}
