package controller;
import model.*;
import view.*;

public class ReceitaController{

    private BinarySearchTree tree;
    private MenuView menuView;
    private DataGeneratorControler dataGeneratorControler;


    public ReceitaController(){
        this.tree = new BinarySearchTree();
        this.menuView = new MenuView();
        try {
            this.dataGeneratorControler = new DataGeneratorControler();
        }catch (Exception error){
             menuView.showError("Não foi possivel gerar base inicial(erro no Arquvo base JSON!" + error);
        }
    }


    public void start(){
        boolean run = true;

        menuView.showMessage("Gerando dados iniciais na base");
        dataGeneratorControler.populateInitialBase(this.tree, 1000000);
        menuView.showSucces("Base de dados carregada com sucesso!");


    while (run){
        try {
            menuView.mainMenu();
            int option = menuView.readInt("");

            switch (option){

                case 1:
                    registerCPF();
                    break;
                case 2:
                    searchCPF();
                    break;
                case 3:
                    removeCPF();
                    break;
                case 4:
                    listCPF();
                    break;
                case 0:
                    run = false;
                    menuView.showMessage("Encerrando sistema...");
                    break;
                default:
                    menuView.showError("Opção inválida!");

            }
            if (option != 0){
                menuView.waitEnter();
            }
        }catch (Exception e){
            menuView.showError("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    }// fim start

    public  void registerCPF(){
        menuView.showMessage("\n---CADASTRAR NOVO CPF--- ");

        String cpf = menuView.readString("cpf: aprenas números");

        //validação de cpf


        if(!validateCPF(cpf)){
            menuView.showError("CPF Inválido!");

            return;
        }

        if (tree.search(cpf) != null){
            menuView.showError("CPF já cadastrado");
            return;
        }

        String name = menuView.readString("Nome completo");
        String situation = menuView.readString("Situação: ");

        Contribuinte contribuinte = new Contribuinte(cpf, name, situation);


        tree.insert(contribuinte);

        menuView.showSucces("CPF cadastrado com sucesso!");


    }

   public void searchCPF(){
        menuView.showMessage("\n----CONSULTA DE CPF----");
        String cpf = menuView.readString("Digite o cpf(apenas números):");

        if (!validateCPF(cpf)){
            menuView.showError("CPF inválido!");

        }

        Contribuinte result = tree.search(cpf);

        if (result != null){
            menuView.showSucces("cpf encontrado: " + result.getCpfFormatted());
            menuView.showMessage("Nome do Contribuinte: " + result.getNome());
            menuView.showMessage("Situação cadastral: " + result.getSituation());


        }else {
            menuView.showError("CPF não encontrado a base de dados!");
        }

    }
    public void removeCPF(){
        menuView.showMessage("\n----REMOVÇÃO DE CPF----");
        String cpf = menuView.readString("Digite o CPF para remoção:");

        if (!validateCPF(cpf)){
            menuView.showError("CPF inválido!");
            return;
        }

        boolean result = tree.delete(cpf);

        if (result){
            menuView.showSucces("CPF removido com sucesso!");
        }else{
            menuView.showError("CPF não encontrado na base de dados para a remoção!");
        }

    }

    public void listCPF(){
        menuView.showMessage("\n----Listar cpf ----");


        //Falta implementar métodos de listagem em relatório


    }

    private boolean validateCPF(String cpf){

        String cleanCPF = cpf.replaceAll("[^0-9]", "");
        if(cleanCPF.length() != 11) return false;

        //Falta implementar algoritmo de validação de dígitos verificadores

        return true;
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






}
