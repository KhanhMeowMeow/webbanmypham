package source.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import source.DAO.SanPhamDAO;

@Controller
public class IndexController {

    @Autowired
    private SanPhamDAO sanPhamDAO;

    @GetMapping("/index")
    public String getIndex() {
        return "index";
    }
    
}
