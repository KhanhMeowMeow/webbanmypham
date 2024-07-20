package source.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
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
@Table(name = "NguoiDung")
public class NguoiDung {
    @Id
    private String MaNguoiDung;
    private String TenNguoiDung;
    private String MatKhau;
    private boolean vaiTro;
    private String DiaChi;
    private String SoDienThoai;
    private String Email;

    @OneToOne(mappedBy = "NguoiDung")
    private GioHang GioHang;

    @OneToMany(mappedBy = "NguoiDung")
    private Set<DonHang> DonHangs;

}
