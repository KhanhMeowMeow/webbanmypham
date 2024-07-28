package source.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import source.DAO.GioHangChiTietDAO;
import source.DAO.GioHangDAO;
import source.DAO.NguoiDungDAO;
import source.DAO.SanPhamDAO;
import source.model.SanPham;
import source.model.GioHang;
import source.model.NguoiDung;


@Controller
public class GioHangController {

    @Autowired
    private HttpSession session;
    @Autowired
    private SanPhamDAO sanPhamDAO;
    @Autowired
    private GioHangChiTietDAO gioHangChiTietDAO;
    @Autowired
    private GioHangDAO gioHangDAO;
    @Autowired
    private NguoiDungDAO nguoiDungDAO;

    @GetMapping("/gioHang")
    public String getGioHang(Model model) {
        if (session.getAttribute("idUser") == null) {
            return "login";
        }
        
        return "gioHang";
    }
    @ModelAttribute("listSanPhamTrongGioHang")
    public List<SanPham> listSanPhamTrongGioHang(Model model){
        String userSession = (String) session.getAttribute("idUser");
        List<SanPham> listSPinGioHang = gioHangChiTietDAO.timSanPhamTrongGioHangCuaNguoiDung(userSession);

        if (listSPinGioHang.size() == 0) {
            model.addAttribute("messGioHang", "Bạn chưa mua sắm gì");
        }
        return listSPinGioHang;
    }
    @GetMapping("/themGioHang")
    public String getThemGioHang(@RequestParam("maSanPham") String maSanPham) {
        if (session.getAttribute("idUser") == null) {
            return "login";
        }

        SanPham sanPham = sanPhamDAO.timSanPhamTheoMaSanPham(maSanPham);

        return "gioHang";
    }
}
