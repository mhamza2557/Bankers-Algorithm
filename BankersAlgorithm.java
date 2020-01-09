import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BankersAlgorithm extends Application {
    //First Row
    private Label lInitializeValue = new Label("InitializeValue: ");
    private TextField tfInitializeValueP0 = new TextField();
    private TextField tfInitializeValueP1 = new TextField();
    private TextField tfInitializeValueP2 = new TextField();
    private TextField tfInitializeValueP3 = new TextField();
    private TextField tfInitializeValueP4 = new TextField();
    //Second Row
    private Label lMaxValue = new Label("MAX: ");
    private TextField tfMaxValueP0 = new TextField();
    private TextField tfMaxValueP1 = new TextField();
    private TextField tfMaxValueP2 = new TextField();
    private TextField tfMaxValueP3 = new TextField();
    private TextField tfMaxValueP4 = new TextField();
    //Third Row
    private Label lAvailableResources = new Label("Available Resources: ");
    private TextField tfAvailableResources = new TextField();
    private Button btCalculate = new Button("Calculate");
    private TextField tfAnswer = new TextField("Answer Generate: ");
    //Stage Create
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane, 700, 430);
        //TextField "Answer" Some Property Set
        tfAnswer.setEditable(false);
        tfAnswer.setMinWidth(300);
        //GridPane Set Horizontal, Vertical Gap and Padding
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        //First Row Allocate
        gridPane.add(lInitializeValue, 0, 0);
        gridPane.add(tfInitializeValueP0, 0, 1);
        gridPane.add(tfInitializeValueP1, 0, 2);
        gridPane.add(tfInitializeValueP2, 0, 3);
        gridPane.add(tfInitializeValueP3, 0, 4);
        gridPane.add(tfInitializeValueP4, 0, 5);
        //Second Row Allocate
        gridPane.add(lMaxValue, 1, 0);
        gridPane.add(tfMaxValueP0, 1, 1);
        gridPane.add(tfMaxValueP1, 1, 2);
        gridPane.add(tfMaxValueP2, 1, 3);
        gridPane.add(tfMaxValueP3, 1, 4);
        gridPane.add(tfMaxValueP4, 1, 5);
        //Third Row Allocate
        gridPane.add(lAvailableResources, 2, 0);
        gridPane.add(tfAvailableResources, 2, 1);
        gridPane.add(btCalculate, 2, 2);
        gridPane.add(tfAnswer, 2, 3);
        //Set Button Acton When Clicked/Touched
        btCalculate.setOnAction(e -> setBtCalculate());

        primaryStage.setTitle("Bankers Algorithms");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    int numberOfProcess = 5;
    int maximumNumbersOfResources = 3;
    int [][] need = new int [numberOfProcess][maximumNumbersOfResources];
    int [][] max;
    int [][] allocation;
    int [] availableResources;
    int [] safeSequence = new int[numberOfProcess];

    private void setBtCalculate() {
        initializeValue();
        needMatrixCalculation();
        inSafeSequence();
    }

    private int separateNumbersToArray(TextField textField, int a) {
        String textFieldText = textField.getText();
        String[] items = textFieldText.split(",");
        int[] results = new int[items.length];

        for (int i = 0; i < items.length; i++)
            results[i] = Integer.parseInt(items[i]);

        return results[a];
    }

    private void initializeValue() {
        allocation = new int [][] {
                {separateNumbersToArray(tfInitializeValueP0,0), separateNumbersToArray(tfInitializeValueP0,1), separateNumbersToArray(tfInitializeValueP0,2)}, //p0
                {separateNumbersToArray(tfInitializeValueP1,0), separateNumbersToArray(tfInitializeValueP1,1), separateNumbersToArray(tfInitializeValueP1,2)}, //p1
                {separateNumbersToArray(tfInitializeValueP2,0), separateNumbersToArray(tfInitializeValueP2,1), separateNumbersToArray(tfInitializeValueP2,2)}, //p2
                {separateNumbersToArray(tfInitializeValueP3,0), separateNumbersToArray(tfInitializeValueP3,1), separateNumbersToArray(tfInitializeValueP3,2)}, //p3
                {separateNumbersToArray(tfInitializeValueP4,0), separateNumbersToArray(tfInitializeValueP4,1), separateNumbersToArray(tfInitializeValueP4,2)}  //p4
        };

        max = new int [][] {
                {separateNumbersToArray(tfMaxValueP0,0), separateNumbersToArray(tfMaxValueP0,1), separateNumbersToArray(tfMaxValueP0,2)}, //p0
                {separateNumbersToArray(tfMaxValueP1,0), separateNumbersToArray(tfMaxValueP1,1), separateNumbersToArray(tfMaxValueP1,2)}, //p1
                {separateNumbersToArray(tfMaxValueP2,0), separateNumbersToArray(tfMaxValueP2,1), separateNumbersToArray(tfMaxValueP2,2)}, //p2
                {separateNumbersToArray(tfMaxValueP3,0), separateNumbersToArray(tfMaxValueP3,1), separateNumbersToArray(tfMaxValueP3,2)}, //p3
                {separateNumbersToArray(tfMaxValueP4,0), separateNumbersToArray(tfMaxValueP4,1), separateNumbersToArray(tfMaxValueP4,2)}  //p4
        };

        availableResources = new int [] {
                separateNumbersToArray(tfAvailableResources,0),
                separateNumbersToArray(tfAvailableResources,1),
                separateNumbersToArray(tfAvailableResources,2)
        };
    }
	
    private void inSafeSequence() {
        StringBuilder seqString = new StringBuilder();
		
        int count = 0;
        boolean[] visited = new boolean [numberOfProcess];

        for (int i = 0 ; i < numberOfProcess ;i++) { visited[i] = false; }

        int[] work = new int [maximumNumbersOfResources];

        for (int i = 0 ; i < maximumNumbersOfResources; i++){
            work[i] = availableResources[i];
        }

        while (count < numberOfProcess) {
            boolean flag = false;

            for (int i = 0; i < numberOfProcess; i++) {
                if (!visited[i]) {
                    int j;
                    for (j = 0; j < maximumNumbersOfResources; j++) {
                        if (need[i][j] > work[j])
                            break;
                    }

                    if (j == maximumNumbersOfResources) {
                        safeSequence[count++] = i;
                        visited[i] = true;
                        flag = true;

                        for (j = 0; j < maximumNumbersOfResources; j++) {
                            work[j] = work[j]+allocation[i][j];
                        }
                    }
                }
            }

            if (!flag) { break; }
        }

        if (count < numberOfProcess) {
            System.out.println("The System is UnSafe!");
        } else {
            System.out.println("Following is the Safe Sequence");

            for (int i = 0;i < numberOfProcess; i++) {
                System.out.print("P" + safeSequence[i]);
                seqString.append("P").append(safeSequence[i]);

                if (i != numberOfProcess-1) {
                    System.out.print(" -> ");
                    seqString.append(" -> ");
                }
            }
        }
        tfAnswer.setText(seqString.toString());
    }

    private void needMatrixCalculation(){
        for (int i = 0; i < numberOfProcess ; i++ ) {
            for (int j = 0; j < maximumNumbersOfResources ; j++ ){
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }
    }
}