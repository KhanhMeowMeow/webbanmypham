package source.DAO;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import source.model.GioHang;
import source.model.NguoiDung;
import source.repository.NguoiDungRepository;

@Service
public class NguoiDungDAO {
    @Autowired
    private NguoiDungRepository nguoiDungRepository;
    @Autowired
    private GioHangDAO gioHangDAO;

    public void themNguoiDung(NguoiDung nguoiDung){
        nguoiDungRepository.save(nguoiDung);

        GioHang gioHang = new GioHang();
        gioHang.setMaGioHang("GH_"+nguoiDung.getMaNguoiDung());
        gioHang.setNguoiDung(nguoiDung);
        gioHang.setSoLuongSanPham(0);
        gioHang.setTongTien(0);
        gioHangDAO.taoGioHang(gioHang);
    }

    public NguoiDung timTheoMaNgoiDung(String MaNguoiDung){
        return nguoiDungRepository.findById(MaNguoiDung).orElse(null);
    }

    public List<NguoiDung> timTatCaNgoiDung(){
        return nguoiDungRepository.findAll();
    }

    public void xoaNguoiDung(String maNguoiDung){
        nguoiDungRepository.deleteById(maNguoiDung);
    }

    public List<NguoiDung> timTatCaNgoiDungHoatDong(){
        return nguoiDungRepository.findNguoiDungOn();
    }
}
