package breezeApp;

import javafx.scene.paint.Color;

public class Stitch {

    public final static String STITCHTYPE_SINGLECROCHET = "sc";
    public final static String STITCHTYPE_DOUBLECROCHET = "dc";
    public final static String STITCHTYPE_TREBLECROCHET = "tc";
    public final static String STITCHTYPE_HALFDOUBLECROCHET = "hdc";
    public final static String STITCHTYPE_SLIPSTITCH = "sl";
    public final static String STITCHTYPE_CHAIN = "ch";
    
    
    private Color color;
    private String type;
    private int rowIndex, colIndex;

    public Stitch(Color color, int rowIndex, int colIndex) {

        //by default, single crochet
        this.type = STITCHTYPE_SINGLECROCHET;
        
        this.color = color;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }
    
    

}
