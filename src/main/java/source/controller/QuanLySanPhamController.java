package source.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFileChooser;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
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

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import source.DAO.LoaiSanPhamDAO;
import source.DAO.SanPhamDAO;
import source.model.LoaiSanPham;
import source.model.SanPham;
import org.springframework.web.bind.annotation.RequestMethod;


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

    @RequestMapping("/xuatFileSanPham")
    public String requestXuatFileSanPham(HttpServletResponse response) {
        List<SanPham> listSanPham = sanPhamDAO.timTatCaSanPham();

        try (Workbook workbook = new SXSSFWorkbook()){
            Sheet sheet = workbook.createSheet("SanPham");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Mã sản phẩm");
            headerRow.createCell(1).setCellValue("Tên sản phẩm");
            headerRow.createCell(2).setCellValue("Số lượng");
            headerRow.createCell(3).setCellValue("Hình ảnh");
            headerRow.createCell(4).setCellValue("Màu sắc");
            headerRow.createCell(5).setCellValue("Đơn giá");
            headerRow.createCell(6).setCellValue("Loại sản phẩm");
            headerRow.createCell(7).setCellValue("Mô tả");
            headerRow.createCell(8).setCellValue("Trạng thái");

            int indexRow = 1;
            for (SanPham sanpham : listSanPham) {
                Row row = sheet.createRow(indexRow);
                row.createCell(0).setCellValue(sanpham.getMaSanPham());
                row.createCell(1).setCellValue(sanpham.getTenSanPham());
                row.createCell(2).setCellValue(sanpham.getSoLuong());
                row.createCell(3).setCellValue(sanpham.getHinhAnh());
                row.createCell(4).setCellValue(sanpham.getMauSac());
                row.createCell(5).setCellValue(sanpham.getDonGia());
                row.createCell(6).setCellValue(sanpham.getLoaiSanPham().getMaLoaiSanPham());
                row.createCell(7).setCellValue(sanpham.getMoTa());
                row.createCell(8).setCellValue(sanpham.isTrangThai());
                indexRow ++;
            }

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=sanpham.xlsx");

            try (OutputStream out = response.getOutputStream()) {
            workbook.write(out);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "redirect:/quanLySanPham";
    }
    
}
