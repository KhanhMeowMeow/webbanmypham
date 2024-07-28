package source.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import source.model.LoaiSanPham;
import source.repository.LoaiSanPhamRepository;

@Service
public class LoaiSanPhamDAO {
    @Autowired
    LoaiSanPhamRepository loaiSanPhamRepository;

    public List<LoaiSanPham> timTatCaLoaiSanPham(){
        return loaiSanPhamRepository.findAll();
    }
    
    public LoaiSanPham timTheoMaLoaiSanPham(String maLoaiSanPham){
        return loaiSanPhamRepository.findById(maLoaiSanPham).orElse(null);
    }
    
}
