package source.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import source.model.DonHang;
import source.model.ThongKeDoanhThuDTO;
import source.repository.DonHangRepository;

@Service
public class DonHangDAO {
    @Autowired
    DonHangRepository donHangRepository;
    
    public List<ThongKeDoanhThuDTO> thongKeDoanhThu(){
        return donHangRepository.thongKeDoanhThu();
    }
}
