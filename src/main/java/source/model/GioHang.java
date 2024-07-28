package source.model;

import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "GioHang")
public class GioHang {
    @Id
    private String MaGioHang;
    
    @OneToOne
    @JoinColumn(name = "NguoiDung", referencedColumnName = "MaNguoiDung")
    private NguoiDung NguoiDung;
    
    private int SoLuongSanPham;
    
    private double TongTien;

    @OneToMany(mappedBy = "GioHang")
    private Set<GioHangChiTiet> GioHangChiTiets;
}
