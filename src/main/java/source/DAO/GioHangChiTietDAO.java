package source.DAO;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import source.model.GioHang;
import source.model.GioHangChiTiet;
import source.model.GioHangDTO;
import source.model.SanPham;
import source.repository.GioHangChiTietRepository;

@Service
public class GioHangChiTietDAO {
    
    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    public GioHangChiTiet themGioHangChiTiet(GioHangChiTiet gioHangChiTiet){
        return gioHangChiTietRepository.save(gioHangChiTiet);
    }

    public List<SanPham> timSanPhamTrongGioHangCuaNguoiDung(String maNguoiDung){
        return gioHangChiTietRepository.findSanPhamByMaNguoiDung(maNguoiDung);
    }

    public List<GioHangChiTiet> timTatCaGioHangChiTiet(){
        return gioHangChiTietRepository.findAll();
    }

    public void xoaSanPhamTrongGioHang(String maGHCT){
        gioHangChiTietRepository.deleteById(maGHCT);
    }

    public List<GioHangDTO> timSanPhamVaSoLuongTrongGHCT(String maNguoiDung){
        List<GioHangChiTiet> listGioHangChiTiet = gioHangChiTietRepository.findGioHangChiTietByMaNguoiDung(maNguoiDung);

        return listGioHangChiTiet.stream().map(ghct ->{
            GioHangDTO dto = new GioHangDTO();
            dto.setSanPham(ghct.getSanPham());
            dto.setSoLuong(ghct.getSoLuong());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<GioHangChiTiet> timGHCTByMaNguoiDung(String maNguoiDung){
        return gioHangChiTietRepository.findGioHangChiTietByMaNguoiDung(maNguoiDung);
    }

}
