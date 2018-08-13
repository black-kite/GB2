
public class Products {

    private int id;
    private String title;
    private int prodid;
    private int cost;

    public Products(int prodid, String title, int cost) {
        this.prodid = prodid;
        this.title = title;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getProdid() {
        return prodid;
    }

    public void setProdid(int prodid) {
        this.prodid = prodid;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", prodid =" + prodid +
                ", title='" + title + '\'' +
                ", cost=" + cost +
                '}';
    }
}
