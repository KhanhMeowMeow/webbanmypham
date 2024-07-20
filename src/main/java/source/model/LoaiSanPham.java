package source.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "LoaiSanPham")
public class LoaiSanPham {
    @Id
    private String MaLoaiSanPham;
    private String TenLoaiSanPham;

    @OneToMany(mappedBy = "LoaiSanPham")
    private Set<SanPham> SanPhams;
}
