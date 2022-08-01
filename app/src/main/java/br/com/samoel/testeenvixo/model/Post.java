package br.com.samoel.testeenvixo.model;

import android.text.Html;

import java.io.Serializable;

public class Post  implements Serializable , Comparable<Post>{
    private String page_views;
    private String img;
    private String titulo;
    private String descricao ;
    private String link ;

    public String getPage_views() {
        return page_views;
    }

    public void setPage_views(String page_views) {
        this.page_views = page_views;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return String.valueOf(Html.fromHtml(descricao, Html.FROM_HTML_MODE_COMPACT));
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Post{" +
                "page_views='" + page_views + '\'' +
                ", img='" + img + '\'' +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    @Override
    public int compareTo(Post post) {
        return this.titulo.compareTo(post.getTitulo());
    }
}
