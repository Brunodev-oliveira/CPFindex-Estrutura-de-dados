package model;

public class LinkedList {
    NoLkdList head;
    int amount;
    int searchCount;

    public LinkedList() {
        this.head = null;
        this.amount = 0;
        this.searchCount = 0;
    }


    public Contribuinte search(String cpf, NoLkdList actualNode){
        searchCount= 0;
        return searchInTail(cpf,actualNode);

    }

    public Contribuinte searchInTail(String cpf, NoLkdList actualNode){
        searchCount++;
        if (actualNode != null) {

            Contribuinte actualNodeContribuinte = actualNode.contribuinte;
            int comparison = cpf.compareTo(actualNodeContribuinte.getCpf());

            if (comparison != 0) {
                return searchInTail(cpf, actualNode.getNext());
            }
        }

        return null;

    }
    public void insert(Contribuinte contribuinte){

        head = insertTail(head, contribuinte);
        amount++;

    }
    private NoLkdList insertTail(NoLkdList actualNode, Contribuinte contribuinte){
        if(actualNode == null){

            return new NoLkdList(contribuinte, null);
        }

        actualNode.setNext(insertTail(actualNode.getNext(), contribuinte));

        return actualNode;

    }

    public boolean delete(String cpf){

        NoLkdList target = head;
        NoLkdList next = target.getNext();



        while ( target != null && cpf != target.contribuinte.getCpf()){
            target = next;
            next = target.getNext();

        }
        target.setContribuinte(next.getContribuinte());
        target.setNext(next.getNext());

        return true;


    }












    public NoLkdList getHead() {
        return head;
    }

    public void setHead(NoLkdList head) {
        this.head = head;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(int searchCount) {
        this.searchCount = searchCount;
    }


}
