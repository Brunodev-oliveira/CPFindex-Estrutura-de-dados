package model;


import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree implements CpfRepository {
    private NodeBst root ;
    private int searchCount;
    private int amount;




    public BinarySearchTree() {
        root = null;
        this.searchCount = 0;
        this.amount = 0;




    }
    public boolean insert(Contribuinte contribuinte){
        int amountBefore = amount;
        root = insertRecursive(root, contribuinte.getCpf(), contribuinte);
        return amount > amountBefore;

    }

    private NodeBst insertRecursive(NodeBst actual, String cpf, Contribuinte contribuinte){
        if (actual == null){
            amount++;
            return new NodeBst(cpf, contribuinte );
        }

        int comparison = cpf.compareTo(actual.getKey());

        if( comparison < 0){
            actual.setLeft(insertRecursive(actual.getLeft(),cpf, contribuinte));
        } else if (comparison > 0) {
            actual.setRight(insertRecursive(actual.getRight(), cpf, contribuinte));

        }

        return actual;
        //Aviso: Não insere cpf igual
        //Falta implementar alguma mensagem para CPF iguais!


    }

    public List<Contribuinte> inOrder(){
        List<Contribuinte> lista = new ArrayList<>();
        inOrderTail(root, lista);
        return lista;

    }

    private void inOrderTail(NodeBst node, List<Contribuinte> lista){
        if (node == null) return;

        inOrderTail(node.getLeft(), lista);
        lista.add(node.getContribuinte());
        inOrderTail(node.getRight(), lista);

    }



    //Busca


    public Contribuinte search(String cpf) {
        searchCount = 0;
        return searchRecursive(root, cpf);

    }

    private Contribuinte searchRecursive(NodeBst actual, String cpf) {
        searchCount++;
        if (actual == null){
            return null;
        }

        int comparison = cpf.compareTo((actual.getKey()));
        if ( comparison == 0){
            return actual.getContribuinte();
        }else if (comparison < 0){
            return searchRecursive(actual.getLeft(), cpf);
        }else {
            return searchRecursive(actual.getRight(), cpf);

        }



    }

    //remoção


    public boolean delete(String cpf){

        int amountBefore = amount;
        root = deleteRecursive(root, cpf);

        return amountBefore > amount;

    }


    private NodeBst deleteRecursive(NodeBst actual, String cpf){
        if(actual == null){
            return null;
        }

        int comparison = cpf.compareTo(actual.getKey());

        if (comparison < 0){
            actual.setLeft(deleteRecursive(actual.getLeft(), cpf));

        } else if (comparison > 0) {
            actual.setRight(deleteRecursive(actual.getRight(), cpf));

        }else{
            amount--;

            if(actual.getLeft() == null) return actual.getRight();
            if(actual.getRight() == null) return  actual.getLeft();


            NodeBst successor = searchMinor(actual.getRight());

            actual.setKey(successor.getKey());
            actual.setContribuinte(successor.getContribuinte());
            actual.setRight(deleteSuccessor(actual.getRight(),successor.getKey()));

        }

        return  actual;


    }
    private NodeBst deleteSuccessor(NodeBst actual, String cpf){

        if (actual == null) return null;

        int comparison = cpf.compareTo(actual.getKey());

        if (comparison < 0){
            actual.setLeft(deleteSuccessor(actual.getLeft(), cpf));

        } else if (comparison > 0) {
            actual.setRight(deleteSuccessor(actual.getRight(), cpf));

        }else{

            return actual.getRight();


        }

        return actual;



    }


    private NodeBst searchMinor(NodeBst actualNode){
        while (actualNode.getLeft() != null){
            actualNode = actualNode.getLeft();
        }

        return actualNode;
    }

    public int bstHeight(){
        NodeBst node = root;

        return bstHeightRecursive(node);

    }

    private int bstHeightRecursive(NodeBst node){

        if(node == null) return -1;

        return 1 + Math.max(
                bstHeightRecursive(node.getLeft()),
                bstHeightRecursive(node.getRight())
        );
    }

    public int balanceFactor() {
        return bstHeightRecursive(root.getLeft()) - bstHeightRecursive(root.getRight());
    }

    public NodeBst getRoot() {
        return root;
    }


    public List<NodeBst> searchPath(String cpf) {
        searchCount = 0;
        List<NodeBst> path = new ArrayList<>();
        searchPathRecursive(root, cpf, path);
        return path;
    }

    private void searchPathRecursive(NodeBst actual, String cpf, List<NodeBst> path) {
        if (actual == null) return;
        searchCount++;
        path.add(actual);
        int comparison = cpf.compareTo(actual.getKey());
        if (comparison < 0) {
            searchPathRecursive(actual.getLeft(), cpf, path);
        } else if (comparison > 0) {
            searchPathRecursive(actual.getRight(), cpf, path);
        }
        // comparison == 0 → encontrou, para aqui
    }


    public int getSearchCount() {
        return searchCount;
    }

    public int getAmount() {
        return amount;
    }

    private void resetSearchCount(){

        searchCount = 0;

    }
}