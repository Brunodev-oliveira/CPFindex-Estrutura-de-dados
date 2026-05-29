package model;

public class LinkedList implements CpfRepository {
    private NodeLkdList head;
    private NodeLkdList tail;
    private int amount;
    private int searchCount;


    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.amount = 0;
        this.searchCount = 0;


    }


    public Contribuinte search(String cpf){
        searchCount= 0;
        NodeLkdList actual = this.head;
        while (actual != null){
            searchCount++;
            if (cpf.equals(actual.contribuinte.getCpf())) return actual.contribuinte;

            actual = actual.next;


        }

        return null;

    }


    public boolean insert(Contribuinte contribuinte){

        //if(search(contribuinte.getCpf()) != null) return false;
        NodeLkdList newNode = new NodeLkdList(contribuinte, null);
        if (this.head == null){
            this.head = newNode;
            this.tail = newNode;
        }else{
            tail.setNext(newNode);
            tail = newNode;
        }


        amount++;
        return true;
    }



    public boolean delete(String cpf){

        NodeLkdList target = head;
        NodeLkdList prev = null;


        while ( target != null && !cpf.equals( target.contribuinte.getCpf() )){

            prev = target;
            target = prev.next;

        }

        if ( target == null) return false;

        if (prev != null){

            prev.next = target.next;

        }else {

            this.head = target.next;
        }
        amount--;
        return true;

    }



    public int getAmount() {
        return amount;
    }

    public int getSearchCount() {
        return searchCount;
    }




}
