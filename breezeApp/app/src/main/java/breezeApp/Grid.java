package breezeApp;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class Grid {

    private Point2D dimensions, offset;
    private GridPane gridPane;
    private Point2D[][][] cellBoundingPoints;

    public Grid(int widthCellCount, int heightCellCount) {
        this.dimensions = new Point2D(widthCellCount, heightCellCount);
        this.gridPane = new GridPane();
        double imgWidth = ImageAdjustMenuController.currentImgView.getBoundsInLocal().getWidth();
        double imgHeight = ImageAdjustMenuController.currentImgView.getBoundsInLocal().getHeight();
        //chat gpt optimization answer lol
        double optCellSize = optCellSize(widthCellCount, heightCellCount, imgWidth, imgHeight) + 0;
        for (int i = 0; i < widthCellCount; i++) {

            ColumnConstraints currentColumn = new ColumnConstraints(optCellSize);
            this.gridPane.getColumnConstraints().add(currentColumn);
        }
        for (int i = 0; i < heightCellCount; i++) {

            RowConstraints currentRow = new RowConstraints(optCellSize);
            this.gridPane.getRowConstraints().add(currentRow);
        }
        this.gridPane.setGridLinesVisible(true);
        this.gridPane.setLayoutX(ImageAdjustMenuController.currentImgView.getLayoutX());
        this.gridPane.setLayoutY(ImageAdjustMenuController.currentImgView.getLayoutY());

    }

    private int optCellSize(int widthCellCount, int heightCellCount, double imgWidth, double imgHeight) {
        double initSolution = (widthCellCount * imgHeight + heightCellCount * imgWidth) / (2 * widthCellCount * heightCellCount);
        double solutionA = Integer.MAX_VALUE;
        double solutionB = Integer.MAX_VALUE;

        if (0 <= initSolution && initSolution <= Math.min(imgWidth / widthCellCount, imgHeight / heightCellCount)) {
            solutionA = initSolution;
        }
        if (0 <= Math.min(imgWidth / widthCellCount, imgHeight / heightCellCount)
                && Math.min(imgWidth / widthCellCount, imgHeight / heightCellCount) <= Math.min(imgWidth / widthCellCount, imgHeight / heightCellCount)) {
            solutionB = Math.min(imgWidth / widthCellCount, imgHeight / heightCellCount);
        }

        if (solutionA <= solutionB) {
            return (int) solutionA;
        } else {
            return (int) solutionB;
        }
    }

    public Point2D[][][] cellBoundingPoints() {
        Point2D[][][] boundingPoints = new Point2D[(int) this.dimensions.getY()][(int) this.dimensions.getX()][2];
        for (int i = 0; i < this.dimensions.getY(); i++) {
            for (int j = 0; j < this.dimensions.getX(); j++) {
                Bounds currentBounds = this.gridPane.getCellBounds(j, i);
                boundingPoints[i][j][0] = new Point2D(currentBounds.getMinX(), currentBounds.getMinY());
                boundingPoints[i][j][1] = new Point2D(currentBounds.getMaxX(), currentBounds.getMaxY());
            }
        }
        return boundingPoints;
    }

    public Point2D getDimensions() {
        return dimensions;
    }

    public void setDimensions(Point2D dimensions) {
        this.dimensions = dimensions;
    }

    public Point2D getOffset() {
        return offset;
    }

    public void setOffset(Point2D offset) {
        this.offset = offset;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public Point2D[][][] getCellBoundingPoints() {
        return cellBoundingPoints;
    }

    public void setCellBoundingPoints(Point2D[][][] cellBoundingPoints) {
        this.cellBoundingPoints = cellBoundingPoints;
    }

}
