package source.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class QuanLySanPhamController {
    
    @GetMapping("/quanLySanPham")
    public String getMethodName() {
        return "quanLySanPham";
    }
    
}
