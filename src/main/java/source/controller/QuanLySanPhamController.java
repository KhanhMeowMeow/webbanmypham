package source.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import source.DAO.LoaiSanPhamDAO;
import source.DAO.SanPhamDAO;
import source.model.LoaiSanPham;
import source.model.SanPham;

@Controller
public class QuanLySanPhamController {
    @Autowired
    private SanPhamDAO sanPhamDAO;
    @Autowired
    private LoaiSanPhamDAO loaiSanPhamDAO;
    
    @GetMapping("/quanLySanPham")
    public String getQuanLySanPham(Model model) {
        model.addAttribute("SanPham", new SanPham());
        return "quanLySanPham";
    }

    @ModelAttribute("listLoaiSanPham")
    public List<LoaiSanPham> listLoaiSanPham(){
        return loaiSanPhamDAO.timTatCaLoaiSanPham();
    }

    @ModelAttribute("listSanPham")
    public List<SanPham> listSanPham() {
        List<SanPham> list = sanPhamDAO.timSanPhamOn();
        Collections.reverse(list);
        return list;
    }

    
    @RequestMapping("/quanLySanPham/them")
    public ModelAndView requestThemSanPham(@ModelAttribute("SanPham") @Valid SanPham sanPham ,
                                    BindingResult result, 
                                    Model model,
                                    @RequestParam("fileImage") MultipartFile multipartFile,
                                    @RequestParam("LoaiSanPham") String maLoaiSanPham ){
        String fileName = "";
        String partFile = "src/main/resources/static/image/sanpham/";


        fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        saveFile(partFile, fileName, multipartFile);
        LoaiSanPham loaiSanPham = loaiSanPhamDAO.timTheoMaLoaiSanPham(maLoaiSanPham);

        sanPham.setTrangThai(true);;
        sanPham.setHinhAnh(fileName);
        sanPham.setLoaiSanPham(loaiSanPham);

        if (result.hasErrors()) {
            return new ModelAndView("quanLySanPham").addObject("SanPham", sanPham);
        }
        
        if (loaiSanPham == null) {
            model.addAttribute("messLoaiSanPham", "Thiếu loại sản phẩm");
            return new ModelAndView("quanLySanPham").addObject("SanPham", sanPham);
        }

        if (fileName.equals("")) {
            model.addAttribute("mestHinhAnh", "Bạn chưa chọn hình ảnh sản phẩm");
            return new ModelAndView("quanLySanPham").addObject("SanPham", sanPham);
        }

        sanPhamDAO.themSanPham(sanPham);
        model.addAttribute("messQuanLySanPham", "Thêm sản phẩm thành công");
        return new ModelAndView("redirect:/quanLySanPham");
    }
    
    @RequestMapping("/quanLySanPham/chon")
    public String requestChonSanPham(Model model,@RequestParam("MaSanPham") String MaSanPham) {
        List<SanPham> list = sanPhamDAO.timSanPhamOn();
        Collections.reverse(list);
        model.addAttribute("SanPham", sanPhamDAO.timSanPhamTheoMaSanPham(MaSanPham));
        model.addAttribute("listSanPham", list);
        return "/quanLySanPham";    
    }

    @RequestMapping("/quanLySanPham/xoa")
    public String requestXoaSanPham(Model model,@RequestParam("MaSanPham") String MaSanPham) {
        SanPham sanPham = sanPhamDAO.timSanPhamTheoMaSanPham(MaSanPham);
        sanPham.setTrangThai(false);
        sanPhamDAO.themSanPham(sanPham);
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
