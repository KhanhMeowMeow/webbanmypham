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
@Table(name = "GioHangChiTiet")
public class GioHangChiTiet {
    @Id
    private String MaGioHangChiTiet;

    @ManyToOne
    @JoinColumn(name = "GioHang", referencedColumnName = "MaGioHang")
    private GioHang GioHang;

    @ManyToOne
    @JoinColumn(name = "SanPham", referencedColumnName = "MaSanPham")
    private SanPham SanPham;

    private int SoLuong;
}
