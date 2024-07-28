package source.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import source.DAO.NguoiDungDAO;
import source.DAO.SanPhamDAO;
import source.model.NguoiDung;
import source.model.SanPham;

@Controller
public class IndexController {

    @Autowired
    private SanPhamDAO sanPhamDAO;
    @Autowired
    private HttpSession session;
    @Autowired
    private NguoiDungDAO nguoiDungDAO;

    @GetMapping("/")
    public String getIndex(Model model) {
        String usersession = "";
        
        if (session.getAttribute("idUser") != null) {
            usersession = (String) session.getAttribute("idUser");
        }

        session.setAttribute("idUser", "ND001");

        NguoiDung nguoiDung = nguoiDungDAO.timTheoMaNgoiDung(usersession);
        if (nguoiDung != null) {
            if (nguoiDung.isVaiTro()) {
                model.addAttribute("vaitro", "quanly");
            } else {
                model.addAttribute("vaitro", "khachhang");
            }
            model.addAttribute("user", "yes");
        } else {
            model.addAttribute("user", "none");
        }
        
        return "index";
    }

    @GetMapping("/login")
    public String getLogin(@ModelAttribute("nguoiDung") NguoiDung nguoiDung) {
        return "login";
    }

    @ModelAttribute("listSanPhamMoi")
    public List<SanPham> list8SanPhamMoi(){

        List<SanPham> allSanPham = sanPhamDAO.timSanPhamMoi();

        return allSanPham.size() > 8 ? allSanPham.subList(0, 10) : allSanPham;

    }

    @RequestMapping("/submitLogin")
    public String submitLogin(@RequestParam("TenDangNhap") String idNguoiDung,
                            @RequestParam("MatKhau") String matKhau, 
                            Model model) {
        List<NguoiDung> nguoiDungs = nguoiDungDAO.timTatCaNgoiDung();
        NguoiDung existingUser = null;
        for (NguoiDung nguoiDung : nguoiDungs) {
            if (nguoiDung.getMaNguoiDung().equals(idNguoiDung)) {
                existingUser = nguoiDung;
            }
        }
        if(existingUser == null){
            model.addAttribute("messLogin", "Sai tên đăng nhập hoặc mật khẩu!");
            return "login";
        }else if (existingUser.getMatKhau().equals(matKhau)) {
            session.setAttribute("idUser", idNguoiDung);
            model.addAttribute("messLogin", "Đăng nhập thành công!");
            return "redirect:/";
        } else {
            model.addAttribute("messLogin", "Sai tên đăng nhập hoặc mật khẩu!");
            return "login";
        }
    }

    @RequestMapping("/submitLogout")
    public String submitLogout() {
        session.removeAttribute("idUser");
        return "login";
    }

    
}
