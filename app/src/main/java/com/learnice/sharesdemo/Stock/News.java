package com.learnice.sharesdemo.Stock;

/**
 * Created by Xuebin He on 2016/6/25.
 */
public class News {
    private String title;
    private String source;
    private String article_url;

    public News(String title, String source, String article_url) {
        this.title = title;
        this.source = source;
        this.article_url = article_url;
    }

    public String getArticle_url() {
        return article_url;
    }

    public void setArticle_url(String article_url) {
        this.article_url = article_url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "News{" +
                "article_url='" + article_url + '\'' +
                ", title='" + title + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
