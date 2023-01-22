package io.study.deneb.model;

public class Cake {
  private int id;
  private String name;
  private int price;

  public Cake(int id, String name, int price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getPrice() {
    return price;
  }

  @Override
  public String toString() {
    return "Cake{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", price=" + price +
      '}';
  }
}
