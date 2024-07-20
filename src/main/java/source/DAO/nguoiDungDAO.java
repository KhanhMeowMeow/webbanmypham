package source.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import source.model.NguoiDung;
import source.repository.NguoiDungRepository;

@Service
public class NguoiDungDao {
    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    public NguoiDung themNguoiDung(NguoiDung nguoiDung){
        return nguoiDungRepository.save(nguoiDung);
    }

    public NguoiDung timTheoTenNgoiDung(String MaNguoiDung){
        return nguoiDungRepository.findById(MaNguoiDung).orElse(null);
    }

    public List<NguoiDung> timTatCaNgoiDung(){
        return nguoiDungRepository.findAll();
    }
}
