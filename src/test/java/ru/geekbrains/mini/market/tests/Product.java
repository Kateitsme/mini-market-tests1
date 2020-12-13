package ru.geekbrains.mini.market.tests;

public class Product {
    private Long id;
    private String title;
    private int price;
    private String categoryTitle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public Product() {
    }

    public Product(Long id, String title, int price, String categoryTitle) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.categoryTitle = categoryTitle;
    }
}
