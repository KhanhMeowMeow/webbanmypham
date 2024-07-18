package source.loginController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import source.DAO.nguoiDungDAO;
import source.model.nguoiDung;



@Controller
public class loginController {

    @Autowired
    nguoiDungDAO ndDAO;

    @GetMapping("/login")
    public String getLogin(@ModelAttribute("nguoiDung") nguoiDung nguoiDung) {
        return "login";
    }

    @RequestMapping("/submitLogin")
    public String submitLogin(@RequestParam("idNguoiDung") String idNguoiDung,
                            @RequestParam("matKhau") String matKhau, Model model) {
        List<nguoiDung> nguoiDungs = ndDAO.findAll();
        nguoiDung existingUser = null;
        for (nguoiDung nguoiDung : nguoiDungs) {
            model.addAttribute("messCC", listNguoiDungString(nguoiDungs));
            if (idNguoiDung.equals(nguoiDung.getIdNguoiDung())) {
                existingUser = nguoiDung;
            }
        }
        if(existingUser == null){
            model.addAttribute("messLogin", "userFale");
            return "login";
        }else if (existingUser.getMatKhau().equals(matKhau)) {
            model.addAttribute("messLogin", "successLogin");
            return "login";
        } else {
            model.addAttribute("messLogin", "failureLogin");
            return "login";
        }
    }

    private String listNguoiDungString(List<nguoiDung> listNguoiDungs){
        String listNguoiDung = "";
        for (nguoiDung nguoiDung : listNguoiDungs) {
            listNguoiDung += nguoiDung.getIdNguoiDung() + ", ";
        }
        return listNguoiDung;
    }
}
