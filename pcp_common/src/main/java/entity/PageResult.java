package entity;


import java.util.List;

/**
 * @depict: 构建通用的分页结果类
 * @author: PCP
 * @create: 2019-02-21 20:15
 */
public class PageResult<T> {

    private long total;//总记录数
    private List<T> rows;//记录的集合

    public PageResult() {
    }

    public PageResult(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}
