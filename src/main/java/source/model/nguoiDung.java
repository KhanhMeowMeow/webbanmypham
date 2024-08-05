package source.model;

import java.util.Set;

import org.eclipse.angus.mail.handlers.message_rfc822;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "Không được để trống mã người dùng")
    private String MaNguoiDung;

    @NotNull(message = "Không được để trống Tên người dùng")
    private String TenNguoiDung;

    @NotBlank(message = "Phải có mật khẩu mới đăng nhập được")
    private String MatKhau;

    private boolean vaiTro;

    private String DiaChi;

    @NotBlank(message = "Không được để trống số điện thoại")
    private String SoDienThoai;

    @NotBlank(message = "Không được để trống Email")
    private String Email;

    private boolean TrangThai;
    @OneToOne(mappedBy = "NguoiDung")
    private GioHang GioHang;


    @OneToMany(mappedBy = "NguoiDung")
    private Set<DonHang> DonHangs;

}
