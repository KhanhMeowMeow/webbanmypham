package source.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
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
import source.DAO.LoaiSanPhamDAO;
import source.DAO.NguoiDungDAO;
import source.DAO.SanPhamDAO;
import source.model.DonHang;
import source.model.DonHangChiTiet;
import source.model.GioHang;
import source.model.GioHangChiTiet;
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
    public List<SanPham> list8SanPhamMoi(){
        List<SanPham> allSanPham = sanPhamDAO.timSanPhamMoi();
        return allSanPham.size() > 8 ? allSanPham.subList(0, 10) : allSanPham;
    }

    @RequestMapping("/submitSignup")
    public String submitSignup() {
        return "signup";
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

    @GetMapping("/messPaymentVNPay")
    public String getMesstPaymentVNPay(
        @RequestParam(name = "vnp_BankTranNo", defaultValue = "") String bankTranNo,
        @RequestParam(name = "vnp_OrderInfo", defaultValue = "") String inforPayment,
        Model model
    ) {
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
        if (donHang ==  null) {
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
//localhost:8080/messPaymentVNPay?vnp_Amount=747300000&vnp_BankCode=VNPAY&vnp_CardType=QRCODE&vnp_OrderInfo=Thanh+toan+don+hang%3ADH813&vnp_PayDate=20240804161538&vnp_ResponseCode=24&vnp_TmnCode=JLO8XJOF&vnp_TransactionNo=0&vnp_TransactionStatus=02&vnp_TxnRef=DH813&vnp_SecureHash=61c97491bf82b3001b0ecd78ce1d83414b13dc6c28621736b6370cc75c63bc7d0f52b19faf18c274713afe43ca494ac7f3d02fc6b492f68ad5a2ab3072ab4dbc


//localhost:8080/messPaymentVNPay?vnp_Amount=747300000&vnp_BankCode=NCB&vnp_BankTranNo=VNP14543958&vnp_CardType=ATM&vnp_OrderInfo=Thanh+toan+don+hang%3ADH507&vnp_PayDate=20240804163500&vnp_ResponseCode=00&vnp_TmnCode=JLO8XJOF&vnp_TransactionNo=14543958&vnp_TransactionStatus=00&vnp_TxnRef=DH507&vnp_SecureHash=3ad78f8cb09b78d58f65f165ea820991f432e495a1b71dbe5e924854257b00c6a920b24e9333fe90a17a5a423be73f088bb1660130a236090e3792fc32c3429c

//localhost:8080/messPaymentVNPay?vnp_Amount=121000000&vnp_BankCode=NCB&vnp_BankTranNo=VNP14543976&vnp_CardType=ATM&vnp_OrderInfo=Thanh+toan+don+hang%3ADH808&vnp_PayDate=20240804165738&vnp_ResponseCode=00&vnp_TmnCode=JLO8XJOF&vnp_TransactionNo=14543976&vnp_TransactionStatus=00&vnp_TxnRef=DH808&vnp_SecureHash=42201e2fc97911da3be558663990b110e8b7156e6583fdb599145abd9867eac3bd17fab8274e94491d855220300f874b96d868c48eb11f2c4e94bd27d147599f