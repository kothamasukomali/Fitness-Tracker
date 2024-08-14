import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// User class
class User {
    private String name;
    private int age;
    private double weight;
    private double height;
    private List<Workout> workouts = new ArrayList<>();
    private List<Diet> diets = new ArrayList<>();
    private Goal goal;

    public User(String name, int age, double weight, double height) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    public void updateProfile(String name, int age, double weight, double height) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public void addWorkout(Workout workout) {
        workouts.add(workout);
    }

    public void addDiet(Diet diet) {
        diets.add(diet);
    }

    public String viewProgress() {
        StringBuilder progress = new StringBuilder();
        progress.append("User: ").append(name).append("\n");
        progress.append("Workouts:\n");
        for (Workout workout : workouts) {
            progress.append(workout.getWorkoutDetails()).append("\n");
        }
        progress.append("Diets:\n");
        for (Diet diet : diets) {
            progress.append(diet.getNutritionalInfo()).append("\n");
        }
        if (goal != null) {
            progress.append("Goal: ").append(goal.checkProgress()).append("%\n");
        }
        return progress.toString();
    }
}

// Workout class
abstract class Workout {
    protected String type;
    protected int duration; // in minutes
    protected int intensity; // on a scale of 1-10

    public Workout(String type, int duration, int intensity) {
        this.type = type;
        this.duration = duration;
        this.intensity = intensity;
    }

    public abstract String getWorkoutDetails();
}

// CardioWorkout subclass
class CardioWorkout extends Workout {
    private int distance; // in kilometers

    public CardioWorkout(String type, int duration, int intensity, int distance) {
        super(type, duration, intensity);
        this.distance = distance;
    }

    @Override
    public String getWorkoutDetails() {
        return "Cardio workout: " + type + " for " + duration + " minutes at intensity " + intensity + " with distance " + distance + " km.";
    }
}

// StrengthTrainingWorkout subclass
class StrengthTrainingWorkout extends Workout {
    private int sets;
    private int reps;

    public StrengthTrainingWorkout(String type, int duration, int intensity, int sets, int reps) {
        super(type, duration, intensity);
        this.sets = sets;
        this.reps = reps;
    }

    @Override
    public String getWorkoutDetails() {
        return "Strength training: " + type + " for " + duration + " minutes at intensity " + intensity + " with " + sets + " sets and " + reps + " reps.";
    }
}

// Diet class
class Diet {
    private String mealType;
    private int calories;
    private Map<String, Double> nutrients;

    public Diet(String mealType, int calories, Map<String, Double> nutrients) {
        this.mealType = mealType;
        this.calories = calories;
        this.nutrients = nutrients;
    }

    public String getNutritionalInfo() {
        StringBuilder info = new StringBuilder();
        info.append(mealType).append(" - Calories: ").append(calories).append(", Nutrients: ");
        for (Map.Entry<String, Double> entry : nutrients.entrySet()) {
            info.append(entry.getKey()).append(": ").append(entry.getValue()).append(" ");
        }
        return info.toString();
    }
}

// Goal class
class Goal {
    private String goalType;
    private double targetValue;
    private double currentValue;

    public Goal(String goalType, double targetValue, double currentValue) {
        this.goalType = goalType;
        this.targetValue = targetValue;
        this.currentValue = currentValue;
    }

    public double checkProgress() {
        return (currentValue / targetValue) * 100; // Returns percentage of goal achieved
    }
}

// Main class to demonstrate the functionality
public class FitnessTrackerApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create a new user
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your age: ");
        int age = scanner.nextInt();
        System.out.print("Enter your weight (kg): ");
        double weight = scanner.nextDouble();
        System.out.print("Enter your height (cm): ");
        double height = scanner.nextDouble();
        scanner.nextLine();  // Consume newline left-over

        User user = new User(name, age, weight, height);

        // Set a goal
        System.out.print("Enter your goal type (e.g., Weight Loss): ");
        String goalType = scanner.nextLine();
        System.out.print("Enter your target value: ");
        double targetValue = scanner.nextDouble();
        System.out.print("Enter your current value: ");
        double currentValue = scanner.nextDouble();
        scanner.nextLine();  // Consume newline left-over
        Goal goal = new Goal(goalType, targetValue, currentValue);
        user.setGoal(goal);

        // Add workouts
        System.out.print("Enter workout type (Cardio/Strength): ");
        String workoutType = scanner.nextLine();
        System.out.print("Enter workout name: ");
        String type = scanner.nextLine();
        System.out.print("Enter duration (minutes): ");
        int duration = scanner.nextInt();
        System.out.print("Enter intensity (1-10): ");
        int intensity = scanner.nextInt();
        scanner.nextLine();  // Consume newline left-over

        if (workoutType.equalsIgnoreCase("Cardio")) {
            System.out.print("Enter distance (km): ");
            int distance = scanner.nextInt();
            scanner.nextLine();  // Consume newline left-over
            Workout cardio = new CardioWorkout(type, duration, intensity, distance);
            user.addWorkout(cardio);
        } else if (workoutType.equalsIgnoreCase("Strength")) {
            System.out.print("Enter sets: ");
            int sets = scanner.nextInt();
            System.out.print("Enter reps: ");
            int reps = scanner.nextInt();
            scanner.nextLine();  // Consume newline left-over
            Workout strength = new StrengthTrainingWorkout(type, duration, intensity, sets, reps);
            user.addWorkout(strength);
        }

        // Add diet
        System.out.print("Enter meal type: ");
        String mealType = scanner.nextLine();
        System.out.print("Enter calories: ");
        int calories = scanner.nextInt();
        scanner.nextLine();  // Consume newline left-over
        System.out.print("Enter number of nutrients: ");
        int numNutrients = scanner.nextInt();
        scanner.nextLine();  // Consume newline left-over

        Map<String, Double> nutrients = new HashMap<>();
        for (int i = 0; i < numNutrients; i++) {
            System.out.print("Enter nutrient name: ");
            String nutrientName = scanner.nextLine();
            System.out.print("Enter nutrient amount: ");
            double nutrientAmount = scanner.nextDouble();
            scanner.nextLine();  // Consume newline left-over
            nutrients.put(nutrientName, nutrientAmount);
        }
        Diet diet = new Diet(mealType, calories, nutrients);
        user.addDiet(diet);

        // View progress
        System.out.println(user.viewProgress());

        scanner.close();
    }
}