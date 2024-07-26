package source.model;


import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "SanPham")
public class SanPham {
    @Id
    private String MaSanPham;
    private String TenSanPham;
    private int SoLuong;
    private String HinhAnh;
    private String MauSac;
    private double DonGia;
    @ManyToOne
    @JoinColumn(name = "LoaiSanPham", referencedColumnName = "MaLoaiSanPham")
    private LoaiSanPham LoaiSanPham;
    private String MoTa;

    @OneToMany(mappedBy = "SanPham")
    private Set<GioHangChiTiet> GioHangChiTiets;

    @OneToMany(mappedBy = "SanPham")
    private Set<DonHangChiTiet> DonHangChiTiets;
    
    public String getDonGiaDecimal(){
        return String.format("%.0f", DonGia);
    }
}

