package source.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import source.DAO.SanPhamDAO;
import source.model.SanPham;

@Controller
public class QuanLySanPhamController {
    @Autowired
    private SanPhamDAO sanPhamDAO;
    
    @GetMapping("/quanLySanPham")
    public String getQuanLySanPham(Model model) {
        List<SanPham> listSanPham = sanPhamDAO.timTatCaSanPham();
        model.addAttribute("SanPham", new SanPham());
        model.addAttribute("listSanPham", listSanPham);
        return "quanLySanPham";
    }
    
    @RequestMapping("/quanLySanPham/them")
    public String requestThemSanPham(@ModelAttribute("SanPham") SanPham sanPham, 
                                    Model model,
                                    @RequestParam("fileImage") MultipartFile multipartFile) {
        String fileName = "";
        String partFile = "src/main/resources/static/image/sanpham/";

        fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        saveFile(partFile, fileName, multipartFile);
        sanPham.setHinhAnh(fileName);

        sanPhamDAO.themSanPham(sanPham);
        model.addAttribute("messQuanLySanPham", "Thêm sản phẩm thành công");
        return "redirect:/quanLySanPham";
    }
    
    @RequestMapping("/quanLySanPham/chon")
    public String requestChonSanPham(Model model,@RequestParam("MaSanPham") String MaSanPham) {
        model.addAttribute("SanPham", sanPhamDAO.timSanPhamTheoTen(MaSanPham));
        model.addAttribute("listSanPham", sanPhamDAO.timTatCaSanPham());
        return "/quanLySanPham";
    }

    @RequestMapping("/quanLySanPham/xoa")
    public String requestXoaSanPham(Model model,@RequestParam("MaSanPham") String MaSanPham) {
        sanPhamDAO.xoaSanPham(MaSanPham);
        return "redirect:/quanLySanPham";
    }

    public void saveFile(String urlImageServer, String fileName, MultipartFile multipartFile) {
        Path path = Paths.get(urlImageServer);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                System.out.println("Lỗi tạo file mới trên server: " + e);
            }
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path pathfile = path.resolve(fileName);
            Files.copy(inputStream, pathfile, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.out.println("Lỗi lưu file trên server: " + e);
        }
    }
}
