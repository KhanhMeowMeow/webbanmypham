package source.model;


import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank(message = "Mã sản phẩm không được để trống")
    private String MaSanPham;

    @NotEmpty(message = "Tên sản phẩm không được để trống")
    private String TenSanPham;

    @Min(value = 1, message = "Giá trị phải lớn hơn hoặc bằng 1")
    private int SoLuong;

    private String HinhAnh;

    private String MauSac;

    @Min(value = 1, message = "Không bán miễn phí")
    private double DonGia;

    @ManyToOne
    @JoinColumn(name = "LoaiSanPham", referencedColumnName = "MaLoaiSanPham")
    private LoaiSanPham LoaiSanPham;
    
    private String MoTa;
    
    private boolean TrangThai;

    @OneToMany(mappedBy = "SanPham")
    private Set<GioHangChiTiet> GioHangChiTiets;

    @OneToMany(mappedBy = "SanPham")
    private Set<DonHangChiTiet> DonHangChiTiets;
    
    public String getDonGiaDecimal(){
        return String.format("%.0f", DonGia);
    }
}

