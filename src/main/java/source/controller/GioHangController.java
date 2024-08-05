package source.controller;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
import source.DAO.DonHangChiTietDAO;
import source.DAO.DonHangDAO;
import source.DAO.GioHangChiTietDAO;
import source.DAO.GioHangDAO;
import source.DAO.NguoiDungDAO;
import source.DAO.SanPhamDAO;
import source.model.DonHang;
import source.model.DonHangChiTiet;
import source.model.GioHang;
import source.model.GioHangChiTiet;
import source.model.GioHangDTO;
import source.model.NguoiDung;
import source.model.OtherSanPhamDTO;
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
    private ObjectMapper objectMapper;
    @Autowired
    private DonHangDAO donHangDAO;
    @Autowired
    private NguoiDungDAO nguoiDungDAO;
    @Autowired
    private DonHangChiTietDAO donHangChiTietDAO;

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
    
    @PostMapping("/datHang")
    public String getDatHang(@RequestParam("selectedItems") String selectedItemsJson, 
                                Model model) throws JsonMappingException, JsonProcessingException {
        List<OtherSanPhamDTO> listSanPhamOther = objectMapper.readValue(selectedItemsJson, new TypeReference<List<OtherSanPhamDTO>>(){});
        List<GioHangDTO> listGioHangDTO = new ArrayList<>();
        String maDonHang = newIDDonHang(donHangDAO.timTatCaDonHang());
        NguoiDung nguoiDung = nguoiDungDAO.timTheoMaNgoiDung((String) session.getAttribute("idUser"));
        double tongTien = 0;
        String diaChiGiao = "";
        LocalDate ngatThang = toDate();
        try {
            String idSanPham = listSanPhamOther.get(0).getProductId();
        } catch (Exception e) {
            return "redirect:/gioHang";
        }
        for (OtherSanPhamDTO otherSanPhamDTO : listSanPhamOther) {
            tongTien += sanPhamDAO.timSanPhamTheoMaSanPham(otherSanPhamDTO.getProductId()).getDonGia() * otherSanPhamDTO.getQuantity();
            GioHangDTO gioHangDTO = new GioHangDTO();
            gioHangDTO.setSanPham(sanPhamDAO.timSanPhamTheoMaSanPham(otherSanPhamDTO.getProductId()));
            gioHangDTO.setSoLuong(otherSanPhamDTO.getQuantity());
            listGioHangDTO.add(gioHangDTO);
        }
        if (nguoiDung != null) {
            diaChiGiao = nguoiDung.getDiaChi();
        } else {
            return "login";
        }
        DonHang donHang = new DonHang(maDonHang, nguoiDung, tongTien, diaChiGiao, ngatThang, false, null);
        donHangDAO.thenDonHang(donHang);
        for (GioHangDTO gioHangDTO : listGioHangDTO) {
            donHangChiTietDAO.themDonHangChiTiet(new DonHangChiTiet(newIDDonHangChiTiet(donHangChiTietDAO.timTatCa()), donHang, gioHangDTO.getSanPham(), gioHangDTO.getSoLuong(), (double) gioHangDTO.getSanPham().getDonGia() * gioHangDTO.getSoLuong()));
        }
        model.addAttribute("donHang", donHang);
        model.addAttribute("listOther", listGioHangDTO);
        return "datHang";
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

    private String newIDDonHang(List<DonHang> listDonHang){
        Random random = new Random();
        String maGHTCT = "DH" + (random.nextInt(999 - 1 + 1) + 1);
        for (DonHang donHang : listDonHang) {
            if (donHang.getMaDonHang().equals(maGHTCT)) {
                maGHTCT = "DH" + (random.nextInt(999 - 1 + 1) + 1);
            }
        }
        return maGHTCT;
    }
    private String newIDDonHangChiTiet(List<DonHangChiTiet> listDonHangChiTiet){
        Random random = new Random();
        String maGHTCT = "DHCT" + (random.nextInt(999 - 1 + 1) + 1);
        for (DonHangChiTiet donHang : listDonHangChiTiet) {
            if (donHang.getMaDonHangChiTiet().equals(maGHTCT)) {
                maGHTCT = "DHCT" + (random.nextInt(999 - 1 + 1) + 1);
            }
        }
        return maGHTCT;
    }

    private LocalDate toDate(){
        return LocalDate.now();
    }
}
