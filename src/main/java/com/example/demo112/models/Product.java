package com.example.demo112.models;
import javax.persistence.*;
import java.util.List;
@Entity
@Table(name="products")

public  class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column(name = "name",nullable = false,length = 350)
    private String name;
    @Column(name="price")
    private Float price;
    @Column(name="thumbnail",length =300)
    private String thumbnail;
    @Column (name="description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private com.example.demo112.models.Category category;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductImage> productImages;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Product(){}
//    public Product(String name, Float price,String thumbnail,String description,int )
//    {
//        this.name=name;
//        this.price=price;
//        this.thumbnail=thumbnail;
//        this.description=description;
//    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
