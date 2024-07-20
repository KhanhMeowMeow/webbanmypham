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

import source.DAO.NguoiDungDao;
import source.model.NguoiDung;




@Controller
public class loginController {

    @Autowired
    private NguoiDungDao nguoiDungDao;
    

    @GetMapping("/login")
    public String getLogin(@ModelAttribute("nguoiDung") NguoiDung nguoiDung) {
        return "login";
    }

    @RequestMapping("/submitLogin")
    public String submitLogin(@RequestParam("idNguoiDung") String idNguoiDung,
                            @RequestParam("matKhau") String matKhau, 
                            Model model) {
        List<NguoiDung> nguoiDungs = nguoiDungDao.timTatCaNgoiDung();
        NguoiDung existingUser = null;
        for (NguoiDung nguoiDung : nguoiDungs) {
            model.addAttribute("messCC", listNguoiDungString(nguoiDungs));
            if (idNguoiDung.equals(nguoiDung.getMaNguoiDung())) {
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

    private String listNguoiDungString(List<NguoiDung> listNguoiDungs){
        String listNguoiDung = "";
        for (NguoiDung nguoiDung : listNguoiDungs) {
            listNguoiDung += nguoiDung.getTenNguoiDung() + ", ";
        }
        return listNguoiDung;
    }
}
