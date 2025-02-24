import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Plant {
    private String name;
    private String notes;
    private LocalDate timePlanted;
    private LocalDate lastWatering;
    private int wateringFrequency;
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("d.M.yyy");

    public Plant(String name, String notes, LocalDate timePlanted, LocalDate lastWatering, int wateringFrequency) throws PlantException {
        this.name = name;
        this.notes = notes;
        this.timePlanted = timePlanted;
        this.lastWatering = lastWatering;
        setWateringFrequency(wateringFrequency);
        this.wateringFrequency = wateringFrequency;
    }

    public Plant(String name, int wateringFrequency) throws PlantException {
        this(name, "", LocalDate.now(),LocalDate.now(), wateringFrequency);
    }

    public Plant(String name) throws PlantException {
        this(name, 7);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getTimePlanted() {
        return timePlanted;
    }

    public void setTimePlanted(LocalDate timePlanted) {
        this.timePlanted = timePlanted;
    }

    public LocalDate getLastWatering() {
        return lastWatering;
    }

    public void setLastWatering(LocalDate lastWatering)throws PlantException {
        if(lastWatering.isBefore(this.lastWatering)) {
            throw new PlantException("new watering cannot happen before the last one");
        }else {
            this.lastWatering = lastWatering;
        }
    }

    public int getWateringFrequency() {
        return wateringFrequency;
    }

    public void setWateringFrequency(int wateringFrequency) throws PlantException {
        if(wateringFrequency<=0){
               throw new PlantException("Watering frequency cannot be lesser or equal to 0");
        }else {
            this.wateringFrequency = wateringFrequency;
        }
    }

    public String getWateringInfo(){
        LocalDate temp = LocalDate.of(lastWatering.getYear(),lastWatering.getMonth().getValue(), lastWatering.getDayOfMonth())
                        .plusDays(wateringFrequency);
        return name + ", " + lastWatering.format(DATE_FORMAT) + ", " + temp.format(DATE_FORMAT);
    }

    public void doWateringNow(){
        this.lastWatering = LocalDate.now();
    }

    @Override
    public String toString() {
        return name + "\t" +
               notes + "\t" +
               wateringFrequency + "\t" +
               lastWatering.format(DateTimeFormatter.ISO_LOCAL_DATE) + "\t"  +
               timePlanted.format(DateTimeFormatter.ISO_LOCAL_DATE);

    }
}
