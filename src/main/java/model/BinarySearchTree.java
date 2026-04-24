package model;


public class BinarySearchTree {
    private NoBst root ;
    private int searchCount;
    private int amount;


    public BinarySearchTree() {
        root = null;
        this.searchCount = 0;
        this.amount = 0;




    }
    public void insert(Contribuinte contribuinte){
        root = insertTail(root, contribuinte.getCpf(), contribuinte);
        amount++;
    }

    private NoBst insertTail(NoBst actual, String cpf, Contribuinte contribuinte){
        if (actual == null){
            return new NoBst(cpf, contribuinte );
        }

        int comparison = cpf.compareTo(actual.getKey());

        if( comparison < 0){
            actual.setLeft(insertTail(actual.getLeft(),cpf, contribuinte));
        } else if (comparison > 0) {
            actual.setRight(insertTail(actual.getRight(), cpf, contribuinte));

        }

        return actual;
        //Aviso: Não insere cpf igual
        //Falta incrementar alguma mensagem para CPF iguais!


    }

    //Busca


    public Contribuinte search(String cpf) {
        searchCount = 0;
        return searchInTail(root, cpf);

    }

    private Contribuinte searchInTail(NoBst actual, String cpf) {
        searchCount++;
        if (actual == null){
            return null;
        }

        int comparison = cpf.compareTo((actual.getKey()));
        if ( comparison == 0){
            return actual.getContribuinte();
        }else if (comparison < 0){
            return searchInTail(actual.getLeft(), cpf);
        }else {
            return searchInTail(actual.getRight(), cpf);

        }



    }

    //remoção


    public boolean delete(String cpf){

        int amountBefore = amount;
        root = deleteTail(root, cpf);

        return amountBefore > amount;

    }


    private NoBst deleteTail(NoBst actual, String cpf){
        if(actual == null){
            return null;
        }

        int comparison = cpf.compareTo(actual.getKey());

        if (comparison < 0){
            actual.setLeft(deleteTail(actual.getLeft(), cpf));

        } else if (comparison > 0) {
            actual.setRight(deleteTail(actual.getRight(), cpf));

        }else{
            amount--;

            if(actual.getLeft() == null) return actual.getRight();
            if(actual.getRight() == null) return  actual.getLeft();


            NoBst successor = searchMinor(actual.getRight());

            actual.setKey(successor.getKey());
            actual.setContribuinte(successor.getContribuinte());
            actual.setRight(deleteTail(actual.getRight(),successor.getKey()));

        }

        return  actual;


    }


    private NoBst searchMinor(NoBst actualNode){
        while (actualNode.getLeft() != null){
            actualNode = actualNode.getLeft();
        }

        return actualNode;
    }

    public int getSearchCount() {
        return searchCount;
    }

    public int getAmount() {
        return amount;
    }

    public void resetSearchCount(){

        searchCount = 0;

    }
}//final da árvore
