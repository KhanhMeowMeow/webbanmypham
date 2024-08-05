package source.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import source.DAO.NguoiDungDAO;
import source.model.NguoiDung;

@Controller
public class TaiKhoanCaNhanController {

    @Autowired
    private HttpSession session;
    @Autowired
    private NguoiDungDAO nguoiDungDAO;

    @GetMapping("/taiKhoanCaNhan")
    public String getMethodName(
            Model model) {
        NguoiDung nguoiDung = nguoiDungDAO.timTheoMaNgoiDung((String) session.getAttribute("idUser"));
        if (nguoiDung == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("nguoiDung", nguoiDung);
            return "taiKhoanCaNhan";
        }
    }

    @RequestMapping("/taiKhoanCaNhan/capNhat")
    public String requestCapNhatTaiKhoanCaNhan(
            @RequestParam("TenDangNhap") String tenDangNhap,
            @RequestParam("MatKhau") String matKhau,
            @RequestParam("TenNguoiDung") String tenNguoiDung,
            @RequestParam("SoDienThoai") String soDienThoai,
            @RequestParam("Email") String email,
            @RequestParam("DiaChi") String diaChi,
            Model model) {
        NguoiDung nguoiDung = nguoiDungDAO.timTheoMaNgoiDung(tenDangNhap);
        try {
            Integer intsodienthoai = Integer.parseInt(soDienThoai);
        } catch (Exception e) {
            model.addAttribute("messSodienThoai", "Số điện thoại không đúng định dạng");
            model.addAttribute("nguoiDung", nguoiDung);
            return "taiKhoanCaNhan";
        }
        if (nguoiDung != null) {
            nguoiDung.setDiaChi(diaChi);
            nguoiDung.setEmail(email);
            nguoiDung.setSoDienThoai(soDienThoai);
            nguoiDung.setTenNguoiDung(tenNguoiDung);
            nguoiDungDAO.themNguoiDung(nguoiDung);
        }
        return "redirect:/taiKhoanCaNhan";
    }
    @RequestMapping("/doiMatKhau/view")
    public String requestDoiMatKhau(){
        
        
        return "redirect:/taiKhoanCaNhan";
    }

}
