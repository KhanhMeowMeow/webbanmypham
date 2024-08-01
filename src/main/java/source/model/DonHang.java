package source.model;

import java.time.LocalDate;
import java.time.LocalTime;
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
@Table(name = "DonHang")
public class DonHang {
    @Id
    private String MaDonHang;

    @ManyToOne
    @JoinColumn(name = "NguoiDung", referencedColumnName = "MaNguoiDung")
    private NguoiDung NguoiDung;
    private double TongTien;
    private String DiaChiGiao;
    private LocalDate NgayThang;

    @OneToMany(mappedBy = "DonHang")
    private Set<DonHangChiTiet> DonHangChiTiets;
}
