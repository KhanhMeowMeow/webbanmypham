package source.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import source.DAO.GioHangDAO;
import source.DAO.NguoiDungDAO;
import source.model.GioHang;
import source.model.NguoiDung;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;


@Controller
public class QuanLyNguoiDungController {

    @Autowired
    private NguoiDungDAO nguoiDungDAO;
    @Autowired
    private GioHangDAO gioHangDAO;

    @GetMapping("/quanLyNguoiDung")
    public String getQuanLyNguoiDung(Model model) {
        model.addAttribute("NguoiDung",new NguoiDung());
        return "quanLyNguoiDung";
    }

    @ModelAttribute("listNguoiDung")
    public List<NguoiDung> tatCaNguoiDung(){
        return nguoiDungDAO.timTatCaNgoiDungHoatDong();
    }

    @GetMapping("/quanLyNguoiDung/chon")
    public String getChonNguoiDung(@RequestParam("MaNguoiDung") String maNguoiDung,
                                    Model model) {
        model.addAttribute("NguoiDung",nguoiDungDAO.timTheoMaNgoiDung(maNguoiDung));
        return "/quanLyNguoiDung";
    }

    @GetMapping("/quanLyNguoiDung/xoa")
    public String getMethodName(@RequestParam("MaNguoiDung") String maNguoiDung) {
        NguoiDung nguoiDung = nguoiDungDAO.timTheoMaNgoiDung(maNguoiDung);
        nguoiDung.setTrangThai(false);
        nguoiDungDAO.themNguoiDung(nguoiDung);
        return "redirect:/quanLyNguoiDung";
    }
    
    @RequestMapping("/quanLyNguoiDung/them")
    public ModelAndView requestThemNguoiDung(@ModelAttribute("NguoiDung") @Valid NguoiDung nguoiDung,
                                    BindingResult result,
                                    Model model,
                                    @RequestParam("VaiTro") boolean VaiTro){
        
        if (VaiTro) {
            nguoiDung.setVaiTro(true);
        }  else {
            nguoiDung.setVaiTro(false);
        }
        
        nguoiDung.setTrangThai(true);

        if (result.hasErrors()) {
            return new ModelAndView("/quanLyNguoiDung").addObject("NguoiDung", nguoiDung);
        }
        nguoiDungDAO.themNguoiDung(nguoiDung);
        return new ModelAndView("redirect:/quanLyNguoiDung");
    }
}
