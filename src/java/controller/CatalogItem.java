package controller;

public class CatalogItem {

    private String name;
    private String description;
    private String itemId;
    private double price;

    public CatalogItem() {
        this("", "", 0.00);
    }

    public CatalogItem(String name,
            String description, double price) {
        setName(name);
        setDescription(description);
        setPrice(price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
