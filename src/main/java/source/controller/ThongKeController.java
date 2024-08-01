package source.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import source.DAO.DonHangDAO;
import source.model.DonHang;
import source.model.ThongKeDoanhThuDTO;

@Controller
public class ThongKeController {
    @Autowired
    DonHangDAO donHangDAO;

    @GetMapping("/thongKeDoanhThu")
    public String getThongKeDoanhThu(Model model) {
        List<ThongKeDoanhThuDTO> listThongKe = donHangDAO.thongKeDoanhThu();

        int size = listThongKe.size();
        String[] listMonth = new String[size];
        double[] listTotalMoney = new double[size];

        for (int i = 0; i < size; i++) {
            ThongKeDoanhThuDTO thongKe = listThongKe.get(i);
            listMonth[i] = "Tháng " + thongKe.getMonth();
            listTotalMoney[i] = thongKe.getTotalMoney();
        }

        model.addAttribute("listMonth", listMonth);
        model.addAttribute("listTotalMoney", listTotalMoney);

        return "thongKeDoanhThu";
    }
}