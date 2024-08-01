package source.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import source.DAO.LoaiSanPhamDAO;
import source.DAO.NguoiDungDAO;
import source.DAO.SanPhamDAO;
import source.model.LoaiSanPham;
import source.model.NguoiDung;
import source.model.SanPham;
import source.until.TopageUntil;

@Controller
public class IndexController {

    @Autowired
    private SanPhamDAO sanPhamDAO;
    @Autowired
    private HttpSession session;
    @Autowired
    private NguoiDungDAO nguoiDungDAO;
    @Autowired
    private LoaiSanPhamDAO loaiSanPhamDAO;

    @GetMapping("/")
    public String getIndex() {
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
        List<NguoiDung> nguoiDungs = nguoiDungDAO.timTatCaNgoiDungHoatDong();
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
        }else{
            model.addAttribute("messLogin", "Sai tên đăng nhập hoặc mật khẩu!");
            return "login";
        }
    }

    @RequestMapping("/submitLogout")
    public String submitLogout() {
        session.removeAttribute("idUser");
        return "login";
    }
    
    @GetMapping("/timSanPham")
    public String getTimSanPham(@RequestParam(name = "textFind", defaultValue = "", required = false) String tenSanPham,
                                @RequestParam(defaultValue = "", required = false) String loaiSanPham,
                                Model model,
                                @RequestParam(defaultValue = "", required = false) String rule,
                                @RequestParam(defaultValue = "", required = false) String all,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "0", required = false) Double minPrice,
                                @RequestParam(defaultValue = "0", required = false) Double maxPrice
                                ){
        model.addAttribute("messListSanPham",false);
        int pageSize = 12;
        List<SanPham> listSanPham = sanPhamDAO.timSanPhamOn();
        Iterator<SanPham> inIterator = listSanPham.iterator();
        LoaiSanPham loaiSP = loaiSanPhamDAO.timTheoMaLoaiSanPham(loaiSanPham);
        while (inIterator.hasNext()) {
            SanPham item = inIterator.next();
            if (!tenSanPham.equals("") && !item.getTenSanPham().toLowerCase().contains(tenSanPham.toLowerCase())) {
                inIterator.remove();
            } else if (item.getDonGia() < minPrice && minPrice >= 0) {
                inIterator.remove();
            } else if (item.getDonGia() > maxPrice && maxPrice > 0) {
                inIterator.remove();
            } else if (!item.getLoaiSanPham().getMaLoaiSanPham().equals(loaiSanPham) && loaiSP != null) {
                inIterator.remove();
            }
            System.out.println(tenSanPham);
            System.out.println(loaiSanPham);
            System.out.println(maxPrice);
            System.out.println(minPrice);
        }

        if (listSanPham.size() == 0) {
            model.addAttribute("messListSanPham",true);
            listSanPham = sanPhamDAO.timSanPhamOn();
        }

        if (all.equals("all")) {
            model.addAttribute("messListSanPham",false);
            listSanPham = sanPhamDAO.timSanPhamOn();
        }
        if (rule.equals("max")) {
            Collections.sort(listSanPham, Comparator.comparingDouble(SanPham :: getDonGia).reversed());
        } else if (rule.equals("min")) {
            Collections.sort(listSanPham, Comparator.comparingDouble(SanPham :: getDonGia));
        }
        List<SanPham> pageListSanPham = TopageUntil.paginateFake(listSanPham, page, pageSize);
        model.addAttribute("listSanPham", pageListSanPham);
        model.addAttribute("listNumberPage", listNumber((int)Math.ceil((double) listSanPham.size() / pageSize)));
        model.addAttribute("totalPage", (int)Math.ceil((double) listSanPham.size() / pageSize));
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("formFind", tenSanPham);
        model.addAttribute("loaiSanPham", loaiSanPham);
        model.addAttribute("rule", rule);
        return "/danhMucSanPham";
    }

    private List<Integer> listNumber(int totalPage){
        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = 0; i < totalPage; i++) {
            pageNumbers.add(i);
        }
        return pageNumbers;
    }

}
