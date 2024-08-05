package source.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import source.model.GioHang;
import source.model.NguoiDung;
import source.repository.GioHangRepository;

@Service
public class GioHangDAO {

    @Autowired
    private GioHangRepository gioHangRepository;
    
    public GioHang timGioHangTheoMaNguoiDung(String maNguoiDung){
        return gioHangRepository.findByMaNguoiDung(maNguoiDung);
    }

    public GioHang taoGioHang(GioHang gioHang){
        return gioHangRepository.save(gioHang);
    }

    public List<GioHang> timTatCaGioHang(){
        return gioHangRepository.findAll();
    }
    

    // public List<GioHang> timGioHangTheoNguoiDung(NguoiDung maNguoiDung){
    //     return gioHangRepository.findByNguoiDung(maNguoiDung);
    // }
}
