package com.example.myfirstfinalapplication;

import java.util.ArrayList;

public class Person {
  private String username;
  private String password;
  private int imageName;

  public Person(String username, String password, int imageName) { //Image image
    this.username = username;
    this.password = password;
    this.imageName = imageName;
  }

  public Person(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public int getImageName() {
    return imageName;
  }

  public void setImageName(int imageName) {
    this.imageName = imageName;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return this.username;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  public String getPassword() {
    return this.password;
  }


  public static ArrayList<Person> getData() {
    ArrayList<Person> personList = new ArrayList<Person>();
    int[] personImages = {R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4,
            R.drawable.p5, R.drawable.p6, R.drawable.p7, R.drawable.p8, R.drawable.p9,
            R.drawable.p10, R.drawable.p11, R.drawable.p12, R.drawable.p13};
    String[] personNames = {"Ahmet", "Bülent", "Neşe", "Pelin", "Mehmet", "Kemal", "Sevgi",
            "Zeynep", "Can", "Josh", "Funda","Melahat", "Admin"};
    String[] personPasswords = {"zj41ba", "hqah450", "Z1Pqi", "65yqPd", "jdys20", "JMnw12",
            "Bdme80", "123654", "hxswaQ", "yU859", "87PlP", "dxC7L", "0"};

    for (int i=0; i < personImages.length; i++) {
      Person p = new Person(personNames[i], personPasswords[i], personImages[i]);
      personList.add(p);
    }

    return personList;
  }


}