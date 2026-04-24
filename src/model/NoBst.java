package model;
import model.Contribuinte;
import java.lang.String;


public class NoBst {
    private String key;
    private Contribuinte contribuinte;
    private NoBst right;
    private NoBst left;

    public NoBst(String key, Contribuinte contribuinte){

        this.key = key;
        this.contribuinte = contribuinte;
        this.right = null;
        this.left = null;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Contribuinte getContribuinte() {
        return contribuinte;
    }

    public void setContribuinte(Contribuinte contribuinte) {
        this.contribuinte = contribuinte;
    }

    public NoBst getLeft() {
        return left;
    }

    public void setLeft(NoBst left) {
        this.left = left;
    }

    public NoBst getRight() {
        return right;
    }

    public void setRight(NoBst right) {
        this.right = right;
    }
}
