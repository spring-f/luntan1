package life.majiang.community.community.dto;

import life.majiang.community.community.Model.Question;

import java.util.ArrayList;
import java.util.List;

public class PageDTO<T> {
    private List<T> questions;

    public List<T> getQuestions() {
        return questions;
    }

    public void setQuestions(List<T> questions) {
        this.questions = questions;
    }

    public boolean isShowPrevious() {
        return showPrevious;
    }

    public void setShowPrevious(boolean showPrevious) {
        this.showPrevious = showPrevious;
    }

    public boolean isShowFirstPage() {
        return showFirstPage;
    }

    public void setShowFirstPage(boolean showFirstPage) {
        this.showFirstPage = showFirstPage;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    public boolean isShowEndPage() {
        return showEndPage;
    }

    public void setShowEndPage(boolean showEndPage) {
        this.showEndPage = showEndPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages=new ArrayList<>();
    private Integer totalCount;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    private Integer totalPage;

    @Override
    public String toString() {
        return "PageDTO{" +
                "questions=" + questions +
                ", showPrevious=" + showPrevious +
                ", showFirstPage=" + showFirstPage +
                ", showNext=" + showNext +
                ", showEndPage=" + showEndPage +
                ", page=" + page +
                ", pages=" + pages +
                '}';
    }

    public void setPageination(Integer totalPage, Integer size, Integer page) {
        this.totalPage=totalPage;

        if (page>totalPage){
            page=totalPage;
        }
        if (page<1){
            page=1;
        }
        this.page=page;
        pages.add(page);
        for (int i=1;i<=3;i++){
            if (page-i>0){
                pages.add(0,page-i);
            }
            if (page+i<=totalPage){
                pages.add(page+i);
            }
        }

        if (page==1){
            showPrevious=false;
        }else {
            showPrevious=true;
        }
        if (page==totalPage){
            showNext=false;
        }else{
            showNext=true;
        }
        if (page>=size){
            showFirstPage=true;
        }else {
            showFirstPage=false;
        }
        if (page<=(totalPage-size)){
            showEndPage=true;
        }else {
            showEndPage=false;
        }
    }
}
