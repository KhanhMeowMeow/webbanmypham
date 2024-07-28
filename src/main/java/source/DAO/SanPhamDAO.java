package source.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import source.model.LoaiSanPham;
import source.model.SanPham;
import source.repository.SanPhamRepository;
@Service
public class SanPhamDAO {
    @Autowired
    private SanPhamRepository sanPhamRepository;

    public SanPham themSanPham(SanPham sanPham) {
        return sanPhamRepository.save(sanPham);
    }

    public SanPham timSanPhamTheoMaSanPham(String idSanPham){
        return sanPhamRepository.findById(idSanPham).orElse(null);
    }

    public List<SanPham> timTatCaSanPham() {
        return sanPhamRepository.findAll();
    }

    public void xoaSanPham(String idSanPham) {
        sanPhamRepository.deleteById(idSanPham);
    }

    public List<SanPham> timSanPhamMoi(){
        return sanPhamRepository.findOrderByDesc();
    }

    public List<SanPham> timSanPhamCungLoai(LoaiSanPham loaiSanPham) {
        return sanPhamRepository.findByLoaiSanPham(loaiSanPham);
    }

}
