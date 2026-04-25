package model;

public class NodeLkdList {
    Contribuinte contribuinte;
    NodeLkdList next;


    public NodeLkdList( Contribuinte contribuinte, NodeLkdList next) {
        this.contribuinte = contribuinte;
        this.next = next;
    }

    public Contribuinte getContribuinte() {
        return contribuinte;
    }



    public void setContribuinte(Contribuinte contribuinte) {
        this.contribuinte = contribuinte;
    }

    public NodeLkdList getNext() {
        return next;
    }

    public void setNext(NodeLkdList next) {
        this.next = next;
    }
}

