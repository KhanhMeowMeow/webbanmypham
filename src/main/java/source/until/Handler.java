package source.until;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import source.DAO.NguoiDungDAO;
import source.model.NguoiDung;

@Component
public class Handler implements HandlerInterceptor {
    @Autowired
    private HttpSession session;
    @Autowired
    private NguoiDungDAO nguoiDungDAO;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String usersession = "";
        if (session.getAttribute("idUser") != null) {
            usersession = (String) session.getAttribute("idUser");
        }
        
        NguoiDung nguoiDung = nguoiDungDAO.timTheoMaNgoiDung(usersession);
        if (nguoiDung != null) {
            if (nguoiDung.isVaiTro()) {
                request.setAttribute("vaitro", "quanly");
            } else {
                request.setAttribute("vaitro", "khachhang");
            }
            request.setAttribute("user", "yes");
        } else {
            request.setAttribute("user", "none");
        }
        return true;
    }
}
