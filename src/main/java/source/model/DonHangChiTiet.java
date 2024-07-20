package source.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DonHangChiTiet")
public class DonHangChiTiet {
    @Id
    private String MaDonHangChiTiet;

    @ManyToOne
    @JoinColumn(name = "DonHang", referencedColumnName = "MaDonHang")
    private DonHang DonHang;

    @ManyToOne
    @JoinColumn(name = "SanPham", referencedColumnName = "MaSanPham")
    private SanPham SanPham;
    
    private int SoLuong;
    private double TongGia;
}
