package breezeApp;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Pattern {

    private int rowCount, colCount;
    private Stitch[][] patternMatrix;

    public Pattern(int rowCount, int colCount) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.patternMatrix = new Stitch[rowCount][colCount];
    }

    public void populateMatrix(Color[][] colorMatrix) {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                patternMatrix[i][j] = new Stitch(colorMatrix[i][j], i, j);
            }
        }
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getColCount() {
        return colCount;
    }

    public void setColCount(int colCount) {
        this.colCount = colCount;
    }

    public Stitch[][] getPatternMatrix() {
        return patternMatrix;
    }

    public void setPatternMatrix(Stitch[][] patternMatrix) {
        this.patternMatrix = patternMatrix;
    }

    
    
}
