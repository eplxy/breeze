package breezeApp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

public class ImageAdjustMenuController {

    @FXML
    Pane activePane, gridLayerPane;
    @FXML
    GridPane patternPreviewGridPane;
    @FXML
    ImageView imgView;
    Image snapshotImg;
    @FXML
    VBox leftVBox, rightVBox;
    @FXML
    Button btnChangeImage, btnAddGrid, btnCompute;

    public static ImageView currentImgView;
    public Grid grid;
    Rectangle selectedRectangle;
    Stage stage;
    Pattern currentPattern;

    final FileChooser fileChooser = new FileChooser();

    Rectangle[] customizableRectArr;

    public Color dominantColor;

    static Point2D rangePointTopLeft;
    static Point2D rangePointBottomRight;
    public Point2D[] rangePointArr = new Point2D[]{};

    public ImageAdjustMenuController(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        setupBtnCompute();
        setupBtnChangeImage();
        setupBtnAddGrid();

    }

    private void setupGrid() {
        if (!gridLayerPane.getChildren().isEmpty()) {
            gridLayerPane.getChildren().clear();
        }
        grid = new Grid(35, 35);

        this.gridLayerPane.getChildren().add(grid.getGridPane());

    }

    private void setupBtnChangeImage() {
        btnChangeImage.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                imgView.setImage(new Image(file.getPath()));
                currentImgView = imgView;
            }
        });
    }

    private void setupBtnAddGrid() {
        this.btnAddGrid.setOnAction(e -> {
            currentImgView = imgView;
            setupGrid();
        });
    }

    private void setupBtnCompute() {

        this.btnCompute.setOnAction(e -> {
            snapshotImg = imgView.snapshot(new SnapshotParameters(), null);
            grid.setCellBoundingPoints(grid.cellBoundingPoints());
            //dominantColor = computeDominantColorFromRange(rangePointTopLeft, rangePointBottomRight);
            //rectAvgColor.setFill(dominantColor);
            Color[][] colorMatrix = new Color[(int) grid.getDimensions().getY()][(int) grid.getDimensions().getX()];

            currentPattern = new Pattern((int) grid.getDimensions().getY(), (int) grid.getDimensions().getX());
            for (int i = 0; i < grid.getDimensions().getY(); i++) {
                for (int j = 0; j < grid.getDimensions().getX(); j++) {
                    Color currentColor = computeDominantColorFromRange(
                            grid.getCellBoundingPoints()[i][j][0],
                            grid.getCellBoundingPoints()[i][j][1]);
                    colorMatrix[i][j] = currentColor;
                }
            }
            currentPattern.populateMatrix(colorMatrix);
            displayPatternPreview(currentPattern);
        });
    }

    private void displayPatternPreview(Pattern pattern) {
        //cleaning
        this.patternPreviewGridPane.getColumnConstraints().clear();
        this.patternPreviewGridPane.getRowConstraints().clear();

        //getting sizing
        double previewWidth = this.patternPreviewGridPane.getWidth();
        double previewCellSize = previewWidth / pattern.getColCount();
        double previewHeight = previewCellSize * pattern.getRowCount();
        patternPreviewGridPane.setPrefWidth(previewHeight);

        //constraints
        for (int i = 0; i < pattern.getRowCount(); i++) {
            this.patternPreviewGridPane.getRowConstraints().add(new RowConstraints(previewCellSize));
        }
        for (int j = 0; j < pattern.getColCount(); j++) {
            this.patternPreviewGridPane.getColumnConstraints().add(new ColumnConstraints(previewCellSize));
        }

        //displaying colors
        for (int i = 0; i < pattern.getRowCount(); i++) {
            for (int j = 0; j < pattern.getColCount(); j++) {
                Pane currentPane = new Pane();
                currentPane.setBackground(new Background(new BackgroundFill(pattern.getPatternMatrix()[i][j].getColor(), CornerRadii.EMPTY, Insets.EMPTY)));
                patternPreviewGridPane.getChildren().add(currentPane);
                GridPane.setRowIndex(currentPane, i);
                GridPane.setColumnIndex(currentPane, j);
            }
        }
    }

//    private void updateImagePixelRange() {
//        rangePointTopLeft = new Point2D(imgView.getLayoutX(), imgView.getLayoutY());
//        rangePointBottomRight = new Point2D(imgView.getLayoutX() + imgView.getBoundsInLocal().getWidth(), imgView.getLayoutY() + imgView.getBoundsInLocal().getHeight());
//    }
//    private void setupCustomizableRectangles() {
//        for (int i = 0; i < customizableRectArr.length; i++) {
//            Rectangle rect = customizableRectArr[i];
//            rect.setOnMouseClicked(e -> {
//                selectRectangle(rect);
//            });
//            
//        }
//        
//    }
//    private void setupColorPicker() {
//        colorPicker.setOnAction(e -> {
//            selectedRectangle.setFill(colorPicker.getValue());
//        });
//    }
//    private void selectRectangle(Rectangle rect) {
//        selectedRectangle = rect;
//        selectedRectangle.setStrokeWidth(1);
//        for (int i = 0; i < customizableRectArr.length; i++) {
//            if (!customizableRectArr[i].equals(selectedRectangle)) {
//                customizableRectArr[i].setStrokeWidth(0);
//            }
//        }
//    }
    public Color computeDominantColorFromRange(Point2D pTopleft, Point2D pBottomRight) {
        ArrayList<Point2D> pointList = new ArrayList<>();
        for (int i = (int) pTopleft.getX(); i < pBottomRight.getX(); i++) {
            for (int j = (int) pTopleft.getY(); j < pBottomRight.getY(); j++) {

                pointList.add(new Point2D(i, j));
            }
        }
        return computeDominantColor(pointList);
    }

    //from a list of given pixels points
    public Color computeDominantColor(List<Point2D> pointList) {
        //Image screenAsImage = activePane.snapshot(new SnapshotParameters(), null);

        //Image screenAsImage = currentImgView.getImage();
        //PixelReader pr = screenAsImage.getPixelReader();
        PixelReader pr = snapshotImg.getPixelReader();

        double redSum = 0;
        double greenSum = 0;
        double blueSum = 0;
        int tempSize = pointList.size();

        for (Point2D p : pointList) {
            Color currentColor = null;
            try {
                currentColor = pr.getColor((int) p.getX(), (int) p.getY());
                redSum += currentColor.getRed();
                greenSum += currentColor.getGreen();
                blueSum += currentColor.getBlue();
            } catch (IndexOutOfBoundsException e) {
                tempSize--;
            }
            //Exclude White
            //if (!currentColor.equals(Color.WHITE)) {

            //}
        }

        return new Color((redSum / tempSize), (greenSum / tempSize), (blueSum / tempSize), 1);
    }

}
