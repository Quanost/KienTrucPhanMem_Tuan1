package com.example.receiver_point_to_point;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder={"maSo","cMND","hoTen","diaChi"})
public class Person {
    private String maSo;
    private String cMND;
    private String hoTen;
    private String diaChi;

    public Person(String maSo, String cMND, String hoTen, String diaChi) {
        this.maSo = maSo;
        this.cMND = cMND;
        this.hoTen = hoTen;
        this.diaChi = diaChi;
    }

    public Person() {
    }

    public String getMaSo() {
        return maSo;
    }

    public void setMaSo(String maSo) {
        this.maSo = maSo;
    }

    public String getcMND() {
        return cMND;
    }

    public void setcMND(String cMND) {
        this.cMND = cMND;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    @Override
    public String toString() {
        return "Person{" +
                "maSo='" + maSo + '\'' +
                ", cMND='" + cMND + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", diaChi='" + diaChi + '\'' +
                '}';
    }
}
