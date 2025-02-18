import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class PlantManager {
    private ArrayList<Plant> plants;
    public static final int SORT_BY_DATE = 1;
    public static final int DEFAULT_SORT = 0;

    public PlantManager(){
        this.plants = new ArrayList<>();
    }
    public void addPlant(Plant plant){
        this.plants.add(plant);
    }

    public ArrayList<Plant> getPlants() {
        return new ArrayList<>(plants);
    }
    public Plant getPlant(int index){
        return plants.get(index);
    }
    public void removePlant(Plant plant){
        this.plants.remove(plant);
    }
    public void removePlant(int index){
        this.plants.remove(index);
    }
    public ArrayList<Plant> getUnwateredPlants(){
        ArrayList<Plant> unwateredPlants = new ArrayList<>();
        for (Plant plant:plants){
            if(ChronoUnit.DAYS.between(plant.getLastWatering(),LocalDate.now())>plant.getWateringFrequency()){
                unwateredPlants.add(plant);
            }
        }
        return unwateredPlants;
    }
    public void sort(int sortType){
        if (sortType == SORT_BY_DATE) {
            plants.sort(Comparator.comparing(Plant::getLastWatering));
        }else if(sortType == DEFAULT_SORT) {
            plants.sort(Comparator.comparing(Plant::getName));
        }
    }
    public void sort(){
        this.sort(DEFAULT_SORT);
    }

    public void readFile(String filename) throws PlantException {
        plants.clear();
        try(Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)))) {
            String line;
            while (scanner.hasNextLine()) {
                line =  scanner.nextLine();
                String[] data = line.split(Settings.getDelimiter());
                LocalDate lastWatering = LocalDate.parse(data[3]);
                LocalDate timePlanted = LocalDate.parse(data[4]);
                int wateringFrequency = Integer.parseInt(data[2]);
                this.addPlant(new Plant(data[0],data[1],timePlanted,lastWatering, wateringFrequency));
            }
        } catch (FileNotFoundException e) {
            throw new PlantException("Could not find the file: " + e.getMessage());
        } catch (DateTimeParseException e) {
            throw new PlantException("Unknown local date format: " + e.getMessage());
        }catch (NumberFormatException e){
            throw new PlantException("Unkown number format:  " + e.getMessage());
        }
    }
    public void writeToFile(){
        try (PrintWriter writer =
             new PrintWriter(
             new BufferedWriter(
             new FileWriter(Settings.getOutputFile())))
        ) {
            for (Plant plant : plants) {
                writer.println(plant);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
