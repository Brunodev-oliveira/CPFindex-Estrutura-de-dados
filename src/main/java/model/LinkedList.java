package model;

public class LinkedList {
    private NodeLkdList head;
    private int amount;
    private int searchCount;

    public LinkedList() {
        this.head = null;
        this.amount = 0;
        this.searchCount = 0;
    }


    public Contribuinte search(String cpf){
        searchCount= 0;
        return searchInTail(cpf, head);

    }

    private Contribuinte searchInTail(String cpf, NodeLkdList actualNode){
        searchCount++;
        if (actualNode != null) {

            Contribuinte actualNodeContribuinte = actualNode.contribuinte;
            int comparison = cpf.compareTo(actualNodeContribuinte.getCpf());

            if (comparison != 0) {
                return searchInTail(cpf, actualNode.getNext());
            }

            return actualNodeContribuinte;
        }

        return null;

    }
    public void insert(Contribuinte contribuinte){

        head = insertTail(head, contribuinte);
        amount++;

    }
    private NodeLkdList insertTail(NodeLkdList actualNode, Contribuinte contribuinte){
        if(actualNode == null){

            return new NodeLkdList(contribuinte, null);
        }

        actualNode.setNext(insertTail(actualNode.getNext(), contribuinte));

        return actualNode;

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
