import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class PlantManager {
    private ArrayList<Plant> plants;

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

    public void sort(PlantSortType sortType){
        if (sortType.equals(PlantSortType.SORT_BY_DATE)) {
            plants.sort(Comparator.comparing(Plant::getLastWatering));
        }else if(sortType.equals(PlantSortType.DEFAULT_SORT)) {
            plants.sort(Comparator.comparing(Plant::getName));
        }
    }

    public void sort(){
        this.sort(PlantSortType.DEFAULT_SORT);
    }

    public void readFile(String filename) throws PlantException {
        ArrayList<Plant> newPlants = new ArrayList<>();
        try(Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)))) {
            String line;
            while (scanner.hasNextLine()) {
                line =  scanner.nextLine();

                String[] data = line.split(Settings.getDelimiter());
                LocalDate lastWatering = LocalDate.parse(data[3]);
                LocalDate timePlanted = LocalDate.parse(data[4]);
                int wateringFrequency = Integer.parseInt(data[2]);

                newPlants.add(new Plant(data[0],data[1],timePlanted,lastWatering, wateringFrequency));
            }
            this.plants = newPlants;
        } catch (FileNotFoundException e) {
            throw new PlantException("Could not find the file: " + e.getMessage());
        } catch (DateTimeParseException e) {
            throw new PlantException("Unknown local date format: " + e.getMessage());
        } catch (NumberFormatException e){
            throw new PlantException("Unkown number format:  " + e.getMessage());
        }
    }

    public void writeToFile(String outputFile) {
        try (PrintWriter writer =
             new PrintWriter(
             new BufferedWriter(
             new FileWriter(outputFile)))
        ) {
            for (Plant plant : plants) {
                writer.println(plant);
            }
        } catch (Exception e) {
            System.err.println("An error has occured while writing into file " +
                               outputFile + ". " + e.getLocalizedMessage());
        }
    }
}
