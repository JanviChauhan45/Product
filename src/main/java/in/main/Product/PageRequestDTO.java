package in.main.Product;

import lombok.Data;

import java.util.List;

@Data
public class PageRequestDTO<T> {


    private int pageNumber = 0;
    private int pageSize   = 10;
    private String sortBy  = "id";
    private String sortDir = "asc";
    private String search  = "";

    private List<T> content;
    private long totalElements;
    private int totalPages;
    private boolean lastPage;
}
