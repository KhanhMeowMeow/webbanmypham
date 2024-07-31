package source.controller;

import java.util.List;
import java.util.Random;

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
import source.model.GioHang;
import source.model.GioHangChiTiet;
import source.model.GioHangDTO;
import source.model.SanPham;


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
    public List<GioHangDTO> listSanPhamTrongGioHang(Model model){
        String userSession = (String) session.getAttribute("idUser");

        List<GioHangDTO> listSPinGioHang = gioHangChiTietDAO.timSanPhamVaSoLuongTrongGHCT(userSession);
        
        if (listSPinGioHang.size() == 0) {
            model.addAttribute("messGioHang", "Bạn chưa mua sắm gì");
        }
        return listSPinGioHang;
    }

    @GetMapping("/themGioHang")
    public String getThemGioHang(@RequestParam("maSanPham") String maSanPham, Model model) {
        if (session.getAttribute("idUser") == null) {
            return "login";
        }

        List<GioHangChiTiet> listGioHangChiTiet = gioHangChiTietDAO.timGHCTByMaNguoiDung((String) session.getAttribute("idUser"));
        SanPham sanPham = sanPhamDAO.timSanPhamTheoMaSanPham(maSanPham);
        GioHang gioHang = gioHangDAO.timGioHangTheoMaNguoiDung((String) session.getAttribute("idUser"));
        String newIdGHCT = newIDGiohangChiTiet(listGioHangChiTiet);
        
        GioHangChiTiet gioHangChiTiet = new GioHangChiTiet(newIdGHCT, gioHang, sanPham, 1);
        
        for (GioHangChiTiet ghct : listGioHangChiTiet) {
            if (ghct.getSanPham().getMaSanPham().equals(sanPham.getMaSanPham())) {
                ghct.setSoLuong(ghct.getSoLuong() + 1);
                gioHangChiTietDAO.themGioHangChiTiet(ghct);
                return "redirect:/gioHang";
            }
        }
        gioHangChiTietDAO.themGioHangChiTiet(gioHangChiTiet);
        
        // String userSession = (String) session.getAttribute("idUser");
        // List<GioHangDTO> listSPinGioHang = gioHangChiTietDAO.timSanPhamVaSoLuongTrongGHCT(userSession);

        // model.addAttribute("listSanPhamTrongGioHang", listSPinGioHang);
        return "redirect:/gioHang";
    }

    @GetMapping("/themSoLuongSanPham")
    public String getThemSoLuong(@RequestParam("maSanPham") String maSanPham) {
        List<GioHangChiTiet> listGioHangChiTiet = gioHangChiTietDAO.timGHCTByMaNguoiDung((String)session.getAttribute("idUser"));
        SanPham sanPham = sanPhamDAO.timSanPhamTheoMaSanPham(maSanPham);
            
        for (GioHangChiTiet ghct : listGioHangChiTiet) {
            if (ghct.getSanPham().getMaSanPham().equals(sanPham.getMaSanPham())) {
                ghct.setSoLuong(ghct.getSoLuong() + 1);
                gioHangChiTietDAO.themGioHangChiTiet(ghct);
                return "redirect:/gioHang";
            }
        }
        return "redirect:/gioHang";
    }

    @GetMapping("/truSoLuongSanPham")
    public String getTruSoLuong(@RequestParam("maSanPham") String maSanPham) {
        List<GioHangChiTiet> listGioHangChiTiet = gioHangChiTietDAO.timGHCTByMaNguoiDung((String)session.getAttribute("idUser"));
        SanPham sanPham = sanPhamDAO.timSanPhamTheoMaSanPham(maSanPham);
                
        for (GioHangChiTiet ghct : listGioHangChiTiet) {
            if (ghct.getSanPham().getMaSanPham().equals(sanPham.getMaSanPham())) {
                ghct.setSoLuong(ghct.getSoLuong() - 1);
                if (ghct.getSoLuong() < 1) {
                    gioHangChiTietDAO.xoaSanPhamTrongGioHang(ghct.getMaGioHangChiTiet());
                } else {
                    gioHangChiTietDAO.themGioHangChiTiet(ghct);
                }
                return "redirect:/gioHang";
            }
        }
        return "redirect:/gioHang";
    }
    @GetMapping("/xoaSanPham")
    public String getXoaSanPham(@RequestParam("maSanPham") String maSanPham) {
        List<GioHangChiTiet> listGioHangChiTiet = gioHangChiTietDAO.timTatCaGioHangChiTiet();
        
        for (GioHangChiTiet ghct : listGioHangChiTiet) {
            if (ghct.getSanPham().getMaSanPham().equals(maSanPham)) {
                ghct.setSoLuong(ghct.getSoLuong() - 1);
                gioHangChiTietDAO.xoaSanPhamTrongGioHang(ghct.getMaGioHangChiTiet());
                return "redirect:/gioHang";
            }
        }
        return "redirect:/gioHang";
    }
    private String newIDGiohangChiTiet(List<GioHangChiTiet> listGioHangChiTiet){
        Random random = new Random();
        String maGHTCT = "GHCT" + (random.nextInt(999 - 1 + 1) + 1);
        for (GioHangChiTiet gioHangChiTiet : listGioHangChiTiet) {
            if (gioHangChiTiet.getMaGioHangChiTiet().equals(maGHTCT)) {
                maGHTCT = "GHCT" + (random.nextInt(999 - 1 + 1) + 1);
            }
        }
        return maGHTCT;
    }
}
