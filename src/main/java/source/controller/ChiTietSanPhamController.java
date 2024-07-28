package source.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.xml.sax.SAXException;

import source.DAO.SanPhamDAO;
import source.model.SanPham;


@Controller
public class ChiTietSanPhamController {

    @Autowired
    private SanPhamDAO sanPhamDAO;

    @GetMapping("/chiTietSanPham")
    public String getChiTietSanPham(@RequestParam("maSanPham") String maSanPham,
                                    Model model) {
        SanPham sanPham = sanPhamDAO.timSanPhamTheoMaSanPham(maSanPham);
        List<SanPham> sanPhamCungLoai = sanPhamDAO.timSanPhamCungLoai(sanPham.getLoaiSanPham());

        model.addAttribute("sanPham", sanPham);
        model.addAttribute("listSanPhamBanCoTheThich", sanPhamCungLoai.size() >= 5 ? sanPhamCungLoai.subList(0, 5) : sanPhamCungLoai);
        return "chiTietSanPham";
    }
    
    
}
