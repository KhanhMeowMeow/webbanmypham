package source.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import source.DAO.DonHangChiTietDAO;
import source.DAO.DonHangDAO;
import source.DAO.GioHangChiTietDAO;
import source.DAO.GioHangDAO;
import source.DAO.LoaiSanPhamDAO;
import source.DAO.NguoiDungDAO;
import source.DAO.SanPhamDAO;
import source.model.DonHang;
import source.model.DonHangChiTiet;
import source.model.GioHang;
import source.model.GioHangChiTiet;
import source.model.GioHangDTO;
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
    @Autowired
    private DonHangDAO donHangDAO;
    @Autowired
    private GioHangChiTietDAO gioHangChiTietDAO;
    @Autowired
    private DonHangChiTietDAO donHangChiTietDAO;

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/signup")
    public String getSignup() {
        return "signup";
    }

    @ModelAttribute("listSanPhamMoi")
    public List<SanPham> list10SanPhamMoi() {
        List<SanPham> allSanPham = sanPhamDAO.timSanPhamMoi();
        return allSanPham.size() > 10 ? allSanPham.subList(0, 10) : allSanPham;
    }

    @ModelAttribute("listSanPhamBestSell")
    public List<SanPham> list5BestSell() {
        List<SanPham> listGioHangDTO = donHangChiTietDAO.timSanPhamBestSell();
        // List<SanPham> listBestSell = new ArrayList<>();
        // for (BestSellDTO gioHangDTO : listGioHangDTO) {
        // listBestSell.add(gioHangDTO.getSanPham());
        // }
        return listGioHangDTO.size() > 5 ? listGioHangDTO.subList(0, 5) : listGioHangDTO;
    }

    @RequestMapping("/submitSignup")
    public String submitSignup(
            @RequestParam("TenDangNhap") String tenDangNhap,
            @RequestParam("MatKhau") String matKhau,
            @RequestParam("NhapLaiMatKhau") String laiMatKhau,
            @RequestParam("Email") String email,
            @RequestParam("SoDienThoai") String soDienThoai,
            Model model) {

        String messSignup = "";
        List<NguoiDung> listNguoiDung = nguoiDungDAO.timTatCaNgoiDungHoatDong();
        model.addAttribute("tenDangNhap", tenDangNhap);
        model.addAttribute("matKhau", matKhau);
        model.addAttribute("laiMatKhau", laiMatKhau);
        model.addAttribute("email", email);
        model.addAttribute("soDienThoai", soDienThoai);

        if (tenDangNhap.equals("") || matKhau.equals("") || laiMatKhau.equals("") || email.equals("")
                || soDienThoai.equals("")) {
            messSignup = "Không được để trống thông tin !";
            model.addAttribute("messSignup", messSignup);
            return "signup";
        }
        for (NguoiDung nguoiDung : listNguoiDung) {
            if (tenDangNhap.equals(nguoiDung.getMaNguoiDung())) {
                messSignup = "Đã có người sử dụng tên đăng nhập này !";
                model.addAttribute("messSignup", messSignup);
                return "signup";
            }
        }
        if (!matKhau.equals(laiMatKhau)) {
            messSignup = "Xác nhận lại mật khẩu không trùng khớp !";
            model.addAttribute("messSignup", messSignup);
            return "signup";
        }
        try {
            Integer soDienThoaiint = Integer.parseInt(soDienThoai);
        } catch (Exception e) {
            messSignup = "Số điện thoại không đúng định dạng !";
            model.addAttribute("messSignup", messSignup);
            return "signup";
        }
        if (soDienThoai.length() < 10 || soDienThoai.length() > 13) {
            messSignup = "Số điện thoại không đúng định dạng !";
            model.addAttribute("messSignup", messSignup);
            return "signup";
        }

        if (messSignup.equals("")) {
            NguoiDung nguoiDung = new NguoiDung(tenDangNhap, " ", matKhau, false, null, soDienThoai, email, true, null,
                    null);
            nguoiDungDAO.themNguoiDung(nguoiDung);
            model.addAttribute("isSignup", true);
        }
        return "login";
    }

    @RequestMapping("/submitLogin")
    public String submitLogin(
            @RequestParam("TenDangNhap") String idNguoiDung,
            @RequestParam("MatKhau") String matKhau,
            Model model) {

        List<NguoiDung> nguoiDungs = nguoiDungDAO.timTatCaNgoiDungHoatDong();
        NguoiDung existingUser = null;
        for (NguoiDung nguoiDung : nguoiDungs) {
            if (nguoiDung.getMaNguoiDung().equals(idNguoiDung)) {
                existingUser = nguoiDung;
            }
        }
        if (existingUser == null) {
            model.addAttribute("messLogin", "Sai tên đăng nhập hoặc mật khẩu!");
            return "login";
        } else if (existingUser.getMatKhau().equals(matKhau)) {
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

    @GetMapping("/timSanPham")
    public String getTimSanPham(@RequestParam(name = "textFind", defaultValue = "", required = false) String tenSanPham,
            @RequestParam(defaultValue = "", required = false) String loaiSanPham,
            Model model,
            @RequestParam(defaultValue = "", required = false) String rule,
            @RequestParam(defaultValue = "", required = false) String all,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "0", required = false) Double minPrice,
            @RequestParam(defaultValue = "0", required = false) Double maxPrice) {
        model.addAttribute("messListSanPham", false);
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
            model.addAttribute("messListSanPham", true);
            listSanPham = sanPhamDAO.timSanPhamOn();
        }

        if (all.equals("all")) {
            model.addAttribute("messListSanPham", false);
            listSanPham = sanPhamDAO.timSanPhamOn();
        }
        if (rule.equals("max")) {
            Collections.sort(listSanPham, Comparator.comparingDouble(SanPham::getDonGia).reversed());
        } else if (rule.equals("min")) {
            Collections.sort(listSanPham, Comparator.comparingDouble(SanPham::getDonGia));
        }
        List<SanPham> pageListSanPham = TopageUntil.paginateFake(listSanPham, page, pageSize);
        model.addAttribute("listSanPham", pageListSanPham);
        model.addAttribute("listNumberPage", listNumber((int) Math.ceil((double) listSanPham.size() / pageSize)));
        model.addAttribute("totalPage", (int) Math.ceil((double) listSanPham.size() / pageSize));
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("formFind", tenSanPham);
        model.addAttribute("loaiSanPham", loaiSanPham);
        model.addAttribute("rule", rule);
        return "/danhMucSanPham";
    }

    private List<Integer> listNumber(int totalPage) {
        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = 0; i < totalPage; i++) {
            pageNumbers.add(i);
        }
        return pageNumbers;
    }

    @GetMapping("/messPaymentVNPay")
    public String getMesstPaymentVNPay(
            @RequestParam(name = "vnp_BankTranNo", defaultValue = "") String bankTranNo,
            @RequestParam(name = "vnp_OrderInfo", defaultValue = "") String inforPayment,
            Model model) {
        Pattern pattern = Pattern.compile("DH\\d+");
        Matcher matcher = pattern.matcher(inforPayment);
        if (!matcher.find()) {
            model.addAttribute("messPayment", "Không tìm thấy thông tin thanh toán");
            return "thanhToanThanhCong";
        }
        String maNguoiDung = (String) session.getAttribute("idUser");
        if (maNguoiDung.equals("")) {
            model.addAttribute("messPayment", "Không tìm thấy thông tin người dùng");
            return "thanhToanThanhCong";
        }
        String messPayment = "";
        String idDH = matcher.group();
        DonHang donHang = donHangDAO.timDonHangTheoID(idDH);
        if (donHang == null) {
            model.addAttribute("messPayment", "Không tìm thấy thông tin thanh toán");
            return "thanhToanThanhCong";
        }
        List<DonHangChiTiet> listDonHangChiTiet = donHangChiTietDAO.timDHCTbyDH(donHang);
        List<GioHangChiTiet> listGioHangChiTiet = gioHangChiTietDAO.timGHCTByMaNguoiDung(maNguoiDung);
        if (!bankTranNo.equals("")) {
            messPayment = "Thanh toán thành công!";
            donHang.setThanhToan(true);
            donHangDAO.thenDonHang(donHang);
            for (GioHangChiTiet gioHangChiTiet : listGioHangChiTiet) {
                for (DonHangChiTiet donHangChiTiet : listDonHangChiTiet) {
                    if (donHangChiTiet.getSanPham().getMaSanPham().equals(gioHangChiTiet.getSanPham().getMaSanPham())) {
                        gioHangChiTietDAO.xoaSanPhamTrongGioHang(gioHangChiTiet.getMaGioHangChiTiet());
                        SanPham sanPham  = donHangChiTiet.getSanPham();
                        sanPham.setSoLuong((int) (sanPham.getSoLuong() - donHangChiTiet.getSoLuong()));
                        sanPhamDAO.themSanPham(sanPham);
                    }
                }
            }
        } else {
            messPayment = "Thanh toán thất bại !";
        }
        model.addAttribute("messPayment", messPayment);
        return "thanhToanThanhCong";
    }
}
