package source.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import source.DAO.NguoiDungDao;
import source.model.NguoiDung;




@Controller
public class LoginController {

    @Autowired
    private NguoiDungDao nguoiDungDao;
    

    @GetMapping("/login")
    public String getLogin(@ModelAttribute("nguoiDung") NguoiDung nguoiDung) {
        return "login";
    }

    @RequestMapping("/submitLogin")
    public String submitLogin(@RequestParam("TenDangNhap") String idNguoiDung,
                            @RequestParam("MatKhau") String matKhau, 
                            Model model) {
        List<NguoiDung> nguoiDungs = nguoiDungDao.timTatCaNgoiDung();
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
            model.addAttribute("messLogin", "Đăng nhập thành công!");
            return "index";
        } else {
            model.addAttribute("messLogin", "Sai tên đăng nhập hoặc mật khẩu!");
            return "login";
        }
    }
}
