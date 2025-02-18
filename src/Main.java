import java.time.LocalDate;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        //načtení květin ze souboru
        PlantManager plantManager = new PlantManager();
        String fileName = Settings.getPlantsAllCorrect();
        try {
            plantManager.readFile(fileName);
        } catch (PlantException e) {
            System.err.println("An error has occured while reading " + fileName + ". " + e.getLocalizedMessage());
        }
        try {
            plantManager.writeToFile();
        } catch (Exception e) {
            System.err.println("An error has occured while writing into file  " + fileName + ". " + e.getLocalizedMessage());
        }

        //výpis informací o zílivkách květin
        ArrayList<Plant> plants = plantManager.getPlants();
        plants.forEach(plant->{
            System.out.println(plant.getWateringInfo());
        });
        System.out.println();

        //přidání nové květiny do seznamu
        plantManager.addPlant(new Plant("Orchidej", "křehká",
                              LocalDate.of(2025, 2,2),
                              LocalDate.of(2025, 2,14),
                              3));

        //přidání 10 tulipánů na prodej
        int TULIP_AMOUNT = 10;
        for (int i = 0; i < TULIP_AMOUNT; i++) {
            plantManager.addPlant(new Plant("Tulipán na prodej " + (i+1), "",
                    LocalDate.now(),
                    LocalDate.now(),
                    14));
        }
        //odebrání prodané květiny (třetí pozice = index 2)
        int SOLD_PLANT_INDEX = 2;
        plantManager.removePlant(SOLD_PLANT_INDEX);

        //uložení do souboru
        plantManager.writeToFile();

        //opetovné načtení vytvořenmého souboru
        try {
            plantManager.readFile(Settings.getOutputFile());
        } catch (PlantException e) {
            System.err.println("An error has occured while reading " + fileName + ". " + e.getLocalizedMessage());
        }

        //seřazení rostlin
        /// podle data poslední zálivky
        plantManager.sort(PlantManager.SORT_BY_DATE);
        plants = plantManager.getPlants();
        plants.forEach(plant->{
            System.out.println(plant.getWateringInfo());
        });

        System.out.println();

        /// podle názvu květiny
        plantManager.sort();
        plants = plantManager.getPlants();
        plants.forEach(plant->{
            System.out.println(plant.getWateringInfo());
        });
    }


}