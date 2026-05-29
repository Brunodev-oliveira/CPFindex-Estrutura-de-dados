package controller;
import model.*;

import java.util.List;


public class ReceitaController{

    private final BinarySearchTree tree;
    private final LinkedList linkedList;
    private final DataGeneratorControler dataGeneratorControler;


    public ReceitaController() throws Exception{
        this.tree = new BinarySearchTree();
        this.linkedList = new LinkedList();
        this.dataGeneratorControler = new DataGeneratorControler();

    }

    // Construtor para testes — não carrega o JSON
  /*  public ReceitaController(BinarySearchTree tree) {
        this.tree = tree;
        this.dataGeneratorControler = null;
    }
*/


    public void populateBstBase(int amount) {
        dataGeneratorControler.populateInitialBase(this.tree, amount);
    }
    public void populateLkdBase(int amount) {
        dataGeneratorControler.populateInitialBase(this.linkedList, amount);
    }


    public String registerCPF(String cpf, String name, String situation){



        if(!validateCPF(cpf)) return "CPF Inválido";
        Contribuinte contribuinte = new Contribuinte(cpf, name, situation);
        boolean inserted = tree.insert(contribuinte);


        return inserted ? "Contribuinte Cadastrado com sucesso" : "CPF Duplicado";


    }



   public Contribuinte searchCPF(String cpf){

        String cleanCPF = cpf.replaceAll("[^0-9]", "" );
        if (!validateCPF(cleanCPF)) return null;


        return tree.search(cleanCPF);

    }


    public boolean removeCPF(String cpf){
        String cleanCPF = cpf.replaceAll("[^0-9]", "");

        if (!validateCPF(cleanCPF)) return false;

        return tree.delete(cleanCPF);

    }

    public List<Contribuinte> listCPF(){ return tree.inOrder(); }

    private boolean validateCPF(String cpf){

        String cleanCPF = cpf.replaceAll("[^0-9]", "");
        return cleanCPF.length() == 11;

        //Falta implementar algoritmo de validação de dígitos verificadores
    }


    private String formatCPF(String cpf){
        String cleanCPF = cpf.replaceAll("[^0-9]", "");
        return String.format(
                "%s.%s.%s.%s",
                cpf.substring(0,3),
                cpf.substring(3,6),
                cpf.substring(6,9),
                cpf.substring(9,11));

    }

    public int getBstHeight(){
        return tree.bstHeight();
    }
    public int getBstNodeAmount(){
        return tree.getAmount();
    }
    public int getBstAmountLastSearch(){
        return tree.getSearchCount();
    }
    public int getBstBalanceFactor(){
        return tree.balanceFactor();
    }
    public boolean isBalanced(){
        return tree.balanceFactor() == 0;

    }

    // Métodos da linked List

    public int getListSearchCount(){
        return linkedList.getSearchCount();
    }
    public int getListAmount(){
        return linkedList.getAmount();
    }





    public Contribuinte searchCPFOnList(String cpf){

        String cleanCPF = cpf.replaceAll("[^0-9]", "" );
        if (!validateCPF(cleanCPF)) return null;


        return linkedList.search(cleanCPF);

    }



    // ── Busca comparativa (aba Comparação) ────────────────────────────────────

    private long bstSearchTimeNs;
    private long listSearchTimeNs;

    public Contribuinte compareBstSearch(String cpf) {
        String clean = cpf.replaceAll("[^0-9]", "");
        if (!validateCPF(clean)) return null;
        long start = System.nanoTime();
        Contribuinte result = tree.search(clean);
        bstSearchTimeNs = System.nanoTime() - start;
        return result;
    }

    public Contribuinte compareListSearch(String cpf) {
        String clean = cpf.replaceAll("[^0-9]", "");
        if (!validateCPF(clean)) return null;
        long start = System.nanoTime();
        Contribuinte result = linkedList.search(clean);
        listSearchTimeNs = System.nanoTime() - start;
        return result;
    }

    public int getCompareBstSearchCount()  { return tree.getSearchCount(); }
    public int getCompareListSearchCount() { return linkedList.getSearchCount(); }
    public long getBstSearchTimeNs()       { return bstSearchTimeNs; }
    public long getListSearchTimeNs()      { return listSearchTimeNs; }








}
