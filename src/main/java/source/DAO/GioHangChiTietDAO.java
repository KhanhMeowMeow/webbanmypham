package source.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import source.model.GioHang;
import source.model.SanPham;
import source.repository.GioHangChiTietRepository;

@Service
public class GioHangChiTietDAO {
    
    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    public List<SanPham> timSanPhamTrongGioHangCuaNguoiDung(String maNguoiDung){
        return gioHangChiTietRepository.findSanPhamByMaNguoiDung(maNguoiDung);
    }
}
