package by.training.finaltask.action;

import by.training.finaltask.parser.FormParser;

import java.util.Objects;

public class Pagination {

    private int amountOfPages;
    private int offset;
    private int pageNumber;

    public Pagination(int amountOfRowsFound, int rowcount, String pageParameter) {
        this.amountOfPages = amountOfRowsFound % rowcount == 0 ?
                amountOfRowsFound / rowcount : amountOfRowsFound / rowcount + 1;
        this.pageNumber = FormParser.parsePageNumber(pageParameter, amountOfPages);
        this.offset = (pageNumber - 1) * rowcount;
    }

    public int getAmountOfPages() {
        return amountOfPages;
    }

    public void setAmountOfPages(int amountOfPages) {
        this.amountOfPages = amountOfPages;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pagination that = (Pagination) o;
        return getAmountOfPages() == that.getAmountOfPages() &&
                getOffset() == that.getOffset() &&
                getPageNumber() == that.getPageNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAmountOfPages(), getOffset(), getPageNumber());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Pagination{");
        sb.append("amountOfPages=").append(amountOfPages);
        sb.append(", offset=").append(offset);
        sb.append(", pageNumber=").append(pageNumber);
        sb.append('}');
        return sb.toString();
    }
}
