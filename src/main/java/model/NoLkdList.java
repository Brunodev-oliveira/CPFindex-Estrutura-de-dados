package model;

public class NoLkdList {
    Contribuinte contribuinte;
    NoLkdList next;


    public NoLkdList( Contribuinte contribuinte, NoLkdList next) {
        this.contribuinte = contribuinte;
        this.next = next;
    }

    public Contribuinte getContribuinte() {
        return contribuinte;
    }



    public void setContribuinte(Contribuinte contribuinte) {
        this.contribuinte = contribuinte;
    }

    public NoLkdList getNext() {
        return next;
    }

    public void setNext(NoLkdList next) {
        this.next = next;
    }
}

