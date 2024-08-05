package source.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import source.model.DonHang;
import source.model.DonHangChiTiet;
import source.model.GioHangDTO;
import source.model.SanPham;
import source.repository.DonHangChiTietRepository;

@Service
public class DonHangChiTietDAO {
    @Autowired
    private DonHangChiTietRepository donHangChiTietRepository;

    public List<DonHangChiTiet> timDHCTbyDH(DonHang donHang){
        return donHangChiTietRepository.findByDonHang(donHang);
    }

    public DonHangChiTiet themDonHangChiTiet(DonHangChiTiet donHangChiTiet){
        return donHangChiTietRepository.save(donHangChiTiet);
    }

    public List<DonHangChiTiet> timTatCa( ){
        return donHangChiTietRepository.findAll();
    }

    public List<SanPham> timSanPhamBestSell(){
        return donHangChiTietRepository.findBestSell();
    }
}
