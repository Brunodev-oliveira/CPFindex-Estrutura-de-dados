package model;
import java.lang.String;


public class NodeBst {
    private String key;
    private Contribuinte contribuinte;
    private NodeBst right;
    private NodeBst left;

    public NodeBst(String key, Contribuinte contribuinte){

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

    public NodeBst getLeft() {
        return left;
    }

    public void setLeft(NodeBst left) {
        this.left = left;
    }

    public NodeBst getRight() {
        return right;
    }

    public void setRight(NodeBst right) {
        this.right = right;
    }
}
