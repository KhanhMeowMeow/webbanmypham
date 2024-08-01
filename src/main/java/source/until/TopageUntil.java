package source.until;

import java.util.ArrayList;
import java.util.List;

public class TopageUntil {
    public static <T> List<T> paginateFake(List<T> list, int page, int pageSize){
        int start = page * pageSize;
        int end = Math.min(start + pageSize, list.size());

        if (start > list.size()) {
            return new ArrayList<>();
        }
        return list.subList(start, end);
    }
}
