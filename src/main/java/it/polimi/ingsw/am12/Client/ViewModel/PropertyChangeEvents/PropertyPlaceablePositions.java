package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;
import it.polimi.ingsw.am12.Utils.Coordinate;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;

/**
 * The player hase received a list of available positions for placing
 */
public class PropertyPlaceablePositions implements PropertyChange{

    private final List<Coordinate> availablePositions;
    private final static int CENTRE_GRID_ROW = 40;

    private final static int MUL_ROW = 3;
    private final static int MUL_COL = 4;
    private final static int CELL_DIM_ROW = 40;
    private final static int CELL_DIM_COL = 30;
    private final static int CENTRE_GRID_COL = 40;


    /**
     * Class constructor
     * @param availablePositions a list with the coordinates of the available positions for placing
     */
    public PropertyPlaceablePositions(List<Coordinate> availablePositions) {
        this.availablePositions = availablePositions;
    }

    /**
     * Update the CLI with the list of available positions
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        String s = "Available positions:";
        for(Coordinate c : availablePositions) {
            s+= " (" + c.getX() + ", " + c.getY() + ")";
        }
        System.out.println(s);
    }

    /**
     * Update the GUI with the list of available positions
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {
        Stage stage = gui.getStage();
        Scene scene = stage.getScene();
        GridPane grid = (GridPane) scene.lookup("#"+gui.getNickname());
        for(Coordinate c: availablePositions){

            int row = c.getX()*MUL_ROW-(c.getX()-CENTRE_GRID_ROW);
            int column = c.getY()*MUL_COL-(c.getY()-CENTRE_GRID_COL);

            for (int i = 0; i<MUL_ROW; i++) {
                for(int j = 0; j<MUL_COL; j++) {
                    Pane cell = new Pane();
                    cell.setMinSize(CELL_DIM_ROW, CELL_DIM_COL);
                    cell.setMaxSize(CELL_DIM_ROW, CELL_DIM_COL);
                    cell.setOnMouseClicked(event -> {
                                gui.setSelectedCoordinates(c.getX(),c.getY());
                                highlightBorder(grid, row, column);
                            }
                    );
                    cell.getStyleClass().add("red-highlight");
                    cell.getStyleClass().add("no-border");
                    grid.add(cell, column+j, row+i);
                }
            }
        }
    }

    /**
     * Highlight the selected placeable position with a border
     * @param grid the playing grid
     * @param row the row of the top-left cell of the selected positions
     * @param col the column of the top-left cell of the selected position
     */
    private void highlightBorder(GridPane grid, int row, int col) {
        for(Node node : grid.getChildren()) {
            int x = GridPane.getRowIndex(node);
            int y = GridPane.getColumnIndex(node);

            if(node.getStyleClass().size() > 1) {
                node.getStyleClass().remove(1);
            }

            if(x==row && y==col) {
                node.getStyleClass().add("top-left-border");
            }
            else if (x==row && y>col && y<col+MUL_COL-1) {
                node.getStyleClass().add("top-border");
            }
            else if (x==row && y==col+MUL_COL-1) {
                node.getStyleClass().add("top-right-border");
            } else if (x>row && x<row+MUL_ROW-1 && y==col) {
                node.getStyleClass().add("left-border");
            } else if (x>row && x<row+MUL_ROW-1 && y==col+MUL_COL-1) {
                node.getStyleClass().add("right-border");
            } else if(x==row+MUL_ROW-1 && y==col) {
                node.getStyleClass().add("bottom-left-border");
            }
            else if (x==row+MUL_ROW-1 && y>col && y<col+MUL_COL-1) {
                node.getStyleClass().add("bottom-border");
            }
            else if (x==row+MUL_ROW-1 && y==col+MUL_COL-1) {
                node.getStyleClass().add("bottom-right-border");
            }
            else {
                node.getStyleClass().add("no-border");
            }
        }
    }
}
