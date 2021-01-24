package Strategy;



public class SaveFunctions {
    private Strategy strategy;

    public SaveFunctions(Strategy strategy) {
        this.strategy = strategy;
    }
    
    public String startSave(String filePath, String text){
        return strategy.save(filePath, text);
    }

}
