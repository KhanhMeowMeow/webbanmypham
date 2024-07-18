package source.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "nguoiDung")
public class nguoiDung {
    @Id
    private String idNguoiDung;
    private String matKhau;
    private String tenNguoiDung;
    private boolean vaiTro;

}
